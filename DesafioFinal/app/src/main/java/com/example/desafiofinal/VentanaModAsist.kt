package com.example.desafiofinal

import Adaptadores.MiAdaptadorRecyclerAsis
import Adaptadores.MiAdaptadorRecyclerUsuAsis
import Modelo.Evento
import Modelo.Usuario
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class VentanaModAsist : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "Daniel"

    lateinit var miRecyclerView : RecyclerView

    lateinit var miRecyclerViewUsu : RecyclerView

    var miArray:ArrayList<Usuario> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_mod_asist)

        var ev: Evento = intent.getSerializableExtra("ev") as Evento

        var seleccionado:Int=0

        Log.e(TAG,ev.asistentes.size.toString())

        var miAdapter = MiAdaptadorRecyclerAsis(ev.asistentes, this, ev)
        miRecyclerView = findViewById(R.id.rvAsistentes) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        //var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerView.adapter = miAdapter

        //----------------------------------------------------------------


        runBlocking {
            val job : Job = launch(context = Dispatchers.Default) {
                val datos : QuerySnapshot = getDataFromFireStore() as QuerySnapshot //Obtenermos la colección
                obtenerDatos(datos as QuerySnapshot?)  //'Destripamos' la colección y la metemos en nuestro ArrayList
            }
            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
            job.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
        }

        Log.e(TAG,"----------------")
        for(e in miArray){
            Log.e(TAG,e.toString())
        }
        Log.e(TAG,"----------------")
        //Aquí se pondría en el setAdapter del RecyclerView.


        var seleccionadoUsu:Int=0

        Log.e(TAG,ev.asistentes.size.toString())

        var miAdapterUsu = MiAdaptadorRecyclerUsuAsis(miArray, this, ev)
        miRecyclerViewUsu = findViewById(R.id.rvUsuAsis) as RecyclerView
        miRecyclerViewUsu.setHasFixedSize(true)
        miRecyclerViewUsu.layoutManager = LinearLayoutManager(this)
        //var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerViewUsu.adapter = miAdapterUsu

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



    fun volver(view:View){
        var intentV1 = Intent(this,EditarEventos::class.java)
        startActivity(intentV1)
        finish()
    }
}