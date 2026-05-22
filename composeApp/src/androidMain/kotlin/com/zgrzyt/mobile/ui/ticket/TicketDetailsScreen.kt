package com.zgrzyt.mobile.ui.tickets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zgrzyt.mobile.data.api.RetrofitClient
import com.zgrzyt.mobile.data.model.Ticket
import com.zgrzyt.mobile.data.repository.SessionManager

@Composable
fun TicketDetailsScreen(
    ticketId: Int,
    onBack: () -> Unit
) {
    var ticket by remember { mutableStateOf<Ticket?>(null) }
    var error by remember { mutableStateOf("") }

    LaunchedEffect(ticketId) {
        try {
            ticket = RetrofitClient.api.getTicket(
                token = SessionManager.token ?: "",
                id = ticketId
            )
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
        }
    }
}