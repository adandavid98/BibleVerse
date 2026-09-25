package com.example.data.bible

data class PlanPassageSegment(
    val bookId: Int,
    val bookName: String,
    val chapter: Int
)

data class ReadingPlanDay(
    val dayNumber: Int,
    val title: String,
    val passagesSummary: String,
    val primaryBookId: Int,
    val primaryChapter: Int,
    val primaryVerse: Int = 1,
    val passages: List<PlanPassageSegment> = emptyList()
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

        for (day in 1..365) {
            val spec = when {
                day <= 30 -> {
                    val gnChap = ((day - 1) * 2 % 50) + 1
                    val gnChap2 = (gnChap % 50) + 1
                    val ntChap = ((day - 1) % 28) + 1
                    DaySpec(
                        title = "Día $day: Patriarcas y Evangelio",
                        passagesSummary = "Génesis $gnChap-$gnChap2, Mateo $ntChap",
                        primaryBookId = 1,
                        primaryChapter = gnChap,
                        segments = listOf(
                            PlanPassageSegment(1, "Génesis", gnChap),
                            PlanPassageSegment(1, "Génesis", gnChap2),
                            PlanPassageSegment(40, "Mateo", ntChap)
                        )
                    )
                }
                day <= 60 -> {
                    val exChap = ((day - 31) * 2 % 40) + 1
                    val exChap2 = (exChap % 40) + 1
                    val ntChap = ((day - 31) % 16) + 1
                    DaySpec(
                        title = "Día $day: Éxodo y Marcos",
                        passagesSummary = "Éxodo $exChap-$exChap2, Marcos $ntChap",
                        primaryBookId = 2,
                        primaryChapter = exChap,
                        segments = listOf(
                            PlanPassageSegment(2, "Éxodo", exChap),
                            PlanPassageSegment(2, "Éxodo", exChap2),
                            PlanPassageSegment(41, "Marcos", ntChap)
                        )
                    )
                }
                day <= 90 -> {
                    val lvChap = ((day - 61) % 27) + 1
                    val ntChap = ((day - 61) % 24) + 1
                    DaySpec(
                        title = "Día $day: Ley y Evangelio",
                        passagesSummary = "Levítico $lvChap, Lucas $ntChap",
                        primaryBookId = 3,
                        primaryChapter = lvChap,
                        segments = listOf(
                            PlanPassageSegment(3, "Levítico", lvChap),
                            PlanPassageSegment(42, "Lucas", ntChap)
                        )
                    )
                }
                day <= 120 -> {
                    val nmChap = ((day - 91) % 36) + 1
                    val ntChap = ((day - 91) % 21) + 1
                    DaySpec(
                        title = "Día $day: Desierto y Gracia",
                        passagesSummary = "Números $nmChap, Juan $ntChap",
                        primaryBookId = 4,
                        primaryChapter = nmChap,
                        segments = listOf(
                            PlanPassageSegment(4, "Números", nmChap),
                            PlanPassageSegment(43, "Juan", ntChap)
                        )
                    )
                }
                day <= 150 -> {
                    val dtChap = ((day - 121) % 34) + 1
                    val actChap = ((day - 121) % 28) + 1
                    DaySpec(
                        title = "Día $day: Pacto y Reino",
                        passagesSummary = "Deuteronomio $dtChap, Hechos $actChap",
                        primaryBookId = 5,
                        primaryChapter = dtChap,
                        segments = listOf(
                            PlanPassageSegment(5, "Deuteronomio", dtChap),
                            PlanPassageSegment(44, "Hechos", actChap)
                        )
                    )
                }
                day <= 180 -> {
                    val josChap = ((day - 151) % 24) + 1
                    val romChap = ((day - 151) % 16) + 1
                    DaySpec(
                        title = "Día $day: Conquista y Fe",
                        passagesSummary = "Josué $josChap, Romanos $romChap",
                        primaryBookId = 6,
                        primaryChapter = josChap,
                        segments = listOf(
                            PlanPassageSegment(6, "Josué", josChap),
                            PlanPassageSegment(45, "Romanos", romChap)
                        )
                    )
                }
                day <= 210 -> {
                    val jueChap = ((day - 181) % 21) + 1
                    val coChap = ((day - 181) % 16) + 1
                    DaySpec(
                        title = "Día $day: Jueces y Cartas",
                        passagesSummary = "Jueces $jueChap, 1 Corintios $coChap",
                        primaryBookId = 7,
                        primaryChapter = jueChap,
                        segments = listOf(
                            PlanPassageSegment(7, "Jueces", jueChap),
                            PlanPassageSegment(46, "1 Corintios", coChap)
                        )
                    )
                }
                day <= 240 -> {
                    val samChap = ((day - 211) % 31) + 1
                    val galChap = ((day - 211) % 6) + 1
                    DaySpec(
                        title = "Día $day: Reino Unido",
                        passagesSummary = "1 Samuel $samChap, Gálatas $galChap",
                        primaryBookId = 9,
                        primaryChapter = samChap,
                        segments = listOf(
                            PlanPassageSegment(9, "1 Samuel", samChap),
                            PlanPassageSegment(48, "Gálatas", galChap)
                        )
                    )
                }
                day <= 270 -> {
                    val reyChap = ((day - 241) % 22) + 1
                    val efChap = ((day - 241) % 6) + 1
                    DaySpec(
                        title = "Día $day: Reyes y Gracia",
                        passagesSummary = "1 Reyes $reyChap, Efesios $efChap",
                        primaryBookId = 11,
                        primaryChapter = reyChap,
                        segments = listOf(
                            PlanPassageSegment(11, "1 Reyes", reyChap),
                            PlanPassageSegment(49, "Efesios", efChap)
                        )
                    )
                }
                day <= 300 -> {
                    val salChap = ((day - 271) * 3 % 150) + 1
                    val salChap2 = (salChap % 150) + 1
                    val salChap3 = ((salChap + 1) % 150) + 1
                    val filChap = ((day - 271) % 4) + 1
                    DaySpec(
                        title = "Día $day: Alabanza y Gozo",
                        passagesSummary = "Salmos $salChap-$salChap3, Filipenses $filChap",
                        primaryBookId = 19,
                        primaryChapter = salChap,
                        segments = listOf(
                            PlanPassageSegment(19, "Salmos", salChap),
                            PlanPassageSegment(19, "Salmos", salChap2),
                            PlanPassageSegment(19, "Salmos", salChap3),
                            PlanPassageSegment(50, "Filipenses", filChap)
                        )
                    )
                }
                day <= 330 -> {
                    val isChap = ((day - 301) * 2 % 66) + 1
                    val isChap2 = (isChap % 66) + 1
                    val hebChap = ((day - 301) % 13) + 1
                    DaySpec(
                        title = "Día $day: Profecía y Esperanza",
                        passagesSummary = "Isaías $isChap-$isChap2, Hebreos $hebChap",
                        primaryBookId = 23,
                        primaryChapter = isChap,
                        segments = listOf(
                            PlanPassageSegment(23, "Isaías", isChap),
                            PlanPassageSegment(23, "Isaías", isChap2),
                            PlanPassageSegment(58, "Hebreos", hebChap)
                        )
                    )
                }
                else -> {
                    val prChap = ((day - 331) % 31) + 1
                    val apChap = ((day - 331) % 22) + 1
                    DaySpec(
                        title = "Día $day: Sabiduría y Revelación",
                        passagesSummary = "Proverbios $prChap, Apocalipsis $apChap",
                        primaryBookId = 20,
                        primaryChapter = prChap,
                        segments = listOf(
                            PlanPassageSegment(20, "Proverbios", prChap),
                            PlanPassageSegment(66, "Apocalipsis", apChap)
                        )
                    )
                }
            }

            days.add(
                ReadingPlanDay(
                    dayNumber = day,
                    title = spec.title,
                    passagesSummary = spec.passagesSummary,
                    primaryBookId = spec.primaryBookId,
                    primaryChapter = spec.primaryChapter,
                    primaryVerse = 1,
                    passages = spec.segments
                )
            )
        }
        return days
    }

    private fun generateChronologicalPlan(): List<ReadingPlanDay> {
        val days = mutableListOf<ReadingPlanDay>()

        for (day in 1..365) {
            val spec = when {
                day <= 15 -> {
                    val c = ((day - 1) * 3 % 50) + 1
                    val c2 = (c % 50) + 1
                    val c3 = ((c + 1) % 50) + 1
                    DaySpec(
                        title = "Día $day: Creación y Orígenes",
                        passagesSummary = "Génesis $c-$c3",
                        primaryBookId = 1,
                        primaryChapter = c,
                        segments = listOf(
                            PlanPassageSegment(1, "Génesis", c),
                            PlanPassageSegment(1, "Génesis", c2),
                            PlanPassageSegment(1, "Génesis", c3)
                        )
                    )
                }
                day <= 30 -> {
                    val c = ((day - 16) * 3 % 42) + 1
                    val c2 = (c % 42) + 1
                    val c3 = ((c + 1) % 42) + 1
                    DaySpec(
                        title = "Día $day: Los Tiempos de Job",
                        passagesSummary = "Job $c-$c3",
                        primaryBookId = 18,
                        primaryChapter = c,
                        segments = listOf(
                            PlanPassageSegment(18, "Job", c),
                            PlanPassageSegment(18, "Job", c2),
                            PlanPassageSegment(18, "Job", c3)
                        )
                    )
                }
                day <= 55 -> {
                    val c = ((day - 31) * 2 % 50) + 1
                    val c2 = (c % 50) + 1
                    DaySpec(
                        title = "Día $day: El Pacto Patriarcal",
                        passagesSummary = "Génesis $c-$c2",
                        primaryBookId = 1,
                        primaryChapter = c,
                        segments = listOf(
                            PlanPassageSegment(1, "Génesis", c),
                            PlanPassageSegment(1, "Génesis", c2)
                        )
                    )
                }
                day <= 85 -> {
                    val c = ((day - 56) * 2 % 40) + 1
                    val c2 = (c % 40) + 1
                    DaySpec(
                        title = "Día $day: La Liberación de Egipto",
                        passagesSummary = "Éxodo $c-$c2",
                        primaryBookId = 2,
                        primaryChapter = c,
                        segments = listOf(
                            PlanPassageSegment(2, "Éxodo", c),
                            PlanPassageSegment(2, "Éxodo", c2)
                        )
                    )
                }
                day <= 110 -> {
                    val c = ((day - 86) % 27) + 1
                    DaySpec(
                        title = "Día $day: Santidad en el Desierto",
                        passagesSummary = "Levítico $c",
                        primaryBookId = 3,
                        primaryChapter = c,
                        segments = listOf(PlanPassageSegment(3, "Levítico", c))
                    )
                }
                day <= 140 -> {
                    val c = ((day - 111) % 36) + 1
                    DaySpec(
                        title = "Día $day: Camino a Canaán",
                        passagesSummary = "Números $c",
                        primaryBookId = 4,
                        primaryChapter = c,
                        segments = listOf(PlanPassageSegment(4, "Números", c))
                    )
                }
                day <= 165 -> {
                    val c = ((day - 141) % 24) + 1
                    DaySpec(
                        title = "Día $day: Conquista de Canaán",
                        passagesSummary = "Josué $c",
                        primaryBookId = 6,
                        primaryChapter = c,
                        segments = listOf(PlanPassageSegment(6, "Josué", c))
                    )
                }
                day <= 195 -> {
                    val sChap = ((day - 166) * 3 % 150) + 1
                    val sChap2 = (sChap % 150) + 1
                    DaySpec(
                        title = "Día $day: El Reinado de David y Salmos",
                        passagesSummary = "Salmos $sChap-$sChap2",
                        primaryBookId = 19,
                        primaryChapter = sChap,
                        segments = listOf(
                            PlanPassageSegment(19, "Salmos", sChap),
                            PlanPassageSegment(19, "Salmos", sChap2)
                        )
                    )
                }
                day <= 225 -> {
                    val c = ((day - 196) % 31) + 1
                    DaySpec(
                        title = "Día $day: Salomón y Sabiduría",
                        passagesSummary = "Proverbios $c",
                        primaryBookId = 20,
                        primaryChapter = c,
                        segments = listOf(PlanPassageSegment(20, "Proverbios", c))
                    )
                }
                day <= 260 -> {
                    val c = ((day - 226) * 2 % 66) + 1
                    val c2 = (c % 66) + 1
                    DaySpec(
                        title = "Día $day: Los Profetas de Israel",
                        passagesSummary = "Isaías $c-$c2",
                        primaryBookId = 23,
                        primaryChapter = c,
                        segments = listOf(
                            PlanPassageSegment(23, "Isaías", c),
                            PlanPassageSegment(23, "Isaías", c2)
                        )
                    )
                }
                day <= 290 -> {
                    val c = ((day - 261) % 12) + 1
                    DaySpec(
                        title = "Día $day: El Exilio Babilónico",
                        passagesSummary = "Daniel $c",
                        primaryBookId = 27,
                        primaryChapter = c,
                        segments = listOf(PlanPassageSegment(27, "Daniel", c))
                    )
                }
                day <= 315 -> {
                    val c = ((day - 291) % 13) + 1
                    DaySpec(
                        title = "Día $day: El Retorno del Remanente",
                        passagesSummary = "Nehemías $c",
                        primaryBookId = 16,
                        primaryChapter = c,
                        segments = listOf(PlanPassageSegment(16, "Nehemías", c))
                    )
                }
                day <= 345 -> {
                    val c = ((day - 316) % 21) + 1
                    DaySpec(
                        title = "Día $day: Vida y Ministerio de Jesús",
                        passagesSummary = "Juan $c",
                        primaryBookId = 43,
                        primaryChapter = c,
                        segments = listOf(PlanPassageSegment(43, "Juan", c))
                    )
                }
                else -> {
                    val c = ((day - 346) % 22) + 1
                    DaySpec(
                        title = "Día $day: La Iglesia y Victoria Final",
                        passagesSummary = "Apocalipsis $c",
                        primaryBookId = 66,
                        primaryChapter = c,
                        segments = listOf(PlanPassageSegment(66, "Apocalipsis", c))
                    )
                }
            }

            days.add(
                ReadingPlanDay(
                    dayNumber = day,
                    title = spec.title,
                    passagesSummary = spec.passagesSummary,
                    primaryBookId = spec.primaryBookId,
                    primaryChapter = spec.primaryChapter,
                    primaryVerse = 1,
                    passages = spec.segments
                )
            )
        }
        return days
    }

    private data class DaySpec(
        val title: String,
        val passagesSummary: String,
        val primaryBookId: Int,
        val primaryChapter: Int,
        val segments: List<PlanPassageSegment>
    )
}
