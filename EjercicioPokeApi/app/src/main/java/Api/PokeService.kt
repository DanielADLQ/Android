package Api

import Modelo.PokeResults
import Modelo.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokeService {

    /*
Métodos usados para el acceso sin ViewModel.
 */
    @GET("pokemon?limit=100&offset=151")
    fun getUsuarioss(): Call<PokeResults>

    @GET("pokemon/{id}")
    fun getUnUsuario(@Path("id") id:Int): Call<Pokemon>



    /*
    Métodos usados para el acceso con ViewModel.
     */
    @GET("pokemon/{id}")
    suspend fun getUsuario(@Path("id") id:Int): Pokemon

    @GET("pokemon?limit=151&offset=0")
    suspend fun getUsuarios(): List<Pokemon>

}

