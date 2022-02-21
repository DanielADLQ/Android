package com.example.desafiofinal

import Modelo.Evento
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class InfoEventoSeleccionado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_evento_seleccionado)

        var ev:Evento = intent.getSerializableExtra("ev") as Evento
        var txtTitEv:TextView = findViewById(R.id.txtTituloEvento)
        //var txtDescUb:TextView = findViewById(R.id.txtDescUbic)

        txtTitEv.text = ev.nombre
    }

    fun abrirComentarios(view:View){

        var ev:Evento = intent.getSerializableExtra("ev") as Evento

        var intentV1 = Intent(this, VentanaComentarios::class.java)
        intentV1.putExtra("ev",ev)
        startActivity(intentV1)
        finish()
    }

    fun abrirFotos(view:View){

        var ev:Evento = intent.getSerializableExtra("ev") as Evento

        var intentV1 = Intent(this, VentanaFotos::class.java)
        intentV1.putExtra("ev",ev)
        startActivity(intentV1)
        finish()
    }

    fun nuevaUbicacion(view:View){

        var ev:Evento = intent.getSerializableExtra("ev") as Evento
        var txtDescNuevaUbi: TextView = findViewById(R.id.txtDescUbic)

        if(txtDescNuevaUbi.text.isNotEmpty()){
            var intentV1 = Intent(this, MapsNuevaUbicacion::class.java)
            intentV1.putExtra("ev",ev)
            intentV1.putExtra("desc",txtDescNuevaUbi.text.toString())
            startActivity(intentV1)
            finish()
        }else{
            Toast.makeText(this,"Pon un titulo a la ubicacion",Toast.LENGTH_LONG).show()
        }
    }

    fun volver(view:View){
        var intentV1 = Intent(this, VentanaUsuario::class.java)
        startActivity(intentV1)
        finish()
    }
}