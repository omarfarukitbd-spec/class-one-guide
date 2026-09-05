package com.helptrickbd.class1.feature.learn_hub.ui.phonics.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.designsystem.modifiers.bounceClick
import com.helptrickbd.class1.core.designsystem.modifiers.glassmorphism
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem
import com.helptrickbd.class1.feature.learn_hub.ui.phonics.PhonicsDisplayMode

@Composable
fun PhonicsLetterCard(
    item: PhonicsItem,
    displayMode: PhonicsDisplayMode,
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(20.dp)

    // Glowing animation when playing audio
    val pulseBorderColor = if (isPlaying) {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulseAlpha"
        )
        item.primaryColor.copy(alpha = alpha)
    } else {
        Color.White.copy(alpha = 0.15f)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .bounceClick(scaleDown = 0.93f, onClick = onClick)
            .glassmorphism(
                color = item.primaryColor.copy(alpha = if (isPlaying) 0.22f else 0.10f),
                shape = cardShape,
                borderStroke = if (isPlaying) 2.dp else 1.dp
            )
            .border(
                width = if (isPlaying) 2.dp else 0.5.dp,
                color = pulseBorderColor,
                shape = cardShape
            )
            .padding(12.dp)
    ) {
        // Soundwave / Playing indicator pill
        if (isPlaying) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(item.primaryColor)
            ) {
                Icon(
                    imageVector = Icons.Rounded.VolumeUp,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Main Glyph Box (Vector or Text)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(52.dp)
            ) {
                if (item.vectorDrawableRes != null) {
                    Image(
                        painter = painterResource(id = item.vectorDrawableRes),
                        contentDescription = item.letter,
                        colorFilter = ColorFilter.tint(item.primaryColor),
                        modifier = Modifier.size(40.dp)
                    )
                } else {
                    Text(
                        text = item.letter,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = item.primaryColor,
                            fontSize = 36.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle Name (e.g., স্বর অ, ক, চন্দ্রবিন্দু)
            Text(
                text = item.name,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Word Display in WORDS mode (Zero emojis, pristine audio chip)
            if (displayMode == PhonicsDisplayMode.WORDS) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = item.primaryColor.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, item.primaryColor.copy(alpha = 0.25f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.VolumeUp,
                            contentDescription = null,
                            tint = item.primaryColor,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.word,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = item.primaryColor,
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
