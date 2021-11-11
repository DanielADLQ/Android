package com.example.ejencuesta_3ventanas

import Modelo.Encuestado
import Modulo.Datos
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class MostrarCompleta : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_completa)

        var elegido:String=intent.getStringExtra("seleccion") as String
        //var listadoEnc:ArrayList<Encuestado> = intent.getSerializableExtra("encuestados") as ArrayList<Encuestado>

        var numElegido:Int=elegido.toInt()

        var nom:TextView=findViewById(R.id.txtNom)
        var so:TextView=findViewById(R.id.txtSO)
        var esp:TextView=findViewById(R.id.txtEsp)
        var h:TextView=findViewById(R.id.txtHoras)

        nom.text=Datos.listadoEnc.get(numElegido).nom
        so.text=Datos.listadoEnc.get(numElegido).so
        esp.text=Datos.listadoEnc.get(numElegido).esp.toString()
        h.text=Datos.listadoEnc.get(numElegido).horasE


        //Datos.cambiarSO(numElegido,"Mac")

    }

    fun GuardarVolver(view:View){ //DUDA: QUIERO VOLVER A LA PRIMERA VENTANA PERO SIN PASAR POR EL ONCREATE, PASANDOLE EL ARRAYLIST

        var elegido:String=intent.getStringExtra("seleccion") as String

        var rboW:RadioButton = findViewById(R.id.rboWin)
        var rboL:RadioButton = findViewById(R.id.rboLin)
        var rboM:RadioButton = findViewById(R.id.rboSOMac)

        if(rboW.isChecked || rboL.isChecked || rboM.isChecked){

            if(rboW.isChecked){
                Datos.cambiarSO(elegido.toInt(),"Windows")
            }
            if(rboL.isChecked){
                Datos.cambiarSO(elegido.toInt(),"Linux")
            }
            if(rboM.isChecked){
                Datos.cambiarSO(elegido.toInt(),"Mac")
            }

            Toast.makeText(this,"Modificado",Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(this,"Marca un SO para cambiar",Toast.LENGTH_LONG).show()
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