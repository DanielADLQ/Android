package com.example.desafiofinal

import Adaptadores.MiAdaptadorRecyclerFotos
import Auxiliar.InfoLogin
import Modelo.Evento
import Modelo.Usuario
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class VentanaFotos : AppCompatActivity() {

    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private val TAG = "Daniel"
    var miArray:ArrayList<Usuario> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.

    var listaFotos: ArrayList<StorageReference> = ArrayList()

    private val PICK_IMAGE_REQUEST:Int = 71

    var seleccionado:Int=0
    lateinit var miRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_fotos)


        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PICK_IMAGE_REQUEST)
        }

        runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

        runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                obtenerListaFotos()  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

        miRecyclerView = findViewById(R.id.rvFotos) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        //var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerView.adapter = MiAdaptadorRecyclerFotos(listaFotos,this)

    }

    fun volver(view: View){
        var ev: Evento = intent.getSerializableExtra("ev") as Evento

        var intentV1 = Intent(this, InfoEventoSeleccionado::class.java)
        intentV1.putExtra("ev",ev)
        startActivity(intentV1)
        finish()
    }

    fun subirNuevaFoto(foto:Bitmap){

        var ev: Evento = intent.getSerializableExtra("ev") as Evento
        var txtTituloFoto:TextView = findViewById(R.id.txtTituloFoto)


        val storageRef = storage.reference
        val fotoRef = storageRef.child("Fotos/"+ev.id+"/"+InfoLogin.usu.correo+"/"+txtTituloFoto.text.toString()+".jpg")

        val fotoRutaRef = storageRef.child("Fotos/"+ev.id+"/"+InfoLogin.usu.correo+"/"+txtTituloFoto.text.toString()+".jpg")

        fotoRef.name == fotoRutaRef.name
        fotoRef.path == fotoRutaRef.path

        var baos = ByteArrayOutputStream()
        foto.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()
        var uploadTask = fotoRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(this,"ERROR al subir la imagen", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            Toast.makeText(this,"Imagen subida", Toast.LENGTH_LONG).show()
            txtTituloFoto.text=""
        }

    }

    fun cargarFoto(view:View){
        var txtTituloFoto:TextView = findViewById(R.id.txtTituloFoto)
        if(txtTituloFoto.text.trim().isNotEmpty()){

            var intentV1:Intent = Intent()
            intentV1.setType("image/*")
            intentV1.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intentV1, "Select Picture"), PICK_IMAGE_REQUEST)

        }else{
            Toast.makeText(this,"Pon un titulo a la foto", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null){
                val foto: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,data.data)
                subirNuevaFoto(foto)
            }
        }catch(e: Exception){
            Log.e("Daniel",e.toString())
        }
    }

    fun obtenerListaFotos(){

        var ev: Evento = intent.getSerializableExtra("ev") as Evento

        val storageRef = storage.reference
        val folderRef = storageRef.child("/Fotos/"+ev.id)

        val listAllTask: Task<ListResult> = folderRef.listAll()
        listAllTask.addOnCompleteListener { result ->
            // should check for errors here first
            val prefixes: List<StorageReference> = result.result.prefixes
            for (pref in prefixes) {
                //txtTituloFoto.text = pref.path
                //Toast.makeText(this, pref.name+" "+pref.path,Toast.LENGTH_LONG).show()   // returns another Task
                Log.e("FOTOS",pref.name+" "+pref.path)

                //Con otra llamada a listAll se puede recoger el contenido de cada carpeta
                val imagesRef = storageRef.child(pref.path)
                val listAllImagesTask: Task<ListResult> = imagesRef.listAll()
                listAllImagesTask.addOnCompleteListener { result ->
                    // should check for errors here first
                    val photos: List<StorageReference> = result.result.items
                    for (photo in photos) {
                        listaFotos.add(photo)
                        Log.e("FOTOS",photo.parent!!.name)
                    }

                }
            }
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
        }catch (e : java.lang.Exception){
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

}