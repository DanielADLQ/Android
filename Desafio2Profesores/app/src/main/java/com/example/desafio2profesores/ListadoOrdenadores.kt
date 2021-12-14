package com.example.desafio2profesores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio2profesores.Adaptadores.MiAdaptadorRVOrdenadores
import com.example.desafio2profesores.Adaptadores.MiAdaptadorRVProfesores
import com.example.desafio2profesores.Api.ServiceBuilder
import com.example.desafio2profesores.Api.UserAPI
import com.example.desafio2profesores.Modelo.Ordenador
import com.example.desafio2profesores.Modelo.Profesor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoOrdenadores : AppCompatActivity() {

    var ordenadores: ArrayList<Ordenador> = ArrayList()

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_ordenadores)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaOrdenadores)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()

        if(operacion.equals("listar")){
            //getUsers()
            getOrdenadores()
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString()
            //getBuscarUnUsuario(idBuscar)
            getBuscarUnUsuario2(idBuscar)
        }
    }

    fun getOrdenadores() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getOrdenadores()

        call.enqueue(object : Callback<MutableList<Ordenador>> {
            override fun onResponse(call: Call<MutableList<Ordenador>>, response: Response<MutableList<Ordenador>>) {
                Log.e ("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    ordenadores.add(Ordenador(post.codord,post.cpu,post.ram,post.almacenamiento,post.aula))
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoOrdenadores)
                        adapter = MiAdaptadorRVOrdenadores(this@ListadoOrdenadores, ordenadores)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Ordenador>>, t: Throwable) {
                Toast.makeText(this@ListadoOrdenadores, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBuscarUnUsuario2(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnOrdenador(idBusc);

        call.enqueue(object : Callback<Ordenador> {
            override fun onResponse(call: Call<Ordenador>, response: Response<Ordenador>) {
                val post = response.body()
                if (post != null) {
                    ordenadores.add(post)
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoOrdenadores)
                        adapter = MiAdaptadorRVOrdenadores(this@ListadoOrdenadores, ordenadores)
                    }
                }
                else {
                    Toast.makeText(this@ListadoOrdenadores, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Ordenador>, t: Throwable) {
                Toast.makeText(this@ListadoOrdenadores, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}