package com.example.desafiomicolornote

import Auxiliar.ConexionBD
import Modelo.Nota
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class Notas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        var titNota:TextView = findViewById(R.id.txtTitNota)
        var contenido:TextView = findViewById(R.id.txtContenidoNota)

        var imgBorrar: ImageView =findViewById(R.id.imgBorrar)
        Glide.with(this).load(R.drawable.basura).into(imgBorrar)



        var elegido:String=intent.getStringExtra("seleccion") as String
        var numElegido:Int=elegido.toInt()

        var listadoNotas:ArrayList<Nota> = ConexionBD.obtenerNotas(this)

        titNota.text = listadoNotas.get(numElegido).titulo
        contenido.text = listadoNotas.get(numElegido).texto
    }

    fun volver(view: View){
        finish()
    }

    fun guardarTexto(view:View){

        var idNota:Int=intent.getIntExtra("idNota",0)

        var contenido:TextView = findViewById(R.id.txtContenidoNota)

        ConexionBD.cambiarTexto(this,idNota,contenido.text.toString())
        Toast.makeText(this,R.string.tstMod , Toast.LENGTH_LONG).show()

    }

    fun limpiarTexto(view: View){
        var contenido:TextView = findViewById(R.id.txtContenidoNota)
        contenido.text=""
        Toast.makeText(this,R.string.tstLim, Toast.LENGTH_LONG).show()
    }

    fun enviarSMS(view:View){

        var intentV2: Intent

        var contenido:TextView = findViewById(R.id.txtContenidoNota)

        intentV2 = Intent(this,EnviarSMS::class.java)

        intentV2.putExtra("texto",contenido.text.toString())

        startActivity(intentV2)
    }

}