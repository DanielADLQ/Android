package Adaptadores

import Auxiliar.ConexionBD
import Modelo.Nota
import Modelo.Tarea
import android.app.AlertDialog
import android.app.AppComponentFactory
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiomicolornote.ListaTareas
import com.example.desafiomicolornote.MainActivity
import com.example.desafiomicolornote.R
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MiAdaptadorRecycler(var tareas : ArrayList<Tarea>,var  context: Context, var ventana:AppCompatActivity) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>()   {


    interface onItemClickListener {
        //Método que gestiona el click encima de un elemento
        fun onItemClick(position: Int)
    }

    companion object {
        //Esta variable estática nos será muy útil para saber cual está marcado o no.
        var seleccionado:Int = -1
        private val cameraRequest = 1888
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
        val item = tareas.get(position)
        holder.bind(item, context, position, this,ventana)
    }

    /**
     *  Como su nombre indica lo que hará será devolvernos un objeto ViewHolder al cual le pasamos la celda que hemos creado.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.lista_tareas,parent,false))
    }

    /**
     * getItemCount() nos devuelve el tamaño de la lista, que lo necesita el RecyclerView.
     */
    override fun getItemCount(): Int {

        return tareas.size
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
        val nomTarea = view.findViewById(R.id.txtTitTarea) as TextView
        val imagenEstado = view.findViewById(R.id.imgEstado) as ImageView
        val imagenFoto = view.findViewById(R.id.imgFotoTarea) as ImageView
        /**
         * Éste método se llama desde el método onBindViewHolder de la clase contenedora. Como no vuelve a crear un objeto
         * sino que usa el ya creado en onCreateViewHolder, las asociaciones findViewById no vuelven a hacerse y es más eficiente.
         */
        fun bind(tar: Tarea, context: Context, pos: Int, miAdaptadorRecycler: MiAdaptadorRecycler,ventana: AppCompatActivity){

            var idGrupoTareas = tar.idN

            nomTarea.text = tar.contenido

            //ESTADO INICIAL
            if(tar.estado==0){ //Tarea sin hacer
                val uri = "@drawable/crossboli"
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                imagenEstado.setImageDrawable(res)
                nomTarea.setTextColor(context.resources.getColor(R.color.black))
            }else{
                val uri = "@drawable/tickboli" //Tarea ya hecha
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                imagenEstado.setImageDrawable(res)
                nomTarea.setTextColor(context.resources.getColor(R.color.gris))
            }


            //SELECCIONAR FOTO
            if (tar.foto.equals("")){ //Poner foto por defecto

                val uri2 = "@drawable/iconofotodefecto"
                val imageResource: Int = context.getResources().getIdentifier(uri2, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                imagenFoto.setImageDrawable(res)

            }else{
                val uri2 = "@drawable/" + tar.foto.toLowerCase()
                val imageResource: Int = context.getResources().getIdentifier(uri2, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                imagenFoto.setImageDrawable(res)
            }


            //Se levanta una escucha para cada item. Si pulsamos el seleccionado pondremos la selección a -1, en otro caso será el nuevo sleccionado.
            itemView.setOnClickListener(
                View.OnClickListener
                {

                    //Para marcar o desmarcar al seleccionado usamos el siguiente código.
                    if (tar.estado == 0) { //Hay que marcarla como hecha
                        tar.estado=1
                        with(nomTarea) {
                            this.setTextColor(resources.getColor(R.color.gris))
                        }

                        with(imagenEstado){
                            val uri = "@drawable/tickboli" //Tarea ya hecha
                            val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                            var res: Drawable = context.resources.getDrawable(imageResource)
                            imagenEstado.setImageDrawable(res)
                        }

                    }
                    else { //Hay que desmarcarla
                        tar.estado=0
                        with(nomTarea) {
                            this.setTextColor(resources.getColor(R.color.black))
                        }
                        with(imagenEstado){
                            val uri = "@drawable/crossboli" //Tarea ya hecha
                            val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                            var res: Drawable = context.resources.getDrawable(imageResource)
                            imagenEstado.setImageDrawable(res)
                        }
                    }

                    ConexionBD.modTarea(ventana,tar)

                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                    //Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecycler.seleccionado.toString(), Toast.LENGTH_SHORT).show()
                })

            itemView.setOnLongClickListener(View.OnLongClickListener {

                //MiAdaptadorRecycler.seleccionado = pos

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogo.setPositiveButton(R.string.txtEliminar,
                    DialogInterface.OnClickListener { dialog, which ->

                        ConexionBD.delTarea(
                            ventana,
                            ConexionBD.obtenerTareas(ventana, idGrupoTareas).get(pos).idT
                        )

                        miAdaptadorRecycler.notifyDataSetChanged()
                    })
                dialogo.setNegativeButton(
                    R.string.txtCancelar,
                    DialogInterface.OnClickListener { dialog, which ->
                        //Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()

                        dialog.dismiss()
                    })
                dialogo.setTitle(R.string.txtConfBorrado)
                dialogo.setMessage(R.string.txtDeseaElim)
                dialogo.show()


                true
            })

            imagenFoto.setOnClickListener(
                View.OnClickListener
                {

                    seleccionado=pos

                    fun tomarFoto(view: View){


                        //val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        //startActivityForResult(cameraIntent, cameraRequest)


                        /*var intentFoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        var foto = File(getExternalFilesDir(null), edNombre.text.toString())
                        intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto))
                        startActivity(ventana,intentFoto,null)*/

                    }

                    //https://es.stackoverflow.com/questions/33561/c%C3%B3mo-guardar-un-imageview-en-android
                    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                        super.onActivityResult(requestCode, resultCode, data)
                        try {
                            if (requestCode == cameraRequest) {
                                val photo: Bitmap = data?.extras?.get("data") as Bitmap
                                imagen.setImageBitmap(photo)

                                var fotoFichero = File(getExternalFilesDir(null), edNombre.text.toString())
                                var uri = Uri.fromFile(fotoFichero)
                                var fileOutStream = FileOutputStream(fotoFichero)
                                photo.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                                fileOutStream.flush();
                                fileOutStream.close();
                            }
                        }catch(e: Exception){
                            Log.e("Fernando",e.toString())
                        }
                    }

                    fun recuperarFoto(view: View){
                        var bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null).toString() + "/"+edNombre.text.toString());
                        imagen.setImageBitmap(bitmap1);
                    }*/


                    //---------------------------------------------------------------------------Usar ventana aqui???

                    /*
                    val intento1: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                    //val foto: File = File(context.getExternalFilesDir(), tar.idT.toString()+".jpg")

                    val foto: File = File("/data/data/com.example.desafiomicolornote/files", tar.idT.toString()+".jpg")

                    //intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto))

                    startActivity(ventana,intento1,intento1.extras)
                    //startActivityForResult(ventana,intento1, cameraRequest,intento1.extras)

                    //val bitmap1 = BitmapFactory.decodeFile(
                    //    context.getExternalFilesDir().toString() + "/" + tar.idT.toString()+".jpg")

                    val bitmap1 = BitmapFactory.decodeFile(
                        "/data/data/com.example.desafiomicolornote/files"+ "/" + tar.idT.toString()+".jpg")

                    imagenFoto.setImageBitmap(bitmap1)


                     */



                    //Para marcar o desmarcar al seleccionado usamos el siguiente código.
                    /*if (tar.estado == 0) { //Hay que marcarla como hecha
                        tar.estado=1
                        with(nomTarea) {
                            this.setTextColor(resources.getColor(R.color.gris))
                        }

                        with(imagenEstado){
                            val uri = "@drawable/tickboli" //Tarea ya hecha
                            val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                            var res: Drawable = context.resources.getDrawable(imageResource)
                            imagenEstado.setImageDrawable(res)
                        }

                    }
                    else { //Hay que desmarcarla
                        tar.estado=0
                        with(nomTarea) {
                            this.setTextColor(resources.getColor(R.color.black))
                        }
                        with(imagenEstado){
                            val uri = "@drawable/crossboli" //Tarea ya hecha
                            val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                            var res: Drawable = context.resources.getDrawable(imageResource)
                            imagenEstado.setImageDrawable(res)
                        }


                    }



                    ConexionBD.modTarea(ventana,tar)

                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                    Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecycler.seleccionado.toString(), Toast.LENGTH_SHORT).show()

                    Toast.makeText(context, "Hola foto " +  MiAdaptadorRecycler.seleccionado.toString(), Toast.LENGTH_SHORT).show()
                    */
                })


        }


    }

}