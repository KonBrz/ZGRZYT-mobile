package com.zgrzyt.mobile.data.repository

import android.content.Context

object SessionManager {

    private const val PREFS_NAME = "zgrzyt_session"
    private const val KEY_TOKEN = "token"
    private const val KEY_ROLE = "role"

    var token: String? = null
    var role: String? = null

    fun saveSession(
        context: Context,
        accessToken: String,
        userRole: String
    ) {

        token = "Bearer $accessToken"
        role = userRole

        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_ROLE, role)
            .apply()
    }

    fun saveSessionInMemory(
        accessToken: String,
        userRole: String
    ) {

        token = "Bearer $accessToken"
        role = userRole
    }

    fun loadSession(context: Context) {

        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        token = prefs.getString(KEY_TOKEN, null)
        role = prefs.getString(KEY_ROLE, null)
    }

    fun isLoggedIn(): Boolean {

        return !token.isNullOrBlank()
    }

    fun clearSession(context: Context) {

        token = null
        role = null

        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .clear()
            .apply()
    }

    fun clearSessionInMemory() {

        token = null
        role = null
    }
}