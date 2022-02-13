package com.example.desafio2profesores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafio2profesores.Adaptadores.MiAdaptadorRVProfesores
import com.example.desafio2profesores.Api.ServiceBuilder
import com.example.desafio2profesores.Api.UserAPI
import com.example.desafio2profesores.Modelo.Profesor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var profesores: ArrayList<Profesor> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = Fragmento()
        replaceFragment(fragment)

    }

    private fun replaceFragment(fragment: Fragmento){
        val fragmentTransaction =supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frmFragLogin, fragment)
        fragmentTransaction.commit()
    }

    fun login(view: View){

        var txtCodigo:TextView = findViewById(R.id.txtCodigo)
        var cod = txtCodigo.text.toString()


        profesores.clear()
        getBuscarUnUsuario2(cod)

    }

    fun getBuscarUnUsuario2(idBusc:String){

        var txtClave:TextView = findViewById(R.id.txtClave)


        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnProfesor(idBusc);

        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                val post = response.body()
                if (post != null) {
                    profesores.add(post)
                }
                if (response.isSuccessful){

                    if(profesores.size == 0){
                        Toast.makeText(this@MainActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }else{
                        //Encuentra un usuario con el id indicado, ahora comprueba si la clave es correcta
                        if(profesores[0].clave != txtClave.text.toString()){
                            Toast.makeText(this@MainActivity, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                        }else{
                            //Login correcto, falta comprobar su rol para abrir la ventana correspondiente
                            if(profesores[0].rol == "0"){ //Profe normal

                                var intentV1 = Intent(this@MainActivity, VentanaProfe::class.java)
                                startActivity(intentV1)
                            }else{ //Jefe
                                var intentV1 = Intent(this@MainActivity, VentanaJefe::class.java)
                                startActivity(intentV1)
                            }
                        }
                    }

                }
                else {
                    Toast.makeText(this@MainActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}