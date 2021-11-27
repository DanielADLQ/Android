package Api

import Modelo.User
import Modelo.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface UserAPI {

    /*
    Métodos usados para el acceso sin ViewModel.
     */
    @GET("posts")
    fun getUsuarioss(): Call<MutableList<Usuario>>

    @GET("posts/{id}")
    fun getUnUsuario(@Path("id") id:Int): Call<Usuario>



    /*
    Métodos usados para el acceso con ViewModel.
     */
    @GET("posts/{id}")
    suspend fun getUsuario(@Path("id") id:Int): Usuario

    @GET("posts")
    suspend fun getUsuarios(): List<Usuario>
}