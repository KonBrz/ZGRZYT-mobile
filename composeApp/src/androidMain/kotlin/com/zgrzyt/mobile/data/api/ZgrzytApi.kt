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
import com.zgrzyt.mobile.data.model.Message
import com.zgrzyt.mobile.data.model.SendMessageRequest
import com.zgrzyt.mobile.data.model.CreateTicketRequest

interface ZgrzytApi {

    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("api/tickets")
    suspend fun createTicket(
        @Header("Authorization") token: String,
        @Body request: CreateTicketRequest
    ): Ticket
    @GET("api/tickets")
    suspend fun getTickets(
        @Header("Authorization") token: String
    ): TicketResponse

    @GET("api/tickets/{id}")
    suspend fun getTicket(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Ticket

    @GET("api/tickets/{id}/messages")
    suspend fun getMessages(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): List<Message>

    @POST("api/tickets/{id}/messages")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: SendMessageRequest
    ): retrofit2.Response<Unit>
}