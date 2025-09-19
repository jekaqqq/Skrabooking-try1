package com.example.skrabooking.data

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

class DataModule {
    val module = module {
        single<SettingsRepository> { SettingsRepositoryImpl(androidApplication()) }
    }
}