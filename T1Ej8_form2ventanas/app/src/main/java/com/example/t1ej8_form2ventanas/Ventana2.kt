package com.example.t1ej8_form2ventanas

import Modelo.Encuestado
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Ventana2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana2)

        var cajaInfo:TextView=findViewById(R.id.txtInfoV2)

        var e:ArrayList<Encuestado> = intent.getSerializableExtra("valores") as ArrayList<Encuestado>

        cajaInfo.text=""
        for(i in 0..e.size-1){
            cajaInfo.text = cajaInfo.text.toString() + e[i].toString() + "\n"
        }
    }

    fun volver(view:View){
        finish()
    }

}