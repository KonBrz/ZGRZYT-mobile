package com.example.zgrzyt_apk.data.model

import com.zgrzyt.mobile.data.model.Ticket
import com.zgrzyt.mobile.data.model.TicketResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class TicketResponseTest {

    @Test
    fun ticketResponse_shouldStoreListOfTickets() {
        val tickets = listOf(
            Ticket(
                id = 1,
                title = "Awaria komputera",
                description = "Komputer nie uruchamia się.",
                status = "open",
                priority = "medium",
                created_at = "2026-05-22"
            )
        )

        val response = TicketResponse(
            data = tickets
        )

        assertEquals(1, response.data.size)
        assertEquals("Awaria komputera", response.data[0].title)
    }
}