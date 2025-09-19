package com.example.skrabooking.ui.theme.savedbooks.adapter

import android.content.Context
import android.util.Log
import com.example.skrabooking.R
import com.example.skrabooking.data.dao.BookDao
import com.example.skrabooking.data.entities.Book

class PreloadedBooksManager(private val context: Context) {
    suspend fun loadInitialBooks(bookDao: BookDao) {
        val books = listOf(
            Book(
                title = "Легенда о Данко",
                author = "М. Горький",
                filePath = "books/my_book.fb2",
                coverResId = R.drawable.scale_1200,
                description = "Классический рассказ о самопожертвовании"
            )
            
        )

        books.forEach { book ->
            try {
                context.assets.open(book.filePath).close()
                bookDao.insertBook(book)
                Log.d("BOOK_LOAD", "Added book: ${book.title}")
            } catch (e: Exception) {
                Log.e("BOOK_LOAD", "Error adding book ${book.title}", e)
            }
        }
    }
}
