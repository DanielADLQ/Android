package com.example.desafio2profesores.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio2profesores.Modelo.Aula
import com.example.desafio2profesores.R

class MiAdaptadorRVAulas (private var context: Context,
                          private var aulas : ArrayList<Aula>
) :
    RecyclerView.Adapter<MiAdaptadorRVAulas.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.aula_card, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MiAdaptadorRVAulas.MyViewHolder, position: Int) {
        holder.codaula.text = aulas[position].codaula
        holder.nomaula.text = aulas[position].nomaula

        holder.itemView.setOnClickListener {
            Toast.makeText(context, aulas[position].codaula, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return aulas.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var codaula: TextView = itemView.findViewById<View>(R.id.txtListaCodaula) as TextView
        var nomaula: TextView = itemView.findViewById<View>(R.id.txtListaNomaula) as TextView
    }


}