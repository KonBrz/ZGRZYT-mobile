package com.zgrzyt.mobile.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {

    val viewModel: LoginViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var login by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(uiState.isLoggedIn) {

        if (uiState.isLoggedIn) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "ZGRZYT",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = login,
            onValueChange = {
                login = it
            },
            label = {
                Text("Login")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Hasło")
            },
            visualTransformation =
                PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            onClick = {

                viewModel.login(
                    context = context,
                    login = login,
                    password = password
                )
            }
        ) {

            Text(
                if (uiState.isLoading)
                    "Logowanie..."
                else
                    "Zaloguj"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.error.isNotEmpty()) {

            Text(uiState.error)
        }
    }
}