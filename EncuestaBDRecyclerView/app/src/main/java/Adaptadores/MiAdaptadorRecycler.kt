package Adaptadores

import Modelo.Encuestado
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.encuestabdrecyclerview.R
import android.graphics.drawable.Drawable

class MiAdaptadorRecycler(var encuestados : ArrayList<Encuestado>,var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>()  {

    companion object {
        //Esta variable estática nos será muy útil para saber cual está marcado o no.
        var seleccionado:Int = -1
        /*
        PAra marcar o desmarcar un elemento de la lista lo haremos diferente a una listView. En la listView el listener
        está en la activity por lo que podemos controlar desde fuera el valor de seleccionado y pasarlo al adapter, asociamos
        el adapter a la listview y resuelto.
        En las RecyclerView usamos para pintar cada elemento la función bind (ver código más abajo, en la clase ViewHolder).
        Esto se carga una vez, solo una vez, de ahí la eficiencia de las RecyclerView. Si queremos que el click que hagamos
        se vea reflejado debemos recargar la lista, para ello forzamos la recarga con el método: notifyDataSetChanged().
         */
    }


    /**
     * onBindViewHolder() se encarga de coger cada una de las posiciones de la lista de personajes y pasarlas a la clase
     * ViewHolder(clase interna, ver abajo) para que esta pinte todos los valores y active el evento onClick en cada uno.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = encuestados.get(position)
        holder.bind(item, context, position, this)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.listado_personas_recycler,parent,false))
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {

        return encuestados.size
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
        val nombreEnc = view.findViewById(R.id.txtNombreEnc) as TextView
        val soEnc = view.findViewById(R.id.txtSOEnc) as TextView
        val imagenSO = view.findViewById(R.id.imgSO) as ImageView
        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(enc: Encuestado, context: Context, pos: Int, miAdaptadorRecycler: MiAdaptadorRecycler){
            nombreEnc.text = enc.nom
            soEnc.text = enc.so

            if (enc.so.equals("Windows") || enc.so.equals("Linux") || enc.so.equals("Mac")){
                //Para Gandalf, como ejemplo, le he puesto una imagen que se llama igual en el drawable.
                //se podría hacer igual para los otros personajes.

                val uri = "@drawable/" + enc.so.toLowerCase() //La imagen se llama igual que el so
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                imagenSO.setImageDrawable(res)


                /*
                //--------------- Redondear la imagen ------------------
                //Este redondeo sería necesario desde código si usamos una layout para pintar cada item.
                //Si usamos una cardView, las cardView ya incorporan métodos que redondean las imágenes.

                //La parte de redondear la imagen está sin funcionar aún. Se deja como tarea de investigación.
                //Extraemos el drawable en un bitmap
                val originalBitmap = (res as BitmapDrawable).bitmap

                //Creamos el drawable redondeado
                val roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), originalBitmap)

                //Asignamos el CornerRadius
                roundedDrawable.cornerRadius = originalBitmap.height.toFloat()
                avatar.setImageDrawable(res)
                */
            }



            //Para marcar o desmarcar al seleccionado usamos el siguiente código.
            if (pos == MiAdaptadorRecycler.seleccionado) {
                with(nombreEnc) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(nombreEnc) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(
                View.OnClickListener
            {
                if (pos == MiAdaptadorRecycler.seleccionado){
                    MiAdaptadorRecycler.seleccionado = -1
                }
                else {
                    MiAdaptadorRecycler.seleccionado = pos
                }
                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                miAdaptadorRecycler.notifyDataSetChanged()

                Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecycler.seleccionado.toString(), Toast.LENGTH_SHORT).show()
            })
        }
    }

}