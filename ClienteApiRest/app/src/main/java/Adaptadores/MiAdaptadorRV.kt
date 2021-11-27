package Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.clienteapirest.R

class MiAdaptadorRV (private var context: Context,
                            private var personNames: ArrayList<String>,
                            private var emailIds: ArrayList<String>,
                            private var mobileNumbers: ArrayList<String>
) :
RecyclerView.Adapter<MiAdaptadorRV.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        holder.name.text = personNames[position]
        holder.email.text = emailIds[position]
        holder.mobileNo.text = mobileNumbers[position]
        holder.itemView.setOnClickListener {
            Toast.makeText(context, personNames[position], Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        return personNames.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById<View>(R.id.txtNombre) as TextView
        var email: TextView = itemView.findViewById<View>(R.id.txtEmail) as TextView
        var mobileNo: TextView = itemView.findViewById<View>(R.id.txtMovil) as TextView
    }
}