/*
package com.zgrzyt.mobile.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "zgrzyt_database"
            ).build()

            database = instance
            instance
        }
    }
}*/
