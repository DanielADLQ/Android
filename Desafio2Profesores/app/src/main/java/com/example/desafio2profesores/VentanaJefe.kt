package com.example.desafio2profesores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.desafio2profesores.Api.ServiceBuilder
import com.example.desafio2profesores.Api.UserAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VentanaJefe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_jefe)

    }

    fun listarTodosProfesores(view: View){
        var intentV1 = Intent(this, ListadoProfesores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    fun buscarPorCodProf(view: View){

        var txtCodProf:TextView = findViewById(R.id.txtCodProfBuscar)

        var intentV1 = Intent(this, ListadoProfesores::class.java)

        if (txtCodProf.text.trim().isEmpty()){
            Toast.makeText(this, "Rellene el campo con un codigo de profesor.", Toast.LENGTH_SHORT).show()
        }
        else {
            intentV1.putExtra("operacion","buscar")
            intentV1.putExtra("valorBuscar",txtCodProf.text.toString())
            startActivity(intentV1)
        }
    }

    fun borrarPorCodProf(view:View){

        var txtCodProf:TextView = findViewById(R.id.txtCodProfBuscar)

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.borrarProfesor(txtCodProf.text.toString())
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("Fernando",response.message())
                Log.e ("Fernando", response.code().toString())
                if (response.code() == 200) {
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@VentanaJefe, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.e("Fernando","Algo ha fallado en el borrado: DNI no encontrado.")
                    Toast.makeText(this@VentanaJefe, "Algo ha fallado en el borrado: codigo no encontrado",Toast.LENGTH_LONG).show()
                }
                if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                    Log.e("Fernando","Registro eliminado con éxito.")
                    Toast.makeText(this@VentanaJefe, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Fernando","Algo ha fallado en la conexión.")
                Toast.makeText(this@VentanaJefe, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
            }
        })
    }

    fun nuevoProfesor(view:View){
        var intentV1 = Intent(this, EdicionProfesor::class.java)
        intentV1.putExtra("operacion","nuevo")
        startActivity(intentV1)
    }

    fun modificarProfesor(view:View){

        var txtCodProf:TextView = findViewById(R.id.txtCodProfBuscar)

        var intentV1 = Intent(this, EdicionProfesor::class.java)
        intentV1.putExtra("operacion","modificar")
        intentV1.putExtra("codigo",txtCodProf.text.toString())
        startActivity(intentV1)
    }

}