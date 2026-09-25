package com.example.ui.reader.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.service.AudioVoiceGender
import com.example.service.BibleAudioController
import com.example.service.BibleAudioState

@Composable
fun BibleAudioBottomBar(
    audioState: BibleAudioState,
    themeBg: Color,
    themeText: Color,
    themeSecondary: Color,
    themeAccent: Color,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showExpandedSheet by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = audioState.isActive,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val pulseScale by infiniteTransition.animateFloat(
            initialValue = 0.94f,
            targetValue = 1.06f,
            animationSpec = infiniteRepeatable(
                animation = tween(800),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulseScale"
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .shadow(12.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .clickable { showExpandedSheet = true },
            shape = RoundedCornerShape(20.dp),
            color = themeBg,
            border = BorderStroke(1.dp, themeAccent.copy(alpha = 0.35f))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Slim Progress Line at top of bar
                LinearProgressIndicator(
                    progress = { audioState.progressFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    color = themeAccent,
                    trackColor = themeSecondary.copy(alpha = 0.15f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left: Audio pulse icon & Verse reference info
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(themeAccent.copy(alpha = 0.15f))
                                .then(if (audioState.isPlaying) Modifier.scale(pulseScale) else Modifier),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Headphones,
                                contentDescription = "Audio Bíblico",
                                tint = themeAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "${audioState.currentBookName} ${audioState.currentChapter}:${audioState.currentVerseNumber}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                color = themeText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "V. ${audioState.currentIndex + 1} de ${audioState.totalVerses} • ${audioState.bibleVersion}",
                                fontSize = 11.sp,
                                color = themeSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Right: Voice Gender, Speed pill & Playback Controls
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Voice Gender Toggle (Mujer / Hombre)
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { BibleAudioController.toggleVoiceGender(context) },
                            shape = RoundedCornerShape(12.dp),
                            color = themeAccent.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, themeAccent.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = if (audioState.voiceGender == AudioVoiceGender.FEMALE) "👩 Fem" else "👨 Masc",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.5.sp,
                                color = themeAccent,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                            )
                        }

                        // Speed Cycle Button
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { BibleAudioController.cycleSpeed(context) },
                            shape = RoundedCornerShape(12.dp),
                            color = themeAccent.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, themeAccent.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "${audioState.speechRate}x",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.5.sp,
                                color = themeAccent,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                            )
                        }

                        // Previous
                        IconButton(
                            onClick = { BibleAudioController.previous(context) },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                Icons.Default.SkipPrevious,
                                contentDescription = "Anterior",
                                tint = themeText,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Play / Pause Prominent Button
                        Surface(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .clickable { BibleAudioController.togglePlayPause(context) },
                            shape = CircleShape,
                            color = themeAccent
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (audioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (audioState.isPlaying) "Pausar" else "Reproducir",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        // Next
                        IconButton(
                            onClick = { BibleAudioController.next(context) },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                Icons.Default.SkipNext,
                                contentDescription = "Siguiente",
                                tint = themeText,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Close
                        IconButton(
                            onClick = { BibleAudioController.stop(context) },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = themeSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Detailed Audio Modal Sheet
    if (showExpandedSheet && audioState.isActive) {
        BibleAudioDetailBottomSheet(
            audioState = audioState,
            themeBg = themeBg,
            themeText = themeText,
            themeSecondary = themeSecondary,
            themeAccent = themeAccent,
            onDismiss = { showExpandedSheet = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BibleAudioDetailBottomSheet(
    audioState: BibleAudioState,
    themeBg: Color,
    themeText: Color,
    themeSecondary: Color,
    themeAccent: Color,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = themeBg,
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Book & Chapter
            Text(
                text = "${audioState.currentBookName} ${audioState.currentChapter}",
                fontWeight = FontWeight.Black,
                fontSize = 22.sp,
                color = themeText
            )

            Text(
                text = "Biblia ${audioState.bibleVersion} • Audio Manos Libres",
                fontSize = 13.sp,
                color = themeSecondary,
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
            )

            // Current Verse Box
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                color = themeAccent.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, themeAccent.copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Versículo ${audioState.currentVerseNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = themeAccent
                        )
                        Text(
                            text = "${audioState.currentIndex + 1} / ${audioState.totalVerses}",
                            fontSize = 12.sp,
                            color = themeSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (audioState.currentVerseText.isNotBlank()) {
                            "\"${audioState.currentVerseText}\""
                        } else {
                            "Preparando lectura de audio..."
                        },
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        color = themeText,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Verse Scrubber Slider
            if (audioState.totalVerses > 1) {
                Slider(
                    value = audioState.currentIndex.toFloat(),
                    onValueChange = { targetIdx ->
                        val verseNumber = BibleAudioController.currentPlaylist.getOrNull(targetIdx.toInt())?.verseNumber
                        if (verseNumber != null) {
                            BibleAudioController.seekToVerse(context, verseNumber)
                        }
                    },
                    valueRange = 0f..(audioState.totalVerses - 1).toFloat(),
                    steps = (audioState.totalVerses - 2).coerceAtLeast(0),
                    colors = SliderDefaults.colors(
                        thumbColor = themeAccent,
                        activeTrackColor = themeAccent,
                        inactiveTrackColor = themeSecondary.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Playback Controls Row (YouVersion Style)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous
                IconButton(
                    onClick = { BibleAudioController.previous(context) },
                    modifier = Modifier.size(54.dp)
                ) {
                    Icon(
                        Icons.Default.SkipPrevious,
                        contentDescription = "Anterior",
                        tint = themeText,
                        modifier = Modifier.size(34.dp)
                    )
                }

                // Play / Pause Huge Button
                Surface(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .clickable { BibleAudioController.togglePlayPause(context) },
                    shape = CircleShape,
                    color = themeAccent,
                    shadowElevation = 6.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (audioState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (audioState.isPlaying) "Pausar" else "Reproducir",
                            tint = Color.White,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }

                // Next
                IconButton(
                    onClick = { BibleAudioController.next(context) },
                    modifier = Modifier.size(54.dp)
                ) {
                    Icon(
                        Icons.Default.SkipNext,
                        contentDescription = "Siguiente",
                        tint = themeText,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Voice Gender Selector Buttons (Voz Femenina / Voz Masculina)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.RecordVoiceOver,
                    contentDescription = null,
                    tint = themeSecondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Voz:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = themeSecondary
                )
                Spacer(modifier = Modifier.width(10.dp))

                AudioVoiceGender.values().forEach { gender ->
                    val isSelected = (audioState.voiceGender == gender)
                    Surface(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { BibleAudioController.setVoiceGender(context, gender) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) themeAccent else themeAccent.copy(alpha = 0.10f),
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) themeAccent else themeSecondary.copy(alpha = 0.25f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (gender == AudioVoiceGender.FEMALE) "👩" else "👨",
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = gender.displayName,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.5.sp,
                                color = if (isSelected) Color.White else themeText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Speed Selector Buttons (1.0x, 1.25x, 1.5x)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Speed,
                    contentDescription = null,
                    tint = themeSecondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Velocidad:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = themeSecondary
                )
                Spacer(modifier = Modifier.width(12.dp))

                BibleAudioController.availableSpeeds.forEach { speed ->
                    val isSelected = (audioState.speechRate == speed)
                    Surface(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { BibleAudioController.setSpeed(context, speed) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) themeAccent else themeAccent.copy(alpha = 0.10f),
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) themeAccent else themeSecondary.copy(alpha = 0.25f)
                        )
                    ) {
                        Text(
                            text = "${speed}x",
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            color = if (isSelected) Color.White else themeText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}
