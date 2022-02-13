package Adaptadores

import Auxiliar.ConexionBD
import Auxiliar.Parametro
import Modelo.Tarea
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiomicolornote.R

class MiAdaptadorRecycler(var tareas : ArrayList<Tarea>,var  context: Context, var ventana:AppCompatActivity) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>()   {

    companion object {

        var seleccionado:Int = -1
        var tareaSel:Int = -1
        private val cameraRequest = 1888

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tareas.get(position)
        holder.bind(item, context, position, this,ventana)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.lista_tareas,parent,false))
    }

    override fun getItemCount(): Int {

        return tareas.size
    }


    //--------------------------------- Clase interna ViewHolder -----------------------------------

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nomTarea = view.findViewById(R.id.txtTitTarea) as TextView
        val imagenEstado = view.findViewById(R.id.imgEstado) as ImageView
        val imagenFoto = view.findViewById(R.id.imgFotoTarea) as ImageView

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
                val uri2 = ventana.getFilesDir().getPath() +"/"+ tar.idT+".jpg" //La foto sera un jpg con el nombre del id de la tarea a la que corresponde

                var fotoCamara: Bitmap = BitmapFactory.decodeFile(uri2)
                imagenFoto.setImageBitmap(fotoCamara)
            }

            itemView.setOnClickListener(
                View.OnClickListener
                {

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
                            val uri = "@drawable/crossboli" //Tarea sin hacer
                            val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                            var res: Drawable = context.resources.getDrawable(imageResource)
                            imagenEstado.setImageDrawable(res)
                        }
                    }

                    ConexionBD.modTarea(ventana,tar)

                    miAdaptadorRecycler.notifyDataSetChanged()

                })

            itemView.setOnLongClickListener(View.OnLongClickListener {

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogo.setPositiveButton(R.string.txtEliminar,
                    DialogInterface.OnClickListener { dialog, which ->

                        ConexionBD.delTarea(
                            ventana,
                            ConexionBD.obtenerTareas(ventana, idGrupoTareas).get(pos).idT
                        )
                        //miAdaptadorRecycler.notifyItemRemoved(pos)

                        miAdaptadorRecycler.tareas.removeAt(pos)
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

                    seleccionado = pos
                    tareaSel = tar.idT

                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(ventana,cameraIntent, cameraRequest,null)

                })

        }

    }

}