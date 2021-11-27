package com.example.ejercicioapi

import Adaptadores.MiAdaptadorRV
import Api.MainActivityViewModel
import Api.ServiceBuilder
import Api.UserAPI
import Modelo.ListaPokemon
import Modelo.Poke
import Modelo.Pokemon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.nio.charset.Charset

class Listado : AppCompatActivity() {

    /*var personName: ArrayList<String> = ArrayList()
    var emailId: ArrayList<String> = ArrayList()
    var mobileNumbers: ArrayList<String> = ArrayList()*/

    var pokeIds: ArrayList<Int> = ArrayList()
    var pokeName: ArrayList<String> = ArrayList()
    var pokeTipo1: ArrayList<String> = ArrayList()
    var pokeTipo2: ArrayList<String> = ArrayList()
    var pokeImagen: ArrayList<String> = ArrayList()



    lateinit var customAdapter : MiAdaptadorRV
    lateinit var recyclerView : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        recyclerView = findViewById<RecyclerView>(R.id.RVListaPersonas)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager

        val operacion = intent.getStringExtra("operacion").toString()

        if(operacion.equals("leerArchivo")){
            leeFicheroJSON()
        }
        if(operacion.equals("listar")){
            //getUsers()
            getUsers2()
        }
        if(operacion.equals("buscar")){
            val idBuscar = intent.getStringExtra("valorBuscar").toString().toInt()
            //getBuscarUnUsuario(idBuscar)
            getBuscarUnUsuario2(idBuscar)
        }

    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    fun leeFicheroJSON(){
        try {
            val obj = JSONObject(loadJSONFromAsset())
            val userArray = obj.getJSONArray("pokemon")
            for (i in 0 until userArray.length()) {
                val userDetail = userArray.getJSONObject(i)
                pokeIds.add(userDetail.getInt("id"))
                pokeName.add(userDetail.getString("name"))
                pokeTipo1.add(userDetail.getString("tipo1"))
                pokeTipo1.add(userDetail.getString("tipo2"))
                pokeImagen.add(userDetail.getString("imagen"))
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }

        customAdapter = MiAdaptadorRV(this@Listado, pokeIds, pokeName
                //,pokeTipo1,pokeTipo2,pokeImagen
                )
        recyclerView.adapter = customAdapter
    }

    //https://www.tutorialspoint.com/how-to-parse-json-objects-on-android-using-kotlin
    //Otra opción es guardarlo en res/raw folder: https://medium.com/mobile-app-development-publication/assets-or-resource-raw-folder-of-android-5bdc042570e0
    private fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("users_list.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }



    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************
    //Api's públicas:
    //https://pokeapi.co/
    //https://randomuser.me/api/
    //https://cataas.com/#/
    //https://jsonplaceholder.typicode.com/posts
    //https://dog.ceo/dog-api/
    //https://rapidapi.com/collection/list-of-free-apis
    //https://apipheny.io/free-api/#apis-without-key

    //https://medium.com/android-news/consuming-rest-api-using-retrofit-library-in-android-ed47aef01ecb
    //https://ed.team/blog/como-consumir-una-api-rest-desde-android
    //https://dev.to/theimpulson/making-get-requests-with-retrofit2-on-android-using-kotlin-4e4c
    //https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter/issues/24
    fun getUsers() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getPosts()
        viewModel.myResponseList.observe(this, {

            for (poke in it) {
                //Log.d("Fernando", user.userId.toString())
                //Log.d("Fernando", user.id.toString())
                //Log.d("Fernando", user.title.toString())
                //Log.d("Fernando", user.body.toString())
                //runOnUiThread(){
                pokeIds.add(poke.id!!)
                pokeName.add(poke.name.toString())
                //pokeTipo1.add(poke.tipo1.toString())
                //pokeTipo2.add(poke.tipo2.toString())
                //pokeImagen.add(poke.imagen.toString())
                //}
            }
            //Log.e("Fernando",personName.toString())
            customAdapter = MiAdaptadorRV(this@Listado, pokeIds, pokeName)
            recyclerView.adapter = customAdapter
        })

    }

    //--------------------------------------------------------------------------------------------------------

    //https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
    fun getUsers2() {

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUsuarioss()
        call.enqueue(object : Callback<MutableList<Pokemon>>{
            override fun onResponse(call: Call<MutableList<Pokemon>>, response: Response<MutableList<Pokemon>>) {
                Log.e ("Fernando", response.code().toString())

                for (poke in response.body()!!) {
                    pokeIds.add(poke.id!!)
                    pokeName.add(poke.name.toString())

                    //pokeTipo1.add(poke.tipo1.toString())
                    //pokeTipo2.add(poke.tipo2.toString())
                    //pokeImagen.add(poke.imagen.toString())
                    //mobileNumbers.add(poke.body.toString())
                }

                if (response.isSuccessful){


                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@Listado)
                        adapter = MiAdaptadorRV(this@Listado, pokeIds, pokeName
                                //pokeTipo1, pokeTipo2, pokeImagen
                    )
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Pokemon>>, t: Throwable) {
                Toast.makeText(this@Listado, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //************************************************************************************************
    //************************************************************************************************
    //************************************************************************************************

    fun getBuscarUnUsuario(idBusc:Int){
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getPost(idBusc)
        viewModel.myResponse.observe(this, {
            /*Log.d("Fernando", it.body.toString())
            Log.d("Fernando", it.title.toString())
            Log.d("Fernando", it.id.toString())
            Log.d("Fernando", it.userId.toString())*/

            pokeIds.add(it.id!!)
            pokeName.add(it.name.toString())
            //pokeTipo1.add(it.tipo1.toString())
            //pokeTipo2.add(it.tipo2.toString())
            //pokeImagen.add(it.imagen.toString())
            customAdapter = MiAdaptadorRV(this@Listado, pokeIds, pokeName
                    //pokeTipo1, pokeTipo2, pokeImagen
            )
            recyclerView.adapter = customAdapter
        })
    }



    //https://howtodoinjava.com/retrofit2/query-path-parameters/
    fun getBuscarUnUsuario2(idBusc:Int){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnUsuario(idBusc)

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                val poke = response.body()
                if (poke != null) {
                    pokeIds.add(poke.id!!)
                    pokeName.add(poke.name.toString())
                    //pokeTipo1.add(poke.tipo1.toString())
                    //pokeTipo2.add(poke.tipo2.toString())
                    //pokeImagen.add(poke.imagen.toString())
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@Listado)
                        adapter = MiAdaptadorRV(this@Listado, pokeIds, pokeName
                                //pokeTipo1, pokeTipo2, pokeImagen
                        )
                    }
                }
                else {
                    Toast.makeText(this@Listado, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Toast.makeText(this@Listado, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}