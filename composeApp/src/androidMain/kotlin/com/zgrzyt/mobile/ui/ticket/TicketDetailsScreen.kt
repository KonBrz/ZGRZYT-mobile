package com.zgrzyt.mobile.ui.tickets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zgrzyt.mobile.data.repository.SessionManager
import kotlinx.coroutines.delay

@Composable
fun TicketDetailsScreen(
    ticketId: Int
) {
    val viewModel: TicketDetailsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var newMessage by remember { mutableStateOf("") }

    LaunchedEffect(ticketId) {
        while (true) {
            viewModel.loadTicket(ticketId)
            delay(5000)
        }
    }

    val ticket = uiState.ticket

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (uiState.isLoading && ticket == null) {
            CircularProgressIndicator()
            return@Column
        }

        if (uiState.error.isNotEmpty() && ticket == null) {
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(ticket.description)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Status: ${ticket.status}",
            color = when (ticket.status) {
                "nowe" -> androidx.compose.ui.graphics.Color.Blue
                "w trakcie" -> androidx.compose.ui.graphics.Color(0xFFFF9800)
                "zamknięte" -> androidx.compose.ui.graphics.Color.Green
                else -> androidx.compose.ui.graphics.Color.Gray
            }
        )

        Text(
            text = "Priorytet: ${ticket.priority}",
            color = when (ticket.priority) {
                "niski" -> androidx.compose.ui.graphics.Color.Green
                "średni" -> androidx.compose.ui.graphics.Color(0xFFFF9800)
                "wysoki" -> androidx.compose.ui.graphics.Color.Red
                else -> androidx.compose.ui.graphics.Color.Gray
            }
        )

        Text("Utworzono: ${ticket.created_at}")

        Spacer(modifier = Modifier.height(12.dp))

        if (
            SessionManager.role == "admin" ||
            SessionManager.role == "it"
        ) {
            Text("Zmień status")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("nowe", "w trakcie", "zamknięte").forEach { status ->
                    Button(
                        modifier = Modifier.weight(1f),
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

            Spacer(modifier = Modifier.height(12.dp))

            Text("Zmień priorytet")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("niski", "średni", "wysoki").forEach { priority ->
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.updatePriority(
                                ticketId = ticketId,
                                priority = priority
                            )
                        }
                    ) {
                        Text(priority)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        Text(
            text = "Wiadomości",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.messages) { message ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text =
                                "${message.user_name ?: "Użytkownik"} " +
                                        "(${message.user_role ?: "user"})",
                            style = MaterialTheme.typography.labelMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text =
                                message.body
                                    ?: message.message
                                    ?: message.content
                                    ?: message.text
                                    ?: "Brak treści"
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                label = { Text("Napisz wiadomość") },
                modifier = Modifier.weight(1f)
            )

            Button(
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
                    if (uiState.isSending) "..." else "Wyślij"
                )
            }
        }
    }
}