package com.example.skrabooking.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "backgrounds")
data class Background(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int, // Связь с книгой
    val imageResId: Int // ID фона
)
