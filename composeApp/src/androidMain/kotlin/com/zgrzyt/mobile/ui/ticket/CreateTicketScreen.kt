package com.zgrzyt.mobile.ui.tickets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zgrzyt.mobile.data.api.RetrofitClient
import com.zgrzyt.mobile.data.model.CreateTicketRequest
import com.zgrzyt.mobile.data.repository.SessionManager
import kotlinx.coroutines.launch
import com.zgrzyt.mobile.data.repository.TicketRepository

@Composable
fun CreateTicketScreen(
    onBack: () -> Unit,
    onCreated: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("średni") }
    var result by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val repository = remember { TicketRepository() }

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
            onClick = {
                scope.launch {
                    if (title.isBlank() || description.isBlank()) {
                        result = "Uzupełnij tytuł i opis"
                        return@launch
                    }

                    try {
                        repository.createTicket(
                            title = title,
                            description = description,
                            priority = priority
                        )

                        onCreated()
                    } catch (e: Exception) {
                        result = "Błąd tworzenia zgłoszenia: ${e.message}"
                    }
                }
            }
        ) {
            Text("Utwórz zgłoszenie")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(result)
    }
}