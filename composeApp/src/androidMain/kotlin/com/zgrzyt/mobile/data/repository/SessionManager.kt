package com.zgrzyt.mobile.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SessionManager {

    private const val PREFS_NAME = "zgrzyt_session"
    private const val KEY_TOKEN = "token"
    private const val KEY_ROLE = "role"

    var token: String? = null
    var role: String? = null

    private fun getEncryptedPrefs(context: Context) =
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveSession(
        context: Context,
        accessToken: String,
        userRole: String
    ) {
        token = "Bearer $accessToken"
        role = userRole

        getEncryptedPrefs(context)
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
        val prefs = getEncryptedPrefs(context)

        token = prefs.getString(KEY_TOKEN, null)
        role = prefs.getString(KEY_ROLE, null)
    }

    fun isLoggedIn(): Boolean {
        return !token.isNullOrBlank()
    }

    fun clearSession(context: Context) {
        token = null
        role = null

        getEncryptedPrefs(context)
            .edit()
            .clear()
            .apply()
    }

    fun clearSessionInMemory() {
        token = null
        role = null
    }
}