package com.example.skrabooking.data.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: BookProgress)

    @Query("SELECT * FROM book_progress WHERE bookId = :bookId LIMIT 1")
    suspend fun getByBookId(bookId: Int): BookProgress?

    @Query("DELETE FROM book_progress WHERE bookId = :bookId")
    suspend fun deleteByBookId(bookId: Int)
}
