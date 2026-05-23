package com.zgrzyt.mobile.ui.tickets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zgrzyt.mobile.data.repository.SessionManager

@Composable
fun TicketsScreen(
    onTicketClick: (Int) -> Unit,
    onCreateClick: () -> Unit,
    onLogout: () -> Unit
) {
    val viewModel: TicketsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var searchText by remember { mutableStateOf("") }

    val filteredTickets = uiState.tickets.filter { ticket ->
        ticket.title.contains(searchText, true) ||
                ticket.description.contains(searchText, true)
    }

    LaunchedEffect(Unit) {
        viewModel.loadTickets()
    }
    LaunchedEffect(uiState.shouldLogout) {
        if (uiState.shouldLogout) {
            onLogout()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Zgłoszenia",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Rola: ${SessionManager.role ?: "brak"}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = when (SessionManager.role) {
                "admin" -> "Widok administratora: wszystkie zgłoszenia"
                "it" -> "Widok IT: obsługa zgłoszeń technicznych"
                "user" -> "Widok użytkownika: Twoje zgłoszenia"
                else -> "Nieznana rola"
            },
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onCreateClick
        ) {
            Text("Nowe zgłoszenie")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onLogout
        ) {
            Text("Wyloguj")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Szukaj zgłoszenia") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }  else if (uiState.error.isNotEmpty()) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(uiState.error)

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.loadTickets()
                }
            ) {
                Text("Spróbuj ponownie")
            }
        }

    } else {
            if (filteredTickets.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Brak zgłoszeń do wyświetlenia")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredTickets) { ticket ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .clickable {
                                    onTicketClick(ticket.id)
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = when (ticket.status) {
                                    "nowe" -> androidx.compose.ui.graphics.Color(0xFFE3F2FD)
                                    "w trakcie" -> androidx.compose.ui.graphics.Color(0xFFFFF3E0)
                                    "zamknięte" -> androidx.compose.ui.graphics.Color(0xFFE8F5E9)
                                    else -> MaterialTheme.colorScheme.surface
                                }
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 16.dp)
                            ) {
                                Text(
                                    text = ticket.title,
                                    style = MaterialTheme.typography.titleMedium
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
                            }
                        }
                    }
                }
            }
        }
    }
}