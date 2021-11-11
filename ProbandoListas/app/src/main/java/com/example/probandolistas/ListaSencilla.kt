package com.example.probandolistas

import Adaptadores.MiAdaptador
import Modelo.Persona
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.util.ArrayList

class ListaSencilla : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_sencilla)

        var vector = arrayOf(1,2,3,4,6,8,9)

        var vec = ArrayList<Persona>(4)
        vec.add(Persona("Fernando",38))
        vec.add(Persona("Aranzabe",71))


        var ml: ListView = findViewById(R.id.lstMilista)
        //val adaptador = ArrayAdapter(this, R.layout.itemlo,R.id.txtItem,vector)
        var miAdaptadorModificado: MiAdaptador = MiAdaptador(this,R.layout.itemlo,vector)
        ml.adapter = miAdaptadorModificado

        ml.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                val texto = ml.getItemAtPosition(pos)
                //var p = vec.get(pos)
                //Log.e("Fernando",p.toString())
                Log.e("Fernando",texto.toString())
                Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }
}