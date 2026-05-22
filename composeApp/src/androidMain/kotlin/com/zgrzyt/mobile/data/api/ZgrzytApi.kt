package com.zgrzyt.mobile.data.api

import com.zgrzyt.mobile.data.model.AuthResponse
import com.zgrzyt.mobile.data.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST
import com.zgrzyt.mobile.data.model.TicketResponse
import retrofit2.http.GET
import retrofit2.http.Header
import com.zgrzyt.mobile.data.model.Ticket
import retrofit2.http.Path

interface ZgrzytApi {

    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse
    @GET("api/tickets")
    suspend fun getTickets(
        @Header("Authorization") token: String
    ): TicketResponse

    @GET("api/tickets/{id}")
    suspend fun getTicket(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Ticket
}