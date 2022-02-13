package com.example.desafiofinal

import Auxiliar.InfoLogin
import Modelo.Usuario
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_perfil_usuario.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class PerfilUsuario : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "Daniel"
    var miArray:ArrayList<Usuario> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        var txtCor: TextView = findViewById(R.id.txtCorreoPerfil)
        var txtNom: TextView = findViewById(R.id.txtNombrePerfil)
        var txtOri:TextView = findViewById(R.id.txtOrigenPerfil)
        var btnDejAdmin:Button = findViewById(R.id.btnDejarAdmin)

        txtCor.text = InfoLogin.usu.correo
        txtNom.text = InfoLogin.usu.nombre
        txtOri.text = InfoLogin.usu.origen

        if(!InfoLogin.usu.admin) {
            btnDejAdmin.isVisible = false
        }

        runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

    }

    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("usuarios")
                //.whereEqualTo("age", 41)
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

                var al = Usuario(
                    dc.document.get("correo").toString(),
                    dc.document.get("nombre").toString(),
                    dc.document.get("origen").toString(),
                    dc.document.get("admin").toString().toBoolean(),
                    dc.document.get("activado").toString().toBoolean()

                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }
    }

    fun modificarDatos(view:View){

        var txtNom: TextView = findViewById(R.id.txtNombrePerfil)
        var txtOri:TextView = findViewById(R.id.txtOrigenPerfil)

        if(txtNom.text.trim().toString() != ""){
            db.collection("usuarios").document(InfoLogin.usu.correo).update("nombre",txtNom.text.trim().toString())
            InfoLogin.usu.nombre = txtNom.text.trim().toString()
        }
        if(txtOri.text.trim().toString() != ""){
            db.collection("usuarios").document(InfoLogin.usu.correo).update("origen",txtOri.text.trim().toString())
            InfoLogin.usu.origen = txtOri.text.trim().toString()
        }

        finish()

    }

    fun dejarAdmin(view: View){
        var numAdmin = 0
        for(usua in miArray){
            if(usua.admin){
                numAdmin++
            }
        }
        if(numAdmin>1){

            val dialogo: AlertDialog.Builder = AlertDialog.Builder(this)
            dialogo.setPositiveButton("Confirmar",
                DialogInterface.OnClickListener { dialog, which ->

                    db.collection("usuarios").document(InfoLogin.usu.correo)
                        .update("admin",false)
                        .addOnSuccessListener { Log.d(TAG,"Ya no eres administrador") }
                        .addOnFailureListener {e -> Log.w(TAG,"Error",e)}

                    InfoLogin.usu.admin=false

                    val intentV1 = Intent(this, MainActivity::class.java)
                    startActivity(intentV1)
                    finish()

                })
            dialogo.setNegativeButton(
                "Cancelar",
                DialogInterface.OnClickListener { dialog, which ->
                    //Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()

                    dialog.dismiss()
                })
            dialogo.setTitle("DEJAR DE SER ADMIN")
            dialogo.setMessage("¿Desea dejar de ser admin?")
            dialogo.show()

            true
        }else{
            Toast.makeText(this, "Eres el unico administrador. Debe haber al menos otro para poder dejar de ser administrador", Toast.LENGTH_LONG).show()
        }
    }

    fun cancelar(view: View){
        finish()
    }

}