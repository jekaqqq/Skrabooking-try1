package com.example.skrabooking.data.entities

import com.example.skrabooking.data.dao.BookDao

class BookRepository(private val bookDao: BookDao) {

    // Получение книги по ID
    suspend fun getBookById(bookId: Int): Book? {
        return bookDao.getBookById(bookId)
    }

    // Получение всех книг
    suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    // Вставка новой книги
    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    // Получение последней прочитанной книги
    suspend fun getLastReadBook(): Book? {
        return bookDao.getAllBooks()
            .maxByOrNull { it.lastReadTime }
    }
}