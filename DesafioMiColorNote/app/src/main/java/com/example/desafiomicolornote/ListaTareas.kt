package com.example.desafiomicolornote

import Adaptadores.MiAdaptadorRecycler
import Auxiliar.ConexionBD
import Conexion.AdminSQLIteConexion
import Modelo.Nota
import Modelo.Tarea
import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

class ListaTareas : AppCompatActivity() {


    lateinit var miRecyclerView : RecyclerView
    private val cameraRequest = 1888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_tareas)

    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_lista_tareas)


        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)


        var idNotaSelec:Int=intent.getIntExtra("idNota",0)

        var txtTitListaTareas:TextView=findViewById(R.id.txtTitListaTareas)
        var txtIdNotaTarea:String=intent.getStringExtra("seleccion") as String
        var idTar:Int=txtIdNotaTarea.toInt()
        var listadoNotas:ArrayList<Nota> = ConexionBD.obtenerNotas(this)
        txtTitListaTareas.text=listadoNotas.get(idTar).titulo


        miRecyclerView = findViewById(R.id.rcvListaTareas) as RecyclerView

        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)
        var miAdapter = MiAdaptadorRecycler(ConexionBD.obtenerTareas(this, idNotaSelec), this,this)
        miRecyclerView.adapter = miAdapter


    }

    fun crearTarea(view: View) {

        var contenidoTarea: TextView = findViewById(R.id.txtTitNuevaTarea)

        if (contenidoTarea.text.trim().toString().length == 0) {
            Toast.makeText(this, R.string.errNoTitulo, Toast.LENGTH_LONG).show()
        } else {

            var idNotaSelec:Int=intent.getIntExtra("idNota",0) as Int

            val admin = AdminSQLIteConexion(this, ConexionBD.nombreBD, null, 1)
            val bd = admin.writableDatabase

            ConexionBD.addTarea(
                this,
                Tarea(0, idNotaSelec, contenidoTarea.text.trim().toString(), 0, "")
            )

            //Toast.makeText(this, "Tarea añadida con éxito", Toast.LENGTH_LONG).show()
            contenidoTarea.text = ""

            var intentActual = intent

            intentActual.putExtra("idNota",idNotaSelec)

            finish()
            startActivity(intentActual)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {

            if(resultCode != 0){ // 0 si se cancela la foto
                ConexionBD.modFoto(this,MiAdaptadorRecycler.tareaSel)
                if (requestCode == cameraRequest) {
                    val photo: Bitmap = data?.extras?.get("data") as Bitmap


                    var fotoFichero = File(getFilesDir().getPath(),MiAdaptadorRecycler.tareaSel.toString()+".jpg")

                    var fileOutStream = FileOutputStream(fotoFichero)
                    photo.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                    fileOutStream.flush()
                    fileOutStream.close()
                }

            }
        }catch(e: Exception){
            Log.e("Fernando",e.toString())
        }
    }


    fun volver(view:View){
        finish()
    }

}