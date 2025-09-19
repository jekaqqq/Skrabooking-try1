package com.example.skrabooking.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.skrabooking.data.entities.Music

@Dao  // ← Эта аннотация обязательна!
interface MusicDao {
    @Insert
    suspend fun insert(music: Music)

    @Query("SELECT * FROM music WHERE bookId = :bookId")
    suspend fun getMusicForBook(bookId: Int): List<Music>
}
