package com.example.desafio2profesores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txtCodigo:TextView = findViewById(R.id.txtCodigo)
        var txtClave:TextView = findViewById(R.id.txtClave)
        var btnLogin:Button = findViewById(R.id.btnLogin)

    }

    fun login(view: View){

    }

}