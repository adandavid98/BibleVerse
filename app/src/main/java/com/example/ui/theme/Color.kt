package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme Colors
val LightBackground = Color(0xFFFBF9F5)
val LightSurface = Color(0xFFF2ECE1)
val LightSurfaceVariant = Color(0xFFE7DFD2)
val LightPrimary = Color(0xFF1E3A5F)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightSecondary = Color(0xFF9E6B28)
val LightTertiary = Color(0xFF2C5E43)
val LightTextPrimary = Color(0xFF1C222B)
val LightTextSecondary = Color(0xFF5E6B7E)
val LightBorder = Color(0xFFDDD4C5)

// Sepia / Modo Lectura Nocturna Anti-Fatiga Colors
val SepiaBackground = Color(0xFF241D17)
val SepiaSurface = Color(0xFF30261E)
val SepiaSurfaceVariant = Color(0xFF3D3127)
val SepiaPrimary = Color(0xFFE5B869)
val SepiaOnPrimary = Color(0xFF1F1710)
val SepiaSecondary = Color(0xFFD49354)
val SepiaTertiary = Color(0xFFA5B888)
val SepiaTextPrimary = Color(0xFFF3E7D7)
val SepiaTextSecondary = Color(0xFFB5A493)
val SepiaBorder = Color(0xFF473A2F)

// Dark Theme Colors (Noche Profunda)
val DarkBackground = Color(0xFF0F141C)
val DarkSurface = Color(0xFF18202C)
val DarkSurfaceVariant = Color(0xFF232D3D)
val DarkPrimary = Color(0xFF79A6D2)
val DarkOnPrimary = Color(0xFF0A1929)
val DarkSecondary = Color(0xFFE5B869)
val DarkTertiary = Color(0xFF68B684)
val DarkTextPrimary = Color(0xFFE9EEF5)
val DarkTextSecondary = Color(0xFF8B9BB0)
val DarkBorder = Color(0xFF283446)

// Vivid Bible Highlighter Colors (Synchronized with YouVersion Palette)
object BibleHighlightColors {
    val Yellow = Color(0xFFFACC15)
    val Green = Color(0xFF22C55E)
    val Blue = Color(0xFF38BDF8)
    val Pink = Color(0xFFEC4899)
    val Orange = Color(0xFFF97316)

    val list = listOf(
        HighlightOption("Amarillo", "#FACC15", Yellow),
        HighlightOption("Verde Esmeralda", "#22C55E", Green),
        HighlightOption("Azul Cielo", "#38BDF8", Blue),
        HighlightOption("Rosa Fucsia", "#EC4899", Pink),
        HighlightOption("Naranja Cálido", "#F97316", Orange)
    )

    fun getColorFromHex(hex: String): Color? {
        if (hex.isBlank()) return null
        return try {
            Color(android.graphics.Color.parseColor(hex))
        } catch (e: Exception) {
            list.firstOrNull { it.hex.equals(hex, ignoreCase = true) }?.color
        }
    }
}

data class HighlightOption(
    val name: String,
    val hex: String,
    val color: Color
)
