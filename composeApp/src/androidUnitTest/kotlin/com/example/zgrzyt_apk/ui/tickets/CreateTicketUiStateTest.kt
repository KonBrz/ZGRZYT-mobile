package com.example.zgrzyt_apk.ui.tickets

import com.zgrzyt.mobile.ui.tickets.CreateTicketUiState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
import org.junit.Test

class CreateTicketUiStateTest {

    @Test
    fun createTicketUiState_shouldHaveDefaultValues() {
        val state = CreateTicketUiState()

        assertFalse(state.isLoading)
        assertFalse(state.isCreated)
        assertEquals("", state.error)
    }
}