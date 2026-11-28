package com.example.export

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.data.bible.BibleCatalog
import com.example.data.bible.ChatMessage
import com.example.data.model.VerseEntity
import com.example.ui.reader.export.CardBitmapRenderer
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ExportManager {

    fun exportToPdf(context: Context, title: String, verses: List<VerseEntity>): File {
        val exportDir = File(context.cacheDir, "exports").apply { mkdirs() }
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val pdfFile = File(exportDir, "versiculos_${timeStamp}.pdf")

        val pdfDoc = PdfDocument()
        val pageWidth = 595 // Standard A4 width in points at 72dpi
        val pageHeight = 842 // Standard A4 height in points at 72dpi
        val margin = 40f
        val contentWidth = pageWidth - (margin * 2)

        val titlePaint = Paint().apply {
            color = Color.rgb(26, 47, 75) // Deep navy
            textSize = 18f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            isAntiAlias = true
        }

        val subtitlePaint = Paint().apply {
            color = Color.rgb(120, 110, 100)
            textSize = 10f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }

        val refPaint = Paint().apply {
            color = Color.rgb(30, 41, 59)
            textSize = 12f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            isAntiAlias = true
        }

        val badgePaint = Paint().apply {
            color = Color.rgb(180, 130, 60)
            textSize = 9f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            isAntiAlias = true
        }

        val textPaint = Paint().apply {
            color = Color.rgb(33, 37, 41)
            textSize = 11f
            typeface = Typeface.create(Typeface.SERIF, Typeface.ITALIC)
            isAntiAlias = true
        }

        val contextPaint = Paint().apply {
            color = Color.rgb(74, 85, 104)
            textSize = 9.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }

        val notesPaint = Paint().apply {
            color = Color.rgb(40, 116, 166)
            textSize = 9.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            isAntiAlias = true
        }

        val dividerPaint = Paint().apply {
            color = Color.rgb(220, 224, 230)
            strokeWidth = 1f
            isAntiAlias = true
        }

        val pageNumberPaint = Paint().apply {
            color = Color.rgb(150, 150, 150)
            textSize = 9f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        var pageNumber = 1
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
        var page = pdfDoc.startPage(pageInfo)
        var canvas: Canvas = page.canvas

        fun drawHeader(c: Canvas) {
            c.drawText(title, margin, margin + 15f, titlePaint)
            val dateStr = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
            c.drawText("Sagrada Escritura • Versículos Fundamentales • Exportado el $dateStr", margin, margin + 30f, subtitlePaint)
            c.drawLine(margin, margin + 38f, pageWidth - margin, margin + 38f, dividerPaint)
        }

        drawHeader(canvas)
        var currentY = margin + 55f

        fun splitIntoLines(text: String, paint: Paint, maxWidth: Float): List<String> {
            val lines = mutableListOf<String>()
            val words = text.split(" ")
            var currentLine = StringBuilder()

            for (word in words) {
                val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
                val width = paint.measureText(testLine)
                if (width <= maxWidth) {
                    currentLine = StringBuilder(testLine)
                } else {
                    if (currentLine.isNotEmpty()) {
                        lines.add(currentLine.toString())
                    }
                    currentLine = StringBuilder(word)
                }
            }
            if (currentLine.isNotEmpty()) {
                lines.add(currentLine.toString())
            }
            return lines
        }

        for (verse in verses) {
            val textLines = splitIntoLines(verse.text, textPaint, contentWidth - 16f)
            val contextLines = splitIntoLines("CONTEXTO: " + verse.context, contextPaint, contentWidth - 16f)
            val noteLines = if (verse.notes.isNotBlank()) {
                splitIntoLines("NOTA PERSONAL: " + verse.notes, notesPaint, contentWidth - 16f)
            } else emptyList()

            val verseHeight = 25f + (textLines.size * 14f) + 6f + (contextLines.size * 12f) + (if (noteLines.isNotEmpty()) noteLines.size * 12f + 6f else 0f) + 20f

            if (currentY + verseHeight > pageHeight - margin - 20f) {
                // Draw page number on footer
                canvas.drawText("Página $pageNumber", pageWidth / 2f, pageHeight - margin + 10f, pageNumberPaint)
                pdfDoc.finishPage(page)

                pageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                page = pdfDoc.startPage(pageInfo)
                canvas = page.canvas
                drawHeader(canvas)
                currentY = margin + 55f
            }

            // Reference and topic
            canvas.drawText(verse.reference, margin, currentY, refPaint)
            val topicBadge = "[ ${verse.testament} • ${verse.topic} ]"
            val badgeX = pageWidth - margin - badgePaint.measureText(topicBadge)
            canvas.drawText(topicBadge, badgeX, currentY, badgePaint)
            currentY += 16f

            // Left vertical accent bar
            val accentPaint = Paint().apply {
                color = Color.rgb(200, 160, 60)
                strokeWidth = 2.5f
            }
            val startY = currentY - 10f
            val endY = currentY + (textLines.size * 14f)

            // Text lines
            for (line in textLines) {
                canvas.drawText(line, margin + 10f, currentY, textPaint)
                currentY += 14f
            }

            canvas.drawLine(margin + 2f, startY, margin + 2f, endY, accentPaint)
            currentY += 6f

            // Context lines
            for (cline in contextLines) {
                canvas.drawText(cline, margin + 10f, currentY, contextPaint)
                currentY += 12f
            }

            // Notes if available
            if (noteLines.isNotEmpty()) {
                currentY += 4f
                for (nline in noteLines) {
                    canvas.drawText(nline, margin + 10f, currentY, notesPaint)
                    currentY += 12f
                }
            }

            currentY += 10f
            canvas.drawLine(margin, currentY, pageWidth - margin, currentY, dividerPaint)
            currentY += 14f
        }

        // Draw last page number
        canvas.drawText("Página $pageNumber", pageWidth / 2f, pageHeight - margin + 10f, pageNumberPaint)
        pdfDoc.finishPage(page)

        FileOutputStream(pdfFile).use { out ->
            pdfDoc.writeTo(out)
        }
        pdfDoc.close()

        return pdfFile
    }

    fun exportChatToPdf(
        context: Context,
        messages: List<ChatMessage>,
        title: String = "Estudio y Consulta Teológica"
    ): File {
        val exportDir = File(context.cacheDir, "exports").apply { mkdirs() }
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val pdfFile = File(exportDir, "chat_teologico_${timeStamp}.pdf")

        val validMessages = messages.filter { it.text.isNotBlank() && !it.text.trim().startsWith("⏳") }
        val userQuestionsCount = validMessages.count { it.isUser }
        val firstQuestion = validMessages.firstOrNull { it.isUser }?.text?.trim().orEmpty()
        val resolvedTitle = if (firstQuestion.isNotBlank()) {
            val cleanQ = firstQuestion.replace(Regex("\\s+"), " ")
            if (cleanQ.length > 74) "${cleanQ.take(71)}..." else cleanQ
        } else {
            title
        }

        val pdfDoc = PdfDocument()
        val pageWidth = 595
        val pageHeight = 842
        val margin = 42f
        val contentWidth = pageWidth - (margin * 2)
        val footerTopY = pageHeight - 36f
        val maxContentBottomY = footerTopY - 14f

        val eyebrowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(158, 107, 40)
            textSize = 9f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            letterSpacing = 0.08f
        }
        val docTitlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(26, 47, 75)
            textSize = 15.5f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        }
        val compactHeaderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(100, 116, 139)
            textSize = 9f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        }
        val metaPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(100, 116, 139)
            textSize = 9.2f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        }
        val roleUserPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(26, 47, 75)
            textSize = 8.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            letterSpacing = 0.06f
        }
        val roleAssistantPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(158, 107, 40)
            textSize = 8.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            letterSpacing = 0.06f
        }
        val timePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(100, 116, 139)
            textSize = 8.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.RIGHT
        }
        val userTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(15, 23, 42)
            textSize = 10.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        }
        val headingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(26, 47, 75)
            textSize = 10.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        }
        val bodyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(33, 37, 41)
            textSize = 10.2f
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
        }
        val bulletPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(51, 65, 85)
            textSize = 10f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        }
        val citeLabelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(29, 78, 216)
            textSize = 9f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        }
        val goldDividerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(180, 130, 60)
            strokeWidth = 2f
        }
        val lightDividerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(226, 232, 240)
            strokeWidth = 1f
        }
        val userBoxBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(241, 245, 249)
            style = Paint.Style.FILL
        }
        val userAccentBarPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(26, 47, 75)
            style = Paint.Style.FILL
        }
        val assistantAccentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(200, 160, 60)
            strokeWidth = 2.5f
        }
        val indexBoxBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(248, 250, 252)
            style = Paint.Style.FILL
        }
        val indexBoxBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(203, 213, 225)
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        val footerLeftPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(148, 163, 184)
            textSize = 8.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.LEFT
        }
        val footerRightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(148, 163, 184)
            textSize = 8.5f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.RIGHT
        }

        fun wrapLines(text: String, paint: Paint, maxWidth: Float): List<String> {
            val result = mutableListOf<String>()
            val words = text.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
            if (words.isEmpty()) return result
            var current = StringBuilder()
            for (word in words) {
                val candidate = if (current.isEmpty()) word else "$current $word"
                if (paint.measureText(candidate) <= maxWidth) {
                    current = StringBuilder(candidate)
                } else {
                    if (current.isNotEmpty()) {
                        result.add(current.toString())
                        current = StringBuilder()
                    }
                    if (paint.measureText(word) <= maxWidth) {
                        current.append(word)
                    } else {
                        var chunk = StringBuilder()
                        for (ch in word) {
                            val test = chunk.toString() + ch
                            if (paint.measureText(test) <= maxWidth) {
                                chunk.append(ch)
                            } else {
                                if (chunk.isNotEmpty()) result.add(chunk.toString())
                                chunk = StringBuilder().append(ch)
                            }
                        }
                        if (chunk.isNotEmpty()) current = chunk
                    }
                }
            }
            if (current.isNotEmpty()) result.add(current.toString())
            return result
        }

        val bookNamesRegexPart = BibleCatalog.books
            .map { Regex.escape(it.name) }
            .sortedByDescending { it.length }
            .joinToString("|")
        val citationRegex = Regex("""\b((?:$bookNamesRegexPart)\s+\d+(?::\d+(?:[-–]\d+)?)?)\b""")

        fun extractCitations(msg: ChatMessage): List<String> {
            val found = LinkedHashSet<String>()
            msg.suggestedVerseReference?.trim()?.takeIf { it.isNotEmpty() }?.let { found.add(it) }
            citationRegex.findAll(msg.text).forEach { match ->
                found.add(match.groupValues[1].replace("–", "-"))
            }
            return found.toList()
        }

        fun sanitizeLine(raw: String): String {
            return raw
                .replace(Regex("""^\s*#{1,6}\s*"""), "")
                .replace("**", "")
                .replace("__", "")
                .replace("`", "")
                .replace(Regex("""\*([^*\n]+)\*"""), "$1")
                .replace("*", "")
                .replace(Regex("""\s+--\s+"""), " — ")
                .trim()
        }

        val exportDateStr = SimpleDateFormat("dd/MM/yyyy • HH:mm", Locale.getDefault()).format(Date())
        val timeFmt = SimpleDateFormat("HH:mm", Locale.getDefault())

        var pageNumber = 1
        var page = pdfDoc.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create())
        var canvas: Canvas = page.canvas
        var currentY = margin

        fun drawFooter(c: Canvas, num: Int) {
            c.drawLine(margin, footerTopY - 8f, pageWidth - margin, footerTopY - 8f, lightDividerPaint)
            c.drawText(CardBitmapRenderer.WATERMARK_TEXT, margin, footerTopY + 6f, footerLeftPaint)
            c.drawText("Página $num", pageWidth - margin, footerTopY + 6f, footerRightPaint)
        }

        fun drawFirstPageHeader(c: Canvas): Float {
            var y = margin + 8f
            c.drawText("BIBLEVERSE • ESTUDIO TEOLÓGICO", margin, y, eyebrowPaint)
            y += 18f
            val titleLines = wrapLines(resolvedTitle, docTitlePaint, contentWidth)
            for (tl in titleLines) {
                c.drawText(tl, margin, y, docTitlePaint)
                y += 19f
            }
            val metaText = "Exportado: $exportDateStr   •   $userQuestionsCount consulta(s) • ${validMessages.size} mensaje(s)"
            c.drawText(metaText, margin, y + 2f, metaPaint)
            y += 12f
            c.drawLine(margin, y, pageWidth - margin, y, goldDividerPaint)
            return y + 18f
        }

        fun drawSubsequentPageHeader(c: Canvas): Float {
            val y = margin + 6f
            c.drawText("BibleVerse • Estudio y Consulta Teológica", margin, y, compactHeaderPaint)
            c.drawLine(margin, y + 8f, pageWidth - margin, y + 8f, lightDividerPaint)
            return y + 24f
        }

        fun startNewPage() {
            drawFooter(canvas, pageNumber)
            pdfDoc.finishPage(page)
            pageNumber++
            page = pdfDoc.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create())
            canvas = page.canvas
            currentY = drawSubsequentPageHeader(canvas)
        }

        fun ensureSpace(neededHeight: Float) {
            if (currentY + neededHeight > maxContentBottomY) {
                startNewPage()
            }
        }

        currentY = drawFirstPageHeader(canvas)

        val allSessionCitations = LinkedHashSet<String>()
        var questionCounter = 0

        for ((idx, msg) in validMessages.withIndex()) {
            val msgTime = if (msg.timestamp > 0L) timeFmt.format(Date(msg.timestamp)) else ""

            if (msg.isUser) {
                questionCounter++
                val cleanQ = sanitizeLine(msg.text.replace("\n", " "))
                val qLines = wrapLines(cleanQ, userTextPaint, contentWidth - 26f)
                val boxHeight = 22f + (qLines.size * 14.5f) + 10f
                ensureSpace(boxHeight + 8f)

                val boxTop = currentY
                val boxBottom = boxTop + boxHeight
                canvas.drawRoundRect(RectF(margin, boxTop, pageWidth - margin, boxBottom), 6f, 6f, userBoxBgPaint)
                canvas.drawRect(margin, boxTop, margin + 3.5f, boxBottom, userAccentBarPaint)

                canvas.drawText("CONSULTA #$questionCounter", margin + 12f, boxTop + 14f, roleUserPaint)
                if (msgTime.isNotEmpty()) {
                    canvas.drawText(msgTime, pageWidth - margin - 10f, boxTop + 14f, timePaint)
                }

                var qY = boxTop + 30f
                for (line in qLines) {
                    canvas.drawText(line, margin + 12f, qY, userTextPaint)
                    qY += 14.5f
                }

                currentY = boxBottom + 12f
            } else {
                ensureSpace(34f)
                val labelY = currentY + 10f
                canvas.drawText("RESPUESTA TEOLÓGICA", margin + 10f, labelY, roleAssistantPaint)
                if (msgTime.isNotEmpty()) {
                    canvas.drawText(msgTime, pageWidth - margin, labelY, timePaint)
                }
                currentY = labelY + 14f

                val rawParagraphs = msg.text.replace("\r\n", "\n").split("\n")
                for (rawLine in rawParagraphs) {
                    val trimmed = rawLine.trim()
                    if (trimmed.isEmpty()) {
                        currentY += 5f
                        continue
                    }
                    if (trimmed.matches(Regex("^[-*_]{3,}$"))) {
                        continue
                    }

                    val isMarkdownHeading = trimmed.startsWith("#")
                    val isBullet = trimmed.startsWith("- ") || trimmed.startsWith("• ") || trimmed.startsWith("* ")
                    val cleaned = if (isBullet) {
                        sanitizeLine(trimmed.replaceFirst(Regex("^[-•*]\\s+"), ""))
                    } else {
                        sanitizeLine(trimmed)
                    }
                    if (cleaned.isEmpty()) continue

                    val isNumberedHeading = !isBullet && cleaned.length <= 92 &&
                        (isMarkdownHeading || cleaned.matches(Regex("""^(\d+\.|[IVX]+\.)\s+.+""")))

                    val paintToUse = when {
                        isNumberedHeading -> headingPaint
                        isBullet -> bulletPaint
                        else -> bodyPaint
                    }
                    val leftX = if (isBullet) margin + 20f else margin + 12f
                    val wrapW = (pageWidth - margin) - leftX - 4f
                    val lineStep = if (isNumberedHeading) 15f else 14.2f
                    val wrapped = wrapLines(if (isBullet) "• $cleaned" else cleaned, paintToUse, wrapW)

                    if (isNumberedHeading) {
                        ensureSpace(lineStep + 18f)
                        currentY += 3f
                    }

                    for (wl in wrapped) {
                        ensureSpace(lineStep + 4f)
                        val lineTop = currentY - 10f
                        canvas.drawText(wl, leftX, currentY, paintToUse)
                        canvas.drawLine(margin + 2f, lineTop, margin + 2f, currentY + 3f, assistantAccentPaint)
                        currentY += lineStep
                    }
                    currentY += if (isNumberedHeading) 3f else 4f
                }

                val msgCitations = extractCitations(msg)
                allSessionCitations.addAll(msgCitations)
                if (msgCitations.isNotEmpty()) {
                    val citeText = "Referencias citadas: ${msgCitations.joinToString(" • ")}"
                    val citeLines = wrapLines(citeText, citeLabelPaint, contentWidth - 16f)
                    ensureSpace(citeLines.size * 13f + 8f)
                    currentY += 2f
                    for (cl in citeLines) {
                        canvas.drawText(cl, margin + 12f, currentY, citeLabelPaint)
                        currentY += 13f
                    }
                }

                currentY += 8f
                if (idx < validMessages.lastIndex) {
                    ensureSpace(12f)
                    canvas.drawLine(margin, currentY, pageWidth - margin, currentY, lightDividerPaint)
                    currentY += 14f
                }
            }
        }

        if (allSessionCitations.isNotEmpty()) {
            val indexBodyText = allSessionCitations.joinToString("  •  ")
            val indexLines = wrapLines(indexBodyText, metaPaint, contentWidth - 24f)
            val boxH = 26f + (indexLines.size * 13.5f) + 8f
            ensureSpace(boxH + 12f)

            currentY += 4f
            val boxRect = RectF(margin, currentY, pageWidth - margin, currentY + boxH)
            canvas.drawRoundRect(boxRect, 6f, 6f, indexBoxBgPaint)
            canvas.drawRoundRect(boxRect, 6f, 6f, indexBoxBorderPaint)

            canvas.drawText("ÍNDICE DE PASAJES BÍBLICOS CITADOS EN ESTA SESIÓN", margin + 12f, currentY + 15f, roleUserPaint)
            var iy = currentY + 30f
            for (il in indexLines) {
                canvas.drawText(il, margin + 12f, iy, metaPaint)
                iy += 13.5f
            }
        }

        drawFooter(canvas, pageNumber)
        pdfDoc.finishPage(page)

        FileOutputStream(pdfFile).use { out ->
            pdfDoc.writeTo(out)
        }
        pdfDoc.close()

        return pdfFile
    }

    fun exportToPlainText(context: Context, title: String, verses: List<VerseEntity>): File {
        val exportDir = File(context.cacheDir, "exports").apply { mkdirs() }
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val txtFile = File(exportDir, "versiculos_${timeStamp}.txt")

        val sb = StringBuilder()
        val dateStr = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

        sb.append("====================================================\n")
        sb.append(title.uppercase()).append("\n")
        sb.append("Sagrada Escritura • Versículos Fundamentales\n")
        sb.append("Fecha de exportación: ").append(dateStr).append("\n")
        sb.append("Total de versículos: ").append(verses.size).append("\n")
        sb.append("====================================================\n\n")

        for ((index, verse) in verses.withIndex()) {
            sb.append("${index + 1}. ${verse.reference} (${verse.testament} - ${verse.topic})\n")
            sb.append(verse.text).append("\n")
            sb.append("CONTEXTO: ").append(verse.context).append("\n")
            if (verse.notes.isNotBlank()) {
                sb.append("NOTA PERSONAL: ").append(verse.notes).append("\n")
            }
            if (verse.highlightColor.isNotBlank()) {
                sb.append("RESALTADO: ").append(verse.highlightColor).append("\n")
            }
            sb.append("----------------------------------------------------\n\n")
        }

        txtFile.writeText(sb.toString())
        return txtFile
    }

    fun shareExportedFile(context: Context, file: File, mimeType: String, subject: String) {
        try {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            val chooser = Intent.createChooser(intent, "Exportar / Compartir contenido").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun shareSingleVerse(context: Context, verse: VerseEntity) {
        try {
            val shareBody = """
                ${verse.text}
                — ${verse.reference} (${verse.testament})

                📖 Contexto:
                ${verse.context}
                
                Compartido desde BibleVerse
            """.trimIndent()

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Versículo Bíblico: ${verse.reference}")
                putExtra(Intent.EXTRA_TEXT, shareBody)
            }
            val chooser = Intent.createChooser(intent, "Compartir Versículo").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
