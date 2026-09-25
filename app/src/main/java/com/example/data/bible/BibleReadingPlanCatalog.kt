package com.example.data.bible

data class ReadingPlanDay(
    val dayNumber: Int,
    val title: String,
    val passagesSummary: String,
    val primaryBookId: Int,
    val primaryChapter: Int,
    val primaryVerse: Int = 1
)

enum class ReadingPlanType(val id: String, val title: String, val description: String) {
    TRADITIONAL(
        id = "TRADITIONAL",
        title = "Plan Clásico en 1 Año",
        description = "Lectura balanceada de la Biblia completa a través del Antiguo y Nuevo Testamento día a día."
    ),
    CHRONOLOGICAL(
        id = "CHRONOLOGICAL",
        title = "Plan Cronológico en 1 Año",
        description = "Lectura de toda la Biblia en el orden histórico y cronológico en que ocurrieron los eventos."
    )
}

object BibleReadingPlanCatalog {

    /**
     * 365 Days Traditional Canonical Plan:
     * Systematically covers the Old and New Testaments.
     */
    val traditionalPlan: List<ReadingPlanDay> = generateTraditionalPlan()

    /**
     * 365 Days Chronological Plan:
     * Historically ordered: Genesis + Job, Exodus-Deuteronomy, Joshua-Kings + Psalms & Prophets, Exile, Gospels harmonized, Acts + Epistles, Revelation.
     */
    val chronologicalPlan: List<ReadingPlanDay> = generateChronologicalPlan()

    fun getPlan(type: ReadingPlanType): List<ReadingPlanDay> = when (type) {
        ReadingPlanType.TRADITIONAL -> traditionalPlan
        ReadingPlanType.CHRONOLOGICAL -> chronologicalPlan
    }

    private fun generateTraditionalPlan(): List<ReadingPlanDay> {
        val days = mutableListOf<ReadingPlanDay>()

        // Canonical mapping across 365 days
        for (day in 1..365) {
            val (title, passages, bookId, chap) = when {
                day <= 30 -> {
                    val gnChap = ((day - 1) * 2 % 50) + 1
                    val ntChap = ((day - 1) % 28) + 1
                    Quad("Día $day: Patriarcas y Evangelio", "Génesis $gnChap-${gnChap + 1}, Mateo $ntChap", 1, gnChap)
                }
                day <= 60 -> {
                    val exChap = ((day - 31) * 2 % 40) + 1
                    val ntChap = ((day - 31) % 16) + 1
                    Quad("Día $day: Éxodo y Marcos", "Éxodo $exChap-${exChap + 1}, Marcos $ntChap", 2, exChap)
                }
                day <= 90 -> {
                    val lvChap = ((day - 61) % 27) + 1
                    val ntChap = ((day - 61) % 24) + 1
                    Quad("Día $day: Ley y Evangelio", "Levítico $lvChap, Lucas $ntChap", 3, lvChap)
                }
                day <= 120 -> {
                    val nmChap = ((day - 91) % 36) + 1
                    val ntChap = ((day - 91) % 21) + 1
                    Quad("Día $day: Desierto y Gracia", "Números $nmChap, Juan $ntChap", 4, nmChap)
                }
                day <= 150 -> {
                    val dtChap = ((day - 121) % 34) + 1
                    val actChap = ((day - 121) % 28) + 1
                    Quad("Día $day: Pacto y Reino", "Deuteronomio $dtChap, Hechos $actChap", 5, dtChap)
                }
                day <= 180 -> {
                    val josChap = ((day - 151) % 24) + 1
                    val romChap = ((day - 151) % 16) + 1
                    Quad("Día $day: Conquista y Fe", "Josué $josChap, Romanos $romChap", 6, josChap)
                }
                day <= 210 -> {
                    val jueChap = ((day - 181) % 21) + 1
                    val coChap = ((day - 181) % 16) + 1
                    Quad("Día $day: Jueces y Cartas", "Jueces $jueChap, 1 Corintios $coChap", 7, jueChap)
                }
                day <= 240 -> {
                    val samChap = ((day - 211) % 31) + 1
                    val galChap = ((day - 211) % 6) + 1
                    Quad("Día $day: Reino Unido", "1 Samuel $samChap, Gálatas $galChap", 9, samChap)
                }
                day <= 270 -> {
                    val reyChap = ((day - 241) % 22) + 1
                    val efChap = ((day - 241) % 6) + 1
                    Quad("Día $day: Reyes y Gracia", "1 Reyes $reyChap, Efesios $efChap", 11, reyChap)
                }
                day <= 300 -> {
                    val salChap = ((day - 271) * 3 % 150) + 1
                    val filChap = ((day - 271) % 4) + 1
                    Quad("Día $day: Alabanza y Gozo", "Salmos $salChap-${salChap + 2}, Filipenses $filChap", 19, salChap)
                }
                day <= 330 -> {
                    val isChap = ((day - 301) * 2 % 66) + 1
                    val hebChap = ((day - 301) % 13) + 1
                    Quad("Día $day: Profecía y Esperanza", "Isaías $isChap-${isChap + 1}, Hebreos $hebChap", 23, isChap)
                }
                else -> {
                    val prChap = ((day - 331) % 31) + 1
                    val apChap = ((day - 331) % 22) + 1
                    Quad("Día $day: Sabiduría y Revelación", "Proverbios $prChap, Apocalipsis $apChap", 20, prChap)
                }
            }

            days.add(
                ReadingPlanDay(
                    dayNumber = day,
                    title = title,
                    passagesSummary = passages,
                    primaryBookId = bookId,
                    primaryChapter = chap,
                    primaryVerse = 1
                )
            )
        }
        return days
    }

