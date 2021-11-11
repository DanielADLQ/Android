package Adaptadores

import Modelo.Encuestado
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ejencuesta_3ventanas.R

class MiAdaptadorVH: ArrayAdapter<Encuestado> {

    private var context: Activity
    private var resource: Int
    private var valores: ArrayList<Encuestado>? = null
    private var seleccionado:Int = 0

    constructor(context: Activity, resource: Int, valores: ArrayList<Encuestado>, seleccionado:Int) : super(context, resource) {
        this.context = context
        this.resource = resource
        this.valores = valores
        this.seleccionado = seleccionado
    }

    override fun getCount(): Int {
        return this.valores?.size!!
    }

    override fun getItem(position: Int): Encuestado? {
        return this.valores?.get(position)
    }

    /**
     * Este método se carga para cada elemento de la lista. Tanta llamada a findviewbyid hace que se pueda
     * ralentizar y que caiga el rendimiento.
     */
    /**
     * Este método se carga para cada elemento de la lista. Tanta llamada a findviewbyid hace que se pueda
     * ralentizar y que caiga el rendimiento.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        var holder = ViewHolder()
        if (view == null) {
            if (this.context!=null) {
                view = context.layoutInflater.inflate(this.resource, null)
                holder.txtNombreS = view.findViewById(R.id.txtNombreEnc)
                holder.txtSO = view.findViewById(R.id.txtSOEnc)
                holder.imagen = view.findViewById(R.id.imgSO)

                view.tag = holder
            }
        }
        else {
            holder = view?.tag as ViewHolder
        }

        var valor: Encuestado = this.valores!![position]
        holder.txtNombreS?.text = valor.nom
        holder.txtSO?.text = valor.so

        if (holder.txtSO?.text == "Linux") {
            with(holder.imagen) {
                holder.imagen?.setImageResource(com.example.ejencuesta_3ventanas.R.drawable.linux)
            }
        }
        if (holder.txtSO?.text == "Windows") {
            with(holder.imagen) {
                holder.imagen?.setImageResource(com.example.ejencuesta_3ventanas.R.drawable.windows)
            }
        }
        if (holder.txtSO?.text == "Mac") {
            with(holder.imagen) {
                holder.imagen?.setImageResource(com.example.ejencuesta_3ventanas.R.drawable.mac)
            }
        }

        return view!!
    }

    class ViewHolder(){
        var txtNombreS: TextView? = null
        var txtSO: TextView? = null
        var imagen: ImageView? = null
    }


}