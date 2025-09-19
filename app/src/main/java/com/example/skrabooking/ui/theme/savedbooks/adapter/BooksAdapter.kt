package com.example.skrabooking.ui.theme.savedbooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skrabooking.R
import com.example.skrabooking.data.entities.Book

class BooksAdapter(
    private val onItemClick: (Book) -> Unit
) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    private var books = emptyList<Book>()

    // Внутренний класс ViewHolder теперь хранит ссылки на View
    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Находим View один раз в конструкторе
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.bookAuthor)
        val bookCover: ImageView = itemView.findViewById(R.id.bookCover)

        fun bind(book: Book) {
            // Используем найденные View
            bookTitle.text = book.title
            bookAuthor.text = book.author
            bookCover.setImageResource(book.coverResId)

            itemView.setOnClickListener { onItemClick(book) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount() = books.size

    fun submitList(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}