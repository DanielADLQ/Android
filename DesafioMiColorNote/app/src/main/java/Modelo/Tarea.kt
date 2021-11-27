package Modelo

import java.io.Serializable

data class Tarea(var idT:Int, var idN:Int,var contenido:String,var estado:Int,var foto:String):
    Serializable {
}
