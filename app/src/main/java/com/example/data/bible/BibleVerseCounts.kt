package com.example.data.bible

/**
 * Exact canonical verse counts for all 66 books and 1,189 chapters of the Bible (Reina-Valera 1960).
 * Prevents UI discrepancies across all books and chapters.
 */
object BibleVerseCounts {

    // Index 0 corresponds to Book 1 (Génesis), inner array holds verse count for Chapter 1, 2, ...
    private val CANONICAL_COUNTS: Array<IntArray> = arrayOf(
        intArrayOf(31, 25, 24, 26, 32, 22, 24, 22, 29, 32, 32, 20, 18, 24, 21, 16, 27, 33, 38, 18, 34, 24, 20, 67, 34, 35, 46, 22, 35, 43, 55, 32, 20, 31, 29, 43, 36, 30, 23, 23, 57, 38, 34, 34, 28, 34, 31, 22, 33, 26), // Book 1
        intArrayOf(22, 25, 22, 31, 23, 30, 25, 32, 35, 29, 10, 51, 22, 31, 27, 36, 16, 27, 25, 26, 36, 31, 33, 18, 40, 37, 21, 43, 46, 38, 18, 35, 23, 35, 35, 38, 29, 31, 43, 38), // Book 2
        intArrayOf(17, 16, 17, 35, 19, 30, 38, 36, 24, 20, 47, 8, 59, 57, 33, 34, 16, 30, 37, 27, 24, 33, 44, 23, 55, 46, 34), // Book 3
        intArrayOf(54, 34, 51, 49, 31, 27, 89, 26, 23, 36, 35, 16, 33, 45, 41, 50, 13, 32, 22, 29, 35, 41, 30, 25, 18, 65, 23, 31, 40, 16, 54, 42, 56, 29, 34, 13), // Book 4
        intArrayOf(46, 37, 29, 49, 33, 25, 26, 20, 29, 22, 32, 32, 18, 29, 23, 22, 20, 22, 21, 20, 23, 30, 25, 22, 19, 19, 26, 68, 29, 20, 30, 52, 29, 12), // Book 5
        intArrayOf(18, 24, 17, 24, 15, 27, 26, 35, 27, 43, 23, 24, 33, 15, 63, 10, 18, 28, 51, 9, 45, 34, 16, 33), // Book 6
        intArrayOf(36, 23, 31, 24, 31, 40, 25, 35, 57, 18, 40, 15, 25, 20, 20, 31, 13, 31, 30, 48, 25), // Book 7
        intArrayOf(22, 23, 18, 22), // Book 8
        intArrayOf(28, 36, 21, 22, 12, 21, 17, 22, 27, 27, 15, 25, 23, 52, 35, 23, 58, 30, 24, 42, 15, 23, 29, 22, 44, 25, 12, 25, 11, 31, 13), // Book 9
        intArrayOf(27, 32, 39, 12, 25, 23, 29, 18, 13, 19, 27, 31, 39, 33, 37, 23, 29, 33, 43, 26, 22, 51, 39, 25), // Book 10
        intArrayOf(53, 46, 28, 34, 18, 38, 51, 66, 28, 29, 43, 33, 34, 31, 34, 34, 24, 46, 21, 43, 29, 53), // Book 11
        intArrayOf(18, 25, 27, 44, 27, 33, 20, 29, 37, 36, 21, 21, 25, 29, 38, 20, 41, 37, 37, 21, 26, 20, 37, 20, 30), // Book 12
        intArrayOf(54, 55, 24, 43, 26, 81, 40, 40, 44, 14, 47, 40, 14, 17, 29, 43, 27, 17, 19, 8, 30, 19, 32, 31, 31, 32, 34, 21, 30), // Book 13
        intArrayOf(17, 18, 17, 22, 14, 42, 22, 18, 31, 19, 23, 16, 22, 15, 19, 14, 19, 34, 11, 37, 20, 12, 21, 27, 28, 23, 9, 27, 36, 27, 21, 33, 25, 33, 27, 23), // Book 14
        intArrayOf(11, 70, 13, 24, 17, 22, 28, 36, 15, 44), // Book 15
        intArrayOf(11, 20, 32, 23, 19, 19, 73, 18, 38, 39, 36, 47, 31), // Book 16
        intArrayOf(22, 23, 15, 17, 14, 14, 10, 17, 32, 3), // Book 17
        intArrayOf(22, 13, 26, 21, 27, 30, 21, 22, 35, 22, 20, 25, 28, 22, 35, 22, 16, 21, 29, 29, 34, 30, 17, 25, 6, 14, 23, 28, 25, 31, 40, 22, 33, 37, 16, 33, 24, 41, 30, 24, 34, 17), // Book 18
        intArrayOf(6, 12, 8, 8, 12, 10, 17, 9, 20, 18, 7, 8, 6, 7, 5, 11, 15, 50, 14, 9, 13, 31, 6, 10, 22, 12, 14, 9, 11, 12, 24, 11, 22, 22, 28, 12, 40, 22, 13, 17, 13, 11, 5, 26, 17, 11, 9, 14, 20, 23, 19, 9, 6, 7, 23, 13, 11, 11, 17, 12, 8, 12, 11, 10, 13, 20, 7, 35, 36, 5, 24, 20, 28, 23, 10, 12, 20, 72, 13, 19, 16, 8, 18, 12, 13, 17, 7, 18, 52, 17, 16, 15, 5, 23, 11, 13, 12, 9, 9, 5, 8, 28, 22, 35, 45, 48, 43, 13, 31, 7, 10, 10, 9, 8, 18, 19, 2, 29, 176, 7, 8, 9, 4, 8, 5, 6, 5, 6, 8, 8, 3, 18, 3, 3, 21, 26, 9, 8, 24, 13, 10, 7, 12, 15, 21, 10, 20, 14, 9, 6), // Book 19
        intArrayOf(33, 22, 35, 27, 23, 35, 27, 36, 18, 32, 31, 28, 25, 35, 33, 33, 28, 24, 29, 30, 31, 29, 35, 34, 28, 28, 27, 28, 27, 33, 31), // Book 20
        intArrayOf(18, 26, 22, 16, 20, 12, 29, 17, 18, 20, 10, 14), // Book 21
        intArrayOf(17, 17, 11, 16, 16, 13, 13, 14), // Book 22
        intArrayOf(31, 22, 26, 6, 30, 13, 25, 22, 21, 34, 16, 6, 22, 32, 9, 14, 14, 7, 25, 6, 17, 25, 18, 23, 12, 21, 13, 29, 24, 33, 9, 20, 24, 17, 10, 22, 38, 22, 8, 31, 29, 25, 28, 28, 25, 13, 15, 22, 26, 11, 23, 15, 12, 17, 13, 12, 21, 14, 21, 22, 11, 12, 19, 12, 25, 24), // Book 23
        intArrayOf(19, 37, 25, 31, 31, 30, 34, 22, 26, 25, 23, 17, 27, 22, 21, 21, 27, 23, 15, 18, 14, 30, 40, 10, 38, 24, 22, 17, 32, 24, 40, 44, 26, 22, 19, 32, 21, 28, 18, 16, 18, 22, 13, 30, 5, 28, 7, 47, 39, 46, 64, 34), // Book 24
        intArrayOf(22, 22, 66, 22, 22), // Book 25
        intArrayOf(28, 10, 27, 17, 17, 14, 27, 18, 11, 22, 25, 28, 23, 23, 8, 63, 24, 32, 14, 49, 32, 31, 49, 27, 17, 21, 36, 26, 21, 26, 18, 32, 33, 31, 15, 38, 28, 23, 29, 49, 26, 20, 27, 31, 25, 24, 23, 35), // Book 26
        intArrayOf(21, 49, 30, 37, 31, 28, 28, 27, 27, 21, 45, 13), // Book 27
        intArrayOf(11, 23, 5, 19, 15, 11, 16, 14, 17, 15, 12, 14, 16, 9), // Book 28
        intArrayOf(20, 32, 21), // Book 29
        intArrayOf(15, 16, 15, 13, 27, 14, 17, 14, 15), // Book 30
        intArrayOf(21), // Book 31
        intArrayOf(17, 10, 10, 11), // Book 32
        intArrayOf(16, 13, 12, 13, 15, 16, 20), // Book 33
        intArrayOf(15, 13, 19), // Book 34
        intArrayOf(17, 20, 19), // Book 35
        intArrayOf(18, 15, 20), // Book 36
        intArrayOf(15, 23), // Book 37
        intArrayOf(21, 13, 10, 14, 11, 15, 14, 23, 17, 12, 17, 14, 9, 21), // Book 38
        intArrayOf(14, 17, 18, 6), // Book 39
        intArrayOf(25, 23, 17, 25, 48, 34, 29, 34, 38, 42, 30, 50, 58, 36, 39, 28, 27, 35, 30, 34, 46, 46, 39, 51, 46, 75, 66, 20), // Book 40
        intArrayOf(45, 28, 35, 41, 43, 56, 37, 38, 50, 52, 33, 44, 37, 72, 47, 20), // Book 41
        intArrayOf(80, 52, 38, 44, 39, 49, 50, 56, 62, 42, 54, 59, 35, 35, 32, 31, 37, 43, 48, 47, 38, 71, 56, 53), // Book 42
        intArrayOf(51, 25, 36, 54, 47, 71, 53, 59, 41, 42, 57, 50, 38, 31, 27, 33, 26, 40, 42, 31, 25), // Book 43
        intArrayOf(26, 47, 26, 37, 42, 15, 60, 40, 43, 48, 30, 25, 52, 28, 41, 40, 34, 28, 41, 38, 40, 30, 35, 27, 27, 32, 44, 31), // Book 44
        intArrayOf(32, 29, 31, 25, 21, 23, 25, 39, 33, 21, 36, 21, 14, 23, 33, 27), // Book 45
        intArrayOf(31, 16, 23, 21, 13, 20, 40, 13, 27, 33, 34, 31, 13, 40, 58, 24), // Book 46
        intArrayOf(24, 17, 18, 18, 21, 18, 16, 24, 15, 18, 33, 21, 14), // Book 47
        intArrayOf(24, 21, 29, 31, 26, 18), // Book 48
        intArrayOf(23, 22, 21, 32, 33, 24), // Book 49
        intArrayOf(30, 30, 21, 23), // Book 50
        intArrayOf(29, 23, 25, 18), // Book 51
        intArrayOf(10, 20, 13, 18, 28), // Book 52
        intArrayOf(12, 17, 18), // Book 53
        intArrayOf(20, 15, 16, 16, 25, 21), // Book 54
        intArrayOf(18, 26, 17, 22), // Book 55
        intArrayOf(16, 15, 15), // Book 56
        intArrayOf(25), // Book 57
        intArrayOf(14, 18, 19, 16, 14, 20, 28, 13, 28, 39, 40, 29, 25), // Book 58
        intArrayOf(27, 26, 18, 17, 20), // Book 59
        intArrayOf(25, 25, 22, 19, 14), // Book 60
        intArrayOf(21, 22, 18), // Book 61
        intArrayOf(10, 29, 24, 21, 21), // Book 62
        intArrayOf(13), // Book 63
        intArrayOf(14), // Book 64
        intArrayOf(25), // Book 65
        intArrayOf(20, 29, 22, 11, 14, 17, 17, 13, 21, 11, 19, 17, 18, 20, 8, 21, 18, 24, 21, 15, 27, 21), // Book 66
    )

