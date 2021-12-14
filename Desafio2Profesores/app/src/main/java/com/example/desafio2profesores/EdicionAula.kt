package com.example.desafio2profesores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.desafio2profesores.Api.ServiceBuilder
import com.example.desafio2profesores.Api.UserAPI
import com.example.desafio2profesores.Modelo.Aula
import com.example.desafio2profesores.Modelo.Profesor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EdicionAula : AppCompatActivity() {

    lateinit var nuevoCodaula : TextView
    lateinit var nuevoNomaula : TextView

    lateinit var botonAceptar : Button
    var contexto = this
    lateinit var operacion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicion_aula)

        nuevoCodaula = findViewById(R.id.edCodaulaNuevo)
        nuevoNomaula = findViewById(R.id.edNomaulaNuevo)

        botonAceptar = findViewById(R.id.btnAceptarNuevoAula)

        operacion = intent.getStringExtra("operacion").toString()
        val codBuscar = intent.getStringExtra("codigo").toString()
        if (operacion.equals("modificar")) {
            getBuscarUnAula(codBuscar)
            nuevoCodaula.isEnabled = false  //No dejamos modificar el codaula que es la clave del registro.


        }

    }


    fun cancelar(view: View){
        finish()
    }

    fun aceptar(view: View) {
        val us = Aula(
            nuevoCodaula.text.toString(),
            nuevoNomaula.text.toString(),
        )


        //--------------- Añadir nuevo registro -----------------
        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.addAula(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro efectuado con éxito.")
                        Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la inserción: clave duplicada.")
                        Toast.makeText(
                            contexto,
                            "Algo ha fallado en la inserción: clave duplicada",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando", "Registro efectuado con éxito.")
                        Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexión.")
                    Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
        //---------------- Modificar un registro -----------------
        else {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.modAula(us)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Fernando", response.message())
                    Log.e("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando", "Registro modificado con éxito.")
                        Toast.makeText(
                            contexto,
                            "Registro modificado con éxito",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Log.e("Fernando", "Algo ha fallado en la modificación.")
                        Toast.makeText(
                            contexto,
                            "Algo ha fallado en la modificación",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando", "Registro modificado con éxito.")
                        Toast.makeText(
                            contexto,
                            "Registro modificado con éxito",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando", "Algo ha fallado en la conexión.")
                    Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }

    fun getBuscarUnAula(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnAula(idBusc);

        call.enqueue(object : Callback<Aula> {
            override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                val post = response.body()
                if (post != null) {
                    nuevoCodaula.append(post.codaula)
                    nuevoNomaula.append(post.nomaula)
                }
                else {
                    Toast.makeText(this@EdicionAula, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    botonAceptar.isEnabled = false
                }
            }
            override fun onFailure(call: Call<Aula>, t: Throwable) {
                Toast.makeText(this@EdicionAula, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}