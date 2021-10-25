package com.example.t1ej4_radiobutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var n1:EditText = findViewById(R.id.numN1)
        var n2:EditText = findViewById(R.id.numN2)

        var resul:TextView = findViewById(R.id.numResul)
        var botonSuma:RadioButton = findViewById(R.id.rboSuma)
        var botonResta:RadioButton = findViewById(R.id.rboResta)
        var botonMultiplica:RadioButton = findViewById(R.id.rboMult)
        var botonDivide:RadioButton = findViewById(R.id.rboDiv)

        var botonCalcular:Button = findViewById(R.id.btnCalcular)

        botonCalcular.setOnClickListener(){
            if(botonSuma.isChecked){
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

            if(botonResta.isChecked){
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

            if(botonMultiplica.isChecked){
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

            if(botonDivide.isChecked){
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
}