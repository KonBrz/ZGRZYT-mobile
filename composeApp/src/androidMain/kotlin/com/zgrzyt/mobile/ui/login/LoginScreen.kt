package com.zgrzyt.mobile.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.zgrzyt.mobile.data.api.RetrofitClient
import com.zgrzyt.mobile.data.model.LoginRequest
import kotlinx.coroutines.launch
import com.zgrzyt.mobile.data.repository.SessionManager
import com.zgrzyt.mobile.ui.tickets.TicketsScreen



@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var loggedIn by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    if (loggedIn) {
        TicketsScreen()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("ZGRZYT", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Login") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Hasło") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                scope.launch {
                    result = "Logowanie..."

                    try {
                        val response = RetrofitClient.api.login(
                            LoginRequest(email, password)
                        )

                        SessionManager.saveSession(
                            accessToken = response.access_token,
                            userRole = response.role
                        )
                        loggedIn = true

                        result = "Zalogowano jako: ${response.role}"
                    } catch (e: Exception) {
                        result = "Błąd logowania: ${e.message}"
                    }
                }
            }
        ) {
            Text("Zaloguj")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result)
    }
}