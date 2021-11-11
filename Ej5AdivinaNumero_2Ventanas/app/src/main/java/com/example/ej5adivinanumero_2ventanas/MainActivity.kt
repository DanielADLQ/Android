package com.example.ej5adivinanumero_2ventanas

import Adaptadores.MiAdaptador
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    var seleccionado:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var vector = arrayOf("Facil","Intermedio","Dificil","Modo Vidente")

        var dificultades = ArrayList<String>(4)
        dificultades.add("Facil")
        dificultades.add("Intermedio")
        dificultades.add("Dificil")
        dificultades.add("Modo Vidente")

        var listaDificicultades:ListView = findViewById(R.id.lstDificultades)
        var adaptador = MiAdaptador(this,R.layout.listview_dificultad,vector,seleccionado)
        listaDificicultades.adapter=adaptador
        var ventanaActual:MainActivity = this


        listaDificicultades.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                val texto = listaDificicultades.getItemAtPosition(pos)
                //var p = vec.get(pos)
                //Log.e("Fernando",p.toString())
                Log.e("Error",texto.toString())
                if (pos == seleccionado)
                    seleccionado = -1
                else
                    seleccionado = pos

                adaptador = MiAdaptador(ventanaActual,R.layout.listview_dificultad,vector,seleccionado)
                listaDificicultades.adapter = adaptador
                Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        var botonComenzar:Button = findViewById(R.id.btnComenzar)

        botonComenzar.setOnClickListener() {


            var intentV1: Intent = Intent(this,JuevoAdivina::class.java)

            if(seleccionado >= 0){
                intentV1.putExtra("seleccion",seleccionado)
                startActivity(intentV1)
            }

        }


    }
}