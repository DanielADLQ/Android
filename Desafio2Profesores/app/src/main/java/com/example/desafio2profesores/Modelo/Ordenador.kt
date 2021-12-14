package com.example.desafio2profesores.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ordenador(@SerializedName("codord")
                    val codord: String? = null,

                    @SerializedName("cpu")
                    val cpu: String? = null,

                    @SerializedName("ram")
                    val ram: String? = null,

                     @SerializedName("almacenamiento")
                     val almacenamiento: String? = null,

                    @SerializedName("aula")
                    val aula: String? = null) : Serializable {
}