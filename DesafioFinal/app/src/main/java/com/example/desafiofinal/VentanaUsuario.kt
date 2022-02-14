package com.example.desafiofinal

import Adaptadores.MiAdaptadorRecyclerEve
import Adaptadores.MiAdaptadorRecyclerEveUsu
import Modelo.Asistente
import Modelo.Comentario
import Modelo.Evento
import Modelo.Ubicacion
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

class VentanaUsuario : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "Daniel"
    var miArray:ArrayList<Evento> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.

    var seleccionado:Int=0
    lateinit var miRecyclerView : RecyclerView

    var miAdapter = MiAdaptadorRecyclerEveUsu(miArray, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_usuario)
    }

    override fun onStart() {
        super.onStart()

        miArray.clear()

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

        miRecyclerView = findViewById(R.id.rvEvUsu) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        //var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerView.adapter = miAdapter

    }

    suspend fun getDataFromFireStore()  : QuerySnapshot? {
        return try{
            val data = db.collection("eventos")
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
                var asistentes : ArrayList<Asistente> = ArrayList()
                var asistent : ArrayList<HashMap<String, Asistente>>

                var comentarios : ArrayList<Comentario> = ArrayList()
                var coment : ArrayList<HashMap<String, Comentario>>

                var ubicaciones : ArrayList<Ubicacion> = ArrayList()
                var ubic : ArrayList<HashMap<String, Ubicacion>>

                if (dc.document.get("asistentes") != null){
                    //asistentes = dc.document.get("asistentes") as ArrayList<Asistente>
                    asistent = dc.document.get("asistentes") as ArrayList<HashMap<String,Asistente>>

                    //Log.e(TAG,asistent[0]["correo"].toString())

                    for(asist in asistent){
                        asistentes.add(Asistente(asist["correo"].toString(),asist["horaLlegada"].toString(),asist["latitud"].toString().toDouble(),asist["longitud"].toString().toDouble()))
                    }

                }
                else {
                    asistentes = arrayListOf()
                }

                if (dc.document.get("comentarios") != null){
                    coment = dc.document.get("comentarios") as ArrayList<HashMap<String, Comentario>>

                    for(com in coment){
                        comentarios.add(Comentario(com["emisor"].toString(),com["mensaje"].toString()))
                    }

                }
                else {
                    comentarios = arrayListOf()
                }

                if (dc.document.get("ubicaciones") != null){
                    ubic = dc.document.get("ubicaciones") as ArrayList<HashMap<String, Ubicacion>>

                    for(ub in ubic){
                        ubicaciones.add(Ubicacion(ub["descripcion"].toString(),ub["latitud"].toString().toDouble(),ub["latitud"].toString().toDouble()))
                    }

                }
                else {
                    comentarios = arrayListOf()
                }

                var al = Evento(
                    dc.document.get("id").toString(),
                    dc.document.get("nombre").toString(),
                    dc.document.get("ubicacion").toString(),
                    dc.document.get("fecha").toString(),
                    dc.document.get("hora").toString(),
                    dc.document.get("latitud").toString().toDouble(),
                    dc.document.get("longitud").toString().toDouble(),
                    asistentes,
                    comentarios,
                    ubicaciones
                )
                //Log.e(TAG, al.toString())
                miArray.add(al)
            }
        }
    }


    fun abrirPerfil(view:View){
        val intentV1 = Intent(this, PerfilUsuario::class.java)
        startActivity(intentV1)
    }

    fun volver(view: View){
        finish()
    }
}