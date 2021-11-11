package Auxiliar

import android.util.Log
import java.io.*
import java.lang.StringBuilder
import androidx.appcompat.app.AppCompatActivity

class Fichero (var nombreFichero:String = "", var contexto: AppCompatActivity){

    fun cambiarFichero(nf:String){
        nombreFichero = nf
    }

    fun existeFichero():Boolean{
        return this.contexto.fileList().contains(nombreFichero)
    }

    fun escribirLinea(linea:String){
        try {
            val archivo = FileWriter(File(this.contexto.filesDir, nombreFichero), true) //true para añadir, false para sobreescribir
            //archivo.write(linea + "\r\n")
            archivo.use { it.write(linea + "\r\n") }
            archivo.close()
        } catch (e: IOException) {
            Log.e("Fernando",e.toString())
        }
    }

    fun leerFichero():String {
        val archivo = InputStreamReader(this.contexto.openFileInput(nombreFichero))
        val br = BufferedReader(archivo)
        var linea = br.readLine()
        val todo = StringBuilder()

        if (linea == null) {
            todo.append("No hay contenido en el fichero.")
        } else {
            while (linea != null) {
                todo.append(linea + "\n")
                linea = br.readLine()
            }
            br.close()
            archivo.close()
        }
        return todo.toString()
    }

    fun borrarFichero(){
        try {
            val archivo = FileWriter(File(this.contexto.filesDir, nombreFichero))
            archivo.close()
        } catch (e: IOException) {
            Log.e("Fernando",e.toString())
        }
    }

    fun borrarFicheroFisico(){
        this.contexto.deleteFile(nombreFichero)
    }

}