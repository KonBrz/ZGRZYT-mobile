package com.example.zgrzyt_apk.data.model

import com.zgrzyt.mobile.data.model.Ticket
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TicketDetailsTest {

    @Test
    fun ticketDetails_shouldAllowNullableFields() {
        val ticket = Ticket(
            id = 5,
            title = "Brak internetu",
            description = "Komputer nie ma połączenia z siecią.",
            status = null,
            priority = null,
            created_at = null
        )

        assertEquals(5, ticket.id)
        assertEquals("Brak internetu", ticket.title)
        assertEquals("Komputer nie ma połączenia z siecią.", ticket.description)
        assertNull(ticket.status)
        assertNull(ticket.priority)
        assertNull(ticket.created_at)
    }
}