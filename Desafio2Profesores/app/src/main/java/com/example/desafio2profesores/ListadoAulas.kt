package com.example.desafio2profesores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio2profesores.Adaptadores.MiAdaptadorRVAulas
import com.example.desafio2profesores.Adaptadores.MiAdaptadorRVProfesores
import com.example.desafio2profesores.Api.ServiceBuilder
import com.example.desafio2profesores.Api.UserAPI
import com.example.desafio2profesores.Modelo.Aula
import com.example.desafio2profesores.Modelo.Profesor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoAulas : AppCompatActivity() {

    var aulas: ArrayList<Aula> = ArrayList()

    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_aulas)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaAulas)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()

        if(operacion.equals("listar")){
            getAulas()
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString()
            getBuscarUnAula(idBuscar)
        }

    }

    fun getAulas() {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getAulas()

        call.enqueue(object : Callback<MutableList<Aula>> {
            override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {
                Log.e ("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    aulas.add(Aula(post.codaula,post.nomaula))
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoAulas)
                        adapter = MiAdaptadorRVAulas(this@ListadoAulas, aulas)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                Toast.makeText(this@ListadoAulas, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBuscarUnAula(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnAula(idBusc);

        call.enqueue(object : Callback<Aula> {
            override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                val post = response.body()
                if (post != null) {
                    aulas.add(post)
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoAulas)
                        adapter = MiAdaptadorRVAulas(this@ListadoAulas, aulas)
                    }
                }
                else {
                    Toast.makeText(this@ListadoAulas, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Aula>, t: Throwable) {
                Toast.makeText(this@ListadoAulas, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}