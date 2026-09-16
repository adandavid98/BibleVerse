package com.example.data.bible

/**
 * Catálogo canónico integral que indexa los versículos donde Jesucristo habla
 * en los cuatro Evangelios (Mateo, Marcos, Lucas, Juan), Hechos de los Apóstoles y Apocalipsis.
 */
object WordsOfJesusCatalog {

    /**
     * Verifica de forma inmediata si un versículo específico contiene palabras de Jesús.
     */
    fun isWordsOfJesus(bookId: Int, chapter: Int, verseNumber: Int): Boolean {
        val chapterMap = redLetterVerses[bookId] ?: return false
        val versesSet = chapterMap[chapter] ?: return false
        return versesSet.contains(verseNumber)
    }

    /**
     * Mapeo canónico: BookId -> (Chapter -> Set<VerseNumber>)
     *
     * Libros:
     * 40 = Mateo
     * 41 = Marcos
     * 42 = Lucas
     * 43 = Juan
     * 44 = Hechos
     * 66 = Apocalipsis
     */
    private val redLetterVerses: Map<Int, Map<Int, Set<Int>>> = mapOf(
        // ==========================================
        // 40: EVANGELIO SEGÚN SAN MATEO
        // ==========================================
        40 to mapOf(
            3 to setOf(15),
            4 to setOf(4, 7, 10, 17, 19),
            // Sermón del Monte (caps. 5, 6, 7)
            5 to (3..48).toSet(),
            6 to (1..34).toSet(),
            7 to (1..27).toSet(),
            8 to setOf(3, 4, 7, 10, 11, 12, 13, 20, 22, 26, 32),
            9 to setOf(2, 4, 5, 6, 9, 12, 13, 15, 22, 24, 28, 29, 30, 37, 38),
            // Instrucciones misioneras a los Doce
            10 to (5..42).toSet(),
            11 to (4..30).toSet(),
            12 to setOf(3, 4, 5, 6, 7, 8, 11, 12, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 39, 40, 41, 42, 43, 44, 45, 48, 49, 50),
            // Mateo 13: Las parábolas del Reino (Sembrador, Cizaña, Mostaza, Levadura, Tesoro, Perla, Red)
            13 to (setOf(3, 4, 5, 6, 7, 8, 9) + (11..33).toSet() + (37..52).toSet() + setOf(57)),
            14 to setOf(16, 18, 27, 29, 31),
            15 to (setOf(3, 4, 5, 6, 7, 8, 9, 10, 11) + (13..20).toSet() + setOf(24, 26, 28, 32, 34)),
            16 to (setOf(2, 3, 4, 6) + (8..12).toSet() + setOf(15, 17, 18, 19, 23) + (24..28).toSet()),
            17 to setOf(7, 9, 11, 12, 17, 20, 21, 22, 23, 25, 26, 27),
            // Discurso de la Iglesia y el perdón
            18 to (3..35).toSet(),
            19 to (setOf(4, 5, 6, 8, 9, 11, 12, 14, 17, 18, 19, 21, 23, 24, 26) + (28..30).toSet()),
            20 to ((1..16).toSet() + setOf(18, 19, 21, 22, 23) + (25..28).toSet() + setOf(32)),
            21 to (setOf(2, 3, 13, 16, 19, 21, 22, 24, 25, 27) + (28..32).toSet() + (33..44).toSet()),
            22 to ((1..14).toSet() + setOf(18, 19, 20, 21) + (29..32).toSet() + (37..40).toSet() + setOf(42, 43, 44, 45)),
            // Los siete ayes y lamento sobre Jerusalén
            23 to (1..39).toSet(),
            // Discurso del Monte de los Olivos (Escatología)
            24 to (setOf(2) + (4..51).toSet()),
            // Las diez vírgenes, talentos y juicio final
            25 to (1..46).toSet(),
            26 to setOf(2, 10, 11, 12, 13, 18, 21, 23, 24, 25, 26, 27, 28, 29, 31, 32, 34, 36, 38, 39, 40, 41, 42, 45, 46, 50, 52, 53, 54, 55, 64),
            27 to setOf(11, 46),
            28 to setOf(9, 10, 18, 19, 20)
        ),

        // ==========================================
        // 41: EVANGELIO SEGÚN SAN MARCOS
        // ==========================================
        41 to mapOf(
            1 to setOf(15, 17, 25, 38, 41, 44),
            2 to (setOf(5) + (8..11).toSet() + setOf(17) + (19..22).toSet() + (25..28).toSet()),
            3 to (setOf(4) + (23..29).toSet() + (33..35).toSet()),
            4 to ((2..9).toSet() + (11..32).toSet() + setOf(35, 39, 40)),
            5 to setOf(8, 9, 19, 30, 34, 36, 39, 41),
            6 to (setOf(4, 10, 11, 31, 37, 38, 50)),
            7 to ((6..13).toSet() + (14..16).toSet() + (18..23).toSet() + setOf(27, 29, 34)),
            8 to (setOf(2, 3, 5, 12, 15) + (17..21).toSet() + setOf(26, 27, 29, 33) + (34..38).toSet()),
            9 to (setOf(1, 12, 13, 16, 19, 21, 23, 25, 29, 31, 33) + (35..37).toSet() + (39..50).toSet()),
            10 to (setOf(3) + (5..9).toSet() + setOf(11, 12, 14, 15, 18, 19, 21, 23, 24, 25, 27) + (29..31).toSet() + (33..34).toSet() + setOf(36, 38, 39, 40) + (42..45).toSet() + setOf(49, 51, 52)),
            11 to (setOf(2, 3, 14, 17) + (22..26).toSet() + setOf(29, 30, 33)),
            12 to ((1..11).toSet() + setOf(15, 16, 17) + (24..27).toSet() + (29..31).toSet() + setOf(34, 35, 36, 37) + (38..40).toSet() + setOf(43, 44)),
            13 to (setOf(2) + (5..37).toSet()),
            14 to (setOf(6, 7, 8, 9, 13, 14, 15, 18, 20, 21, 22, 24, 25, 27, 28, 30, 32, 34, 36, 37, 38, 41, 42, 48, 49, 62)),
            15 to setOf(2, 34),
            16 to (15..18).toSet()
        ),

        // ==========================================
        // 42: EVANGELIO SEGÚN SAN LUCAS
        // ==========================================
        42 to mapOf(
            2 to setOf(49),
            4 to (setOf(4, 8, 12, 18, 19, 21) + (23..27).toSet() + setOf(35, 43)),
            5 to (setOf(4, 10, 14, 20) + (22..24).toSet() + setOf(27, 31, 32) + (34..39).toSet()),
            6 to (setOf(3, 4, 5, 8, 9, 10) + (20..49).toSet()),
            7 to (setOf(9, 13, 14) + (22..28).toSet() + (31..35).toSet() + (40..43).toSet() + (44..47).toSet() + setOf(48, 50)),
            8 to ((4..8).toSet() + (10..18).toSet() + setOf(21, 22, 25, 28, 30, 39, 45, 46, 48, 50, 52, 54)),
            9 to (setOf(3, 4, 5, 13, 14, 18, 20, 22) + (23..27).toSet() + setOf(41, 44, 48, 50, 55, 58, 60, 62)),
            10 to ((2..16).toSet() + (18..24).toSet() + setOf(26, 28) + (30..37).toSet() + setOf(41, 42)),
            11 to ((2..13).toSet() + (17..26).toSet() + setOf(28) + (29..36).toSet() + (39..52).toSet()),
            12 to (1..59).toSet(),
            13 to ((2..5).toSet() + (6..9).toSet() + setOf(12, 15, 16) + (18..21).toSet() + (24..30).toSet() + (32..35).toSet()),
            14 to (setOf(3, 5) + (8..14).toSet() + (16..24).toSet() + (26..35).toSet()),
            15 to (3..32).toSet(),
            16 to ((1..13).toSet() + (15..18).toSet() + (19..31).toSet()),
            17 to ((1..4).toSet() + (6..10).toSet() + setOf(14, 17, 19) + (20..37).toSet()),
            18 to ((2..8).toSet() + (9..14).toSet() + setOf(16, 17, 19, 20, 22, 24, 25, 27) + (29..30).toSet() + (31..33).toSet() + setOf(41, 42)),
            19 to (setOf(5, 9, 10) + (11..27).toSet() + setOf(30, 31, 40) + (42..44).toSet() + setOf(46)),
            20 to (setOf(3, 4, 8) + (9..18).toSet() + setOf(24, 25) + (34..38).toSet() + (41..44).toSet() + setOf(46, 47)),
            21 to (setOf(3, 4) + (8..36).toSet()),
            22 to (setOf(8, 10, 11, 12) + (15..18).toSet() + setOf(19, 20, 21, 22) + (25..30).toSet() + setOf(31, 32, 34, 35, 36, 38, 40, 42, 46, 48, 51, 52, 53, 67, 68, 70)),
            23 to (setOf(3) + (28..31).toSet() + setOf(34, 43, 46)),
            24 to (setOf(17, 19) + (25..27).toSet() + setOf(36, 38, 39, 41, 44) + (46..49).toSet())
        ),

        // ==========================================
        // 43: EVANGELIO SEGÚN SAN JUAN
        // ==========================================
        43 to mapOf(
            1 to setOf(38, 39, 42, 43, 47, 48, 50, 51),
            2 to setOf(4, 7, 8, 16, 19),
            3 to (setOf(3) + (5..8).toSet() + (10..21).toSet()),
            4 to (setOf(7, 10, 13, 14, 16, 17) + (21..24).toSet() + setOf(26, 32) + (34..38).toSet() + setOf(48, 50, 53)),
            5 to (setOf(6, 8, 14, 17) + (19..47).toSet()),
            6 to (setOf(5, 10, 12, 20, 26, 27, 29, 32, 33) + (35..40).toSet() + (43..51).toSet() + (53..58).toSet() + (61..65).toSet() + setOf(67, 70)),
            7 to ((6..8).toSet() + (16..19).toSet() + (21..24).toSet() + setOf(28, 29, 33, 34, 37, 38)),
            8 to (setOf(7, 10, 11, 12) + (14..19).toSet() + setOf(21) + (23..26).toSet() + setOf(28, 29, 31, 32) + (34..47).toSet() + setOf(49, 50, 51, 54, 55, 56, 58)),
            9 to (setOf(3, 4, 5, 7, 35, 37, 39, 41)),
            10 to ((1..18).toSet() + (25..30).toSet() + setOf(32) + (34..38).toSet()),
            11 to (setOf(4, 9, 10, 11, 14, 15, 23, 25, 26, 34, 39, 40, 41, 42, 44)),
            12 to (setOf(7, 8) + (23..28).toSet() + setOf(30, 31, 32, 35, 36) + (44..50).toSet()),
            13 to (setOf(7, 8, 10, 11) + (12..20).toSet() + setOf(21, 26, 27) + (31..35).toSet() + setOf(36, 38)),
            // Discurso de Despedida y Oración Sacerdotal (caps. 14, 15, 16, 17)
            14 to (1..31).toSet(),
            15 to (1..27).toSet(),
            16 to (1..33).toSet(),
            17 to (1..26).toSet(),
            18 to setOf(4, 5, 7, 8, 11, 20, 21, 23, 34, 36, 37),
            19 to setOf(11, 26, 27, 28, 30),
            20 to (setOf(15, 16, 17, 19, 21, 22, 23, 26, 27, 29)),
            21 to (setOf(5, 6, 10, 12, 15, 16, 17, 18, 19, 22))
        ),

        // ==========================================
        // 44: HECHOS DE LOS APÓSTOLES
        // ==========================================
        44 to mapOf(
            1 to setOf(4, 5, 7, 8),
            9 to (setOf(4, 5, 6, 10, 11, 12, 15, 16)),
            18 to setOf(9, 10),
            20 to setOf(35),
            22 to setOf(7, 8, 10, 18, 21),
            23 to setOf(11),
            26 to (14..18).toSet()
        ),

        // ==========================================
        // 66: APOCALIPSIS
        // ==========================================
        66 to mapOf(
            1 to setOf(8, 11, 17, 18, 19, 20),
            // Mensajes a las 7 Iglesias
            2 to (1..29).toSet(),
            3 to (1..22).toSet(),
            16 to setOf(15),
            22 to setOf(7, 12, 13, 14, 15, 16, 20)
        )
    )
}
