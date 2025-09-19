package com.example.skrabooking.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skrabooking.data.dao.BookDao
import com.example.skrabooking.data.entities.Book
import com.example.skrabooking.data.entities.BookProgress
import com.example.skrabooking.data.entities.BookProgressDao

@Database(
    entities = [Book::class, BookProgress::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookProgressDao(): BookProgressDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                    .also { instance = it }
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `book_progress` (
                        `bookId` INTEGER PRIMARY KEY NOT NULL,
                        `currentPage` INTEGER NOT NULL,
                        `totalPages` INTEGER NOT NULL
                    )
                """.trimIndent())
            }
        }


        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Проверяем и добавляем колонки — SQLite не поддерживает ALTER ADD COLUMN с NOT NULL без DEFAULT,
                // поэтому добавляем с DEFAULT, как у тебя было.
                database.execSQL("ALTER TABLE books ADD COLUMN lastReadPage INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE books ADD COLUMN lastReadTime INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE books ADD COLUMN totalPages INTEGER NOT NULL DEFAULT 100")
            }
        }
    }
}
