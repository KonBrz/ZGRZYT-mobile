package com.zgrzyt.mobile.data.repository

import com.zgrzyt.mobile.data.model.Ticket

object TicketCache {
    private var tickets: List<Ticket> = emptyList()

    fun saveTickets(newTickets: List<Ticket>) {
        tickets = newTickets
    }

    fun getTickets(): List<Ticket> {
        return tickets
    }

    fun clear() {
        tickets = emptyList()
    }
}