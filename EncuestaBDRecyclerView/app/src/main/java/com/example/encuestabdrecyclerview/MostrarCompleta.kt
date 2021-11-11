package com.example.encuestabdrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList
import Auxiliar.Conexion
import Auxiliar.Fichero
import Auxiliar.Parametro
import Modelo.Encuestado

class MostrarCompleta : AppCompatActivity() {

    var fichBitacora: Fichero = Fichero(Parametro.fichBitacora , this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_completa)

        var elegido:String=intent.getStringExtra("seleccion") as String
        //var listadoEnc:ArrayList<Encuestado> = intent.getSerializableExtra("encuestados") as ArrayList<Encuestado>

        var numElegido:Int=elegido.toInt()

        var nom: TextView =findViewById(R.id.txtNom)
        var so: TextView =findViewById(R.id.txtSO)
        var esp: TextView =findViewById(R.id.txtEsp)
        var h: TextView =findViewById(R.id.txtHoras)

        var listadoEnc:ArrayList<Encuestado> = Conexion.obtenerPersonas(this)

        nom.text=listadoEnc.get(numElegido).nom
        so.text=listadoEnc.get(numElegido).so
        esp.text=listadoEnc.get(numElegido).esp.toString()
        h.text=listadoEnc.get(numElegido).horasE



    }

    fun GuardarVolver(view: View){

        var elegido:String=intent.getStringExtra("seleccion") as String
        var listadoEnc:ArrayList<Encuestado> = Conexion.obtenerPersonas(this)
        var numElegido:Int=elegido.toInt()
        var idEnc = listadoEnc.get(numElegido).id



        var rboW: RadioButton = findViewById(R.id.rboWin)
        var rboL: RadioButton = findViewById(R.id.rboLin)
        var rboM: RadioButton = findViewById(R.id.rboSOMac)

        if(rboW.isChecked || rboL.isChecked || rboM.isChecked){

            if(rboW.isChecked){
                Conexion.cambiarSO(this,idEnc,"Windows")
                fichBitacora.escribirLinea(Calendar.getInstance().time.toString()+ " - Sistema operativo del usuario con ID: "+idEnc+" modificado a Windows")
            }
            if(rboL.isChecked){
                Conexion.cambiarSO(this,idEnc,"Linux")
                fichBitacora.escribirLinea(Calendar.getInstance().time.toString()+ " - Sistema operativo del usuario con ID: "+idEnc+" modificado a Linux")
            }
            if(rboM.isChecked){
                Conexion.cambiarSO(this,idEnc,"Mac")
                fichBitacora.escribirLinea(Calendar.getInstance().time.toString()+ " - Sistema operativo del usuario con ID: "+idEnc+" modificado a Mac")
            }

            Toast.makeText(this,"Modificado", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this,"Marca un SO para cambiar", Toast.LENGTH_LONG).show()
        }

        /*
        var intentV4: Intent = Intent(this,MainActivity::class.java)

        intentV4.putExtra("encuestados",intent.getSerializableExtra("valores") as ArrayList<Encuestado>)
        startActivity(intentV4)

        */

    }

    fun volver(view: View){
        finish()
    }


}