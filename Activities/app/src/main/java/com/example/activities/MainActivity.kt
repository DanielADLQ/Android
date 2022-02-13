package com.example.activities

import Modelo.Persona
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun Pulsar(view:View){ //En el boton que quiero que haga esto, voy a onClick y pongo Pulsar

        var intentV1:Intent = Intent(this,Ventana2::class.java)
        var ed:EditText=findViewById(R.id.txtTexto)
        var p:Persona=Persona(ed.text.toString())
        intentV1.putExtra("valor",ed.text.toString())
        intentV1.putExtra("objeto",p)
        startActivity(intentV1)
    }

    fun reiniciar(view:View){
        var intentActual = intent
        finish()
        startActivity(intentActual)
    }

}