package com.example.ejerciciopokeapi

import Adaptadores.MiAdaptadorRV
import Api.MainActivityViewModel
import Api.ServiceBuilder
import Api.PokeService
import Modelo.PokeResults
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

class ListadoActivity : AppCompatActivity() {

    var pokemonName: ArrayList<String> = ArrayList()
    //var pokemonId: ArrayList<String> = ArrayList()
    //var pokemonHeight: ArrayList<String> = ArrayList()
    //var pokemonWeight: ArrayList<String> = ArrayList()
    var pokemonURL: ArrayList<String> = ArrayList()
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
                pokemonName.add(userDetail.getString("name"))
                //pokemonId.add(userDetail.getString("id"))
                //pokemonHeight.add(userDetail.getString("height"))
                //pokemonWeight.add(userDetail.getString("weight"))
                //val contact = userDetail.getJSONObject("contact")
                //mobileNumbers.add(contact.getString("mobile"))
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }

        customAdapter = MiAdaptadorRV(this@ListadoActivity, pokemonName, pokemonURL,-1)
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
                pokemonName.add(poke.name.toString())
                //pokemonId.add(poke.id.toString())
                //pokemonHeight.add(poke.height.toString())
                //pokemonWeight.add(poke.weight.toString())
                pokemonName.add(poke.name.toString())
                //mobileNumbers.add(user.body.toString())
                //}
            }
            //Log.e("Fernando",personName.toString())
            customAdapter = MiAdaptadorRV(this@ListadoActivity, pokemonName, pokemonURL,-1)
            recyclerView.adapter = customAdapter
        })

    }

    //--------------------------------------------------------------------------------------------------------

    //https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
    fun getUsers2() {
        val request = ServiceBuilder.buildService(PokeService::class.java)
        val call = request.getUsuarioss()

        call.enqueue(object : Callback<PokeResults> {
            override fun onResponse(call: Call<PokeResults>, response: Response<PokeResults>) {
                Log.e ("Fernando", response.code().toString())
                /*for (post in response.body()!!) {



                    /*pokemonName.add(post.name.toString())
                    pokemonId.add(post.id.toString())
                    pokemonHeight.add(post.height.toString())
                    pokemonWeight.add(post.weight.toString())*/

                }*/
                val post = response.body()
                if (post != null) {
                    //pokemonName.add(post.name.toString())
                    //pokemonId.add(post.id.toString())
                    //pokemonHeight.add(post.height.toString())
                    //pokemonWeight.add(post.weight.toString())
                    //emailId.add(post.title.toString())
                    //mobileNumbers.add(post.body.toString())

                    for(poke in response.body()!!.listaP){
                        pokemonName.add(poke.name.toString())
                        //pokemonId.add(poke.id.toString())
                        //pokemonHeight.add(poke.height.toString())
                        //pokemonWeight.add(poke.weight.toString())
                        pokemonURL.add(poke.url.toString())
                    }

                }

                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, pokemonName, pokemonURL,-1)
                    }
                }
            }
            override fun onFailure(call: Call<PokeResults>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
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
            //Log.d("Fernando", it.body.toString())
            //Log.d("Fernando", it.title.toString())
            //Log.d("Fernando", it.id.toString())
            //Log.d("Fernando", it.userId.toString())

            pokemonName.add(it.name.toString())
            //pokemonId.add(it.id.toString())
            //pokemonHeight.add(it.height.toString())
            //pokemonWeight.add(it.weight.toString())
            pokemonURL.add(it.url.toString())
            customAdapter = MiAdaptadorRV(this@ListadoActivity, pokemonName, pokemonURL,idBusc)
            recyclerView.adapter = customAdapter
        })
    }



    //https://howtodoinjava.com/retrofit2/query-path-parameters/
    fun getBuscarUnUsuario2(idBusc:Int){
        val request = ServiceBuilder.buildService(PokeService::class.java)
        val call = request.getUnUsuario(idBusc);

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                val post = response.body()
                if (post != null) {
                    pokemonName.add(post.name.toString())
                    //pokemonId.add(post.id.toString())
                    //pokemonHeight.add(post.height.toString())
                    //pokemonWeight.add(post.weight.toString())
                    pokemonURL.add(post.url.toString())
                    //emailId.add(post.title.toString())
                    //mobileNumbers.add(post.body.toString())
                }
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@ListadoActivity)
                        adapter = MiAdaptadorRV(this@ListadoActivity, pokemonName, pokemonURL,idBusc)
                    }
                }
                else {
                    Toast.makeText(this@ListadoActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Toast.makeText(this@ListadoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}