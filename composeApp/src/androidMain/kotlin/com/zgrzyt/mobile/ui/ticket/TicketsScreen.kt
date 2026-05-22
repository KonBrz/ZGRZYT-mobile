package com.zgrzyt.mobile.ui.tickets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zgrzyt.mobile.data.api.RetrofitClient
import com.zgrzyt.mobile.data.model.Ticket
import com.zgrzyt.mobile.data.repository.SessionManager
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable

@Composable
fun TicketsScreen() {

    var tickets by remember {
        mutableStateOf<List<Ticket>>(emptyList())
    }

    var error by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    var selectedTicketId by remember {
        mutableStateOf<Int?>(null)
    }

    if (selectedTicketId != null) {

        TicketDetailsScreen(
            ticketId = selectedTicketId!!,
            onBack = {
                selectedTicketId = null
            }
        )

        return
    }

    LaunchedEffect(Unit) {

        scope.launch {

            try {

                val response = RetrofitClient.api.getTickets(
                    SessionManager.token ?: ""
                )

                tickets = response.data

            } catch (e: Exception) {

                error = e.message ?: "Błąd"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Zgłoszenia",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {

            Text(error)

        } else {

            LazyColumn {

                items(tickets) { ticket ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clickable {
                                selectedTicketId = ticket.id
                            }
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = ticket.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(ticket.description)

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Status: ${ticket.status}")

                            Text("Priorytet: ${ticket.priority}")
                        }
                    }
                }
            }
        }
    }
}