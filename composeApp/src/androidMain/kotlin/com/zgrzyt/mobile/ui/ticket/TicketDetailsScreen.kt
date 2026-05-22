package com.zgrzyt.mobile.ui.tickets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zgrzyt.mobile.data.api.RetrofitClient
import com.zgrzyt.mobile.data.model.Ticket
import com.zgrzyt.mobile.data.repository.SessionManager
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.zgrzyt.mobile.data.model.Message
import com.zgrzyt.mobile.data.model.SendMessageRequest
import kotlinx.coroutines.launch
import com.zgrzyt.mobile.data.repository.TicketRepository

@Composable
fun TicketDetailsScreen(
    ticketId: Int,
    onBack: () -> Unit
) {
    var ticket by remember { mutableStateOf<Ticket?>(null) }
    var error by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf<List<Message>>(emptyList()) }
    var newMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val repository = remember { TicketRepository() }

    LaunchedEffect(ticketId) {
        try {
            ticket = repository.getTicket(ticketId)

            messages = repository.getMessages(ticketId)
        } catch (e: Exception) {
            error = e.message ?: "Błąd pobierania zgłoszenia"
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

        if (error.isNotEmpty()) {
            Text(error)
        } else if (ticket == null) {
            CircularProgressIndicator()
        } else {
            Text(
                text = ticket!!.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(ticket!!.description)

            Spacer(modifier = Modifier.height(12.dp))

            Text("Status: ${ticket!!.status}")
            Text("Priorytet: ${ticket!!.priority}")
            Text("Utworzono: ${ticket!!.created_at}")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Wiadomości",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(messages) { message ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = message.body,
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
                onClick = {
                    if (newMessage.isNotBlank()) {

                        val textToSend = newMessage

                        newMessage = ""

                        scope.launch {

                            try {

                                repository.sendMessage(
                                    ticketId = ticketId,
                                    body = textToSend
                                )

                                messages = repository.getMessages(ticketId)

                            } catch (e: Exception) {

                                error = e.message ?: "Błąd wysyłania wiadomości"
                            }
                        }
                    }
                }
            ) {
                Text("Wyślij")
            }

        }
    }
}