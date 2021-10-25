package com.example.t1ej8encuesta

import android.widget.Toast

class Encuestado {
    var nom:String=""
    var so:String=""
    var esp:ArrayList<String> = ArrayList<String>()
    var horasE:String=""

    constructor(nom:String,so:String,esp:ArrayList<String>,horasE:String){
        this.nom=nom
        this.so=so
        this.esp=esp
        this.horasE=horasE
    }

    override fun toString(): String {
        /*var strEsp:String = ""

        for(i in 0..esp.size){
            strEsp = strEsp +" "+ esp[i]

        }*/

        var textoEnc:String = nom + ", " + so + ", " + esp + ", " + horasE
        return textoEnc
    }

}