package com.example.skrabooking.ui.my_books

import com.example.skrabooking.data.entities.Book

data class MyBooksState(
    val books: List<Book> = listOf(),
    val screenState: State = State.BOOKS,
    val selectedBookId: Int = 0
)

enum class State {
    BOOKS, READ
}