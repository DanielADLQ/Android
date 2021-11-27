package Conexion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLIteConexion (context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version){

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("create table Notas(idN integer primary key autoincrement, titulo text, tipo text, fecha text, hora text, texto text)") //160 es el limite de caracteres de un SMS
        db.execSQL("create table Tareas(idT integer primary key autoincrement, idN int, contenido text, estado int, foto text)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

}