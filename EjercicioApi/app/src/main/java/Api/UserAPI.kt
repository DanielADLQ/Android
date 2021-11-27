package Api

import Modelo.ListaPokemon
import Modelo.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {
    /*
Métodos usados para el acceso sin ViewModel.
 */
    @GET("pokemon")
    fun getUsuarioss(): Call<MutableList<Pokemon>>

    @GET("pokemon/{id}")
    fun getUnUsuario(@Path("id") id:Int): Call<Pokemon>



    /*
    Métodos usados para el acceso con ViewModel.
     */
    @GET("pokemon/{id}")
    suspend fun getUsuario(@Path("id") id:Int): Pokemon

    @GET("pokemon")
    suspend fun getUsuarios(): List<Pokemon>
}