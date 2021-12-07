package com.example.desafio2profesores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VentanaJefe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_jefe)

    }

    fun listarTodosProfesores(view: View){
        var intentV1 = Intent(this, ListadoProfesores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

}