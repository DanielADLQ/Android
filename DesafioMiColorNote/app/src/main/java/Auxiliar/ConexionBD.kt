package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Nota
import Modelo.Tarea
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

object ConexionBD {

    var nombreBD = Parametro.nombreBD


    fun cambiarBD(nombreBD:String){
        Parametro.nombreBD=nombreBD
        this.nombreBD = nombreBD
    }

    fun addNota(contexto: AppCompatActivity, n: Nota){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val registro = ContentValues()
        registro.putNull("idN")
        registro.put("titulo",n.titulo)
        registro.put("tipo", n.tipo)
        registro.put("fecha", n.fecha)
        registro.put("hora", n.hora)
        registro.put("texto",n.texto)

        bd.insert("Notas", null, registro)


        bd.close()
    }

    fun delNota(contexto: AppCompatActivity, idN: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("Notas", "idN='${idN}'", null)
        bd.delete("Tareas", "idN='${idN}'", null) //Simulo un delete on cascade
        bd.close()
        return cant //0 si no consigue borrar, 1 si si lo consigue
    }

    fun cambiarTexto(contexto: AppCompatActivity, id:Int, textoNuevo:String):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        /*val fila = bd.rawQuery("update Encuestas set so='${soNuevo}' where idEnc='${id}'", null)
        fila.moveToNext()*/

        val registro = ContentValues()
        registro.put("texto",textoNuevo)

        val cant = bd.update("Notas",registro,"idN='${id}'",null)

        bd.close()
        return cant
    }

    fun obtenerNotas(contexto: AppCompatActivity):ArrayList<Nota>{

        var listadoNotas:ArrayList<Nota> = ArrayList(0)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select idN,titulo,tipo,fecha,hora,texto from Notas", null)

        while (fila.moveToNext()) {

            var n:Nota = Nota(fila.getInt(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),fila.getString(5))

            listadoNotas.add(n)

        }
        bd.close()

        return listadoNotas
    }

    fun addTarea(contexto: AppCompatActivity, t: Tarea){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val registro = ContentValues()
        registro.putNull("idT")
        registro.put("idN",t.idN)
        registro.put("contenido", t.contenido)
        registro.put("estado", t.estado)
        registro.put("foto", t.foto)


        bd.insert("Tareas", null, registro)

        bd.close()
    }


    fun obtenerTareas(contexto: AppCompatActivity,seleccionado:Int):ArrayList<Tarea>{

        var listadoTareas:ArrayList<Tarea> = ArrayList(0)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select idT,idN,contenido,estado,foto from Tareas where idN='${seleccionado}'", null)

        while (fila.moveToNext()) {


            var t:Tarea = Tarea(fila.getInt(0),fila.getInt(1),fila.getString(2),fila.getInt(3),fila.getString(4))

            listadoTareas.add(t)

        }

        bd.close()

        return listadoTareas
    }

    fun delTarea(contexto: AppCompatActivity, idT: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("Tareas", "idT='${idT}'", null)
        bd.close()
        return cant //0 si no consigue borrar, 1 si si lo consigue
    }

    fun modTarea(contexto:AppCompatActivity, t:Tarea):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("estado", t.estado)
        val cant = bd.update("Tareas", registro, "idT='${t.idT}'", null)

        bd.close()
        return cant
    }

    fun modFoto(contexto:AppCompatActivity, tId:Int):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("foto", tId)
        val cant = bd.update("Tareas", registro, "idT='${tId}'", null)

        bd.close()
        return cant
    }

}