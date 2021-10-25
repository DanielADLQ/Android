package com.example.activities

import Modelo.Persona
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Ventana2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana2)

        var caja:TextView=findViewById(R.id.txtTextoRecibido)

        //Forma1
        //caja.text=intent.getStringExtra("valor")

        var p:Persona = intent.getSerializableExtra("objeto") as Persona
        caja.text = p.toString()

        //Forma2
        //var bun:Bundle? = intent.extras
        //caja.text = bun!!.getString("valor") //!! Indica que no es nulo

    }

    fun volver(view: View){
        finish()
    }

}