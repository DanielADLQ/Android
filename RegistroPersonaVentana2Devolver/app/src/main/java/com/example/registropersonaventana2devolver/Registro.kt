package com.example.registropersonaventana2devolver

import Modelo.Registrado
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    }

    fun volver(view: View){

        var dni:EditText=findViewById(R.id.txtDni)
        var nombre:EditText=findViewById(R.id.txtNombre)
        var edad:EditText=findViewById(R.id.txtEdad)
        var direccion:EditText=findViewById(R.id.txtDireccion)
        var telefono:EditText=findViewById(R.id.txtTelefono)

        // Put the String to pass back into an Intent and close this activity
        val intent = Intent()

        val r:Registrado = Registrado(dni.text.toString(),nombre.text.toString(),edad.text.toString(),direccion.text.toString(),telefono.text.toString())
        intent.putExtra("registrado",r) //El objeto debe ser serailizable


        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun cancelar(view:View){
        finish()
    }

}