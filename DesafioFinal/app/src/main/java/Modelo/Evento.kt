package Modelo

import java.io.Serializable

data class Evento (var id:String, var nombre:String, var ubicacion:String, var fecha:String, var hora:String, var lat:Double, var long:Double, var asistentes:ArrayList<Asistente>, var comentarios:ArrayList<Comentario>, var ubicaciones:ArrayList<Ubicacion>):Serializable