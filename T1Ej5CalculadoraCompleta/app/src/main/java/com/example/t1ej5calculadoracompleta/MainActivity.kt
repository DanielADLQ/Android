package com.example.t1ej5calculadoracompleta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textoInput:TextView = findViewById(R.id.txtInput)
        var textoResul:TextView = findViewById(R.id.txtResul)

        var boton1:Button = findViewById(R.id.btn1)
        var boton2:Button = findViewById(R.id.btn2)
        var boton3:Button = findViewById(R.id.btn3)
        var boton4:Button = findViewById(R.id.btn4)
        var boton5:Button = findViewById(R.id.btn5)
        var boton6:Button = findViewById(R.id.btn6)
        var boton7:Button = findViewById(R.id.btn7)
        var boton8:Button = findViewById(R.id.btn8)
        var boton9:Button = findViewById(R.id.btn9)
        var boton0:Button = findViewById(R.id.btn0)
        var botonComa:Button = findViewById(R.id.btnComa)
        var botonIgual:Button = findViewById(R.id.btnIgual)
        var botonBorrar:Button = findViewById(R.id.btnBorrar)
        var botonSuma:Button = findViewById(R.id.btnSum)
        var botonResta:Button = findViewById(R.id.btnRes)
        var botonMult:Button = findViewById(R.id.btnMul)
        var botonDiv:Button = findViewById(R.id.btnDiv)

        var num1:Double=-1.toDouble()
        var num2:Double=-1.toDouble()
        var operador:String=""

        textoInput.text = ""

        boton0.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "0"
        }
        boton1.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "1"
        }
        boton2.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "2"
        }
        boton3.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "3"
        }
        boton4.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "4"
        }
        boton5.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "5"
        }
        boton6.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "6"
        }
        boton7.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "7"
        }
        boton8.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "8"
        }
        boton9.setOnClickListener(){
            textoInput.text = textoInput.text.toString() + "9"
        }

        botonComa.setOnClickListener(){
            if(textoInput.length()>0){ //Si ya hay algo escrito
                if(textoInput.text.indexOf(".")==-1){ //Si no tiene coma aun
                    textoInput.text = textoInput.text.toString() + "."
                }

            }
        }

        botonSuma.setOnClickListener(){
            if(num1==-1.toDouble() && textoInput.text.isNotEmpty()){ //-1 si aun no se ha registrado numero (Esta calculadora no permite introducir numeros negativos)
                num1 = textoInput.text.toString().toDouble()
                operador="+"
                textoInput.text = ""
                textoResul.text = ""
            }
        }
        botonResta.setOnClickListener(){
            if(num1==-1.toDouble() && textoInput.text.isNotEmpty()){
                num1 = textoInput.text.toString().toDouble()
                operador="-"
                textoInput.text = ""
                textoResul.text = ""
            }
        }
        botonMult.setOnClickListener(){
            if(num1==-1.toDouble() && textoInput.text.isNotEmpty()){
                num1 = textoInput.text.toString().toDouble()
                operador="*"
                textoInput.text = ""
                textoResul.text = ""
            }
        }
        botonDiv.setOnClickListener(){
            if(num1==-1.toDouble() && textoInput.text.isNotEmpty()){
                num1 = textoInput.text.toString().toDouble()
                operador="/"
                textoInput.text = ""
                textoResul.text = ""
            }
        }

        botonBorrar.setOnClickListener(){
            textoInput.text=""
            textoResul.text=""
            num1=-1.toDouble()
            num2=-1.toDouble()
            operador=""
        }

        botonIgual.setOnClickListener(){
            if(num1==-1.toDouble() || operador.equals("") || !textoInput.text.isNotEmpty()){
                Toast.makeText(this,"Escribe bien los datos. EMPEZANDO DE NUEVO",Toast.LENGTH_LONG).show()

            }else{
                num2=textoInput.text.toString().toDouble()

                when(operador){
                    "+" -> textoResul.text = (num1+num2).toString()
                    "-" -> textoResul.text = (num1-num2).toString()
                    "*" -> textoResul.text = (num1*num2).toString()
                    "/" -> {
                        if(num2==0.toDouble()){
                            Toast.makeText(this,"ERROR. División por 0",Toast.LENGTH_LONG).show()
                        }else{
                            textoResul.text = (num1/num2).toString()
                        }
                    }
                }

            }

            num1=-1.toDouble()
            num2=-1.toDouble()
            operador=""
            textoInput.text=""

        }


    }
}