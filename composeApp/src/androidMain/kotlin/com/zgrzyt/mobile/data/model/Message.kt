package com.zgrzyt.mobile.data.model

data class Message(
    val id: Int,
    val body: String,
    val user_id: Int?,
    val ticket_id: Int?,
    val created_at: String?
)