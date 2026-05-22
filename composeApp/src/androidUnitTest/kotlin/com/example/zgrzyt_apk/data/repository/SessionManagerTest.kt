package com.example.zgrzyt_apk.data.repository

import com.zgrzyt.mobile.data.repository.SessionManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SessionManagerTest {

    @Test
    fun saveSession_shouldSaveBearerTokenAndRole() {
        SessionManager.saveSession(
            accessToken = "abc123",
            userRole = "admin"
        )

        assertEquals("Bearer abc123", SessionManager.token)
        assertEquals("admin", SessionManager.role)
    }

    @Test
    fun clearSession_shouldClearTokenAndRole() {
        SessionManager.saveSession(
            accessToken = "abc123",
            userRole = "admin"
        )

        SessionManager.clearSession()

        assertNull(SessionManager.token)
        assertNull(SessionManager.role)
    }
}