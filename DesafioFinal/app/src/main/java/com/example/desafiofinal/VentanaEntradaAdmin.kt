package com.example.desafiofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaEntradaAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_entrada_admin)
    }

    fun volver(view: View){
        finish()
    }

    fun entrarComoUsuario(view:View){
        val intentV1 = Intent(this, VentanaUsuario::class.java).apply {

        }
        startActivity(intentV1)
    }

    fun entrarComoAdmin(view:View){
        val intentV1 = Intent(this, VentanaAdmin::class.java).apply {

        }
        startActivity(intentV1)
    }

}