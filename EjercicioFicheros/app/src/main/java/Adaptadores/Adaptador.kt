package Adaptadores

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.ejercicioficheros.R

class Adaptador:ArrayAdapter<String> {
    private var context: Activity
    private var resource: Int
    private var valores: Array<String>? = null
    private var seleccionado:Int = 0

    constructor(context: Activity, resource: Int, valores: Array<String>, seleccionado:Int) : super(context, resource) {
        this.context = context
        this.resource = resource
        this.valores = valores
        this.seleccionado = seleccionado
    }

    override fun getCount(): Int {
        return this.valores?.size!!
    }

    override fun getItem(position: Int): String? {
        return this.valores?.get(position)
    }

    /**
     * Este método se carga para cada elemento de la lista. Tanta llamada a findviewbyid hace que se pueda
     * ralentizar y que caiga el rendimiento.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if (this.context != null) {
            view = context.layoutInflater.inflate(this.resource, null)
            var txtItem: TextView = view.findViewById(R.id.txtNomFich)
            var valor: String = this.valores!![position]
            txtItem.text = valor
            //if (valor < 5) {
            if (position == seleccionado) {
                with(txtItem) {
                    setTextColor(Color.BLUE)
                    setBackgroundResource(R.color.lila)
                }
            }
        }
        return view!!
    }
}