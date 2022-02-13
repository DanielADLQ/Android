package com.example.desafio2profesores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio2profesores.Adaptadores.MiAdaptadorRVAulas
import com.example.desafio2profesores.Api.ServiceBuilder
import com.example.desafio2profesores.Api.UserAPI
import com.example.desafio2profesores.Modelo.Aula
import com.example.desafio2profesores.Modelo.Ordenador
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EdicionOrdenador : AppCompatActivity() {

    lateinit var nuevoCodord : TextView
    lateinit var nuevoCpu : TextView
    lateinit var nuevoRam : TextView
    lateinit var nuevoAlmacenamiento : TextView
    lateinit var nuevoAula : TextView

    lateinit var botonAceptar : Button
    var contexto = this
    lateinit var operacion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edicion_ordenador)

        val fragment = Fragmento()
        replaceFragment(fragment)

        nuevoCodord = findViewById(R.id.edCodordNuevo)
        nuevoCpu = findViewById(R.id.edCpuNuevo)
        nuevoRam = findViewById(R.id.edRamNuevo)
        nuevoAlmacenamiento = findViewById(R.id.edAlmacenamientoNuevo)
        nuevoAula = findViewById(R.id.edAulaNuevo)

        botonAceptar = findViewById(R.id.btnAceptarNuevoOrd)

        operacion = intent.getStringExtra("operacion").toString()
        val codBuscar = intent.getStringExtra("codigo").toString()
        if (operacion.equals("modificar")) {
            getBuscarUnOrdenador(codBuscar)
            nuevoCodord.isEnabled =
                false  //No dejamos modificar el codord que es la clave del registro.
        }

        //var aulas: ArrayList<Aula> = ArrayList()
        var codigosAula: ArrayList<String> = ArrayList()
        //Obtener aulas

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getAulas()

        var check=0

        call.enqueue(object : Callback<MutableList<Aula>> {
            override fun onResponse(
                call: Call<MutableList<Aula>>,
                response: Response<MutableList<Aula>>
            ) {
                Log.e("Fernando", response.code().toString())
                for (post in response.body()!!) {
                    //aulas.add(Aula(post.codaula, post.nomaula))
                    codigosAula.add(post.codaula!!)
                }
                if (response.isSuccessful){

                    //Spinner con las aulas

                    var miSp: Spinner = findViewById(R.id.spAulas)
                    val adaptador2 = ArrayAdapter(this@EdicionOrdenador, R.layout.item_spinner, R.id.txtSpinner, codigosAula)
                    miSp.adapter = adaptador2

                    //check=0
                    miSp.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            vista: View?,
                            pos: Int,
                            idElemento: Long
                        ) {
                            if(check != 0){
                                var aulaSel = codigosAula.get(pos)
                                nuevoAula.text = aulaSel
                            }else{
                                check++
                            }

                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                    })


                }
            }

            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                //Toast.makeText(this@ListadoAulas, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun replaceFragment(fragment: Fragmento){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frmedord, fragment)
        fragmentTransaction.commit()
    }

    fun cancelar(view: View){
        finish()
    }

    fun aceptar(view: View) {
        val us = Ordenador(
            nuevoCodord.text.toString(),
            nuevoCpu.text.toString(),
            nuevoRam.text.toString(),
            nuevoAlmacenamiento.text.toString(),
            nuevoAula.text.toString()
        )


        //--------------- Añadir nuevo registro -----------------
        if (operacion.equals("nuevo")) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.addOrdenador(us)
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
            val call = request.modOrdenador(us)
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

    fun getBuscarUnOrdenador(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnOrdenador(idBusc);

        call.enqueue(object : Callback<Ordenador> {
            override fun onResponse(call: Call<Ordenador>, response: Response<Ordenador>) {
                val post = response.body()
                if (post != null) {
                    nuevoCodord.append(post.codord)
                    nuevoCpu.append(post.cpu)
                    nuevoRam.append(post.ram)
                    nuevoAlmacenamiento.append(post.almacenamiento)
                    nuevoAula.append(post.aula)
                }
                else {
                    Toast.makeText(this@EdicionOrdenador, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    botonAceptar.isEnabled = false
                }
            }
            override fun onFailure(call: Call<Ordenador>, t: Throwable) {
                Toast.makeText(this@EdicionOrdenador, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
