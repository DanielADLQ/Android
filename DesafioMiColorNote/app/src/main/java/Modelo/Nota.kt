package Modelo

import java.io.Serializable
import java.util.*

data class Nota(var idN:Int, var titulo:String,var tipo:String,var fecha:String,var hora:String, var texto:String):
    Serializable {
    //lateinit var fecha:Date
}