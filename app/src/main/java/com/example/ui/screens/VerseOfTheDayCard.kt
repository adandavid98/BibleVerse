package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.VerseEntity
import java.util.Calendar

private val VERSE_BACKGROUND_DRAWABLES = listOf(
    R.drawable.verse_bg_01,
    R.drawable.verse_bg_02,
    R.drawable.verse_bg_03,
    R.drawable.verse_bg_04,
    R.drawable.verse_bg_05,
    R.drawable.verse_bg_06,
    R.drawable.verse_bg_07,
    R.drawable.verse_bg_08,
    R.drawable.verse_bg_09,
    R.drawable.verse_bg_10,
    R.drawable.verse_bg_11,
    R.drawable.verse_bg_12,
)

@Composable
fun VerseOfTheDayCard(
    verse: VerseEntity,
    fontScale: Float = 1.0f,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onShare: () -> Unit
) {
    // Rotation by day of year + verse.id with manual user shuffle offset
    val dayOfYear = remember { Calendar.getInstance().get(Calendar.DAY_OF_YEAR) }
    var userBgOffset by remember(verse.id) { mutableIntStateOf(0) }
    val currentBgIndex = (dayOfYear + verse.id + userBgOffset).mod(VERSE_BACKGROUND_DRAWABLES.size)
    val backgroundDrawableRes = VERSE_BACKGROUND_DRAWABLES[currentBgIndex]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable { onClick() }
            .testTag("card_verse_of_the_day"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // 1. Background Photographic Image (WebP)
            Image(
                painter = painterResource(id = backgroundDrawableRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // 2. High-contrast Scrim gradient overlay (ensures WCAG AAA readability on any background)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.42f),
                                Color.Black.copy(alpha = 0.58f),
                                Color.Black.copy(alpha = 0.82f)
                            )
                        )
                    )
            )

            // 3. Foreground content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "VERSÍCULO DEL DÍA",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.3.sp,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.8f),
                                    offset = Offset(0f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = Color(0xFFFFD54F)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Change background button
                        IconButton(
                            onClick = { userBgOffset++ },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Palette,
                                contentDescription = "Cambiar imagen de fondo",
                                tint = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        // Favorite toggle
                        IconButton(
                            onClick = onFavoriteToggle,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = if (verse.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (verse.isFavorite) Color(0xFFEF5350) else Color.White
                            )
                        }

                        // Share
                        IconButton(
                            onClick = onShare,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Compartir",
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Verse Biblical Text
                Text(
                    text = verse.text,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontSize = (18.5f * fontScale).sp,
                        lineHeight = (27 * fontScale).sp,
                        fontWeight = FontWeight.Normal,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.85f),
                            offset = Offset(0f, 2f),
                            blurRadius = 6f
                        )
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Bottom Reference & Topic
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "— ${verse.reference}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.8f),
                                offset = Offset(0f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        color = Color.White
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White.copy(alpha = 0.22f)
                    ) {
                        Text(
                            text = verse.topic,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
