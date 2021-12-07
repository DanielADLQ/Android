package com.example.desafio2profesores.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profesor(@SerializedName("codigo")
                   val codigo: String? = null,

                   @SerializedName("nya")
                   val nya: String? = null,

                   @SerializedName("rol")
                   val rol: String? = null,

                   @SerializedName("clave")
                   val clave: String? = null) : Serializable {
}