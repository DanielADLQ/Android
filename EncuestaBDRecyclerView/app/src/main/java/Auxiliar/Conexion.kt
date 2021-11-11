package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Encuestado
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

object Conexion {

    var nombreBD = Parametro.nombreBD


    fun cambiarBD(nombreBD:String){
        this.nombreBD = nombreBD
    }

    fun addPersona(contexto: AppCompatActivity, e: Encuestado){
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        //var numId:Int = admin.seleccionarIDEnc(bd)

        val registro = ContentValues()
        registro.put("idEnc", e.id)
        registro.put("nombre",e.nom)
        registro.put("so", e.so)
        registro.put("horas", e.horasE)
        bd.insert("Encuestas", null, registro)

        val registroEsp = ContentValues()
        var numIdEspEleg:Int = 0
        for(esp in e.esp) {

            if (esp == "DAM") {
                numIdEspEleg = this.seleccionarIDEspEnc(bd)
                registroEsp.put("idEspEleg",numIdEspEleg)
                registroEsp.put("idEnc",e.id)
                registroEsp.put("idEsp",1)
                bd.insert("EspElegida", null, registroEsp)
            }
            if (esp == "ASIR") {
                numIdEspEleg = this.seleccionarIDEspEnc(bd)
                registroEsp.put("idEspEleg",numIdEspEleg)
                registroEsp.put("idEnc",e.id)
                registroEsp.put("idEsp",2)
                bd.insert("EspElegida", null, registroEsp)
            }
            if (esp == "DAW") {
                numIdEspEleg = this.seleccionarIDEspEnc(bd)
                registroEsp.put("idEspEleg",numIdEspEleg)
                registroEsp.put("idEnc",e.id)
                registroEsp.put("idEsp",3)
                bd.insert("EspElegida", null, registroEsp)
            }

        }


        bd.close()
    }

    fun delPersona(contexto: AppCompatActivity, idEnc: Int):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase
        val cant = bd.delete("Encuestas", "idEnc='${idEnc}'", null)
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

    fun cambiarSO(contexto: AppCompatActivity, id:Int, soNuevo:String):Int{
        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        /*val fila = bd.rawQuery("update Encuestas set so='${soNuevo}' where idEnc='${id}'", null)
        fila.moveToNext()*/

        val registro = ContentValues()

        registro.put("so",soNuevo)

        val cant = bd.update("Encuestas",registro,"idEnc='${id}'",null)

        bd.close()
        return cant
    }

    fun obtenerPersonas(contexto: AppCompatActivity):ArrayList<Encuestado>{

        var listaEncuestados:ArrayList<Encuestado> = ArrayList(0)

        val admin = AdminSQLIteConexion(contexto, nombreBD, null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select idEnc,nombre,so,horas from Encuestas", null)

        while (fila.moveToNext()) { //Cada vuelta es un encuestado
            val listaEspecialidades = ArrayList<String>(0)
            val filaEsp = bd.rawQuery("select Especialidades.descripcion from Especialidades join EspElegida on EspElegida.idEsp=Especialidades.idEsp and EspElegida.idEnc='${fila.getString(0)}'", null)

            while(filaEsp.moveToNext()){
                listaEspecialidades.add(filaEsp.getString(0))
            }

            var e:Encuestado = Encuestado(fila.getString(1),fila.getString(2),listaEspecialidades,fila.getString(3),fila.getInt(0))

            listaEncuestados.add(e)

        }
        bd.close()

        return listaEncuestados
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