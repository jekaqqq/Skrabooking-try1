package com.example.skrabooking.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.skrabooking.data.entities.BookCharacter

@Dao
interface CharacterDao {
    @Query("SELECT * FROM book_characters WHERE bookId = :bookId")
    suspend fun getByBookId(bookId: Int): List<BookCharacter>
}