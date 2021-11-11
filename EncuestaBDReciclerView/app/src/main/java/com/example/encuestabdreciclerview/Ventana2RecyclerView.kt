package com.example.encuestabdreciclerview

import Adaptadores.MiAdaptadorRecycler
import Adaptadores.MiAdaptadorVH
import Auxiliar.Conexion
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Ventana2RecyclerView : AppCompatActivity() {

    var seleccionado:Int=0

    lateinit var miRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana2_recycler_view)

    }

    override fun onStart() { //Cada vez que vuelve la pagina al frente
        super.onStart()

        //var listaEnc: ListView = findViewById(R.id.lsvListaEncuestados)
        //var adaptador = MiAdaptador(this,R.layout.listado_personas,Datos.listadoEnc,seleccionado)
        //var adaptador = MiAdaptadorVH(this,R.layout.listado_personas, Conexion.obtenerPersonas(this),seleccionado)
        //listaEnc.adapter=adaptador
        //var ventanaActual:MainActivity = this

        Log.e("DANIEL","1")
        miRecyclerView = findViewById(R.id.RecyclerEncuestados) as RecyclerView
        Log.e("DANIEL","2")
        miRecyclerView.setHasFixedSize(true)
        Log.e("DANIEL","3")
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        Log.e("DANIEL","4")
        var miAdapter = MiAdaptadorRecycler(Conexion.obtenerPersonas(this), this)
        Log.e("DANIEL","5")
        miRecyclerView.adapter = miAdapter
        Log.e("DANIEL","6")




        /*listaEnc.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {

                seleccionado=pos

            }
        }*/



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