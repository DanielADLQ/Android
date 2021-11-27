package Modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon (@SerializedName("url")
                    val url: String? = null,
                    @SerializedName("name")
                    val name: String? = null):Serializable{

                    }

