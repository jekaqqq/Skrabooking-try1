package com.example.skrabooking.ui

import com.example.skrabooking.data.AppDatabase
import com.example.skrabooking.ui.catalog.CatalogViewModel
import com.example.skrabooking.ui.my_books.AudioPlayerManager
import com.example.skrabooking.ui.my_books.MyBooksViewModel
import com.example.skrabooking.ui.theme.reader.ReaderViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class UiModule {
    val module = module {
        viewModel {
            CatalogViewModel()
        }
        viewModel {
            MyBooksViewModel(
                bookDao = AppDatabase.getDatabase(androidApplication()).bookDao()
            )
        }
        viewModel { (bookId: Int) ->
            ReaderViewModel(
                bookDao = AppDatabase.getDatabase(androidApplication()).bookDao(),
                bookId = bookId,
                context = androidApplication()
            )
        }
        single { AudioPlayerManager(androidApplication()) }
    }
}