package Modelo

import java.io.Serializable
import kotlin.collections.ArrayList

data class Contacto(var idC:String, var nombre:String, var telefonos:ArrayList<String>?):
    Serializable {
}