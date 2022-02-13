package com.example.desafiofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_admin)
    }

    fun volver(view: View){
        finish()
    }

    fun abrirGestionUsu(view:View){
        val intentV1 = Intent(this, VentanaGestionUsuarios::class.java).apply {

        }
        startActivity(intentV1)
    }

    fun abrirGestionEve(view:View){
        val intentV1 = Intent(this, EditarEventos::class.java).apply {

        }
        startActivity(intentV1)
    }

}