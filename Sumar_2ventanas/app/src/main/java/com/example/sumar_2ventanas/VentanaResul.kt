package com.example.sumar_2ventanas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class VentanaResul : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_resul)

        var caja: TextView = findViewById(R.id.txtResulV2)

        caja.text = intent.getStringExtra("valor")
    }

    fun volver(view:View){
        finish()
    }

}