package com.example.encuestabdreciclerview

import Auxiliar.Conexion
import Auxiliar.Fichero
import Auxiliar.Parametro
import Conexion.AdminSQLIteConexion
import Modelo.Encuestado
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    var fichBitacora: Fichero = Fichero(Parametro.fichBitacora , this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var nombre: TextView = findViewById(R.id.txtNombre)
        var esAnonimo: Switch = findViewById(R.id.swAnonimo)

        var radioGrupo: RadioGroup = findViewById(R.id.radioGroup)
        var radioMac: RadioButton = findViewById(R.id.rboMac)
        var radioWindows: RadioButton = findViewById(R.id.rboWindows)
        var radioLinux: RadioButton = findViewById(R.id.rboLinux)

        var checkDam: CheckBox = findViewById(R.id.chkDAM)
        var checkAsir: CheckBox = findViewById(R.id.chkASIR)
        var checkDaw: CheckBox = findViewById(R.id.chkDAW)

        var barraHoras: SeekBar = findViewById(R.id.skbHoras)
        var textoHoras: TextView = findViewById(R.id.lblNumHoras)

        var botonValidar: Button = findViewById(R.id.btnValidar)
        var botonReiniciar: Button = findViewById(R.id.btnReiniciar)
        var botonCuantas: Button = findViewById(R.id.btnCuantas)
        var botonResumen: Button = findViewById(R.id.btnResumen)
        var botonResumenRecycler: Button = findViewById(R.id.btnResumenRecycler)



        //var listaEncuestados:ArrayList<Encuestado> = ArrayList<Encuestado>()


        esAnonimo.setOnClickListener(){
            if(esAnonimo.isChecked){
                nombre.text=""
                nombre.isEnabled=false
            }else{
                nombre.isEnabled=true
            }
        }

        barraHoras.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var s=seekBar.progress.toString()
                textoHoras.setText(s)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })



        botonValidar.setOnClickListener(){
            if(!esAnonimo.isChecked && nombre.text.length==0){
                Toast.makeText(this,"ERROR. Escribe un nombre o marca anónimo", Toast.LENGTH_LONG).show()
            }else if(!radioMac.isChecked && !radioWindows.isChecked && !radioLinux.isChecked){ //Ninguno marcado
                Toast.makeText(this,"ERROR. Marca un sistema operativo", Toast.LENGTH_LONG).show()
            }else{

                var datoNombre:String=""

                if(esAnonimo.isChecked){
                    datoNombre="Anónimo"
                }else{
                    datoNombre=nombre.text.toString()
                }

                var datoSO:String=""
                var posicionSeleccionado = radioGrupo.indexOfChild(findViewById(radioGrupo.checkedRadioButtonId))
                when(posicionSeleccionado){
                    0 -> datoSO="Mac"
                    1 -> datoSO="Windows"
                    2 -> datoSO="Linux"
                }


                var esp:ArrayList<String> = ArrayList()
                if(checkDam.isChecked){
                    esp.add("DAM")
                }
                if(checkAsir.isChecked){
                    esp.add("ASIR")
                }
                if(checkDaw.isChecked){
                    esp.add("DAW")
                }
                if(esp.size==0){
                    esp.add("Sin especialidad")
                }

                //listaEncuestados.add(Encuestado(datoNombre,datoSO,esp,textoHoras.text.toString()))
                //Datos.aniadirEnc(Encuestado(datoNombre,datoSO,esp,textoHoras.text.toString()))

                val admin = AdminSQLIteConexion(this, Conexion.nombreBD, null, 1)
                val bd = admin.writableDatabase

                var idNuevo:Int = Conexion.seleccionarIDEnc(bd)


                Conexion.addPersona(this,Encuestado(datoNombre,datoSO,esp,textoHoras.text.toString(),idNuevo))
                fichBitacora.escribirLinea(Calendar.getInstance().time.toString() + " - Usuario añadido con ID: "+idNuevo)

                Toast.makeText(this,"Añadido con éxito", Toast.LENGTH_LONG).show()
            }
        }

        botonReiniciar.setOnClickListener(){
            var intentActual = intent
            finish()
            startActivity(intentActual)
            //Esto borra la lista y todo

        }

        botonCuantas.setOnClickListener(){
            Toast.makeText(this,("Hay "+Conexion.obtenerPersonas(this).size.toString()+" encuestados"), Toast.LENGTH_LONG).show()
        }

        botonResumen.setOnClickListener(){
            //if(Datos.listadoEnc.size>0){
            var listaEnc:ArrayList<Encuestado> = Conexion.obtenerPersonas(this)
            if(listaEnc.size>0){
                var intentV2: Intent = Intent(this,Ventana2::class.java)
                //intentV2.putExtra("valores",listaEncuestados)
                startActivity(intentV2)
            }else{
                Toast.makeText(this,("No hay encuestados"), Toast.LENGTH_LONG).show()
            }

        }


        botonResumenRecycler.setOnClickListener(){
            //if(Datos.listadoEnc.size>0){
            var listaEnc:ArrayList<Encuestado> = Conexion.obtenerPersonas(this)
            if(listaEnc.size>0){
                var intentV2: Intent = Intent(this,Ventana2RecyclerView::class.java)
                //intentV2.putExtra("valores",listaEncuestados)
                startActivity(intentV2)
            }else{
                Toast.makeText(this,("No hay encuestados"), Toast.LENGTH_LONG).show()
            }

        }

    }



}