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

class VentanaProfe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_profe)

        val fragment = Fragmento()
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragmento){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frmFragmento, fragment)
        fragmentTransaction.commit()
    }


    //PROFESORES--------------------------------------------------------------------

    fun listarTodosProfesores(view: View){
        var intentV1 = Intent(this, ListadoProfesores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }


    //AULAS--------------------------------------------------------------------

    fun listarTodasAulas(view: View){
        var intentV1 = Intent(this, ListadoAulas::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }

    //ORDENADORES--------------------------------------------------------------------

    fun listarTodosOrdenadores(view: View){
        var intentV1 = Intent(this, ListadoOrdenadores::class.java)
        intentV1.putExtra("operacion","listar")
        startActivity(intentV1)
    }


    fun logout(view: View){
        var intentV1 = Intent(this, MainActivity::class.java)
        startActivity(intentV1)
    }

}