package com.example.desafio2profesores.Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Aula(@SerializedName("codaula")
                    val codaula: String? = null,

                    @SerializedName("nomaula")
                    val nomaula: String? = null) : Serializable {
}