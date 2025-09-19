package com.example.skrabooking.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val coverResId: Int,
    val filePath: String,
    val description: String,
    val lastReadPage: Int = 0,
    val lastReadTime: Long = 0, // Время последнего чтения в миллисекундах
    val totalPages: Int = 100
)