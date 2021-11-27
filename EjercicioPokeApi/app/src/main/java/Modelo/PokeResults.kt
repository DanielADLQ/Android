package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokeResults(@SerializedName("results")
                       val listaP: ArrayList<Pokemon> = ArrayList()) {
}