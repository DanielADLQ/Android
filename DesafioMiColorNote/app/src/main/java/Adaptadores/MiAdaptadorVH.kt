package Adaptadores

import Modelo.Nota
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.desafiomicolornote.R

class MiAdaptadorVH: ArrayAdapter<Nota> {

    private var context: Activity
    private var resource: Int
    private var valores: ArrayList<Nota>? = null
    private var seleccionado:Int = -1

    constructor(context: Activity, resource: Int, valores: ArrayList<Nota>, seleccionado:Int) : super(context,resource) {

        this.context = context
        this.resource = resource
        this.valores = valores
        this.seleccionado = seleccionado

    }

    override fun getCount(): Int {
        return this.valores?.size!!
    }

    override fun getItem(position: Int): Nota? {
        return this.valores?.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view: View? = convertView
        var holder = ViewHolder()
        if (view == null) {
            if (this.context!=null) {
                view = context.layoutInflater.inflate(this.resource, null)
                holder.txtTitulo = view.findViewById(R.id.txtTituloNota)
                holder.imagen = view.findViewById(R.id.imgTipoNota)
                holder.txtFecha = view.findViewById(R.id.txtFechaNota)
                holder.txtHora = view.findViewById(R.id.txtHoraNota)

                view.tag = holder
            }
        }
        else {
            holder = view?.tag as ViewHolder
        }



        var valor: Nota = this.valores!![position]
        holder.txtTitulo?.text = valor.titulo
        holder.txtFecha?.text = valor.fecha
        holder.txtHora?.text = valor.hora

        if (valor.tipo == "nota") {
            with(holder.imagen) {
                holder.imagen?.setImageResource(com.example.desafiomicolornote.R.drawable.iconotexto) //PREPARAR IMAGEN
            }

        }
        if (valor.tipo == "lista") {
            with(holder.imagen) {
                holder.imagen?.setImageResource(com.example.desafiomicolornote.R.drawable.iconolista)
            }
        }

        if(position==seleccionado){
            with(holder.txtTitulo){ this?.setTextColor(Color.WHITE)
                this?.textSize=18F
            }
            with(holder.txtFecha){ this?.setTextColor(Color.WHITE) }
            with(holder.txtHora){ this?.setTextColor(Color.WHITE) }
            with(view) {
                this?.setBackgroundResource(R.color.lila)
            }

        }

        return view!!
    }

    class ViewHolder(){
        var txtTitulo: TextView? = null
        var imagen: ImageView? = null
        var txtFecha: TextView? = null
        var txtHora: TextView? = null
    }

}