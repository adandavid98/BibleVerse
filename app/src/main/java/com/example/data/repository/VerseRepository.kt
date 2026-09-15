package com.example.data.repository

import com.example.data.initial.InitialVersesData
import com.example.data.local.VerseDao
import com.example.data.model.VerseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class VerseRepository(private val verseDao: VerseDao) {

    val allVerses: Flow<List<VerseEntity>> = verseDao.getAllVerses()
    val favoriteVerses: Flow<List<VerseEntity>> = verseDao.getFavoriteVerses()
    val highlightedVerses: Flow<List<VerseEntity>> = verseDao.getHighlightedVerses()
    val versesWithNotes: Flow<List<VerseEntity>> = verseDao.getVersesWithNotes()

    suspend fun checkAndSeedInitialData() {
        withContext(Dispatchers.IO) {
            val count = verseDao.getCount()
            if (count == 0) {
                verseDao.insertVerses(InitialVersesData.verses)
            }
        }
    }

    fun searchVerses(query: String): Flow<List<VerseEntity>> {
        return verseDao.searchVerses(query)
    }

    fun getVersesByTestament(testament: String): Flow<List<VerseEntity>> {
        return verseDao.getVersesByTestament(testament)
    }

    fun getVerseById(id: Long): Flow<VerseEntity?> {
        return verseDao.getVerseById(id)
    }

    suspend fun getVerseByIdDirect(id: Long): VerseEntity? {
        return withContext(Dispatchers.IO) {
            verseDao.getVerseByIdDirect(id)
        }
    }

    suspend fun insertVerse(verse: VerseEntity): Long {
        return withContext(Dispatchers.IO) {
            verseDao.insertVerse(verse)
        }
    }

    suspend fun updateVerse(verse: VerseEntity) {
        withContext(Dispatchers.IO) {
            verseDao.updateVerse(verse)
        }
    }

    suspend fun deleteVerse(verse: VerseEntity) {
        withContext(Dispatchers.IO) {
            verseDao.deleteVerse(verse)
        }
    }

    suspend fun toggleFavorite(id: Long, currentFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            verseDao.updateFavorite(id, !currentFavorite)
        }
    }

    suspend fun updateNotes(id: Long, notes: String) {
        withContext(Dispatchers.IO) {
            verseDao.updateNotes(id, notes)
        }
    }

    suspend fun updateHighlight(id: Long, colorHex: String, highlightedPhrases: String) {
        withContext(Dispatchers.IO) {
            verseDao.updateHighlight(id, colorHex, highlightedPhrases)
        }
    }

    suspend fun importVersesList(incomingVerses: List<VerseEntity>) {
        withContext(Dispatchers.IO) {
            for (verse in incomingVerses) {
                verseDao.insertVerse(verse)
            }
        }
    }
}
