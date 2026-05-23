package com.zgrzyt.mobile.ui.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zgrzyt.mobile.data.model.Message
import com.zgrzyt.mobile.data.model.Ticket
import com.zgrzyt.mobile.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TicketDetailsUiState(
    val ticket: Ticket? = null,
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val error: String = ""
)

class TicketDetailsViewModel : ViewModel() {

    private val repository = TicketRepository()

    private val _uiState = MutableStateFlow(
        TicketDetailsUiState()
    )

    val uiState: StateFlow<TicketDetailsUiState> =
        _uiState.asStateFlow()

    fun loadTicket(ticketId: Int) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = ""
            )

            try {

                val ticket = repository.getTicket(ticketId)

                val messages =
                    repository.getMessages(ticketId)

                _uiState.value = _uiState.value.copy(
                    ticket = ticket,
                    messages = messages,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Błąd"
                )
            }
        }
    }

    fun sendMessage(
        ticketId: Int,
        body: String
    ) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isSending = true
            )

            try {

                repository.sendMessage(
                    ticketId = ticketId,
                    body = body
                )

                val messages =
                    repository.getMessages(ticketId)

                _uiState.value = _uiState.value.copy(
                    messages = messages,
                    isSending = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isSending = false,
                    error = e.message ?: "Błąd wysyłania"
                )
            }
        }
    }
}