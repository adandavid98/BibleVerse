package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.initial.InitialVersesData
import com.example.data.model.VerseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [VerseEntity::class],
    version = 2,
    exportSchema = false
)
abstract class BibleDatabase : RoomDatabase() {

    abstract fun verseDao(): VerseDao

    companion object {
        @Volatile
        private var INSTANCE: BibleDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE verses ADD COLUMN bibleVersion TEXT NOT NULL DEFAULT 'RVR1960'")
            }
        }

        fun getDatabase(context: Context, scope: CoroutineScope? = null): BibleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BibleDatabase::class.java,
                    "bible_verses_database"
                )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration(false)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
