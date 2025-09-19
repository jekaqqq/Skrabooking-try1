package com.example.skrabooking.ui.theme.savedbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skrabooking.data.dao.BookDao
import com.example.skrabooking.data.entities.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SavedBooksViewModel(private val bookDao: BookDao) : ViewModel() {
    val books: Flow<List<Book>> = bookDao.getAllBooksFlow()

    init {
        viewModelScope.launch {
            // инициализация, при необходимости
        }
    }
}
