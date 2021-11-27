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

        /*if(n.tipo == "nota"){
            registro.put("texto","")
        }

        if(n.tipo == "lista"){
            registro.put("texto","")
        }*/

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

    /*fun modPersona(contexto:AppCompatActivity, dni:String, p:Persona):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nombre", p.nombre)
        registro.put("edad", p.edad)
        val cant = bd.update("personas", registro, "dni='${dni}'", null)
        bd.close()
        return cant
    }


    fun buscarPersona(contexto: AppCompatActivity, dni:String):Persona? {
        var p:Persona? = null
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery(
            "select nombre,edad from personas where dni='${dni}'",
            null
        )
        if (fila.moveToFirst()) {
            p = Persona(dni, fila.getString(0), fila.getInt(1))
        }
        bd.close()
        return p
    }*/

    /*fun cambiarSO(contexto: AppCompatActivity, id:Int, soNuevo:String):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        /*val fila = bd.rawQuery("update Encuestas set so='${soNuevo}' where idEnc='${id}'", null)
        fila.moveToNext()*/

        val registro = ContentValues()

        registro.put("so",soNuevo)

        val cant = bd.update("Encuestas",registro,"idEnc='${id}'",null)

        bd.close()
        return cant
    }*/

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

    fun modFoto(contexto:AppCompatActivity, t:Tarea):Int {
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("foto", t.foto)
        val cant = bd.update("Tareas", registro, "idT='${t.idT}'", null)

        bd.close()
        return cant
    }



fun seleccionarIDEnc(db: SQLiteDatabase):Int{

//Numero de registros
val fila = db.rawQuery("select count(idEnc) from Encuestas", null)
var numRegistros:Int=0

while(fila.moveToNext()){
    numRegistros=fila.getInt(0)
}

//OBTENER ID PARA EL REGISTRO NUEVO
var id:Int = 0
var idMax:Int = 0

if(numRegistros==0){
    id = 1
}else{

    val filaMax = db.rawQuery("select max(idEnc) from Encuestas group by idEnc", null)

    while(filaMax.moveToNext()){
        idMax = filaMax.getInt(0)
    }
    id = idMax + 1
}
return id

}

fun seleccionarIDEspEnc(db: SQLiteDatabase):Int{

val fila = db.rawQuery("select count(idEspEleg) from EspElegida", null)
var numRegistros:Int=0

while(fila.moveToNext()){
    numRegistros=fila.getInt(0)
}
var id:Int = 0
var idMax:Int = 0
if(numRegistros==0){
    id = 1
}else{

    val filaMax = db.rawQuery("select max(idEspEleg) from EspElegida group by idEspEleg", null)

    while(filaMax.moveToNext()){
        idMax = filaMax.getInt(0)
    }
    id = idMax + 1

}
return id
}

}