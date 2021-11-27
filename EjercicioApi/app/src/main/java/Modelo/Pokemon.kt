package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(@SerializedName("id")
                   val id: Int? = null,

                   @SerializedName("name")
                   val name: String? = null/*,

                   @SerializedName("tipo1")
                   val tipo1: String? = null,

                   @SerializedName("tipo2")
                   val tipo2: String? = null,

                   @SerializedName("imagen")
                   val imagen: String? = null*/) :Serializable{
}


