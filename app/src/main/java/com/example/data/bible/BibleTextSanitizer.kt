package com.example.data.bible

object BibleTextSanitizer {

    // HTML blocks (including <p align='center'> headers and headings)
    private val HTML_BLOCKS_REGEX = Regex("(?is)<p[^>]*>.*?</p>|<h[1-6][^>]*>.*?</h[1-6]>|<[^>]*>")

    // Invisible control characters, BOM, line and paragraph separators, NBSP
    private val INVISIBLE_CHARS_REGEX = Regex("[\u200B\u200C\u200D\uFEFF\u2028\u2029\u00A0]")

    // Bullets, list markers, pilcrows, section signs, footnote markers
    private val BULLETS_AND_MARKERS_REGEX = Regex("[•·‣⁃○●¶§†‡*]")

    // Spacing before punctuation: "Dios , dijo" -> "Dios, dijo"
    private val PUNCTUATION_SPACING_REGEX = Regex("\\s+([.,;:!?])")

    // Multiple consecutive whitespace characters
    private val MULTI_SPACE_REGEX = Regex("\\s{2,}")

    /**
     * Sanitizes raw biblical verse text across all translations (especially NBLA, TLA, DHH, PDT).
     * Eliminates stray bullets, footnotes, HTML fragments, invisible unicode artifacts,
     * while normalizing spacing, quotes, and punctuation.
     */
    fun sanitize(raw: String?): String {
        if (raw.isNullOrBlank()) return ""

        var text = raw
            // 1. Remove HTML tags and headers
            .replace(HTML_BLOCKS_REGEX, "")
            // 2. Decode standard HTML entities
            .replace("&nbsp;", " ")
            .replace("&quot;", "\"")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#39;", "'")
            .replace("&apos;", "'")
            // 3. Replace invisible control characters and non-breaking spaces
            .replace(INVISIBLE_CHARS_REGEX, " ")
            // 4. Remove poetic bullet points, footnote symbols, and pilcrows
            .replace(BULLETS_AND_MARKERS_REGEX, "")

        // 5. Fix spaces before punctuation
        text = text.replace(PUNCTUATION_SPACING_REGEX, "$1")

        // 6. Collapse multiple whitespace and trim
        text = text.replace(MULTI_SPACE_REGEX, " ").trim()

        // 7. Strip leading dashes or bullet residue at the start of verse
        if (text.startsWith("-") || text.startsWith("–") || text.startsWith("—")) {
            text = text.substring(1).trim()
        }

        // 8. Strip wrapping stray quotes if the entire verse is enclosed in quotes
        if (text.length >= 2 && text.startsWith("'") && text.endsWith("'")) {
            text = text.substring(1, text.length - 1).trim()
        }

        return text
    }

    /**
     * Strips accents/diacritical marks from text (e.g. "Jesús" -> "Jesus", "oración" -> "oracion").
     * Leaves other characters intact.
     */
    fun removeAccents(input: String?): String {
        if (input.isNullOrBlank()) return ""
        val normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD)
        return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }
}

