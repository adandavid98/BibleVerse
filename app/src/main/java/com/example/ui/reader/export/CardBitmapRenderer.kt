package com.example.ui.reader.export

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.content.FileProvider
import com.example.ui.reader.model.ShareCardTemplate
import java.io.File
import java.io.FileOutputStream

object CardBitmapRenderer {

    fun renderAndShare(
        context: Context,
        verseText: String,
        citationText: String,
        template: ShareCardTemplate
    ): Uri? {
        val bitmap = renderToBitmap(verseText, citationText, template)
        val fileUri = saveBitmapToCache(context, bitmap) ?: return null

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            putExtra(Intent.EXTRA_TEXT, "«$verseText» — $citationText")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooser = Intent.createChooser(intent, "Compartir versículo").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooser)
        return fileUri
    }

    fun renderToBitmap(
        verseText: String,
        citationText: String,
        template: ShareCardTemplate,
        width: Int = 1080,
        height: Int = 1080
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        when (template) {
            ShareCardTemplate.MINIMALIST -> drawMinimalist(canvas, width, height, verseText, citationText)
            ShareCardTemplate.SACRED_GRADIENT -> drawSacredGradient(canvas, width, height, verseText, citationText)
            ShareCardTemplate.PARCHMENT -> drawParchment(canvas, width, height, verseText, citationText)
        }

        return bitmap
    }

    private fun drawMinimalist(canvas: Canvas, w: Int, h: Int, text: String, citation: String) {
        // Background
        val bgPaint = Paint().apply {
            color = Color.parseColor("#FAFAFA")
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, w.toFloat(), h.toFloat(), bgPaint)

        // Subtle frame
        val framePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#E5E7EB")
            style = Paint.Style.STROKE
            strokeWidth = 3f
        }
        canvas.drawRoundRect(40f, 40f, (w - 40).toFloat(), (h - 40).toFloat(), 24f, 24f, framePaint)

        // Header Pill Badge
        val badgeBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#F3F4F6")
            style = Paint.Style.FILL
        }
        val badgeRect = RectF((w / 2f) - 130f, 75f, (w / 2f) + 130f, 125f)
        canvas.drawRoundRect(badgeRect, 25f, 25f, badgeBgPaint)

        val badgeTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#4B5563")
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("PALABRA SAGRADA", w / 2f, 109f, badgeTextPaint)

        // Decorative quote mark
        val quoteMarkPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#E5E7EB")
            textSize = 120f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("“", w / 2f, 220f, quoteMarkPaint)

        val headerBottom = 240f
        val divY = h - 230f

        // Main Verse Text (Centered both horizontally and vertically)
        val contentWidth = w - 180
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#111827")
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            textSize = calculateOptimalFontSize(text, contentWidth, (divY - headerBottom - 30f).toInt(), typeface)
        }

        val textLayout = createStaticLayout(text, textPaint, contentWidth, Layout.Alignment.ALIGN_CENTER)
        canvas.save()
        val textY = headerBottom + ((divY - headerBottom) - textLayout.height) / 2f
        canvas.translate(90f, textY.coerceAtLeast(headerBottom))
        textLayout.draw(canvas)
        canvas.restore()

        // Divider
        val divPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#E5E7EB")
            strokeWidth = 2f
        }
        canvas.drawLine((w / 2f) - 80f, divY, (w / 2f) + 80f, divY, divPaint)

        // Citation Text
        val citPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#1F2937")
            textSize = 36f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(citation, w / 2f, divY + 70f, citPaint)

        // Subtitle
        val subPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#9CA3AF")
            textSize = 22f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("BibleVerse", w / 2f, divY + 120f, subPaint)
    }

    private fun drawSacredGradient(canvas: Canvas, w: Int, h: Int, text: String, citation: String) {
        // Deep Indigo/Navy Gradient
        val gradient = LinearGradient(
            0f, 0f, w.toFloat(), h.toFloat(),
            intArrayOf(
                Color.parseColor("#0F172A"),
                Color.parseColor("#1E1B4B"),
                Color.parseColor("#312E81")
            ),
            floatArrayOf(0f, 0.55f, 1f),
            Shader.TileMode.CLAMP
        )
        val bgPaint = Paint().apply {
            shader = gradient
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, w.toFloat(), h.toFloat(), bgPaint)

        // Glowing border
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#38BDF8")
            alpha = 60
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        canvas.drawRoundRect(40f, 40f, (w - 40).toFloat(), (h - 40).toFloat(), 28f, 28f, borderPaint)

        // Header Tag
        val tagPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FBBF24")
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            letterSpacing = 0.15f
        }
        canvas.drawText("✦ SAGRADA ESCRITURA ✦", w / 2f, 120f, tagPaint)

        val headerBottom = 180f
        val divY = h - 220f

        // Main text (Centered both horizontally and vertically)
        val contentWidth = w - 180
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FFFFFF")
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            textSize = calculateOptimalFontSize(text, contentWidth, (divY - headerBottom - 30f).toInt(), typeface)
            setShadowLayer(8f, 0f, 4f, Color.parseColor("#60000000"))
        }

        val textLayout = createStaticLayout(text, textPaint, contentWidth, Layout.Alignment.ALIGN_CENTER)
        canvas.save()
        val textY = headerBottom + ((divY - headerBottom) - textLayout.height) / 2f
        canvas.translate(90f, textY.coerceAtLeast(headerBottom))
        textLayout.draw(canvas)
        canvas.restore()

        // Golden divider
        val divPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#F59E0B")
            strokeWidth = 3f
        }
        canvas.drawLine((w / 2f) - 90f, divY, (w / 2f) + 90f, divY, divPaint)

        // Citation
        val citPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FDE68A")
            textSize = 38f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(citation, w / 2f, divY + 70f, citPaint)

        // App Footer
        val footPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#93C5FD")
            textSize = 22f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("Lámpara es a mis pies tu palabra", w / 2f, divY + 120f, footPaint)
    }

    private fun drawParchment(canvas: Canvas, w: Int, h: Int, text: String, citation: String) {
        // Parchment base
        val bgPaint = Paint().apply {
            color = Color.parseColor("#F7EEDB")
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, w.toFloat(), h.toFloat(), bgPaint)

        // Ornate outer border
        val outerBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#8B5A2B")
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawRoundRect(40f, 40f, (w - 40).toFloat(), (h - 40).toFloat(), 16f, 16f, outerBorder)

        // Ornate inner thin border
        val innerBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#A07855")
            style = Paint.Style.STROKE
            strokeWidth = 1.5f
        }
        canvas.drawRoundRect(52f, 52f, (w - 52).toFloat(), (h - 52).toFloat(), 12f, 12f, innerBorder)

        // Ornate corner dots
        val cornerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#8B5A2B")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(52f, 52f, 5f, cornerPaint)
        canvas.drawCircle((w - 52).toFloat(), 52f, 5f, cornerPaint)
        canvas.drawCircle(52f, (h - 52).toFloat(), 5f, cornerPaint)
        canvas.drawCircle((w - 52).toFloat(), (h - 52).toFloat(), 5f, cornerPaint)

        // Vintage top ornament
        val headerPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#5C3A21")
            textSize = 26f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            letterSpacing = 0.2f
        }
        canvas.drawText("❖  SANTA BIBLIA  ❖", w / 2f, 125f, headerPaint)

        val headerBottom = 185f
        val divY = h - 220f

        // Verse Text (Centered both horizontally and vertically)
        val contentWidth = w - 180
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#2C1D11")
            typeface = Typeface.create(Typeface.SERIF, Typeface.ITALIC)
            textSize = calculateOptimalFontSize(text, contentWidth, (divY - headerBottom - 30f).toInt(), typeface)
        }

        val textLayout = createStaticLayout(text, textPaint, contentWidth, Layout.Alignment.ALIGN_CENTER)
        canvas.save()
        val textY = headerBottom + ((divY - headerBottom) - textLayout.height) / 2f
        canvas.translate(90f, textY.coerceAtLeast(headerBottom))
        textLayout.draw(canvas)
        canvas.restore()

        // Vintage divider
        val divPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#8B5A2B")
            strokeWidth = 2f
        }
        canvas.drawLine((w / 2f) - 100f, divY, (w / 2f) + 100f, divY, divPaint)

        // Citation
        val citPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#4A2810")
            textSize = 36f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText(citation, w / 2f, divY + 68f, citPaint)

        // Footer
        val footPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#7D5836")
            textSize = 22f
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("El cielo y la tierra pasarán, mas mis palabras no pasarán", w / 2f, divY + 115f, footPaint)
    }

    private fun calculateOptimalFontSize(
        text: String,
        width: Int,
        maxHeight: Int,
        typeface: Typeface = Typeface.SERIF
    ): Float {
        var size = 46f
        val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            this.typeface = typeface
        }
        while (size > 18f) {
            paint.textSize = size
            val layout = createStaticLayout(text, paint, width, Layout.Alignment.ALIGN_CENTER)
            if (layout.height <= maxHeight) {
                return size
            }
            size -= 2f
        }
        return size
    }

    @Suppress("DEPRECATION")
    private fun createStaticLayout(
        text: CharSequence,
        paint: TextPaint,
        width: Int,
        alignment: Layout.Alignment
    ): StaticLayout {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, paint, width)
                .setAlignment(alignment)
                .setLineSpacing(8f, 1.25f)
                .setIncludePad(false)
                .build()
        } else {
            StaticLayout(text, paint, width, alignment, 1.25f, 8f, false)
        }
    }

    private fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
        return try {
            val cacheDir = File(context.cacheDir, "images")
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val imageFile = File(cacheDir, "versiculo_${System.currentTimeMillis()}.png")
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
