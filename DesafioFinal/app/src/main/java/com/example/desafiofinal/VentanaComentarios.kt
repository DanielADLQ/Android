package com.example.desafiofinal

import Adaptadores.MiAdaptadorRecyclerAsis
import Adaptadores.MiAdaptadorRecyclerCom
import Auxiliar.InfoLogin
import Modelo.Asistente
import Modelo.Comentario
import Modelo.Evento
import Modelo.Usuario
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VentanaComentarios : AppCompatActivity() {

    private val db = Firebase.firestore
    private val TAG = "Daniel"

    lateinit var miRecyclerView : RecyclerView
    lateinit var miRecyclerViewCom : RecyclerView
    var miArray:ArrayList<Comentario> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_comentarios)

        var ev: Evento = intent.getSerializableExtra("ev") as Evento

        var txtTituloEv:TextView = findViewById(R.id.txtTitEvComentarios)

        txtTituloEv.text = ev.nombre

        var seleccionado:Int=0

        var miAdapter = MiAdaptadorRecyclerCom(ev.comentarios, this)
        miRecyclerView = findViewById(R.id.rvComentarios) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        //var miAdapter = MiAdaptadorRecycler(miArray, this)
        miRecyclerView.adapter = miAdapter

    }

    fun nuevoComentario(view:View){
        var txtNuevoMensaje:TextView = findViewById(R.id.txtNuevoComentario)
        var ev: Evento = intent.getSerializableExtra("ev") as Evento

        if(txtNuevoMensaje.text.isNotEmpty()){
            var com: Comentario = Comentario(InfoLogin.usu.correo,txtNuevoMensaje.text.toString())
            ev.comentarios.add(com)
            Toast.makeText(this, "Comentario añadido", Toast.LENGTH_LONG).show()
            db.collection("eventos").document(ev.id).update("comentarios",ev.comentarios)
            txtNuevoMensaje.text=""
        }else{
            Toast.makeText(this, "Escribe un comentario antes de enviar", Toast.LENGTH_SHORT).show()
        }
    }


    fun volver(view:View){
        var ev:Evento = intent.getSerializableExtra("ev") as Evento

        var intentV1 = Intent(this, InfoEventoSeleccionado::class.java)
        intentV1.putExtra("ev",ev)
        startActivity(intentV1)
        finish()
    }

}