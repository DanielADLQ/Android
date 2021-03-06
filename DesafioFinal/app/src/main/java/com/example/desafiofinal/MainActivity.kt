package com.example.desafiofinal

import Auxiliar.InfoLogin
import Modelo.Usuario
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.ejemplofirebase1.ProviderType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 1

    private val db = Firebase.firestore
    private val TAG = "Daniel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Con esto lanzamos eventos personalizados a GoogleAnalytics que podemos ver en nuestra consola de FireBase.
        val analy: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","Integración completada")
        analy.logEvent("InitScreen",bundle)

        title = "Autenticación"

        btRegistrar.setOnClickListener(){
            if (edEmail.text.isNotEmpty() && edPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(edEmail.text.toString(),edPass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        crearNuevoUsuario()
                        irHome(it.result?.user?.email?:"", ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email.
                    } else {
                        showAlert()
                    }
                }
            }
        }

        btLogin.setOnClickListener(){

            if (edEmail.text.isNotEmpty() && edPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(edEmail.text.toString(),edPass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        irHome(it.result?.user?.email?:"",ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email.
                    } else {
                        showAlert()
                    }
                }
            }
        }

        btGoogle.setOnClickListener {
            //Configuración
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.request_id_token)) //Esto se encuentra en el archivo google-services.json: client->oauth_client -> client_id
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this,googleConf) //Este será el cliente de autenticación de Google.
            googleClient.signOut() //Con esto salimos de la posible cuenta  de Google que se encuentre logueada.
            val signInIntent = googleClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        session()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Si la respuesta de esta activity se corresponde con la inicializada es que viene de la autenticación de Google.
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                //Log.d("Fernando", "firebaseAuthWithGoogle:" + account.id)
                //Ya tenemos la id de la cuenta. Ahora nos autenticamos con FireBase.
                if (account != null) {
                    val credential: AuthCredential =
                        GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                irHome(
                                    account.email ?: "",
                                    ProviderType.GOOGLE
                                )  //Esto de los interrogantes es por si está vacío el email.
                            } else {
                                showAlert()
                            }
                        }
                }
                //firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                showAlert()
            }
        }
    }

    private fun session(){
        val prefs: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE) //Aquí no invocamos al edit, es solo para comprobar si tenemos datos en sesión.
        val email:String? = prefs.getString("email",null)
        val provider:String? = prefs.getString("provider", null)
        if (email != null){
            //Tenemos iniciada la sesión.
            //irHome(email, ProviderType.valueOf(provider))
            irHome(email, ProviderType.BASIC)
        }
    }

    //*********************************************************************************
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuairo")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //*********************************************************************************
    private fun irHome(email:String, provider:ProviderType){

        //Almaceno email en object para uso posterior (Identificador del usuario)


        //InfoLogin.usu = Usuario(edEmail.text.toString(),)

        runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

        if(InfoLogin.usu.activado){

            if(InfoLogin.usu.admin){

                val intentV1 = Intent(this, VentanaEntradaAdmin::class.java).apply {
                    putExtra("email",email)
                    putExtra("provider",provider.name)
                }
                startActivity(intentV1)

            }else{ //Usuario normal

                val intentV1 = Intent(this, VentanaUsuario::class.java).apply {
                    putExtra("email",email)
                    putExtra("provider",provider.name)
                }
                startActivity(intentV1)

            }

        }else{ //Pendiente de activacion
            val intentV1 = Intent(this, VentanaNoActivado::class.java).apply {
                putExtra("email",email)
                putExtra("provider",provider.name)
            }
            startActivity(intentV1)
        }

    }

    fun crearNuevoUsuario(){

        var usuario = hashMapOf(
            "correo" to edEmail.text.toString(),
            "nombre" to "",
            "origen" to "",
            "admin" to false,
            "activado" to false
        )

        //Añade el evento nuevo
        db.collection("usuarios")
            .document(edEmail.text.toString())  //Si hubiera un campo duplicado, lo reemplaza.
            .set(usuario)
            .addOnSuccessListener {
                Log.e(TAG, "Usuario añadido.")

            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error al añadir el documento", e.cause)
            }

    }

    /**
     * Este método es una función suspend que esperará a que la consulta se realiza. Será llamada
     * en un scope (entorno) de corrutinas. Hilos (ver onCreate, runBlocking).
     */
    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("usuarios")
                .whereEqualTo("correo", edEmail.text.toString())
                //.whereGreaterThanOrEqualTo("age",40)  //https://firebase.google.com/docs/firestore/query-data/order-limit-data?hl=es-419
                //.orderBy("age", Query.Direction.DESCENDING)
                //.limit(4) //Limita la cantidad de elementos mostrados.
                .get()
                .await()
            data
        }catch (e : Exception){
            null
        }
    }


    /**
     * Función que recuperará los datos obtenidos del método: getDataFromFireStore().
     * Llamada también desde el entorno de corrutinas: (ver onCreate, runBlocking).
     */
    private fun obtenerDatos(datos: QuerySnapshot?) {
        for(dc: DocumentChange in datos?.documentChanges!!){
            if (dc.type == DocumentChange.Type.ADDED){
                //miAr.add(dc.document.toObject(User::class.java))

                    //Almacenar usuario logueado en object
                InfoLogin.usu = Usuario(
                    dc.document.get("correo").toString(),
                    dc.document.get("nombre").toString(),
                    dc.document.get("origen").toString(),
                    dc.document.get("admin").toString().toBoolean(),
                    dc.document.get("activado").toString().toBoolean(),
                )
                //Log.e(TAG, al.toString())

            }
        }
    }

}