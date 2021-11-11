package com.example.encuestabdrecyclerview

import Adaptadores.MiAdaptadorVH
import Auxiliar.Conexion.obtenerPersonas
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import Auxiliar.Conexion

class Ventana2 : AppCompatActivity() {

    var seleccionado:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana2)
    }

    override fun onStart() { //Cada vez que vuelve la pagina al frente
        super.onStart()

        var listaEnc: ListView = findViewById(R.id.lsvListaEncuestados)
        //var adaptador = MiAdaptador(this,R.layout.listado_personas,Datos.listadoEnc,seleccionado)
        var adaptador = MiAdaptadorVH(this,R.layout.listado_personas, Conexion.obtenerPersonas(this),seleccionado)
        listaEnc.adapter=adaptador
        //var ventanaActual:MainActivity = this


        listaEnc.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {

                seleccionado=pos

            }
        }
    }


    fun mostrarCompeto(view: View){

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