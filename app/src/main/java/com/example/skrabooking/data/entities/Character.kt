package com.example.skrabooking.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_characters")  // Указать другое имя таблицы
data class BookCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val bookId: Int,
    val name: String,
    val description: String
)