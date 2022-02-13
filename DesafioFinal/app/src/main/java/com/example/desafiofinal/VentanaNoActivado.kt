package com.example.desafiofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaNoActivado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_no_activado)
    }

    fun volver(view:View){
        finish()
    }

}