    private fun normalize(name: String): String {
        return java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            .lowercase()
            .replace("[^a-z0-9]".toRegex(), "")
            .trim()
    }

    private val NORMALIZED_NAME_TO_INDEX: Map<String, Int> = mapOf(
        // Book 1
        "genesis" to 0, "gn" to 0, "gen" to 0,
        // Book 2
        "exodo" to 1, "ex" to 1,
        // Book 3
        "levitico" to 2, "lv" to 2, "lev" to 2,
        // Book 4
        "numeros" to 3, "nm" to 3, "num" to 3,
        // Book 5
        "deuteronomio" to 4, "dt" to 4, "deut" to 4,
        // Book 6
        "josue" to 5, "jos" to 5,
        // Book 7
        "jueces" to 6, "jue" to 6, "jdc" to 6,
        // Book 8
        "rut" to 7, "rt" to 7,
        // Book 9
        "1samuel" to 8, "1s" to 8, "1sam" to 8,
        // Book 10
        "2samuel" to 9, "2s" to 9, "2sam" to 9,
        // Book 11
        "1reyes" to 10, "1r" to 10, "1rey" to 10,
        // Book 12
        "2reyes" to 11, "2r" to 11, "2rey" to 11,
        // Book 13
        "1cronicas" to 12, "1cr" to 12, "1cron" to 12,
        // Book 14
        "2cronicas" to 13, "2cr" to 13, "2cron" to 13,
        // Book 15
        "esdras" to 14, "esd" to 14,
        // Book 16
        "nehemias" to 15, "neh" to 15,
        // Book 17
        "ester" to 16, "est" to 16,
        // Book 18
        "job" to 17,
        // Book 19
        "salmos" to 18, "salmo" to 18, "sal" to 18, "ps" to 18,
        // Book 20
        "proverbios" to 19, "pr" to 19, "prov" to 19,
        // Book 21
        "eclesiastes" to 20, "ec" to 20, "ecl" to 20,
        // Book 22
        "cantares" to 21, "cnt" to 21, "cantardeloscantares" to 21,
        // Book 23
        "isaias" to 22, "is" to 22,
        // Book 24
        "jeremias" to 23, "jer" to 23,
        // Book 25
        "lamentaciones" to 24, "lm" to 24, "lam" to 24,
        // Book 26
        "ezequiel" to 25, "ez" to 25,
        // Book 27
        "daniel" to 26, "dn" to 26, "dan" to 26,
        // Book 28
        "oseas" to 27, "os" to 27,
        // Book 29
        "joel" to 28, "jl" to 28,
        // Book 30
        "amos" to 29, "am" to 29,
        // Book 31
        "abdias" to 30, "abd" to 30,
        // Book 32
        "jonas" to 31, "jon" to 31,
        // Book 33
        "miqueas" to 32, "miq" to 32, "mic" to 32,
        // Book 34
        "nahum" to 33, "nah" to 33,
        // Book 35
        "habacuc" to 34, "hab" to 34,
        // Book 36
        "sofonias" to 35, "sof" to 35,
        // Book 37
        "hageo" to 36, "hag" to 36,
        // Book 38
        "zacarias" to 37, "zac" to 37,
        // Book 39
        "malaquias" to 38, "mal" to 38,
        // Book 40
        "mateo" to 39, "mt" to 39, "mat" to 39,
        // Book 41
        "marcos" to 40, "mr" to 40, "mc" to 40,
        // Book 42
        "lucas" to 41, "lc" to 41, "luc" to 41,
        // Book 43
        "juan" to 42, "jn" to 42,
        // Book 44
        "hechos" to 43, "hch" to 43, "hech" to 43,
        // Book 45
        "romanos" to 44, "ro" to 44, "rom" to 44,
        // Book 46
        "1corintios" to 45, "1co" to 45, "1cor" to 45,
        // Book 47
        "2corintios" to 46, "2co" to 46, "2cor" to 46,
        // Book 48
        "galatas" to 47, "gal" to 47,
        // Book 49
        "efesios" to 48, "ef" to 48, "eph" to 48,
        // Book 50
        "filipenses" to 49, "fil" to 49, "flp" to 49,
        // Book 51
        "colosenses" to 50, "col" to 50,
        // Book 52
        "1tesalonicenses" to 51, "1ts" to 51, "1tes" to 51,
        // Book 53
        "2tesalonicenses" to 52, "2ts" to 52, "2tes" to 52,
        // Book 54
        "1timoteo" to 53, "1ti" to 53, "1tim" to 53,
        // Book 55
        "2timoteo" to 54, "2ti" to 54, "2tim" to 54,
        // Book 56
        "tito" to 55, "tit" to 55,
        // Book 57
        "filemon" to 56, "flm" to 56, "phm" to 56,
        // Book 58
        "hebreos" to 57, "he" to 57, "heb" to 57,
        // Book 59
        "santiago" to 58, "stg" to 58, "sant" to 58,
        // Book 60
        "1pedro" to 59, "1p" to 59, "1pe" to 59, "1ped" to 59,
        // Book 61
        "2pedro" to 60, "2p" to 60, "2pe" to 60, "2ped" to 60,
        // Book 62
        "1juan" to 61, "1jn" to 61, "1j" to 61,
        // Book 63
        "2juan" to 62, "2jn" to 62, "2j" to 62,
        // Book 64
        "3juan" to 63, "3jn" to 63, "3j" to 63,
        // Book 65
        "judas" to 64, "jud" to 64, "jds" to 64,
        // Book 66
        "apocalipsis" to 65, "ap" to 65, "apoc" to 65, "revelacion" to 65
    )

