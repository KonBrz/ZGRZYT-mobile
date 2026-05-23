package com.example.zgrzyt_apk.ui.login

import com.zgrzyt.mobile.ui.login.LoginUiState
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginUiStateTest {

    @Test
    fun loginUiState_shouldHaveDefaultValues() {
        val state = LoginUiState()

        assertFalse(state.isLoading)
        assertFalse(state.isLoggedIn)
        assertEquals("", state.error)
    }
}