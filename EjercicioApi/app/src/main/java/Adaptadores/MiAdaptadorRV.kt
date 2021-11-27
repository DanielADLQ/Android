package Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicioapi.R

class MiAdaptadorRV (private var context: Context,
                     private var pokeIds: ArrayList<Int>,
                     private var pokeName: ArrayList<String>
                     //private var pokeTipo1: ArrayList<String>,
                     //private var pokeTipo2: ArrayList<String>,
                     //private var pokeImagen: ArrayList<String>,
) :
    RecyclerView.Adapter<MiAdaptadorRV.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        for(i in 0..pokeIds.size){
            holder.id.text = pokeIds[i].toString()
            holder.name.text = pokeName[i]
            //holder.tipo1.text = pokeTipo1[position]
            //holder.tipo2.text = pokeTipo2[position]
            //holder.imagen = pokeImagen[position]
            holder.itemView.setOnClickListener {
                Toast.makeText(context, pokeName[i], Toast.LENGTH_SHORT).show()
            }
        }


    }
    override fun getItemCount(): Int {
        return pokeName.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById<View>(R.id.txtId) as TextView
        var name: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        var tipo1: TextView = itemView.findViewById<View>(R.id.txtTipo1) as TextView
        var tipo2: TextView = itemView.findViewById<View>(R.id.txtTipo2) as TextView
        var imagen: ImageView = itemView.findViewById<View>(R.id.imgPoke) as ImageView
    }
    }