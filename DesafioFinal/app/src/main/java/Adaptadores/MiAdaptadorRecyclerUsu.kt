package Adaptadores

import Modelo.Usuario
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MiAdaptadorRecyclerUsu (var usuarios : ArrayList<Usuario>,var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerUsu.ViewHolder>(){

    companion object {
        var seleccionado: Int = -1
    }

    /**
     * onBindViewHolder() se encarga de coger cada una de las posiciones de la lista de personajes y pasarlas a la clase
     * ViewHolder(clase interna, ver abajo) para que esta pinte todos los valores y active el evento onClick en cada uno.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.recycler_gestion_usu, parent, false))
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {

        return usuarios.size
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
        val correo = view.findViewById(R.id.txtCorreoUsu) as TextView
        val admin = view.findViewById(R.id.txtAdminUsu) as TextView
        val activado = view.findViewById(R.id.txtActivadoUsu) as TextView

        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(
            usuario: Usuario,
            context: Context,
            pos: Int,
            miAdaptadorRecycler: MiAdaptadorRecyclerUsu
        ) {
            correo.text = usuario.correo
            admin.text = usuario.admin.toString()
            activado.text = usuario.activado.toString()

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

            val db = Firebase.firestore
            val TAG = "Daniel"

            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == MiAdaptadorRecyclerUsu.seleccionado) {
                        MiAdaptadorRecyclerUsu.seleccionado = -1
                    } else {
                        MiAdaptadorRecyclerUsu.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                    /*Toast.makeText(
                        context,
                        "Valor seleccionado " + MiAdaptadorRecyclerAsis.seleccionado.toString()
                        Toast.LENGTH_SHORT
                    ).show()*/

                    if(!usuario.activado){

                        val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                        dialogo.setPositiveButton("Activar",
                            DialogInterface.OnClickListener { dialog, which ->

                                db.collection("usuarios").document(usuario.correo)
                                    .update("activado",true)
                                    .addOnSuccessListener { Log.d(TAG,"Usuario activado") }
                                    .addOnFailureListener {e -> Log.w(TAG,"Error al activar usuario",e)}

                                //miAdaptadorRecycler.notifyItemRemoved(pos)

                                //miAdaptadorRecycler.eventos.removeAt(pos)
                                miAdaptadorRecycler.notifyDataSetChanged()

                            })
                        dialogo.setNegativeButton(
                            "Cancelar",
                            DialogInterface.OnClickListener { dialog, which ->
                                //Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()

                                dialog.dismiss()
                            })
                        dialogo.setTitle("Activacion")
                        dialogo.setMessage("¿Desea activar este usuario?")
                        dialogo.show()

                        true

                    }else{ //Opcion de desactivar al usuario

                        val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                        dialogo.setPositiveButton("Desactivar",
                            DialogInterface.OnClickListener { dialog, which ->

                                db.collection("usuarios").document(usuario.correo)
                                    .update("activado",false)
                                    .addOnSuccessListener { Log.d(TAG,"Usuario desactivado") }
                                    .addOnFailureListener {e -> Log.w(TAG,"Error al desactivar usuario",e)}

                                //miAdaptadorRecycler.notifyItemRemoved(pos)

                                //miAdaptadorRecycler.eventos.removeAt(pos)
                                miAdaptadorRecycler.notifyDataSetChanged()

                            })
                        dialogo.setNegativeButton(
                            "Cancelar",
                            DialogInterface.OnClickListener { dialog, which ->
                                //Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()

                                dialog.dismiss()
                            })
                        dialogo.setTitle("Desactivacion")
                        dialogo.setMessage("¿Desea desactivar este usuario?")
                        dialogo.show()

                        true

                    }


                })

        }
    }

}