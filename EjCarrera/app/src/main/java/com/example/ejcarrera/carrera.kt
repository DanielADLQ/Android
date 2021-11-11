package com.example.ejcarrera

import Auxiliar.Hilo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

class carrera : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrera)

        var P1:TextView=findViewById(R.id.txtP1)
        var P2:TextView=findViewById(R.id.txtP2)
        var P3:TextView=findViewById(R.id.txtP3)
        var P4:TextView=findViewById(R.id.txtP4)

        var pista1:ProgressBar=findViewById(R.id.prbP1)
        var pista2:ProgressBar=findViewById(R.id.prbP2)
        var pista3:ProgressBar=findViewById(R.id.prbP3)
        var pista4:ProgressBar=findViewById(R.id.prbP4)

        var enviado1:String=intent.getStringExtra("jugador1") as String
        var enviado2:String=intent.getStringExtra("jugador2") as String
        var enviado3:String=intent.getStringExtra("jugador3") as String
        var enviado4:String=intent.getStringExtra("jugador4") as String

        P1.text=enviado1
        P2.text=enviado2
        P3.text=enviado3
        P4.text=enviado4

        var listaBarras=ArrayList<ProgressBar>(0)

        listaBarras.add(pista1)
        listaBarras.add(pista2)
        listaBarras.add(pista3)
        listaBarras.add(pista4)

        var listaPodio=ArrayList<TextView>(0)

        listaPodio.add(findViewById(R.id.txtPodioA))
        listaPodio.add(findViewById(R.id.txtPodioB))
        listaPodio.add(findViewById(R.id.txtPodioC))
        listaPodio.add(findViewById(R.id.txtPodioD))

        for(i in 0..3){
            var h=Hilo(listaBarras[i],listaPodio[i])
            h.start()
        }

    }

    fun volver(view:View){
        if(Hilo.hilosLlegada==4){
            Hilo.hilosLlegada=0
            finish()
        }
    }
}