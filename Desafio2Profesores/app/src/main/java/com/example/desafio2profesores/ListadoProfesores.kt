package com.example.desafio2profesores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio2profesores.Adaptadores.MiAdaptadorRV
import com.example.desafio2profesores.Api.ServiceBuilder
import com.example.desafio2profesores.Api.UserAPI
import com.example.desafio2profesores.Modelo.Profesor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoProfesores : AppCompatActivity() {

    var profesores: ArrayList<Profesor> = ArrayList()

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_profesores)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaProfesores)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()

        if(operacion.equals("listar")){
            //getUsers()
            getUsers2()
        }
        /*if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString()
            //getBuscarUnUsuario(idBuscar)
            getBuscarUnUsuario2(idBuscar)
        }*/
    }

    fun getUsers2() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUsuarioss()

        call.enqueue(object : Callback<MutableList<Profesor>> {
            override fun onResponse(call: Call<MutableList<Profesor>>, response: Response<MutableList<Profesor>>) {
                Log.e ("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    profesores.add(Profesor(post.codigo,post.nya,post.rol,post.clave))
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoProfesores)
                        adapter = MiAdaptadorRV(this@ListadoProfesores, profesores)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Profesor>>, t: Throwable) {
                Toast.makeText(this@ListadoProfesores, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}