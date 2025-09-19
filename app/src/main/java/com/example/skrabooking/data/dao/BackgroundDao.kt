package com.example.skrabooking.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.skrabooking.data.entities.Background

@Dao
interface BackgroundDao {
    @Insert
    suspend fun insert(background: Background)

    @Query("SELECT * FROM backgrounds WHERE bookId = :bookId")
    suspend fun getBackgroundsForBook(bookId: Int): List<Background>
}
