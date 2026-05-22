package com.zgrzyt.mobile.data.model

data class CreateTicketRequest(
    val title: String,
    val description: String,
    val priority: String
)