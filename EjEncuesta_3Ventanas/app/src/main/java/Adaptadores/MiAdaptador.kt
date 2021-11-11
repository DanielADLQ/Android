package Adaptadores

import Modelo.Encuestado
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ejencuesta_3ventanas.R

class MiAdaptador: ArrayAdapter<Encuestado> {

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
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if (this.context != null) {
            view = context.layoutInflater.inflate(this.resource, null)
            var txtNombreS: TextView = view.findViewById(R.id.txtNombreEnc)
            var txtSO: TextView = view.findViewById(R.id.txtSOEnc)
            var imagen: ImageView = view.findViewById(R.id.imgSO)
            var valor: Encuestado = this.valores!![position]
            txtNombreS.text = valor.nom
            txtSO.text = valor.so

            if (txtSO.text == "Linux") {
                with(imagen) {
                    imagen.setImageResource(R.drawable.linux)
                }
            }
            if (txtSO.text == "Windows") {
                with(imagen) {
                    imagen.setImageResource(R.drawable.windows)
                }
            }
            if (txtSO.text == "Mac") {
                with(imagen) {
                    imagen.setImageResource(R.drawable.mac)
                }
            }

        }
        return view!!
    }
}