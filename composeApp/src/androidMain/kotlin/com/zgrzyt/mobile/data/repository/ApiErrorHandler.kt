package com.zgrzyt.mobile.data.repository

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ApiErrorHandler {

    fun getMessage(error: Throwable): String {
        return when (error) {
            is UnknownHostException -> "Brak połączenia z internetem"
            is SocketTimeoutException -> "Serwer nie odpowiada. Spróbuj ponownie za chwilę."
            is HttpException -> when (error.code()) {
                401 -> "Sesja wygasła lub brak autoryzacji"
                403 -> "Brak uprawnień do tej operacji"
                404 -> "Nie znaleziono zasobu"
                500 -> "Błąd serwera"
                503 -> "Serwer jest chwilowo niedostępny"
                else -> "Błąd API: ${error.code()}"
            }
            else -> error.message ?: "Nieznany błąd"
        }
    }
}