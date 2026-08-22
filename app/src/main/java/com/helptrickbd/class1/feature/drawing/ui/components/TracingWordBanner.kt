package com.helptrickbd.class1.feature.drawing.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem

@Composable
fun TracingWordBanner(
    item: TracingItem?,
    isSpeaking: Boolean,
    onSpeak: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (item == null) return

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_speech")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1E2538),
        border = BorderStroke(1.dp, Color(0xFF333E54)),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onPrevious,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "পূর্ববর্তী বর্ণ",
                    tint = Color(0xFFCBD5E1)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = item.character,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFFFD54F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "—  ${item.wordExample}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    if (item.meaning.isNotBlank()) {
                        Text(
                            text = item.meaning,
                            fontSize = 12.sp,
                            color = Color(0xFFCBD5E1),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Speaker Audio Button with Pulse Animation
                Surface(
                    shape = CircleShape,
                    color = if (isSpeaking) Color(0xFFFFD54F).copy(alpha = 0.25f) else Color(0xFF283349),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSpeaking) Color(0xFFFFD54F) else Color(0xFF475569)
                    ),
                    modifier = Modifier
                        .size(38.dp)
                        .scale(if (isSpeaking) pulseScale else 1f)
                ) {
                    IconButton(onClick = onSpeak) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "উচ্চারণ শুনুন",
                            tint = if (isSpeaking) Color(0xFFFFD54F) else Color(0xFFF1F5F9),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            IconButton(
                onClick = onNext,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "পরবর্তী বর্ণ",
                    tint = Color(0xFFCBD5E1)
                )
            }
        }
    }
}
