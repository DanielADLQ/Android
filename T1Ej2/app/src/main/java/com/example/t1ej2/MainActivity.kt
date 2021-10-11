package com.example.t1ej2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var n1 = findViewById<EditText>(R.id.numN1)
        var n2 = findViewById<EditText>(R.id.numN2)

        var resul = findViewById<TextView>(R.id.txtNumResultado)
        var boton = findViewById<Button>(R.id.btnCalcular)

        boton.setOnClickListener(){
            var num1Calcular = n1.text.toString().toInt()
            var num2Calcular = n2.text.toString().toInt()
            var suma = num1Calcular + num2Calcular

            resul.text = suma.toString()

            Toast.makeText(this,"Calculando...",Toast.LENGTH_LONG).show()
        }
    }
}