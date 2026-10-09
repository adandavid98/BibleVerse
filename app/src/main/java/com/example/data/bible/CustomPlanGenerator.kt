package com.example.data.bible

import com.example.data.model.CustomPlanDayEntity
import com.example.data.model.CustomReadingPlanEntity
import com.example.data.model.PlanDistributionMode
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

data class PlanPresetItem(
    val id: String,
    val title: String,
    val emoji: String,
    val bookIds: List<Int>,
    val description: String
)

object CustomPlanPresets {
    val presets = listOf(
        PlanPresetItem(
            id = "gospels",
            title = "Los 4 Evangelios",
            emoji = "✝️",
            bookIds = listOf(40, 41, 42, 43), // Mateo, Marcos, Lucas, Juan
            description = "Vida y enseñanzas de Jesucristo (89 capítulos)"
        ),
        PlanPresetItem(
            id = "new_testament",
            title = "Nuevo Testamento",
            emoji = "📖",
            bookIds = (40..66).toList(),
            description = "Desde Mateo hasta Apocalipsis (260 capítulos)"
        ),
        PlanPresetItem(
            id = "wisdom_psalms",
            title = "Salmos y Sabiduría",
            emoji = "🕊️",
            bookIds = listOf(18, 19, 20, 21, 22), // Job, Salmos, Proverbios, Eclesiastés, Cantares
            description = "Alabanza, consuelo y sabiduría divina (243 capítulos)"
        ),
        PlanPresetItem(
            id = "pentateuch",
            title = "El Pentateuco",
            emoji = "📜",
            bookIds = (1..5).toList(), // Génesis a Deuteronomio
            description = "La Ley y los orígenes de la fe (187 capítulos)"
        ),
        PlanPresetItem(
            id = "paul_epistles",
            title = "Epístolas Paulinas",
            emoji = "✉️",
            bookIds = (45..58).toList(), // Romanos a Filemón
            description = "Cartas apostólicas a las iglesias (87 capítulos)"
        ),
        PlanPresetItem(
            id = "all_bible",
            title = "Toda la Biblia",
            emoji = "✨",
            bookIds = (1..66).toList(),
            description = "Los 66 libros canónicos completos (1,189 capítulos)"
        )
    )
}

object CustomPlanGenerator {

    fun generatePlan(
        title: String,
        description: String = "",
        selectedBookIds: List<Int>,
        mode: PlanDistributionMode,
        targetDays: Int = 30,
        chaptersPerDay: Int = 2
    ): Pair<CustomReadingPlanEntity, List<CustomPlanDayEntity>> {
        val planId = UUID.randomUUID().toString()

        // 1. Resolve ordered books from BibleCatalog
        val books = selectedBookIds.mapNotNull { id ->
            BibleCatalog.books.find { it.bookId == id }
        }.sortedBy { it.bookId }

        // 2. Expand all chapters as individual passage segments
        val allChapters = mutableListOf<PlanPassageSegment>()
        for (book in books) {
            for (ch in 1..book.chaptersCount) {
                allChapters.add(
                    PlanPassageSegment(
                        bookId = book.bookId,
                        bookName = book.name,
                        chapter = ch
                    )
                )
            }
        }

        val totalChapters = allChapters.size.coerceAtLeast(1)

        // 3. Partition chapters according to distribution mode
        val daysChunks: List<List<PlanPassageSegment>> = when (mode) {
            PlanDistributionMode.BY_TARGET_DAYS -> {
                val numDays = targetDays.coerceIn(1, totalChapters)
                val base = totalChapters / numDays
                val remainder = totalChapters % numDays

                val chunks = mutableListOf<List<PlanPassageSegment>>()
                var cursor = 0
                for (d in 0 until numDays) {
                    val take = base + if (d < remainder) 1 else 0
                    val chunk = allChapters.subList(cursor, (cursor + take).coerceAtMost(allChapters.size))
                    chunks.add(chunk)
                    cursor += take
                }
                chunks
            }
            PlanDistributionMode.BY_CHAPTERS_PER_DAY -> {
                val perDay = chaptersPerDay.coerceAtLeast(1)
                allChapters.chunked(perDay)
            }
        }

        val totalDays = daysChunks.size

        // 4. Create CustomReadingPlanEntity
        val planEntity = CustomReadingPlanEntity(
            id = planId,
            title = title.ifBlank { "Mi Plan de Lectura" },
            description = description.ifBlank { "${books.size} libros • $totalChapters capítulos" },
            selectedBookIds = selectedBookIds.joinToString(","),
            totalChapters = totalChapters,
            totalDays = totalDays,
            distributionMode = mode.name,
            chaptersPerDay = if (mode == PlanDistributionMode.BY_CHAPTERS_PER_DAY) chaptersPerDay else null,
            createdAt = System.currentTimeMillis(),
            startDate = System.currentTimeMillis(),
            completedAt = null,
            isCompleted = false,
            isArchived = false
        )

        // 5. Create day schedule entities
        val dayEntities = daysChunks.mapIndexed { index, segments ->
            val dayNumber = index + 1
            val summary = formatPassageSummary(segments)
            val primary = segments.firstOrNull() ?: PlanPassageSegment(1, "Génesis", 1)

            CustomPlanDayEntity(
                planId = planId,
                dayNumber = dayNumber,
                passageSummary = summary,
                primaryBookId = primary.bookId,
                primaryChapter = primary.chapter,
                passagesJson = serializeSegmentsToJson(segments),
                isCompleted = false,
                completedAt = null
            )
        }

        return Pair(planEntity, dayEntities)
    }

    fun formatPassageSummary(segments: List<PlanPassageSegment>): String {
        if (segments.isEmpty()) return "Lectura"
        if (segments.size == 1) {
            val s = segments.first()
            return "${s.bookName} ${s.chapter}"
        }

        // Group consecutive segments by book
        val groups = mutableListOf<MutableList<PlanPassageSegment>>()
        for (seg in segments) {
            if (groups.isEmpty() || groups.last().first().bookId != seg.bookId) {
                groups.add(mutableListOf(seg))
            } else {
                groups.last().add(seg)
            }
        }

        return groups.joinToString(", ") { group ->
            val bookName = group.first().bookName
            if (group.size == 1) {
                "$bookName ${group.first().chapter}"
            } else {
                val firstCh = group.first().chapter
                val lastCh = group.last().chapter
                "$bookName $firstCh-$lastCh"
            }
        }
    }

    fun serializeSegmentsToJson(segments: List<PlanPassageSegment>): String {
        val array = JSONArray()
        for (seg in segments) {
            val obj = JSONObject()
            obj.put("bookId", seg.bookId)
            obj.put("bookName", seg.bookName)
            obj.put("chapter", seg.chapter)
            array.put(obj)
        }
        return array.toString()
    }

    fun deserializeSegmentsFromJson(json: String): List<PlanPassageSegment> {
        if (json.isBlank()) return emptyList()
        return try {
            val array = JSONArray(json)
            val list = mutableListOf<PlanPassageSegment>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    PlanPassageSegment(
                        bookId = obj.getInt("bookId"),
                        bookName = obj.getString("bookName"),
                        chapter = obj.getInt("chapter")
                    )
                )
            }
            list
        } catch (_: Exception) {
            emptyList()
        }
    }
}
