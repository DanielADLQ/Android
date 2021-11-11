package Modulo

import Modelo.Encuestado

object Datos {

    var listadoEnc:ArrayList<Encuestado> = ArrayList<Encuestado>()

    fun aniadirEnc(e:Encuestado){
        listadoEnc.add(e)
    }

    fun cambiarSO(pos:Int,so:String){
        this.listadoEnc[pos].so=so
    }

}