package com.example.skrabooking.ui.my_books

import com.example.skrabooking.data.entities.Book

sealed class MyBooksEvent {
    data object OnFetchData : MyBooksEvent()
    data class OnBookClicked(val book: Book) : MyBooksEvent()
}