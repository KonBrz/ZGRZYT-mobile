package com.example.zgrzyt_apk.data.model
import com.zgrzyt.mobile.data.model.LoginRequest

import org.junit.Assert.assertEquals
import org.junit.Test

class LoginRequestTest {

    @Test
    fun loginRequest_shouldStoreLoginAndPassword() {
        val request = LoginRequest(
            login = "admin",
            password = "admin123"
        )

        assertEquals("admin", request.login)
        assertEquals("admin123", request.password)
    }
}