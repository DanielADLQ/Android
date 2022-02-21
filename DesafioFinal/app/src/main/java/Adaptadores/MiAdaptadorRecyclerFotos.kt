package Adaptadores

import Auxiliar.InfoLogin
import Modelo.Usuario
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.desafiofinal.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class MiAdaptadorRecyclerFotos(var fotos : ArrayList<StorageReference>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecyclerFotos.ViewHolder>(){
    companion object {
        var seleccionado: Int = -1
    }

    /**
     * onBindViewHolder() se encarga de coger cada una de las posiciones de la lista de personajes y pasarlas a la clase
     * ViewHolder(clase interna, ver abajo) para que esta pinte todos los valores y active el evento onClick en cada uno.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = fotos.get(position)
        holder.bind(item, context, position, this)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.recyclerfotos, parent, false))
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {

        return fotos.size
    }


    //--------------------------------- Clase interna ViewHolder -----------------------------------
    /**
     * La clase ViewHolder. No es necesaria hacerla dentro del adapter, pero como van tan ligadas
     * se puede declarar aquí.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgV = view.findViewById(R.id.imgFotoSubida) as ImageView

        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(
            foto: StorageReference,
            context: Context,
            pos: Int,
            miAdaptadorRecycler: MiAdaptadorRecyclerFotos
        ) {

            val storage = Firebase.storage
            val storageRef = storage.reference

            //var fotoRef = storageRef.child(foto)

            val ONE_MEGABYTE: Long = 1024 * 1024
            foto.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                Glide.with(context)
                    .load(it)
                    .into(imgV)
            }.addOnFailureListener {

            }

            if (foto.parent!!.name == InfoLogin.usu.correo){
                with(imgV){
                    setBackgroundResource(R.color.lightblue)
                }
            }



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

                    if(foto.parent!!.name == InfoLogin.usu.correo){

                        Log.e("FOTOS","Pulsada")

                        val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                        dialogo.setPositiveButton("Borrar",
                            DialogInterface.OnClickListener { dialog, which ->

                                foto.delete()
                                miAdaptadorRecycler.notifyItemRemoved(pos)
                                miAdaptadorRecycler.fotos.removeAt(pos)
                                miAdaptadorRecycler.notifyDataSetChanged()

                            })
                        dialogo.setNegativeButton(
                            "Cancelar",
                            DialogInterface.OnClickListener { dialog, which ->
                                //Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()

                                dialog.dismiss()
                            })
                        dialogo.setTitle("Eliminar foto")
                        dialogo.setMessage("¿Desea borrar esta foto?")
                        dialogo.show()

                        true

                    }else{
                        Toast.makeText(context,"Esta foto ha sido subida por "+foto.parent!!.name,Toast.LENGTH_LONG).show()
                    }

                })

        }
    }
}