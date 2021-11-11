package com.example.ejercicioficheros

import Auxiliar.Fichero
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class VentanaEditar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_editar)

        var seleccionado = intent.getIntExtra("seleccion",0)
        var miFichero = this.fileList().get(seleccionado)

        var manejoFichero: Fichero = Fichero(miFichero, this)
        //lateinit var edContenido: EditText
        //lateinit var edLinea: EditText

        var titulo:TextView=findViewById(R.id.txtTituloFichero)
        titulo.text=manejoFichero.nombreFichero

        var contenido:TextView = findViewById(R.id.txtTextoFichero)
        contenido.text = manejoFichero.leerFichero()

    }

    fun Guardar(view:View){

        var contenido:TextView = findViewById(R.id.txtTextoFichero)

        var seleccionado = intent.getIntExtra("seleccion",0)
        var miFichero = this.fileList().get(seleccionado)
        var manejoFichero: Fichero = Fichero(miFichero, this)

        manejoFichero.escribirLinea(contenido.text.toString())
        //contenido.text=manejoFichero.leerFichero() //Comprobar si se ha guardado correctamente en el fichero
        Toast.makeText(this,"Guardado con éxito",Toast.LENGTH_LONG).show()
    }

    fun BorrarContenido(view:View){

        val dialog = AlertDialog.Builder(this)
            .setTitle("Borrado del contenido.")
            .setMessage("¿Estás seguro?")
            .setNegativeButton("No") { view, _ ->
                Toast.makeText(this, "Se ha cancelado el borrado", Toast.LENGTH_SHORT).show()
                view.dismiss()
            }
            .setPositiveButton("Sí") { view, _ ->
                Toast.makeText(this, "Se ha borrado el contenido del fichero", Toast.LENGTH_SHORT).show()

                var seleccionado = intent.getIntExtra("seleccion",0)
                var miFichero = this.fileList().get(seleccionado)
                var manejoFichero: Fichero = Fichero(miFichero, this)

                manejoFichero.borrarFichero()
                var contenido:TextView = findViewById(R.id.txtTextoFichero)
                contenido.text=manejoFichero.leerFichero()

                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()

    }

    fun volver(view:View){
        finish()
    }

}