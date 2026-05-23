package com.zgrzyt.mobile.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zgrzyt.mobile.data.api.RetrofitClient
import com.zgrzyt.mobile.data.model.LoginRequest
import com.zgrzyt.mobile.data.repository.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoggedIn: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow()

    fun login(
        login: String,
        password: String
    ) {

        if (login.isBlank() || password.isBlank()) {

            _uiState.value = _uiState.value.copy(
                error = "Uzupełnij login i hasło"
            )

            return
        }

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = ""
            )

            try {

                val response =
                    RetrofitClient.api.login(
                        LoginRequest(
                            login = login,
                            password = password
                        )
                    )

                SessionManager.saveSession(
                    accessToken = response.access_token,
                    userRole = response.role
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Błąd logowania"
                )
            }
        }
    }
}