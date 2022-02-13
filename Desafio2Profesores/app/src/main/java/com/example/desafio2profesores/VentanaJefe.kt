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

        val fragment = Fragmento()
        replaceFragment(fragment)

    }

    private fun replaceFragment(fragment: Fragmento){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frmFragJefe, fragment)
        fragmentTransaction.commit()
    }

    //PROFESORES--------------------------------------------------------------------

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

    //AULAS--------------------------------------------------------------------

    fun listarTodasAulas(view: View){
        var intentV1 = Intent(this, ListadoAulas::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    fun buscarPorCodAula(view: View){

        var txtCodAula:TextView = findViewById(R.id.txtCodAulaBuscar)

        var intentV1 = Intent(this, ListadoAulas::class.java)

        if (txtCodAula.text.trim().isEmpty()){
            Toast.makeText(this, "Rellene el campo con un codigo de aula.", Toast.LENGTH_SHORT).show()
        }
        else {
            intentV1.putExtra("operacion","buscar")
            intentV1.putExtra("valorBuscar",txtCodAula.text.toString())
            startActivity(intentV1)
        }
    }

    fun borrarPorCodAula(view:View){

        var txtCodAula:TextView = findViewById(R.id.txtCodAulaBuscar)

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.borrarAula(txtCodAula.text.toString())
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

    fun nuevoAula(view:View){
        var intentV1 = Intent(this, EdicionAula::class.java)
        intentV1.putExtra("operacion","nuevo")
        startActivity(intentV1)
    }

    fun modificarAula(view:View){

        var txtCodAula:TextView = findViewById(R.id.txtCodAulaBuscar)

        var intentV1 = Intent(this, EdicionAula::class.java)
        intentV1.putExtra("operacion","modificar")
        intentV1.putExtra("codigo",txtCodAula.text.toString())
        startActivity(intentV1)
    }

    //ORDENADORES--------------------------------------------------------------------

    fun listarTodosOrdenadores(view: View){
        var intentV1 = Intent(this, ListadoOrdenadores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    fun buscarPorCodOrd(view: View){

        var txtCodOrdenador:TextView = findViewById(R.id.txtCodOrdenadorBuscar)

        var intentV1 = Intent(this, ListadoOrdenadores::class.java)

        if (txtCodOrdenador.text.trim().isEmpty()){
            Toast.makeText(this, "Rellene el campo con un codigo de ordenador.", Toast.LENGTH_SHORT).show()
        }
        else {
            intentV1.putExtra("operacion","buscar")
            intentV1.putExtra("valorBuscar",txtCodOrdenador.text.toString())
            startActivity(intentV1)
        }
    }

    fun borrarPorCodOrdenador(view:View){

        var txtCodOrdenador:TextView = findViewById(R.id.txtCodOrdenadorBuscar)

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.borrarOrdenador(txtCodOrdenador.text.toString())
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

    fun nuevoOrdenador(view:View){
        var intentV1 = Intent(this, EdicionOrdenador::class.java)
        intentV1.putExtra("operacion","nuevo")
        startActivity(intentV1)
    }

    fun modificarOrdenador(view:View){

        var txtCodOrdenador:TextView = findViewById(R.id.txtCodOrdenadorBuscar)

        var intentV1 = Intent(this, EdicionOrdenador::class.java)
        intentV1.putExtra("operacion","modificar")
        intentV1.putExtra("codigo",txtCodOrdenador.text.toString())
        startActivity(intentV1)
    }

    fun logout(view: View){
        var intentV1 = Intent(this, MainActivity::class.java)
        startActivity(intentV1)
    }

}