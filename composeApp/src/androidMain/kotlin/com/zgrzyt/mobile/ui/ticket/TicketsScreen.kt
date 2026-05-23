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
import com.zgrzyt.mobile.data.repository.TicketRepository
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

@Composable
fun TicketsScreen(
    onTicketClick: (Int) -> Unit,
    onCreateClick: () -> Unit,
    onLogout: () -> Unit
)
{

    val viewModel: TicketsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()


    val scope = rememberCoroutineScope()

    val repository = remember { TicketRepository() }


    LaunchedEffect(Unit) {
        viewModel.loadTickets()
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

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error.isNotEmpty()) {
            Text(uiState.error)
        } else {

            LazyColumn {

                items(uiState.tickets) { ticket ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clickable {
                                onTicketClick(ticket.id)
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