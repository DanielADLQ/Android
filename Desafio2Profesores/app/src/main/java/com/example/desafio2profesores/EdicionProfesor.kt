package com.example.desafio2profesores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.desafio2profesores.Api.ServiceBuilder
import com.example.desafio2profesores.Api.UserAPI
import com.example.desafio2profesores.Modelo.Profesor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EdicionProfesor : AppCompatActivity() {

    lateinit var nuevoCodigo : TextView
    lateinit var nuevoNya : TextView
    lateinit var nuevoRol : TextView
    lateinit var nuevoClave : TextView
    lateinit var botonAceptar : Button
    var contexto = this
    lateinit var operacion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicion_profesor)

        val fragment = Fragmento()
        replaceFragment(fragment)

        nuevoCodigo = findViewById(R.id.edCodNuevo)
        nuevoNya = findViewById(R.id.edNyaNuevo)
        //nuevoRol = findViewById(R.id.edRolNuevo)
        nuevoClave = findViewById(R.id.edClaveNuevo)
        botonAceptar = findViewById(R.id.btnAceptarNuevo)

        operacion = intent.getStringExtra("operacion").toString()
        val codBuscar = intent.getStringExtra("codigo").toString()
        if (operacion.equals("modificar")) {
            getBuscarUnUsuario(codBuscar)
            nuevoCodigo.isEnabled = false  //No dejamos modificar el DNI que es la clave del registro.

        }
    }

    private fun replaceFragment(fragment: Fragmento){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frmedprof, fragment)
        fragmentTransaction.commit()
    }

    fun cancelar(view: View){
        finish()
    }

    fun aceptar(view:View) {

        var radioProf:RadioButton = findViewById(R.id.rboProfesor)
        var radioJefe:RadioButton = findViewById(R.id.rboJefe)

        var rolSel:String = ""

        if(radioProf.isChecked){ //Es un profesor normal
            rolSel = "0"
        }else{ //Es jefe de departamento
            rolSel = "1"
        }

        val us = Profesor(
            nuevoCodigo.text.toString(),
            nuevoNya.text.toString(),
            rolSel,
            nuevoClave.text.toString()
        )


        //--------------- Añadir nuevo registro -----------------
        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.addProfesor(us)
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
            val call = request.modProfesor(us)
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

    fun getBuscarUnUsuario(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnProfesor(idBusc);

        var radioProf:RadioButton = findViewById(R.id.rboProfesor)
        var radioJefe:RadioButton = findViewById(R.id.rboJefe)

        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                val post = response.body()
                if (post != null) {
                    nuevoCodigo.append(post.codigo)
                    nuevoNya.append(post.nya)


                    if(post.rol=="0"){
                        radioProf.isChecked = true
                        radioJefe.isChecked = false
                    }else{
                        radioProf.isChecked = false
                        radioJefe.isChecked = true
                    }

                    //nuevoRol.append(post.rol)
                    nuevoClave.append(post.clave)
                }
                else {
                    Toast.makeText(this@EdicionProfesor, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    botonAceptar.isEnabled = false
                }
            }
            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                Toast.makeText(this@EdicionProfesor, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}