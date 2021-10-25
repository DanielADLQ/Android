package com.example.t1ej4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var n1 = findViewById<EditText>(R.id.numN1)
        var n2 = findViewById<EditText>(R.id.numN2)

        var resul = findViewById<TextView>(R.id.numResul)
        var botonSuma = findViewById<Button>(R.id.btnSumar)
        var botonResta = findViewById<Button>(R.id.btnRestar)
        var botonMultiplica = findViewById<Button>(R.id.btnMultiplicar)
        var botonDivide = findViewById<Button>(R.id.btnDividir)

        botonSuma.setOnClickListener(){

            if(n1.text.length==0 || n2.text.length==0){
                Toast.makeText(this,"ERROR. Escribe los 2 números", Toast.LENGTH_LONG).show()
            }else{
                var num1Calcular = n1.text.toString().toDouble()
                var num2Calcular = n2.text.toString().toDouble()
                var suma = num1Calcular + num2Calcular

                resul.text = suma.toString()

                Toast.makeText(this,"Sumando...", Toast.LENGTH_LONG).show()
            }

        }

        botonResta.setOnClickListener(){

            if(n1.text.length==0 || n2.text.length==0){
                Toast.makeText(this,"ERROR. Escribe los 2 números", Toast.LENGTH_LONG).show()
            }else{
                var num1Calcular = n1.text.toString().toDouble()
                var num2Calcular = n2.text.toString().toDouble()
                var resta = num1Calcular - num2Calcular

                resul.text = resta.toString()

                Toast.makeText(this,"Restando...", Toast.LENGTH_LONG).show()
            }

        }

        botonMultiplica.setOnClickListener(){

            if(n1.text.length==0 || n2.text.length==0){
                Toast.makeText(this,"ERROR. Escribe los 2 números", Toast.LENGTH_LONG).show()
            }else{
                var num1Calcular = n1.text.toString().toDouble()
                var num2Calcular = n2.text.toString().toDouble()
                var mult = num1Calcular * num2Calcular

                resul.text = mult.toString()

                Toast.makeText(this,"Multiplicando...", Toast.LENGTH_LONG).show()
            }

        }

        botonDivide.setOnClickListener(){

            if(n1.text.length==0 || n2.text.length==0){
                Toast.makeText(this,"ERROR. Escribe los 2 números", Toast.LENGTH_LONG).show()
            }else{
                var num1Calcular = n1.text.toString().toDouble()
                var num2Calcular = n2.text.toString().toDouble()

                if(num2Calcular.toDouble() == 0.toDouble()){
                    Toast.makeText(this,"Error. No se puede dividir por 0", Toast.LENGTH_LONG).show()
                }else{
                    var div = num1Calcular / num2Calcular
                    resul.text = div.toString()
                    Toast.makeText(this,"Dividiendo...", Toast.LENGTH_LONG).show()
                }
            }


        }

    }
}