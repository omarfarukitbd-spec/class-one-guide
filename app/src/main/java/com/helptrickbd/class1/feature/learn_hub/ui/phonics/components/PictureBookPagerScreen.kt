package com.helptrickbd.class1.feature.learn_hub.ui.phonics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material3.*
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PictureBookPagerScreen(
    items: List<PhonicsItem>,
    audioPlayer: PhonicsAudioPlayer,
    currentlyPlayingId: String?,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = { items.size })
    val coroutineScope = rememberCoroutineScope()
    val filmstripState = rememberLazyListState()
    var isAutoPlayActive by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        filmstripState.animateScrollToItem(pagerState.currentPage)
        if (isAutoPlayActive) {
            val item = items[pagerState.currentPage]
            val path = item.sentenceAudioPath ?: item.audioAssetPath
            audioPlayer.play(item.id, path) {
                coroutineScope.launch {
                    delay(800)
                    if (isAutoPlayActive) {
                        val next = (pagerState.currentPage + 1) % items.size
                        pagerState.animateScrollToPage(next)
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            isAutoPlayActive = false
            audioPlayer.stop()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        // Auto-play / Read-to-me Control Strip
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text(
                text = "পৃষ্ঠা ${pagerState.currentPage + 1} / ${items.size}",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )

            Button(
                onClick = {
                    isAutoPlayActive = !isAutoPlayActive
                    if (!isAutoPlayActive) audioPlayer.stop()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAutoPlayActive) Color(0xFFEF4444) else MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = if (isAutoPlayActive) Icons.Rounded.Pause else Icons.Rounded.AutoStories,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isAutoPlayActive) "থামাও" else "অটো প্লে (পড়ে শোনাও)")
            }
        }

        // Horizontal Pager: Illustrated Flashcards
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) { page ->
            val item = items[page]
            PictureBookCard(
                item = item,
                isPlaying = currentlyPlayingId == item.id,
                onPlayLetter = {
                    audioPlayer.play(item.id, item.letterAudioPath ?: item.audioAssetPath)
                },
                onPlayWord = {
                    audioPlayer.play(item.id, item.wordAudioPath ?: item.audioAssetPath)
                },
                onPlaySentence = {
                    audioPlayer.play(item.id, item.sentenceAudioPath ?: item.audioAssetPath)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Bottom Letter Filmstrip for Quick Scrubbing
        LazyRow(
            state = filmstripState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        ) {
            itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
                val isSelected = index == pagerState.currentPage
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) item.primaryColor else item.primaryColor.copy(alpha = 0.15f))
                        .bounceClick(scaleDown = 0.88f) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                ) {
                    if (item.vectorDrawableRes != null) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(item.vectorDrawableRes),
                            contentDescription = item.name,
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                if (isSelected) Color.White else item.primaryColor
                            ),
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = item.letter,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else item.primaryColor,
                                fontSize = 18.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
