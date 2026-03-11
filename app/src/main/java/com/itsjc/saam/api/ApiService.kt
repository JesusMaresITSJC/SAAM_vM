package com.itsjc.saam.api

import com.itsjc.saam.models.LoginRequest
import com.itsjc.saam.models.LoginResponse
import com.itsjc.saam.models.RegisterRequest
import com.itsjc.saam.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("api/register")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

}