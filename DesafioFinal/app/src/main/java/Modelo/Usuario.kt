package Modelo

import java.io.Serializable

data class Usuario(var correo:String, var nombre:String, var origen:String, var admin:Boolean, var activado:Boolean):Serializable