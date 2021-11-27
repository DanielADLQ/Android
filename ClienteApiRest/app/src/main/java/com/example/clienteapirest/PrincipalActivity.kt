package com.example.clienteapirest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
    }

    fun buscarPorID(view: View){
        var ed: EditText = findViewById(R.id.edtBuscarId)
        var intentV1 = Intent(this, ListadoActivity::class.java)

        if (ed.text.trim().isEmpty()){
            Toast.makeText(this, "Rellene el campo con un número",Toast.LENGTH_SHORT).show()
        }
        else {
            intentV1.putExtra("operacion","buscar")
            intentV1.putExtra("valorBuscar",ed.text.toString())
            startActivity(intentV1)
        }
    }

    fun listarTodos(view: View){
        var intentV1 = Intent(this, ListadoActivity::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    fun leerArchivo(view: View){
        var intentV1 = Intent(this, ListadoActivity::class.java)
        intentV1.putExtra("operacion","leerArchivo")
        startActivity(intentV1)
    }
}