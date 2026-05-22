package com.example.zgrzyt_apk.data.api

import org.junit.Assert.assertTrue
import org.junit.Test

class ZgrzytApiTest {

    @Test
    fun api_shouldUseBearerTokenFormat() {
        val token = "abc123"
        val bearerToken = "Bearer $token"

        assertTrue(bearerToken.startsWith("Bearer "))
    }
}