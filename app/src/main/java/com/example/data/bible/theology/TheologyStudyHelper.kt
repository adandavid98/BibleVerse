package com.example.data.bible.theology

import com.example.data.bible.PlanPassageSegment
import com.example.data.bible.ReadingPlanDay

/**
 * Utilidad constructora para los Estudios Bíblicos Teológicos y Exegéticos.
 * Garantiza estudios con rigor bíblico, claridad pedagógica, prosa fluida
 * y una estructura didáctica de 3 pilares:
 * 1. Exposición exegética y teológica profunda (storyNarrative).
 * 2. Tesis doctrinal central (spiritualLesson).
 * 3. Contexto exegético y reflexión de discernimiento (historicalContext).
 */
object TheologyStudyHelper {

    fun seg(bookId: Int, bookName: String, chapter: Int) = PlanPassageSegment(bookId, bookName, chapter)

    fun createStudyDay(
        dayNumber: Int,
        title: String,
        passagesSummary: String,
        primaryBookId: Int,
        primaryChapter: Int,
        passages: List<PlanPassageSegment>,
        studyNarrative: String,
        doctrinalThesis: String,
        exegeticalAnalysis: String
    ): ReadingPlanDay {
        return ReadingPlanDay(
            dayNumber = dayNumber,
            title = title,
            passagesSummary = passagesSummary,
            primaryBookId = primaryBookId,
            primaryChapter = primaryChapter,
            primaryVerse = 1,
            passages = passages,
            storyNarrative = studyNarrative.trimIndent(),
            spiritualLesson = doctrinalThesis.trimIndent(),
            historicalContext = exegeticalAnalysis.trimIndent()
        )
    }
}
