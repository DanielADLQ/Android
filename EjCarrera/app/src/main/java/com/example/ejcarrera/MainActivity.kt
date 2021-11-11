package com.example.ejcarrera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var J1:EditText = findViewById(R.id.txtJ1)
        var J2:EditText = findViewById(R.id.txtJ2)
        var J3:EditText = findViewById(R.id.txtJ3)
        var J4:EditText = findViewById(R.id.txtJ4)

        var botonComenzar:Button=findViewById(R.id.btnEnviar)

        botonComenzar.setOnClickListener(){
            if(J1.text.trim().isEmpty() || J2.text.trim().isEmpty() || J3.text.trim().isEmpty() || J4.text.trim().isEmpty()){
                Toast.makeText(this,"Rellena los 4 participantes",Toast.LENGTH_LONG).show()
            }else{
                var intentJugadores:Intent=Intent(this,carrera::class.java)
                intentJugadores.putExtra("jugador1",J1.text.toString())
                intentJugadores.putExtra("jugador2",J2.text.toString())
                intentJugadores.putExtra("jugador3",J3.text.toString())
                intentJugadores.putExtra("jugador4",J4.text.toString())
                startActivity(intentJugadores)


            }
        }


    }

    fun confirmarParticipantes(view:View){

    }

}