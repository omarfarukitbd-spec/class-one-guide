package com.helptrickbd.class1.feature.learn_hub.ui.phonics.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
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
import com.helptrickbd.class1.core.designsystem.modifiers.bounceClick
import com.helptrickbd.class1.feature.learn_hub.domain.audio.PhonicsAudioPlayer
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem

@Composable
fun WordBuilderLabScreen(
    items: List<PhonicsItem>,
    audioPlayer: PhonicsAudioPlayer,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) return
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentItem = items[currentIndex]

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        // Filmstrip row of words
        Text(
            text = "শব্দ গঠন অনুশীলন (${currentIndex + 1} / ${items.size})",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
                val isSelected = index == currentIndex
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) item.primaryColor else item.primaryColor.copy(alpha = 0.15f))
                        .bounceClick(scaleDown = 0.88f) { currentIndex = index }
                ) {
                    Text(
                        text = item.letter,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else item.primaryColor,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }

        // Active Word Builder Puzzle
        WordBuilderPuzzle(
            item = currentItem,
            onWordCompleted = {
                // Play cheer audio
                audioPlayer.play("cheer", "audio/sentences/shabbash.mp3")
            },
            onNextWord = {
                currentIndex = (currentIndex + 1) % items.size
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
