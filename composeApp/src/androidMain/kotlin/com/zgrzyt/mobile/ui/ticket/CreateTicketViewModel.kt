package com.zgrzyt.mobile.ui.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zgrzyt.mobile.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreateTicketUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isCreated: Boolean = false
)

class CreateTicketViewModel : ViewModel() {

    private val repository = TicketRepository()

    private val _uiState = MutableStateFlow(CreateTicketUiState())
    val uiState: StateFlow<CreateTicketUiState> = _uiState.asStateFlow()

    fun createTicket(
        title: String,
        description: String,
        priority: String
    ) {
        if (title.isBlank() || description.isBlank()) {
            _uiState.value = _uiState.value.copy(
                error = "Uzupełnij tytuł i opis"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = ""
            )

            try {
                repository.createTicket(
                    title = title,
                    description = description,
                    priority = priority
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isCreated = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Błąd tworzenia zgłoszenia"
                )
            }
        }
    }
}