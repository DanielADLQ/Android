package com.example.registropersonaventana2devolver

import Modelo.Registrado
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irAOtraVentanaEsperandoResultado(view: View){

        val intent = Intent(this, Registro::class.java)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data

            val returnSerializable = data!!.getSerializableExtra("registrado") as Registrado

            val txtListaRegistrados:TextView=findViewById(R.id.textViewRegistrados)
            txtListaRegistrados.text = txtListaRegistrados.text.toString() + returnSerializable.toString()+"\n"
        }
    }

}