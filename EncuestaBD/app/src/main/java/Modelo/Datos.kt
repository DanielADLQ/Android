package Modelo

object Datos { // En este ejercicio no se usa

    var listadoEnc:ArrayList<Encuestado> = ArrayList<Encuestado>()

    fun aniadirEnc(e:Encuestado){
        listadoEnc.add(e)
    }

    fun cambiarSO(pos:Int,so:String){
        this.listadoEnc[pos].so=so
    }

}