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

    suspend fun deduplicateVerses() {
        withContext(Dispatchers.IO) {
            val all = verseDao.getAllVersesDirect()
            if (all.isEmpty()) return@withContext

            val groupedByRef = all.groupBy { 
                it.reference.trim().lowercase().replace("\\s+".toRegex(), " ")
            }
            val idsToDelete = mutableListOf<Long>()

            for ((_, group) in groupedByRef) {
                if (group.size > 1) {
                    // Preservar el elemento más completo (favorito, notas, resaltado, o menor ID)
                    val sorted = group.sortedWith(
                        compareByDescending<VerseEntity> { it.isFavorite }
                            .thenByDescending { it.notes.isNotBlank() }
                            .thenByDescending { it.highlightColor.isNotBlank() }
                            .thenBy { it.id }
                    )
                    val duplicates = sorted.drop(1)
                    idsToDelete.addAll(duplicates.map { it.id })
                }
            }

            if (idsToDelete.isNotEmpty()) {
                verseDao.deleteVersesByIds(idsToDelete)
            }
        }
    }

    suspend fun checkAndSeedInitialData() {
        withContext(Dispatchers.IO) {
            deduplicateVerses()
            val existing = verseDao.getAllVersesDirect()
            if (existing.isEmpty()) {
                verseDao.insertVerses(InitialVersesData.verses)
            } else {
                val existingRefs = existing.map {
                    it.reference.trim().lowercase().replace("\\s+".toRegex(), " ")
                }.toSet()
                val missing = InitialVersesData.verses.filter {
                    val ref = it.reference.trim().lowercase().replace("\\s+".toRegex(), " ")
                    ref !in existingRefs
                }
                if (missing.isNotEmpty()) {
                    verseDao.insertVerses(missing)
                }
            }
            repairMissingOrDuplicateContexts()
        }
    }

    suspend fun repairMissingOrDuplicateContexts() {
        withContext(Dispatchers.IO) {
            val all = verseDao.getAllVersesDirect()
            val toUpdate = mutableListOf<VerseEntity>()
            for (v in all) {
                val ctxTrim = v.context.trim()
                val ctxLower = ctxTrim.lowercase()
                val txtClean = v.text.replace("«", "").replace("»", "").replace("\"", "").trim()
                val ctxClean = ctxTrim.replace("«", "").replace("»", "").replace("\"", "").trim()

                val isMarker = ctxLower.contains("lector de la biblia")
                val isBlank = ctxTrim.isBlank()
                val isDuplicateText = ctxClean.isNotEmpty() && (
                    ctxClean.equals(txtClean, ignoreCase = true) ||
                    (ctxClean.length > 25 && txtClean.contains(ctxClean, ignoreCase = true)) ||
                    (txtClean.length > 25 && ctxClean.contains(txtClean, ignoreCase = true))
                )

                if (isMarker || isBlank || isDuplicateText) {
                    val parts = v.chapterVerse.split(":")
                    val ch = parts.getOrNull(0)?.filter { it.isDigit() }?.toIntOrNull() ?: 1
                    val vs = parts.getOrNull(1)?.filter { it.isDigit() }?.ifBlank { "1" } ?: "1"
                    val resolved = com.example.data.bible.BibleContextEngine.getLocalContext(
                        bookName = v.book,
                        chapter = ch,
                        verse = vs,
                        verseText = v.text
                    )
                    if (resolved.isNotBlank() && resolved != v.context) {
                        toUpdate.add(v.copy(context = resolved, updatedAt = System.currentTimeMillis()))
                    }
                }
            }
            if (toUpdate.isNotEmpty()) {
                toUpdate.forEach { verseDao.updateVerse(it) }
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
