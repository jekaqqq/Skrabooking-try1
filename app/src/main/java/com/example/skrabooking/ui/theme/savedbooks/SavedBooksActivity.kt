package com.example.skrabooking.ui.theme.savedbooks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skrabooking.data.AppDatabase
import com.example.skrabooking.databinding.ActivitySavedBooksBinding
import com.example.skrabooking.ui.theme.reader.ReaderActivity
import com.example.skrabooking.ui.theme.savedbooks.adapter.BooksAdapter
import kotlinx.coroutines.launch

class SavedBooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavedBooksBinding
    private val viewModel: SavedBooksViewModel by viewModels {
        SavedBooksViewModelFactory(AppDatabase.getDatabase(this).bookDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = BooksAdapter { book ->
            startActivity(Intent(this, ReaderActivity::class.java).apply {
                putExtra("BOOK_ID", book.id)
            })
        }

        binding.booksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.booksRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.books.collect { books ->
                Log.d("BOOKS_DEBUG", "Books count: ${books.size}")
                if (books.isEmpty()) {
                    binding.emptyView.text = "Нет сохранённых книг\n(Попробуйте перезапустить приложение)"
                    binding.emptyView.visibility = View.VISIBLE
                    binding.booksRecyclerView.visibility = View.GONE
                } else {
                    binding.emptyView.visibility = View.GONE
                    binding.booksRecyclerView.visibility = View.VISIBLE
                    adapter.submitList(books)
                }
            }
        }
    }
}

class SavedBooksViewModelFactory(private val bookDao: com.example.skrabooking.data.dao.BookDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SavedBooksViewModel(bookDao) as T
    }
}
