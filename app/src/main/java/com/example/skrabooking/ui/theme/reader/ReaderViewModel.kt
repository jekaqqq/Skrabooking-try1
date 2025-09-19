package com.example.skrabooking.ui.theme.reader

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skrabooking.data.dao.BookDao
import com.example.skrabooking.data.entities.Book
import com.example.skrabooking.data.entities.BookProgress
import com.example.skrabooking.utils.Fb2Parser
import kotlinx.coroutines.launch
import java.io.InputStream

class ReaderViewModel(
    private val bookDao: BookDao,
    private val bookId: Int,
    private val context: Context
) : ViewModel() {
    private val _currentPage = MutableLiveData<Int>(0)
    private val _bookContent = MutableLiveData<String>()
    private val _progressPercent = MutableLiveData<Int>(0)

    val currentPage: LiveData<Int> = _currentPage
    val bookContent: LiveData<String> = _bookContent
    val progressPercent: LiveData<Int> = _progressPercent

    private var totalPages = 0
    private lateinit var book: Book
    private var fullText: String = ""
    private val charsPerPage = 1200

    init {
        loadBookAndProgress()
    }

    private fun updateProgressPercent() {
        val percent = if (totalPages == 0) 0 else {
            ((_currentPage.value ?: 0).toFloat() / totalPages * 100).toInt()
        }
        _progressPercent.value = percent
    }

    private fun loadBookAndProgress() {
        viewModelScope.launch {
            book = bookDao.getBookById(bookId) ?: return@launch

            try {
                val inputStream: InputStream = context.assets.open(book.filePath)
                fullText = Fb2Parser.parseFb2Stream(inputStream)

                totalPages = calculateTotalPages(fullText)

                // ИСПРАВЛЕНИЕ: Сначала проверяем BookProgress, потом книгу
                val progress = bookDao.getBookProgress(bookId)

                if (progress != null) {
                    // Используем сохраненный прогресс из BookProgress
                    val savedPage = progress.currentPage.coerceIn(0, totalPages - 1)
                    _currentPage.value = savedPage
                } else if (book.lastReadPage > 0) {
                    // Если нет BookProgress, но есть lastReadPage в книге
                    val savedPage = book.lastReadPage.coerceIn(0, totalPages - 1)
                    _currentPage.value = savedPage
                    // Создаем запись в BookProgress
                    saveProgress()
                } else {
                    // Новая книга - начинаем с начала
                    _currentPage.value = 0
                    saveProgress()
                }

                _bookContent.value = getCurrentPageContent()
                updateProgressPercent()

            } catch (e: Exception) {
                _bookContent.value = "Ошибка загрузки книги: ${e.message}"
            }
        }
    }

    private fun calculateTotalPages(text: String): Int {
        if (text.isEmpty()) return 1
        return (text.length / charsPerPage) + 1
    }

    private fun getCurrentPageContent(): String {
        val current = _currentPage.value ?: 0
        val start = current * charsPerPage
        var end = start + charsPerPage

        if (start >= fullText.length) {
            return "Конец книги"
        }

        if (end > fullText.length) {
            end = fullText.length
        }

        return fullText.substring(start, end)
    }

    fun nextPage() {
        val current = _currentPage.value ?: 0
        if (current < totalPages - 1) {
            _currentPage.value = current + 1
            _bookContent.value = getCurrentPageContent()
            updateProgressPercent()
            saveProgress()
        } else {
            _bookContent.value = "Конец книги\n\n${getCurrentPageContent()}"
            _progressPercent.value = 100
            saveProgress()
        }
    }

    fun previousPage() {
        val current = _currentPage.value ?: 0
        if (current > 0) {
            _currentPage.value = current - 1
            _bookContent.value = getCurrentPageContent()
            updateProgressPercent()
            saveProgress()
        }
    }

    private fun saveProgress() {
        viewModelScope.launch {
            val currentPage = _currentPage.value ?: 0

            // Сохраняем прогресс чтения
            bookDao.insertBookProgress(
                BookProgress(
                    bookId = bookId,
                    currentPage = currentPage,
                    totalPages = totalPages
                )
            )

            // Обновляем время последнего чтения в самой книге
            val updatedBook = book.copy(
                lastReadPage = currentPage,
                lastReadTime = System.currentTimeMillis()
            )
            bookDao.insertBook(updatedBook)
        }
    }
}