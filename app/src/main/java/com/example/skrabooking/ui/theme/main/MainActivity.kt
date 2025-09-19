package com.example.skrabooking.ui.theme.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.skrabooking.data.AppDatabase
import com.example.skrabooking.databinding.ActivityMainBinding
import com.example.skrabooking.ui.theme.reader.ReaderActivity
import com.example.skrabooking.ui.theme.savedbooks.SavedBooksActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
        loadLastReadBook()
    }

    private fun setupButtons() {
        // Обработчик кнопки чтения - теперь открывает последнюю книгу
        binding.btnRead.setOnClickListener {
            openLastReadBook()
        }

        // Обработчик кнопки "Мои книги"
        binding.btnSavedBooks.setOnClickListener {
            startActivity(Intent(this, SavedBooksActivity::class.java))
        }
    }

    private fun loadLastReadBook() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@MainActivity)
            val lastBook = db.bookDao().getAllBooks()
                .maxByOrNull { it.lastReadTime }

            lastBook?.let {
                binding.tvLastBookTitle.text = it.title
            } ?: run {
                binding.tvLastBookTitle.text = "Нет истории чтения"
            }
        }
    }

    // НОВЫЙ МЕТОД: Открытие последней читаемой книги
    private fun openLastReadBook() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@MainActivity)

            // Ищем книгу с максимальным временем последнего чтения
            val lastBook = db.bookDao().getAllBooks()
                .maxByOrNull { it.lastReadTime }

            if (lastBook != null && lastBook.lastReadTime > 0) {
                // Получаем прогресс чтения для этой книги
                val progress = db.bookProgressDao().getByBookId(lastBook.id)

                // Запускаем ReaderActivity с ID книги
                val intent = Intent(this@MainActivity, ReaderActivity::class.java).apply {
                    putExtra("BOOK_ID", lastBook.id)
                }
                startActivity(intent)
            } else {
                // Если нет истории чтения, открываем список книг
                startActivity(Intent(this@MainActivity, SavedBooksActivity::class.java))
            }
        }
    }
}