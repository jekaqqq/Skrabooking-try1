package com.example.skrabooking.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music")
data class Music(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int, // Связь с книгой
    val filePath: String // Путь к аудиофайлу
)