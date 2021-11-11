package Auxiliar

import android.widget.ProgressBar
import android.widget.TextView
import kotlin.random.Random

class Hilo(var bp:ProgressBar,var cajaPosicion:TextView): Thread() {
    var parar:Boolean=false

    companion object{
        var hilosLlegada:Int=0
    }

    override fun run(){
        while(!parar){
            bp.progress += Random.nextInt(1,11)
            Thread.sleep(1000)
            if(bp.progress>=bp.max){
                //bp.progress=0
                sumaLlegada()
                pararHilo()

            }
        }
    }

    fun pararHilo(){
        this.parar=true
    }

    @Synchronized fun sumaLlegada(){ //Problema: Aun con syhchronized a veces llegan iguales
        Hilo.hilosLlegada++
        cajaPosicion.text=Hilo.hilosLlegada.toString()
    }
}