package com.example.skrabooking.ui.my_books

import com.example.skrabooking.base.BaseViewModel
import com.example.skrabooking.data.dao.BookDao
import com.example.skrabooking.data.entities.Book
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class MyBooksViewModel(
    private val bookDao: BookDao
): BaseViewModel<MyBooksState, MyBooksSideEffect, MyBooksEvent>(initialState = MyBooksState()) {

    override fun dispatch(event: MyBooksEvent) {
        when(event) {
            is MyBooksEvent.OnBookClicked -> onBookClicked(event.book)
            MyBooksEvent.OnFetchData -> onFetchData()
        }
    }

    private fun onBookClicked(book: Book) {
        intent {
            reduce { state.copy(screenState = State.READ, selectedBookId = book.id) }
        }
    }

    private fun onFetchData() {
        intent {
            val books = bookDao.getAllBooks()
            reduce { state.copy(books = books) }
        }
    }
}