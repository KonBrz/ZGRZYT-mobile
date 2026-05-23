package com.zgrzyt.mobile.ui.tickets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TicketDetailsScreen(
    ticketId: Int,
    onBack: () -> Unit
) {
    val viewModel: TicketDetailsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var newMessage by remember { mutableStateOf("") }

    LaunchedEffect(ticketId) {
        viewModel.loadTicket(ticketId)
    }

    val ticket = uiState.ticket

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("Wróć")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
            return@Column
        }

        if (uiState.error.isNotEmpty()) {
            Text(uiState.error)
            return@Column
        }

        if (ticket == null) {
            Text("Brak danych zgłoszenia")
            return@Column
        }

        Text(
            text = ticket.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(ticket.description)

        Spacer(modifier = Modifier.height(12.dp))

        Text("Status: ${ticket.status}")
        Text("Priorytet: ${ticket.priority}")
        Text("Utworzono: ${ticket.created_at}")

        Spacer(modifier = Modifier.height(16.dp))

        if (
            com.zgrzyt.mobile.data.repository.SessionManager.role == "admin" ||
            com.zgrzyt.mobile.data.repository.SessionManager.role == "it"
        ) {
            Text("Zmień status")

            Row {
                listOf("nowe", "w trakcie", "zamknięte").forEach { status ->
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            viewModel.updateStatus(
                                ticketId = ticketId,
                                status = status
                            )
                        }
                    ) {
                        Text(status)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Wiadomości",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.messages) { message ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text =
                            message.body
                                ?: message.message
                                ?: message.content
                                ?: message.text
                                ?: "Brak treści",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = newMessage,
            onValueChange = { newMessage = it },
            label = { Text("Napisz wiadomość") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isSending,
            onClick = {
                if (newMessage.isNotBlank()) {
                    val textToSend = newMessage
                    newMessage = ""

                    viewModel.sendMessage(
                        ticketId = ticketId,
                        body = textToSend
                    )
                }
            }
        ) {
            Text(
                if (uiState.isSending) "Wysyłanie..." else "Wyślij"
            )
        }
    }
}