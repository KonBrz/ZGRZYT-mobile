package com.zgrzyt.mobile.data.model

data class Ticket(
    val id: Int,
    val title: String,
    val description: String,
    val status: String?,
    val priority: String?,
    val created_at: String?
)