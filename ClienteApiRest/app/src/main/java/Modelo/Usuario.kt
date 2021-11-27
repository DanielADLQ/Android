package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Usuario(@SerializedName("id")
                   val id: Int? = null,

                   @SerializedName("userId")
                   val userId: Int? = null,

                   @SerializedName("title")
                   val title: String? = null,

                   @SerializedName("body")
                   val body: String? = null) :Serializable{
}