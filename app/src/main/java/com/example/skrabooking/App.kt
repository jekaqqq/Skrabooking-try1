package com.example.skrabooking

import android.app.Application
import android.util.Log
import com.example.skrabooking.data.AppDatabase
import com.example.skrabooking.data.DataModule
import com.example.skrabooking.ui.UiModule
import com.example.skrabooking.ui.theme.savedbooks.adapter.PreloadedBooksManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initBooks()
    }

    private fun initBooks() {
        startKoin {
            androidContext(this@App)
            modules(listOf(DataModule().module, UiModule().module))
        }
        val db = AppDatabase.getDatabase(this)
        val preloadedManager = PreloadedBooksManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (db.bookDao().getAllBooks().isEmpty()) {
                    Log.d("DB_INIT", "Loading initial books...")
                    preloadedManager.loadInitialBooks(db.bookDao())

                    val count = db.bookDao().getAllBooks().size
                    Log.d("DB_INIT", "Books loaded: $count")
                }
            } catch (e: Exception) {
                Log.e("DB_INIT", "Error loading initial books", e)
            }
        }
    }
}
