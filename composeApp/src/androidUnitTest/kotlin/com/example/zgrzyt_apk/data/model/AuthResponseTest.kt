package com.zgrzyt.mobile.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class AuthResponseTest {

    @Test
    fun authResponse_shouldStoreTokenTypeAndRole() {
        val response = AuthResponse(
            access_token = "abc123",
            token_type = "Bearer",
            role = "admin"
        )

        assertEquals("abc123", response.access_token)
        assertEquals("Bearer", response.token_type)
        assertEquals("admin", response.role)
    }
}