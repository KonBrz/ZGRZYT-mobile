package com.zgrzyt.mobile.ui.tickets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreateTicketScreen(
    onBack: () -> Unit,
    onCreated: () -> Unit
) {
    val viewModel: CreateTicketViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("średni") }

    LaunchedEffect(uiState.isCreated) {
        if (uiState.isCreated) {
            onCreated()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("Wróć")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nowe zgłoszenie",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tytuł") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Opis problemu") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Priorytet")

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("niski", "średni", "wysoki").forEach { item ->
                Row(
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    RadioButton(
                        selected = priority == item,
                        onClick = { priority = item }
                    )
                    Text(item)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            onClick = {
                viewModel.createTicket(
                    title = title,
                    description = description,
                    priority = priority
                )
            }
        ) {
            Text(
                if (uiState.isLoading) "Tworzenie..." else "Utwórz zgłoszenie"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.error.isNotEmpty()) {
            Text(uiState.error)
        }
    }
}