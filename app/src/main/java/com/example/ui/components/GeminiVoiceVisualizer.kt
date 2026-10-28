package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
    val animatedRms by animateFloatAsState(
        targetValue = rmsLevel.coerceIn(0.08f, 1f),
        animationSpec = tween(durationMillis = 70, easing = LinearEasing),
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
                    val dynamicHeight = (6.dp + (22.dp * bellCurve * animatedRms)).coerceIn(5.dp, 28.dp)

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
