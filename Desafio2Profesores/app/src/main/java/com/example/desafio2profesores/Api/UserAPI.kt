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

}