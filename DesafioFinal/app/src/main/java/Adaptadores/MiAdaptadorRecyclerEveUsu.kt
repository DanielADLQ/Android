package Adaptadores

import Modelo.Evento
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.InfoEventoSeleccionado
import com.example.desafiofinal.MapsActivity
import com.example.desafiofinal.R

class MiAdaptadorRecyclerEveUsu(var eventos : ArrayList<Evento>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerEveUsu.ViewHolder>() {

    companion object {
        var seleccionado: Int = -1

    }

    /**
     * onBindViewHolder() se encarga de coger cada una de las posiciones de la lista de personajes y pasarlas a la clase
     * ViewHolder(clase interna, ver abajo) para que esta pinte todos los valores y active el evento onClick en cada uno.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = eventos.get(position)
        holder.bind(item, context, position, this)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.recyclereventos, parent, false))
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {

        return eventos.size
    }


    //--------------------------------- Clase interna ViewHolder -----------------------------------
    /**
     * La clase ViewHolder. No es necesaria hacerla dentro del adapter, pero como van tan ligadas
     * se puede declarar aquí.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Esto solo se asocia la primera vez que se llama a la clase, en el método onCreate de la clase que contiene a esta.
        //Por eso no hace falta que hagamos lo que hacíamos en el método getView de los adaptadores para las listsViews.
        //val nombrePersonaje = view.findViewById(R.id.txtNombre) as TextView
        //val tipoPersonaje = view.findViewById(R.id.txtTipo) as TextView
        //val avatar = view.findViewById(R.id.imgImagen) as ImageView

        //Como en el ejemplo general de las listas (ProbandoListas) vemos que se puede inflar cada elemento con una card o con un layout.
        //val dni = view.findViewById(R.id.txtDni) as TextView
        val nombre = view.findViewById(R.id.txtNom) as TextView
        val ubicacion = view.findViewById(R.id.txtUbi) as TextView
        val fecha = view.findViewById(R.id.txtFecha) as TextView
        val hora = view.findViewById(R.id.txtHora) as TextView

        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(
            evento: Evento,
            context: Context,
            pos: Int,
            miAdaptadorRecycler: MiAdaptadorRecyclerEveUsu
        ) {
            nombre.text = evento.nombre
            ubicacion.text = evento.ubicacion
            fecha.text = evento.fecha
            hora.text = evento.hora

            /*
            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecycler.seleccionado) {
                with(nombre) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
                with(ubicacion) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
                with(fecha) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
                with(hora) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            } else {
                with(nombre) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                with(ubicacion) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                with(fecha) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                with(hora) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }

             */
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.

            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == MiAdaptadorRecyclerEveUsu.seleccionado) {
                        MiAdaptadorRecyclerEveUsu.seleccionado = -1
                    } else {
                        MiAdaptadorRecyclerEveUsu.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                    /*Toast.makeText(
                        context,
                        "Valor seleccionado " + MiAdaptadorRecycler.seleccionado.toString(),
                        Toast.LENGTH_SHORT
                    ).show()*/

                    var intentV1 = Intent(context, InfoEventoSeleccionado::class.java)

                    intentV1.putExtra("ev",evento)
                    context.startActivity(intentV1)
                    (context as Activity).finish()
                })

            itemView.setOnLongClickListener(View.OnLongClickListener {


                var intentV1 = Intent(context, MapsActivity::class.java)
                intentV1.putExtra("lat",evento.lat.toString())
                intentV1.putExtra("long",evento.long.toString())
                intentV1.putExtra("ev",evento)
                context.startActivity(intentV1)

                true
            })
        }
    }




}