package com.example.ejercicioficheros

import Adaptadores.Adaptador
import Auxiliar.Fichero
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    var seleccionado:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var misFicheros = this.fileList()
        var fichSelec:TextView = findViewById(R.id.txtFichSelec)

        var listaFicheros:ListView=findViewById(R.id.lsvFich)
        var adaptador = Adaptador(this,R.layout.lista_ficheros,misFicheros,seleccionado)
        listaFicheros.adapter=adaptador
        var ventanaActual:MainActivity = this

        listaFicheros.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {
                var texto = listaFicheros.getItemAtPosition(pos)
                fichSelec.text=texto.toString()
                //var p = vec.get(pos)
                //Log.e("Fernando",p.toString())
                Log.e("Error",texto.toString())
                if (pos == seleccionado)
                    seleccionado = -1
                else
                    seleccionado = pos

                adaptador = Adaptador(ventanaActual,R.layout.lista_ficheros,misFicheros,seleccionado)
                listaFicheros.adapter = adaptador
                Toast.makeText(applicationContext, texto.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun editar(view:View){

        var intentV1: Intent = Intent(this,VentanaEditar::class.java)

        if(seleccionado >= 0){
            intentV1.putExtra("seleccion",seleccionado)
            startActivity(intentV1)
        }else{
            Toast.makeText(this,"Por favor, elige un fichero",Toast.LENGTH_LONG).show()
        }
    }

    fun barradoFisico(view:View){

        if(seleccionado>=0){

            val dialog = AlertDialog.Builder(this)
                .setTitle("Borrado del fichero físico.")
                .setMessage("¿Estás seguro?")
                .setNegativeButton("No") { view, _ ->
                    Toast.makeText(this, "Se ha cancelado el borrado", Toast.LENGTH_SHORT).show()
                    view.dismiss()
                }
                .setPositiveButton("Sí") { view, _ ->
                    Toast.makeText(this, "Se ha borrado el fichero físico", Toast.LENGTH_SHORT).show()

                    var miFichero = this.fileList().get(seleccionado)
                    var manejoFichero: Fichero = Fichero(miFichero, this)

                    manejoFichero.borrarFicheroFisico()
                    val inte = intent
                    Toast.makeText(this,"Fichero borrado",Toast.LENGTH_LONG).show()
                    finish()
                    startActivity(inte)
                    view.dismiss()
                }
                .setCancelable(false)
                .create()

            dialog.show()

        }else{
            Toast.makeText(this,"Por favor, elige un fichero",Toast.LENGTH_LONG).show()
        }
    }

    fun nuevoFichero(view:View){
        var nomNuevo: EditText = findViewById(R.id.txtNomNuevo)

        if(nomNuevo.text.isEmpty()){
            Toast.makeText(this,"Escribe un nombre para crear un fichero nuevo",Toast.LENGTH_LONG).show()
        }else{
            var nuevoFichero: Fichero = Fichero(nomNuevo.text.toString()+".txt", this)
            if(nuevoFichero.existeFichero()){
                Toast.makeText(this,"ERROR. Ya existe un fichero con el mismo nombre",Toast.LENGTH_LONG).show()
            }else{
                nuevoFichero.escribirLinea("")
                Toast.makeText(this,"Fichero creado correctamente",Toast.LENGTH_LONG).show()

                val inte = intent
                finish()
                startActivity(inte)

            }

        }
    }

}