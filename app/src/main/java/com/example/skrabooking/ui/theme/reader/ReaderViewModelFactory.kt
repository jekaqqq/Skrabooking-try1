package com.example.skrabooking.ui.theme.reader

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skrabooking.data.dao.BookDao

class ReaderViewModelFactory(
    private val bookDao: BookDao,
    private val bookId: Int,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ReaderViewModel(bookDao, bookId, context) as T
    }
}
