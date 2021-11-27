package Modelo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListaPokemon(@SerializedName("results") @Expose var listaPokemon: List<Poke>) {
}