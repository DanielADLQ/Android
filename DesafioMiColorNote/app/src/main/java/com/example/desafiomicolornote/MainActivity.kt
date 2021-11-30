package com.example.desafiomicolornote

import Adaptadores.MiAdaptadorVH
import Auxiliar.ConexionBD
import Auxiliar.ConexionBD.obtenerNotas
import Auxiliar.Parametro
import Conexion.AdminSQLIteConexion
import Modelo.Nota
import Modelo.Tarea
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList
import android.widget.AdapterView

import android.widget.AdapterView.OnItemLongClickListener
import androidx.core.view.get
import android.content.DialogInterface





class MainActivity : AppCompatActivity() {

    var seleccionado:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var botonAbrir:Button=findViewById(R.id.btnAbrir)

        var listaNot: ListView = findViewById(R.id.lsvNotas)

        var adaptador = MiAdaptadorVH(this,R.layout.lista_notas, ConexionBD.obtenerNotas(this),seleccionado)
        listaNot.adapter=adaptador
        var ventanaActual:MainActivity = this

        listaNot.onItemClickListener = object: AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>?, vista: View?, pos: Int, idElemento: Long) {

                if(pos==seleccionado){
                    seleccionado=-1
                    botonAbrir.isEnabled=false
                }else{
                    seleccionado=pos
                    botonAbrir.isEnabled=true
                }

                adaptador = MiAdaptadorVH(ventanaActual,R.layout.lista_notas, ConexionBD.obtenerNotas(ventanaActual),seleccionado)

                listaNot.adapter=adaptador

            }
        }

        listaNot.onItemLongClickListener = object: AdapterView.OnItemLongClickListener {

            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, pos: Int, idElemento: Long):Boolean {

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                        dialogo.setPositiveButton(R.string.txtEliminar,
                            DialogInterface.OnClickListener { dialog, which ->

                                ConexionBD.delNota(this@MainActivity,ConexionBD.obtenerNotas(this@MainActivity).get(pos).idN)

                                var intentActual:Intent=intent
                                finish()
                                startActivity(intentActual)

                            })
                        dialogo.setNegativeButton(
                            R.string.txtCancelar,
                            DialogInterface.OnClickListener { dialog, which ->

                                dialog.dismiss()
                            })
                        dialogo.setTitle(R.string.txtConfBorrado)
                        dialogo.setMessage(R.string.txtDeseaElim)
                        dialogo.show()


                return true
            }
        }

    }

    fun mostrarCrear(view:View){

        var radioGrupo:RadioGroup=findViewById(R.id.radioGrupo)
        var titulo:TextView=findViewById(R.id.txtTitulo)
        var btnCrear:Button=findViewById(R.id.btnCrear)

        radioGrupo.isVisible=true
        titulo.isVisible=true
        btnCrear.isVisible=true

    }

    fun crearNota(view:View){

        var radioGrupo:RadioGroup=findViewById(R.id.radioGrupo)
        var rboNota:RadioButton=findViewById(R.id.rboNota)
        var rboLista:RadioButton=findViewById(R.id.rboLista)
        var titulo:TextView=findViewById(R.id.txtTitulo)

        if(!rboNota.isChecked && !rboLista.isChecked){
            Toast.makeText(this,R.string.errNoTipo, Toast.LENGTH_LONG).show()
        }else{
            if(titulo.text.trim().toString().length==0){
                Toast.makeText(this,R.string.errNoTitulo, Toast.LENGTH_LONG).show()
            }else{



                var datoTitulo:String = titulo.text.trim().toString()

                var datoTipo:String=""

                var posicionSelec = radioGrupo.indexOfChild(findViewById(radioGrupo.checkedRadioButtonId))

                when(posicionSelec){
                    0 -> datoTipo="nota"
                    1 -> datoTipo="lista"
                }

                var datoFecha:String = Calendar.getInstance().time.date.toString()+"/"+((Calendar.getInstance().time.month)+1).toString()+"/"+((Calendar.getInstance().time.year)+1900).toString()

                var datoHora:String = Calendar.getInstance().time.hours.toString()+":"+Calendar.getInstance().time.minutes.toString()


                val admin = AdminSQLIteConexion(this, ConexionBD.nombreBD, null, 1)
                val bd = admin.writableDatabase


                ConexionBD.addNota(this,
                    Nota(0,datoTitulo,datoTipo,datoFecha,datoHora,"")
                )


                titulo.text=""
                radioGrupo.check(-1)

            }
        }

        var intentActual = intent
        finish()
        startActivity(intentActual)

    }


    fun abrirNota(view:View){

        var intentV1:Intent

        if(seleccionado >= 0){

            var misNotas:ArrayList<Nota> = ConexionBD.obtenerNotas(this)

            var idNotaSelec=misNotas.get(seleccionado).idN

            if(misNotas.get(seleccionado).tipo=="nota"){
                intentV1 = Intent(this,Notas::class.java)
            }else{
                intentV1 = Intent(this,ListaTareas::class.java)
            }

            intentV1.putExtra("seleccion",seleccionado.toString())
            intentV1.putExtra("idNota",idNotaSelec)
            startActivity(intentV1)

        }

    }

}