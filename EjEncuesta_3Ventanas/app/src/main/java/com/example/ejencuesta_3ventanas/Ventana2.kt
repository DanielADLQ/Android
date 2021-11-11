package com.example.ejencuesta_3ventanas

import Adaptadores.MiAdaptador
import Adaptadores.MiAdaptadorVH
import Modelo.Encuestado
import Modulo.Datos
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class Ventana2 : AppCompatActivity() {

    var seleccionado:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana2)

        //var e:ArrayList<Encuestado> = intent.getSerializableExtra("valores") as ArrayList<Encuestado>
        //var e:ArrayList<Encuestado> = Datos.listadoEnc

        /*var listaEnc: ListView = findViewById(R.id.lsvListaEncuestados)
        var adaptador = MiAdaptador(this,R.layout.listado_personas,Datos.listadoEnc,seleccionado)
        listaEnc.adapter=adaptador
        //var ventanaActual:MainActivity = this


        listaEnc.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {

                seleccionado=pos

            }
        }*/

    }

    override fun onStart() { //Cada vez que vuelve la pagina al frente
        super.onStart()

        var listaEnc: ListView = findViewById(R.id.lsvListaEncuestados)
        //var adaptador = MiAdaptador(this,R.layout.listado_personas,Datos.listadoEnc,seleccionado)
        var adaptador = MiAdaptadorVH(this,R.layout.listado_personas,Datos.listadoEnc,seleccionado)
        listaEnc.adapter=adaptador
        //var ventanaActual:MainActivity = this


        listaEnc.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {

                seleccionado=pos

            }
        }
    }


    fun mostrarCompeto(view:View){

        var intentV3: Intent = Intent(this,MostrarCompleta::class.java)
        if(seleccionado >= 0) {
            intentV3.putExtra("seleccion", seleccionado.toString())
            //intentV3.putExtra("encuestados",intent.getSerializableExtra("valores") as ArrayList<Encuestado>)
            startActivity(intentV3)
        }
    }

    fun volver(view: View){
        finish()

    }
}