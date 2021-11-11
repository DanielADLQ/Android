package com.example.ej5adivinanumero_2ventanas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import kotlin.random.Random

class JuevoAdivina : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juevo_adivina)
        Log.e("HOLIS","He llegado a la segunda ventana")
        var seleccionado = intent.getIntExtra("seleccion",0)
        var numMax:Int=0

        var intentos:Int=5

        when(seleccionado){
            0 -> numMax=10
            1 -> numMax=100
            2 -> numMax=1000
            3 -> {
                numMax=10
                intentos=1
            }
        }

        var numOculto:Int= Random.nextInt(1,numMax+1)

        var txtExplicacion:TextView=findViewById(R.id.txtExplicacion)
        var numPrueba: TextView =findViewById(R.id.txtPruebaNumero)
        var intentosRestantes: TextView =findViewById(R.id.txtIntentosRestantes)
        var botonProbar: Button =findViewById(R.id.btnProbar)
        var pista: ImageView =findViewById(R.id.imgPista)
        var solucion: TextView =findViewById(R.id.txtSolucion)
        var nuevaPartida: Button =findViewById(R.id.btnNuevaPartida)

        txtExplicacion.text= "Elige un numero entre 1 y "+numMax

        intentosRestantes.text=intentos.toString()

        botonProbar.setOnClickListener(){
            if(numPrueba.text.isEmpty()){
                Toast.makeText(this,"Escribe un número", Toast.LENGTH_LONG).show()
            }else if(numPrueba.text.toString().toInt()<1 || numPrueba.text.toString().toInt()>numMax){
                Toast.makeText(this,"El número debe ser entre 1 y "+numMax, Toast.LENGTH_LONG).show()
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
                        pista.setImageResource(R.drawable.flechaarriba)
                    }else{
                        pista.setImageResource(R.drawable.flechaabajo)
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

            when(seleccionado){
                0 -> numMax=10
                1 -> numMax=100
                2 -> numMax=1000
                3 -> {
                    numMax=10
                    intentos=1
                }
            }

            numOculto = Random.nextInt(1,numMax+1)
            intentosRestantes.text=intentos.toString()
            solucion.text=""
            pista.setImageResource(R.drawable.iconoadivinanumero)
            botonProbar.isEnabled=true
            numPrueba.isEnabled=true
            nuevaPartida.visibility= View.INVISIBLE

        }
    }

    fun volver(view:View){
        finish()
    }


}