package com.helptrickbd.class1.feature.learn_hub.ui.phonics.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.helptrickbd.class1.core.designsystem.modifiers.bounceClick
import com.helptrickbd.class1.core.designsystem.modifiers.glassmorphism
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

@Composable
fun WordBuilderPuzzle(
    item: PhonicsItem,
    onWordCompleted: () -> Unit,
    onNextWord: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tokens = remember(item.id, item.word) {
        if (item.wordTokens.isNotEmpty()) item.wordTokens
        else com.helptrickbd.class1.feature.learn_hub.domain.util.BengaliClusterUtil.splitIntoClusters(item.word)
    }
    var placedTokens by remember(item.id) { mutableStateOf(listOf<String>()) }
    var availableTokens by remember(item.id) { mutableStateOf(tokens.shuffled()) }
    val isCompleted = placedTokens.size == tokens.size && placedTokens == tokens

    LaunchedEffect(isCompleted) {
        if (isCompleted) onWordCompleted()
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("animations/11272-party-popper.json")
    )
    val lottieProgress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isCompleted,
        iterations = 1
    )

    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .glassmorphism(color = item.primaryColor.copy(alpha = 0.08f), shape = RoundedCornerShape(24.dp))
                .border(1.5.dp, item.primaryColor.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            // Target Word Header
            Text(
                text = "সঠিক বর্ণ সাজিয়ে শব্দ তৈরি করো:",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Word Slots Row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                tokens.forEachIndexed { index, _ ->
                    val placed = placedTokens.getOrNull(index)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (placed != null) item.primaryColor else Color.White.copy(alpha = 0.15f))
                            .border(2.dp, item.primaryColor, RoundedCornerShape(14.dp))
                    ) {
                        Text(
                            text = placed ?: "?",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (placed != null) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Scrambled Available Tokens Row
            if (!isCompleted) {
                Text(
                    text = "বর্ণে স্পর্শ করো:",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    availableTokens.forEachIndexed { index, char ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(item.primaryColor.copy(alpha = 0.2f))
                                .border(1.5.dp, item.primaryColor, CircleShape)
                                .bounceClick(scaleDown = 0.85f) {
                                    val nextRequired = tokens.getOrNull(placedTokens.size)
                                    if (char == nextRequired) {
                                        placedTokens = placedTokens + char
                                        availableTokens = availableTokens.toMutableList().also { it.removeAt(index) }
                                    }
                                }
                        ) {
                            Text(
                                text = char,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = item.primaryColor,
                                    fontSize = 22.sp
                                )
                            )
                        }
                    }
                }
            } else {
                // Success Badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color(0xFF10B981))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "চমৎকার! '${item.word}' শব্দটি তৈরি হয়েছে!",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        placedTokens = emptyList()
                        availableTokens = tokens.shuffled()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(Icons.Rounded.Refresh, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("পুনরায়", color = MaterialTheme.colorScheme.onSurface)
                }

                Button(
                    onClick = onNextWord,
                    colors = ButtonDefaults.buttonColors(containerColor = item.primaryColor)
                ) {
                    Text("পরবর্তী শব্দ", color = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.Rounded.SkipNext, contentDescription = null, tint = Color.White)
                }
            }
        }

        // Lottie Confetti Overlay
        if (isCompleted) {
            LottieAnimation(
                composition = composition,
                progress = { lottieProgress },
                modifier = Modifier.size(240.dp)
            )
        }
    }
}
