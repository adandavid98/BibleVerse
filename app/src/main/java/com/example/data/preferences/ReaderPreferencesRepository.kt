package com.example.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "reader_preferences")

class ReaderPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val FONT_FAMILY = stringPreferencesKey("font_family")
        val FONT_SIZE_SP = floatPreferencesKey("font_size_sp")
        val LINE_SPACING_MULTIPLIER = floatPreferencesKey("line_spacing_multiplier")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val LAST_BOOK_ID = intPreferencesKey("last_book_id")
        val LAST_CHAPTER = intPreferencesKey("last_chapter")
        val LAST_VERSE = intPreferencesKey("last_verse")
        val BIBLE_VERSION = stringPreferencesKey("bible_version")
    }

    val readerPreferences: Flow<ReaderPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val fontFamilyStr = preferences[PreferencesKeys.FONT_FAMILY] ?: ReaderFontFamily.SERIF.name
            val fontFamily = try {
                ReaderFontFamily.valueOf(fontFamilyStr)
            } catch (e: IllegalArgumentException) {
                ReaderFontFamily.SERIF
            }

            val fontSizeSp = preferences[PreferencesKeys.FONT_SIZE_SP] ?: 18f
            val lineSpacingMultiplier = preferences[PreferencesKeys.LINE_SPACING_MULTIPLIER] ?: 1.4f

            val themeModeStr = preferences[PreferencesKeys.THEME_MODE] ?: ReaderThemeMode.SEPIA.name
            val themeMode = try {
                ReaderThemeMode.valueOf(themeModeStr)
            } catch (e: IllegalArgumentException) {
                ReaderThemeMode.SEPIA
            }

            val lastBookId = preferences[PreferencesKeys.LAST_BOOK_ID] ?: 1
            val lastChapter = preferences[PreferencesKeys.LAST_CHAPTER] ?: 1
            val lastVerse = preferences[PreferencesKeys.LAST_VERSE] ?: 1
            val bibleVersion = preferences[PreferencesKeys.BIBLE_VERSION] ?: "RVR1960"

            ReaderPreferences(
                fontFamily = fontFamily,
                fontSizeSp = fontSizeSp,
                lineSpacingMultiplier = lineSpacingMultiplier,
                themeMode = themeMode,
                lastBookId = lastBookId,
                lastChapter = lastChapter,
                lastVerse = lastVerse,
                bibleVersion = bibleVersion
            )
        }

    suspend fun updateFontFamily(fontFamily: ReaderFontFamily) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_FAMILY] = fontFamily.name
        }
    }

    suspend fun updateFontSize(fontSizeSp: Float) {
        val clamped = fontSizeSp.coerceIn(12f, 34f)
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_SIZE_SP] = clamped
        }
    }

    suspend fun updateLineSpacing(multiplier: Float) {
        val clamped = multiplier.coerceIn(1.1f, 2.2f)
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LINE_SPACING_MULTIPLIER] = clamped
        }
    }

    suspend fun updateThemeMode(themeMode: ReaderThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = themeMode.name
        }
    }

    suspend fun updateLastPosition(bookId: Int, chapter: Int, verse: Int = 1) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_BOOK_ID] = bookId
            preferences[PreferencesKeys.LAST_CHAPTER] = chapter
            preferences[PreferencesKeys.LAST_VERSE] = verse
        }
    }

    suspend fun updateBibleVersion(version: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.BIBLE_VERSION] = version
        }
    }
}
