package Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejerciciopokeapi.R

class MiAdaptadorRV (private var context: Context,
                     private var pokemonNames: ArrayList<String>,
                     //private var pokemonIds: ArrayList<String>,
                     //private var pokemonHeights: ArrayList<String>,
                     //private var pokemonWeights: ArrayList<String>,
                     private var pokemonURLs: ArrayList<String>,
                     private var idBusc:Int
) :
    RecyclerView.Adapter<MiAdaptadorRV.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        holder.name.text = pokemonNames[position]
        //holder.id.text = pokemonIds[position]
        //holder.height.text = pokemonHeights[position]
        //holder.weight.text = pokemonWeights[position]
        //holder.url.text = pokemonURLs[position]

        holder.itemView.setOnClickListener {
            Toast.makeText(context, pokemonNames[position], Toast.LENGTH_SHORT).show()
        }

        if(idBusc==-1){
            Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${position+152}.png") //152 es el numero del primer pokemon que quiero mostrar
                .centerCrop()
                .into(holder.fotoPoke)
        }else{
            Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${idBusc}.png")
                .centerCrop()
                .into(holder.fotoPoke)
        }

    }
    override fun getItemCount(): Int {
        return pokemonNames.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        //var id: TextView = itemView.findViewById<View>(R.id.txtId) as TextView
        //var height: TextView = itemView.findViewById<View>(R.id.txtHeight) as TextView
        //var weight: TextView = itemView.findViewById<View>(R.id.txtWeight) as TextView
        //var url: TextView = itemView.findViewById<View>(R.id.txtURL) as TextView
        var fotoPoke:ImageView=itemView.findViewById(R.id.imgFotoPoke) as ImageView

    }

}