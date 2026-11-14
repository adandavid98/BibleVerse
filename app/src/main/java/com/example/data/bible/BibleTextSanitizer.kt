package com.example.data.bible

object BibleTextSanitizer {

    // Line breaks before generic HTML stripping so adjacent words are never glued together
    private val BR_TAG_REGEX = Regex("(?i)<br\\s*/?>")

    // HTML blocks (including <p align='center'> headers and headings)
    private val HTML_BLOCKS_REGEX = Regex("(?is)<p[^>]*>.*?</p>|<h[1-6][^>]*>.*?</h[1-6]>|<[^>]*>")

    // Invisible control characters, BOM, line and paragraph separators, NBSP
    private val INVISIBLE_CHARS_REGEX = Regex("[\u200B\u200C\u200D\uFEFF\u2028\u2029\u00A0]")

    // Bullets, list markers, pilcrows, section signs, footnote markers
    private val BULLETS_AND_MARKERS_REGEX = Regex("[•·‣⁃○●¶§†‡*]")

    // Stray RTF/source markers and corrupted title artifacts
    private val RTF_PAR_REGEX = Regex("(?i)\\|\\s*par\\b")
    private val TRAILING_PIPE_REGEX = Regex("\\|+\\s*$")
    private val TRAILING_ITULO_REGEX = Regex("(?i)\\.itulo\\s*$")
    private val TRAILING_EMBEDDED_PERICOPE_REGEX = Regex(
        "(?i)\\s*(?:Amonestaciones de la Sabidur[ií]a|Amonestaci[oó]n contra el adulterio|Preceptos y amonestaciones|Elogio de la mujer virtuosa|Un obrero aprobado)\\s*$"
    )

    // Spacing before punctuation: "Dios , dijo" -> "Dios, dijo"
    private val PUNCTUATION_SPACING_REGEX = Regex("\\s+([.,;:!?])")

    // Editorial Psalm and Book division headers embedded into verse 1 (e.g. NTV in Bolls: "Salmo 4 Respóndeme...", "LIBRO PRIMERO (Salmos 1-41) Salmo 1...")
    private val PSALM_HEADER_PREFIX_REGEX = Regex(
        "(?i)^(?:LIBRO\\s+(?:PRIMERO|SEGUNDO|TERCERO|CUARTO|QUINTO|I|II|III|IV|V)\\s*\\([^)]*\\)\\s*)?Salmos?\\s+\\d+[.:]?\\s*"
    )

    // Trailing Hebrew acrostic letters (e.g. Dálet, He, Bet in Psalm 119) and translator musical tags (Interludio)
    private val TRAILING_ACROSTIC_AND_POETIC_REGEX = Regex(
        "(?i)\\s+(?:Alef|Álef|Bet|Beth|Gu[ií]mel|Gimel|D[aá]let|Daleth|He|Vav|Waw|Za[ií]n|Z[aá]yin|Jet|Chet|Het|Tet|Thet|Yod|Yodh|Caf|Kaf|Caph|L[aá]med|Lamed|Mem|Nun|S[aá]mec|S[aá]mej|Samekh|Ayin|Ay[ií]n|Ain|Pe|Phe|Tsade|Sade|Tsadhe|Tzaddi|Cof|Qof|Qoph|Resh|Shin|Sin|Tau|Tav|Taw|Interludio)[.:\\-—–]?\\s*$"
    )

    // Leading acrostic letter at the start of verse 1 (e.g. "Alef Felices son los íntegros...")
    private val LEADING_ACROSTIC_REGEX = Regex(
        "(?i)^\\s*(?:Alef|Álef)[.:\\-—–]?\\s+(?=[A-ZÁÉÍÓÚa-záéíóú])"
    )

    // Multiple consecutive whitespace characters
    private val MULTI_SPACE_REGEX = Regex("\\s{2,}")

    /**
     * Sanitizes raw biblical verse text across all translations (especially NBLA, TLA, DHH, PDT, NTV).
     * Eliminates stray bullets, footnotes, HTML fragments, invisible unicode artifacts,
     * editorial Psalm prefixes, while normalizing spacing, quotes, and punctuation.
     */
    fun sanitize(raw: String?): String {
        if (raw.isNullOrBlank()) return ""

        var text = raw
            // 1. Replace <br> with space before generic HTML removal so words never merge
            .replace(BR_TAG_REGEX, " ")
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
            // 4. Remove poetic bullet points, footnote symbols, pilcrows, and RTF/title artifacts
            .replace(BULLETS_AND_MARKERS_REGEX, "")
            .replace(RTF_PAR_REGEX, " ")
            .replace(TRAILING_PIPE_REGEX, "")
            .replace(TRAILING_ITULO_REGEX, ".")

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

        // 9. Strip editorial Psalm and book division headers prepended to verse text (e.g. NTV Salmos)
        text = text.replace(PSALM_HEADER_PREFIX_REGEX, "").trim()

        // 10. Strip stray leading acrostic headers at start of verse (e.g. "Alef Felices...")
        text = text.replace(LEADING_ACROSTIC_REGEX, "").trim()

        // 11. Strip trailing Hebrew acrostic letter names and musical pause tags erroneously glued to verse end (e.g. "Dálet", "Bet", "Interludio")
        text = text.replace(TRAILING_ACROSTIC_AND_POETIC_REGEX, "").trim()

        // 12. Strip any residual trailing pericope phrases erroneously embedded at verse end
        text = text.replace(TRAILING_EMBEDDED_PERICOPE_REGEX, "").replace(MULTI_SPACE_REGEX, " ").trim()

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

