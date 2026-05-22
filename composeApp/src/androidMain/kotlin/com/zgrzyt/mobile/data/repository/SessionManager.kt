package com.zgrzyt.mobile.data.repository

object SessionManager {
    var token: String? = null
    var role: String? = null

    fun saveSession(accessToken: String, userRole: String) {
        token = "Bearer $accessToken"
        role = userRole
    }

    fun clearSession() {
        token = null
        role = null
    }
}