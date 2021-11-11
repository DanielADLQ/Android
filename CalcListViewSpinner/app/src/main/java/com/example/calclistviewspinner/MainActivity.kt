package com.example.calclistviewspinner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var operadores = ArrayList<String>(4)
        operadores.add("+")
        operadores.add("-")
        operadores.add("*")
        operadores.add("/")



        var seleccion:TextView=findViewById(R.id.txtSeleccion)

        var listV: ListView =findViewById(R.id.lsvVista)
        val adaptador =ArrayAdapter(this,R.layout.item_lista,R.id.txtItem,operadores)
        listV.adapter=adaptador

        listV.onItemClickListener = object:AdapterView.OnItemClickListener {

            override fun onItemClick(
                parent: AdapterView<*>?,
                vista: View?,
                pos: Int,
                idElemento: Long
            ) {
                val texto = listV.getItemAtPosition(pos)
                var signo = operadores.get(pos)

                seleccion.text=signo.toString()
                //Log.e("miMensaje",seleccion.text.toString())

            }
        }

        var miSp:Spinner=findViewById(R.id.spSpinnerSeleccion)
        val adaptador2 =ArrayAdapter(this,R.layout.item_spinner,R.id.txtMiSpinner,operadores)
        miSp.adapter=adaptador2

        miSp.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                vista: View?,
                pos: Int,
                idElemento: Long
            ) {
                val texto = miSp.getItemAtPosition(pos)
                var signo = operadores.get(pos)

                seleccion.text=signo.toString()
                //Log.e("miMensaje",seleccion.text.toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })

    }

    fun calcular(view:View){

        var n1 = findViewById<EditText>(R.id.numN1)
        var n2 = findViewById<EditText>(R.id.numN2)

        var num1Calcular = n1.text.toString().toDouble()
        var num2Calcular = n2.text.toString().toDouble()

        var seleccion:TextView=findViewById(R.id.txtSeleccion)

        var resultado:Double=0.toDouble()

        var intentV1: Intent = Intent(this, VentanaResul::class.java)


        if(seleccion.text.toString().isEmpty()){
            Toast.makeText(this,"Elige el tipo de operación",Toast.LENGTH_LONG).show()
        }else{

            if(num1Calcular.toString().isEmpty() || num2Calcular.toString().isEmpty()){
                Toast.makeText(this,"Escribe los dos numeros",Toast.LENGTH_LONG).show()
            }else{
                var div0:Boolean=false
                when(seleccion.text.toString()){

                    "+" -> resultado=num1Calcular + num2Calcular
                    "-" -> resultado=num1Calcular - num2Calcular
                    "*" -> resultado=num1Calcular * num2Calcular
                    "/" -> {
                        if(num2Calcular==0.toDouble()){
                            Toast.makeText(this,"Error.Division entre 0",Toast.LENGTH_LONG).show()
                            div0=true
                        }else{
                            resultado=num1Calcular / num2Calcular
                        }

                    }

                }

                if(!div0){
                    intentV1.putExtra("valor", resultado.toString())
                    startActivity(intentV1)
                }

            }



        }

    }

}