package Conexion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class AdminSQLIteConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table Encuestas(idEnc int primary key, nombre text, so text, horas int)")
        db.execSQL("create table Especialidades(idEsp int primary key, descripcion text)")
        db.execSQL("create table EspElegida(idEspEleg int primary key, idEnc int, idEsp int)")

        db.execSQL("insert into Especialidades values(1,'DAM')")
        db.execSQL("insert into Especialidades values(2,'ASIR')")
        db.execSQL("insert into Especialidades values(3,'DAW')")


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }


}