package com.helptrickbd.class1.feature.learn_hub.ui.phonics.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.core.designsystem.modifiers.bounceClick
import com.helptrickbd.class1.core.designsystem.modifiers.glassmorphism
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

/**
 * Full Digital Illustrated Book-Page Card.
 * Incorporates LiveIllustrationCanvas for cinematic living motion,
 * glowing artistic border, and syllable breakdown chips.
 */
@Composable
fun PictureBookCard(
    item: PhonicsItem,
    isPlaying: Boolean,
    onPlayLetter: () -> Unit,
    onPlayWord: () -> Unit,
    onPlaySentence: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(24.dp)
    var isWordPressed by remember { mutableStateOf(false) }
    val wordScale by animateFloatAsState(
        targetValue = if (isWordPressed) 1.08f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
        label = "wordScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .glassmorphism(color = item.primaryColor.copy(alpha = 0.08f), shape = shape)
            .border(if (isPlaying) 2.dp else 1.dp, if (isPlaying) item.primaryColor else Color.White.copy(0.2f), shape)
            .padding(14.dp)
    ) {
        // Top Header: Letter badge and Word title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(item.primaryColor)
                    .bounceClick(scaleDown = 0.88f, onClick = onPlayLetter)
            ) {
                if (item.vectorDrawableRes != null) {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(item.vectorDrawableRes),
                        contentDescription = item.name,
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White),
                        modifier = Modifier.size(30.dp)
                    )
                } else {
                    Text(
                        text = item.letter,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp
                        )
                    )
                }
            }

            val letterTitle = if (item.vectorDrawableRes != null) item.name else item.letter
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .scale(wordScale)
                    .clip(RoundedCornerShape(20.dp))
                    .background(item.primaryColor.copy(alpha = 0.14f))
                    .border(1.dp, item.primaryColor.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
                    .clickable(remember { MutableInteractionSource() }, null) {
                        isWordPressed = true
                        onPlayWord()
                    }
                    .padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
                Icon(Icons.Rounded.VolumeUp, null, tint = item.primaryColor, modifier = Modifier.size(19.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "$letterTitle তে ${item.word}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = item.primaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Living Illustration Canvas with Artistic Border & Motion
        LiveIllustrationCanvas(
            item = item,
            isPlaying = isPlaying,
            onTap = onPlayWord
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Syllable Breakdown Chips (100% immune to isolated Kar marks)
        val tokens = remember(item.id, item.word) {
            if (item.wordTokens.isNotEmpty()) item.wordTokens
            else com.helptrickbd.class1.feature.learn_hub.domain.util.BengaliClusterUtil.splitIntoClusters(item.word)
        }
        if (tokens.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                tokens.forEach { token ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(item.primaryColor.copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = token,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = item.primaryColor
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Rhyme Sentence Banner
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(item.primaryColor.copy(alpha = if (isPlaying) 0.22f else 0.12f))
                .bounceClick(scaleDown = 0.95f, onClick = onPlaySentence)
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Icon(Icons.Rounded.RecordVoiceOver, null, tint = item.primaryColor, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.sentence,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
