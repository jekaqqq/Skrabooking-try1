package com.example.skrabooking.data

interface SettingsRepository {
    fun isLoggedIn(): Boolean
    fun setIsLoggedIn(value: Boolean)
}