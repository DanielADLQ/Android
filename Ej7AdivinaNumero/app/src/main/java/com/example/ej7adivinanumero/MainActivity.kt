package com.example.ej7adivinanumero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var numOculto:Int= Random.nextInt(1,101)

        var numPrueba:TextView=findViewById(R.id.txtPruebaNumero)
        var intentosRestantes:TextView=findViewById(R.id.txtIntentosRestantes)
        var botonProbar:Button=findViewById(R.id.btnProbar)
        var pista:ImageView=findViewById(R.id.imgPista)
        var solucion:TextView=findViewById(R.id.txtSolucion)
        var nuevaPartida:Button=findViewById(R.id.btnNuevaPartida)

        var intentos:Int=5

        botonProbar.setOnClickListener(){
            if(numPrueba.text.isEmpty()){
                Toast.makeText(this,"Escribe un número",Toast.LENGTH_LONG).show()
            }else if(numPrueba.text.toString().toInt()<1 || numPrueba.text.toString().toInt()>100){
                Toast.makeText(this,"El número debe ser entre 1 y 100",Toast.LENGTH_LONG).show()
            }else{ //NUMERO APROPIADO PARA PROBAR
                if(numPrueba.text.toString().toInt()==numOculto){
                    botonProbar.isEnabled=false
                    numPrueba.isEnabled=false
                    pista.setImageResource(R.drawable.tick)

                    solucion.text="¡HAS GANADO!"
                    nuevaPartida.visibility= View.VISIBLE

                }else{
                    intentos--
                    intentosRestantes.text=intentos.toString()
                    if(numPrueba.text.toString().toInt()<numOculto){
                        pista.setImageResource(R.drawable.flecha_arriba)
                    }else{
                        pista.setImageResource(R.drawable.flecha_abajo)
                    }

                    if(intentos==0){
                        botonProbar.isEnabled=false
                        numPrueba.isEnabled=false

                        solucion.text="Has perdido... Solucion: "+numOculto
                        nuevaPartida.visibility= View.VISIBLE

                    }

                }
            }

            numPrueba.text=""

        }

        nuevaPartida.setOnClickListener(){

            intentos=5
            numOculto = Random.nextInt(1,101)
            intentosRestantes.text=intentos.toString()
            solucion.text=""
            pista.setImageResource(R.drawable.icono_adivina_numero)
            botonProbar.isEnabled=true
            numPrueba.isEnabled=true
            nuevaPartida.visibility=View.INVISIBLE

        }

    }
}