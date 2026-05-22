package com.zgrzyt.mobile.data.model

data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val role: String
)