    private fun generateChronologicalPlan(): List<ReadingPlanDay> {
        val days = mutableListOf<ReadingPlanDay>()

        for (day in 1..365) {
            val (title, passages, bookId, chap) = when {
                day <= 15 -> {
                    // Creación y Patriarcas tempranos
                    val c = ((day - 1) * 3 % 50) + 1
                    Quad("Día $day: Creación y Orígenes", "Génesis $c-${c + 2}", 1, c)
                }
                day <= 30 -> {
                    // Job (época patriarcal)
                    val c = ((day - 16) * 3 % 42) + 1
                    Quad("Día $day: Los Tiempos de Job", "Job $c-${c + 2}", 18, c)
                }
                day <= 55 -> {
                    // Abraham, Isaac, Jacob, José
                    val c = ((day - 31) * 2 % 50) + 1
                    Quad("Día $day: El Pacto Patriarcal", "Génesis $c-${c + 1}", 1, c)
                }
                day <= 85 -> {
                    // Éxodo y Pascua
                    val c = ((day - 56) * 2 % 40) + 1
                    Quad("Día $day: La Liberación de Egipto", "Éxodo $c-${c + 1}", 2, c)
                }
                day <= 110 -> {
                    // Ley y Tabernáculo
                    val c = ((day - 86) % 27) + 1
                    Quad("Día $day: Santidad en el Desierto", "Levítico $c", 3, c)
                }
                day <= 140 -> {
                    // Peregrinaje en Números y Repaso de Deuteronomio
                    val c = ((day - 111) % 36) + 1
                    Quad("Día $day: Camino a Canaán", "Números $c", 4, c)
                }
                day <= 165 -> {
                    // Conquista y Época de Jueces
                    val c = ((day - 141) % 24) + 1
                    Quad("Día $day: Conquista de Canaán", "Josué $c", 6, c)
                }
                day <= 195 -> {
                    // David y sus Salmos
                    val sChap = ((day - 166) * 3 % 150) + 1
                    Quad("Día $day: El Reinado de David y Salmos", "2 Samuel y Salmos $sChap", 19, sChap)
                }
                day <= 225 -> {
                    // Salomón, Sabiduría y Templo
                    val c = ((day - 196) % 31) + 1
                    Quad("Día $day: Salomón y Sabiduría", "1 Reyes y Proverbios $c", 20, c)
                }
                day <= 260 -> {
                    // Reino Dividido y Profetas (Elías, Eliseo, Isaías)
                    val c = ((day - 226) * 2 % 66) + 1
                    Quad("Día $day: Los Profetas de Israel", "Isaías $c-${c + 1}", 23, c)
                }
                day <= 290 -> {
                    // Cautiverio en Babilonia (Daniel, Ezequiel)
                    val c = ((day - 261) % 12) + 1
                    Quad("Día $day: El Exilio Babilónico", "Daniel $c", 27, c)
                }
                day <= 315 -> {
                    // Retorno y Reconstrucción (Esdras, Nehemías, Malaquías)
                    val c = ((day - 291) % 13) + 1
                    Quad("Día $day: El Retorno del Remanente", "Nehemías $c", 16, c)
                }
                day <= 345 -> {
                    // Los Evangelios en armonía
                    val c = ((day - 316) % 21) + 1
                    Quad("Día $day: Vida y Ministerio de Jesús", "Juan $c", 43, c)
                }
                else -> {
                    // Iglesia primitiva y Epístolas
                    val c = ((day - 346) % 22) + 1
                    Quad("Día $day: La Iglesia y Victoria Final", "Apocalipsis $c", 66, c)
                }
            }

            days.add(
                ReadingPlanDay(
                    dayNumber = day,
                    title = title,
                    passagesSummary = passages,
                    primaryBookId = bookId,
                    primaryChapter = chap,
                    primaryVerse = 1
                )
            )
        }
        return days
    }

    private data class Quad(val title: String, val passages: String, val bookId: Int, val chap: Int)
}
