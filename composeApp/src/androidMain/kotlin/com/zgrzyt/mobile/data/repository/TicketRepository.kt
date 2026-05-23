package com.zgrzyt.mobile.data.repository

import com.zgrzyt.mobile.data.api.RetrofitClient
import com.zgrzyt.mobile.data.model.CreateTicketRequest
import com.zgrzyt.mobile.data.model.SendMessageRequest
import com.zgrzyt.mobile.data.model.TicketResponse

class TicketRepository {

    suspend fun getTickets(): TicketResponse =
        try {
            val response = RetrofitClient.api.getTickets(
                token = SessionManager.token ?: ""
            )

            TicketCache.saveTickets(response.data)

            response
        } catch (e: Exception) {
            TicketResponse(
                data = TicketCache.getTickets()
            )
        }

    suspend fun getTicket(id: Int) =
        RetrofitClient.api.getTicket(
            token = SessionManager.token ?: "",
            id = id
        )

    suspend fun getMessages(ticketId: Int) =
        RetrofitClient.api.getMessages(
            token = SessionManager.token ?: "",
            id = ticketId
        )

    suspend fun sendMessage(ticketId: Int, body: String) =
        RetrofitClient.api.sendMessage(
            token = SessionManager.token ?: "",
            id = ticketId,
            request = SendMessageRequest(body)
        )

    suspend fun createTicket(
        title: String,
        description: String,
        priority: String
    ) =
        RetrofitClient.api.createTicket(
            token = SessionManager.token ?: "",
            request = CreateTicketRequest(
                title = title,
                description = description,
                priority = priority
            )
        )
}