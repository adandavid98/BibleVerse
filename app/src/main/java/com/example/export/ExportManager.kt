package com.example.export

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.data.model.VerseEntity
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
