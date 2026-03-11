package com.itsjc.saam.api

import com.itsjc.saam.models.LoginRequest
import com.itsjc.saam.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

}