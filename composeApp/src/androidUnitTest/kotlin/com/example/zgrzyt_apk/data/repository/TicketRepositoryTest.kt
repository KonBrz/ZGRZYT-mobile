package com.example.zgrzyt_apk.data.repository

import com.zgrzyt.mobile.data.repository.SessionManager
import com.zgrzyt.mobile.data.repository.TicketRepository
import org.junit.Assert.assertNotNull
import org.junit.Test

class TicketRepositoryTest {

    @Test
    fun ticketRepository_shouldBeCreated() {
        val repository = TicketRepository()

        assertNotNull(repository)
    }

    @Test
    fun ticketRepository_shouldUseSessionManagerToken() {
        SessionManager.saveSession(
            accessToken = "test-token",
            userRole = "user"
        )

        val repository = TicketRepository()

        assertNotNull(repository)
        assertNotNull(SessionManager.token)
    }
}