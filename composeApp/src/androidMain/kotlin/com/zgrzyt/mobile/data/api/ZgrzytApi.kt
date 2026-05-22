package com.zgrzyt.mobile.data.api

import com.zgrzyt.mobile.data.model.AuthResponse
import com.zgrzyt.mobile.data.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ZgrzytApi {

    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse
}