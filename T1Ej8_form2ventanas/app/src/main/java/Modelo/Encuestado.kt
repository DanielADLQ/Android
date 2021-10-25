package Modelo

import java.io.Serializable

data class Encuestado(var nom:String,var so:String,var esp:ArrayList<String>,var horasE:String):Serializable{

    override fun toString(): String {

        var textoEnc:String = nom + ", " + so + ", " + esp + ", " + horasE
        return textoEnc
    }
}
