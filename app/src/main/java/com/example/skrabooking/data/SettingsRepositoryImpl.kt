package com.example.skrabooking.data

import android.app.Application
import android.content.Context
import androidx.core.content.edit

class SettingsRepositoryImpl(private val application: Application) : SettingsRepository {

    private val sharedPreferences = application.getSharedPreferences("default", Context.MODE_PRIVATE)

    override fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN_KEY, false)
    }

    override fun setIsLoggedIn(value: Boolean) {
        sharedPreferences.edit {
            putBoolean(LOGGED_IN_KEY, value)
            apply()
        }
    }

    private companion object {
        const val LOGGED_IN_KEY = "LOGGED_IN_KEY"
    }
}