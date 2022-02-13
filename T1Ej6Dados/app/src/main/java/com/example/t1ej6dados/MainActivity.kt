package com.example.t1ej6dados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var imagenJ:ImageView=findViewById(R.id.imgDadoJugador)
        var imagenM:ImageView=findViewById(R.id.imgDadoMaquina)

        var botonLanzar:Button=findViewById(R.id.botonDados)

        var puntosJ:TextView=findViewById(R.id.txtresulJ)
        var puntosM:TextView=findViewById(R.id.txtresulM)

        var lanzamientos:TextView=findViewById(R.id.txtNumLanzamientos)

        botonLanzar.setOnClickListener(){

            var aleJugador:Int= Random.nextInt(1,7) //De 1 a 6
            var aleMaquina:Int= Random.nextInt(1,7)

            when(aleJugador){
                1 -> imagenJ.setImageResource(R.drawable.dado1)
                2 -> imagenJ.setImageResource(R.drawable.dado2)
                3 -> imagenJ.setImageResource(R.drawable.dado3)
                4 -> imagenJ.setImageResource(R.drawable.dado4)
                5 -> imagenJ.setImageResource(R.drawable.dado5)
                6 -> imagenJ.setImageResource(R.drawable.dado6)
            }


            when(aleMaquina){
                1 -> imagenM.setImageResource(R.drawable.dado1)
                2 -> imagenM.setImageResource(R.drawable.dado2)
                3 -> imagenM.setImageResource(R.drawable.dado3)
                4 -> imagenM.setImageResource(R.drawable.dado4)
                5 -> imagenM.setImageResource(R.drawable.dado5)
                6 -> imagenM.setImageResource(R.drawable.dado6)
            }

            var auxPuntJ:Int = puntosJ.text.toString().toInt() + aleJugador
            var auxPuntM:Int = puntosM.text.toString().toInt() + aleMaquina

            puntosJ.text= auxPuntJ.toString()
            puntosM.text= auxPuntM.toString()

            var quedan=lanzamientos.text.toString().toInt() - 1
            lanzamientos.text = quedan.toString()

            if(quedan==0){
                //botonLanzar.isEnabled=false
                botonLanzar.visibility=View.INVISIBLE
                if(puntosJ.text.toString().toInt() > puntosM.text.toString().toInt()){
                    Toast.makeText(this,"HAS GANADO",Toast.LENGTH_LONG).show()
                }else if(puntosJ.text.toString().toInt() < puntosM.text.toString().toInt()){
                    Toast.makeText(this,"HAS PERDIDO",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"EMPATE",Toast.LENGTH_LONG).show()
                }

            }


        }

    }
}