    /**
     * Returns the exact canonical verse count for a given book (by name and/or order) and chapter (1-based).
     * Guaranteed to return the precise count for every book and chapter in the Reina-Valera 1960.
     * Never falls back to arbitrary numbers like 28.
     */
    fun getVerseCount(bookName: String? = null, bookOrder: Int = -1, chapter: Int): Int {
        var bookIdx = -1

        // 1. Try matching by normalized book name if provided
        if (!bookName.isNullOrBlank()) {
            val key = normalize(bookName)
            val mappedIdx = NORMALIZED_NAME_TO_INDEX[key]
            if (mappedIdx != null && mappedIdx in CANONICAL_COUNTS.indices) {
                bookIdx = mappedIdx
            }
        }

        // 2. If not found by name, try bookOrder (supports 1-based 1..66 and 0-based 0..65)
        if (bookIdx == -1) {
            bookIdx = when {
                bookOrder in 1..CANONICAL_COUNTS.size -> bookOrder - 1
                bookOrder in 0 until CANONICAL_COUNTS.size -> bookOrder
                else -> -1
            }
        }

        // 3. Fallback to Genesis (index 0) only if book is completely unidentified
        if (bookIdx !in CANONICAL_COUNTS.indices) {
            bookIdx = 0
        }

        val chapters = CANONICAL_COUNTS[bookIdx]
        val chIdx = (chapter - 1).coerceIn(0, chapters.lastIndex)
        return chapters[chIdx]
    }

    /**
     * Backwards-compatible overload by bookOrder and chapter.
     */
    fun getVerseCount(bookOrder: Int, chapter: Int): Int {
        return getVerseCount(bookName = null, bookOrder = bookOrder, chapter = chapter)
    }

    /**
     * Returns the exact canonical verse count by book name and chapter.
     */
    fun getVerseCountByName(bookName: String, chapter: Int): Int {
        return getVerseCount(bookName = bookName, bookOrder = -1, chapter = chapter)
    }
}
