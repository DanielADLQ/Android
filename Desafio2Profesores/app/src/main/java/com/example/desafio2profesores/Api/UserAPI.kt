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
    fun getUsuarioss(): Call<MutableList<Profesor>>

}