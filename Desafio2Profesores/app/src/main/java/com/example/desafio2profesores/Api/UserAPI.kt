package com.example.desafio2profesores.Api

import com.example.desafio2profesores.Modelo.Profesor
import com.example.desafio2profesores.Modelo.Aula
import com.example.desafio2profesores.Modelo.Ordenador

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("profesores")
    fun getProfesores(): Call<MutableList<Profesor>>

    @GET("profesores/{codigo}")
    fun getUnProfesor(@Path("codigo") codigo:String): Call<Profesor>

    @DELETE("profesores/borrar/{codigo}")
    fun borrarProfesor(@Path("codigo") codigo:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("profesores/registrar")
    fun addProfesor(@Body info: Profesor) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("profesores/modificar")
    fun modProfesor(@Body info: Profesor) : Call<ResponseBody>


    @GET("aulas")
    fun getAulas(): Call<MutableList<Aula>>

    @GET("aulas/{codaula}")
    fun getUnAula(@Path("codaula") codigo:String): Call<Aula>

    @DELETE("aulas/borrar/{codaula}")
    fun borrarAula(@Path("codaula") codaula:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("aulas/registrar")
    fun addAula(@Body info: Aula) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("aulas/modificar")
    fun modAula(@Body info: Aula) : Call<ResponseBody>


    @GET("ordenadores")
    fun getOrdenadores(): Call<MutableList<Ordenador>>

    @GET("ordenadores/{codord}")
    fun getUnOrdenador(@Path("codord") codigo:String): Call<Ordenador>

    @DELETE("ordenadores/borrar/{codord}")
    fun borrarOrdenador(@Path("codord") codord:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("ordenadores/registrar")
    fun addOrdenador(@Body info: Ordenador) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("ordenadores/modificar")
    fun modOrdenador(@Body info: Ordenador) : Call<ResponseBody>

}