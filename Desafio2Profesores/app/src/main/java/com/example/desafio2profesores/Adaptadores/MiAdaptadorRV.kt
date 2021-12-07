package com.example.desafio2profesores.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio2profesores.Modelo.Profesor
import com.example.desafio2profesores.R

class MiAdaptadorRV (private var context: Context,
                     private var profesores : ArrayList<Profesor>
) :
    RecyclerView.Adapter<MiAdaptadorRV.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.profesor_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.codigo.text = profesores[position].codigo
        holder.nya.text = profesores[position].nya
        holder.rol.text = profesores[position].rol

        holder.itemView.setOnClickListener {
            Toast.makeText(context, profesores[position].codigo, Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        return profesores.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var codigo: TextView = itemView.findViewById<View>(R.id.txtListaCodigo) as TextView
        var nya: TextView = itemView.findViewById<View>(R.id.txtListaNya) as TextView
        var rol: TextView = itemView.findViewById<View>(R.id.txtListaRol) as TextView
    }


}