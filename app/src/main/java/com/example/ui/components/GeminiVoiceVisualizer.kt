package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun GeminiVoiceVisualizerPill(
    rmsLevel: Float,
    onStopClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val barCount = 13

    // Onda sutil continua que da vida a las barras como en Gemini
    val infiniteTransition = rememberInfiniteTransition(label = "geminiWavePulse")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wavePhase"
    )

    // Reacción inmediata al volumen real de la voz
    val animatedRms by animateFloatAsState(
        targetValue = rmsLevel.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 80, easing = LinearEasing),
        label = "geminiRmsAnimation"
    )

    Box(
        modifier = modifier
            .height(54.dp)
            .clip(RoundedCornerShape(27.dp))
            .background(Color(0xFF1E1F23))
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Waveform visualizer bars (Gemini style)
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0 until barCount) {
                    val bellCurve = sin((i + 1) * Math.PI / (barCount + 1)).toFloat()
                    val waveOffset = (sin(phase + (i * 0.5f)) + 1f) / 2f // 0f a 1f suave
                    val idlePulse = 5.dp + (4.dp * waveOffset.toFloat()) // 5dp a 9dp en reposo
                    val voiceBoost = 20.dp * bellCurve * animatedRms     // hasta 29dp al hablar

                    val dynamicHeight = (idlePulse + voiceBoost).coerceIn(4.dp, 29.dp)

                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(dynamicHeight)
                            .clip(RoundedCornerShape(1.5.dp))
                            .background(Color.White)
                    )

                    if (i < barCount - 1) {
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
            }

            // Stop button (circular with rounded square)
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2C2D32))
                    .clickable(onClick = onStopClick),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White)
                )
            }
        }
    }
}
