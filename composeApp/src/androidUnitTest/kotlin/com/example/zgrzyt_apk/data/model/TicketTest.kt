package com.example.zgrzyt_apk.data.model

import com.zgrzyt.mobile.data.model.Ticket
import org.junit.Assert.assertEquals
import org.junit.Test

class TicketTest {

    @Test
    fun ticket_shouldStoreBasicTicketData() {
        val ticket = Ticket(
            id = 1,
            title = "Problem z drukarką",
            description = "Drukarka nie drukuje dokumentów.",
            status = "open",
            priority = "high",
            created_at = "2026-05-22"
        )

        assertEquals(1, ticket.id)
        assertEquals("Problem z drukarką", ticket.title)
        assertEquals("Drukarka nie drukuje dokumentów.", ticket.description)
        assertEquals("open", ticket.status)
        assertEquals("high", ticket.priority)
        assertEquals("2026-05-22", ticket.created_at)
    }
}