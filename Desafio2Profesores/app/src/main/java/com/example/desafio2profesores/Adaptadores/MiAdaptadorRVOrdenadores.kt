package com.example.desafio2profesores.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio2profesores.Modelo.Ordenador
import com.example.desafio2profesores.Modelo.Profesor
import com.example.desafio2profesores.R

class MiAdaptadorRVOrdenadores(private var context: Context,
                               private var ordenadores : ArrayList<Ordenador>
) :
    RecyclerView.Adapter<MiAdaptadorRVOrdenadores.MyViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ordenador_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.codord.text = ordenadores[position].codord
        holder.cpu.text = ordenadores[position].cpu
        holder.ram.text = ordenadores[position].ram
        holder.almacenamiento.text = ordenadores[position].almacenamiento
        holder.aula.text = ordenadores[position].aula

        holder.itemView.setOnClickListener {
            Toast.makeText(context, ordenadores[position].codord, Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        return ordenadores.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var codord: TextView = itemView.findViewById<View>(R.id.txtListaCodord) as TextView
        var cpu: TextView = itemView.findViewById<View>(R.id.txtListaCpu) as TextView
        var ram: TextView = itemView.findViewById<View>(R.id.txtListaRam) as TextView
        var almacenamiento: TextView = itemView.findViewById<View>(R.id.txtListaAlmacenamiento) as TextView
        var aula: TextView = itemView.findViewById<View>(R.id.txtListaAula) as TextView
    }


}