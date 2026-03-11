package com.itsjc.saam.api

import com.itsjc.saam.models.LoginRequest
import com.itsjc.saam.models.LoginResponse
import com.itsjc.saam.models.RegisterRequest
import com.itsjc.saam.models.RegisterResponse
import com.itsjc.saam.models.GenericResponse
import com.itsjc.saam.models.DatosRequest
import com.itsjc.saam.models.Registro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("api/register")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @POST("api/datos")
    fun recibirDatos(
        @Body datos: DatosRequest
    ): Call<GenericResponse>

    @POST("api/guardar")
    fun guardarRegistro(): Call<GenericResponse>

    @GET("api/registros")
    fun obtenerRegistros(): Call<List<Registro>>

    @DELETE("api/registros/{id}")
    fun eliminarRegistro(
        @Path("id") id: Int
    ): Call<Void>
}