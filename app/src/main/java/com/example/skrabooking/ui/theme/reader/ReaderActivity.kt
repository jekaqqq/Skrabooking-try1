package com.example.skrabooking.ui.theme.reader

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.skrabooking.data.AppDatabase
import com.example.skrabooking.databinding.ActivityReaderBinding

class ReaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReaderBinding
    private val viewModel: ReaderViewModel by viewModels {
        ReaderViewModelFactory(
            bookDao = AppDatabase.getDatabase(this).bookDao(),
            bookId = intent.getIntExtra("BOOK_ID", -1),
            context = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.bookContent.observe(this) { content ->
            binding.bookContent.text = content
        }

        // УДАЛЕНО: старый observer для currentPage
        // viewModel.currentPage.observe(this) {
        //     binding.progressBar.progress = viewModel.getProgressPercent()
        // }

        // ДОБАВЛЕНО: наблюдение за процентом и обновление обоих элементов
        viewModel.progressPercent.observe(this) { percent ->
            binding.progressBar.progress = percent
            binding.progressText.text = "$percent%" // Обновляем текстовое поле с процентом
        }

        binding.nextPageBtn.setOnClickListener { viewModel.nextPage() }
        binding.prevPageBtn.setOnClickListener { viewModel.previousPage() }
    }
}