package com.zgrzyt.mobile.ui.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zgrzyt.mobile.data.model.Ticket
import com.zgrzyt.mobile.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.zgrzyt.mobile.data.repository.ApiErrorHandler
import retrofit2.HttpException

data class TicketsUiState(
    val tickets: List<Ticket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)

class TicketsViewModel : ViewModel() {

    private val repository = TicketRepository()

    private val _uiState = MutableStateFlow(TicketsUiState())
    val uiState: StateFlow<TicketsUiState> = _uiState.asStateFlow()

    val shouldLogout: Boolean = false
    data class TicketsUiState(
        val tickets: List<Ticket> = emptyList(),
        val isLoading: Boolean = false,
        val error: String = "",
        val shouldLogout: Boolean = false
    )
    fun loadTickets() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = ""
            )

            try {
                val response = repository.getTickets()

                _uiState.value = _uiState.value.copy(
                    tickets = response.data,
                    isLoading = false
                )
            } catch (e: Exception) {

                val isUnauthorized =
                    e is HttpException && e.code() == 401

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = ApiErrorHandler.getMessage(e),
                    shouldLogout = isUnauthorized
                )
            }
        }
    }
}