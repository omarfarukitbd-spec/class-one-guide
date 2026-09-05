package com.helptrickbd.class1.feature.learn_hub.ui.phonics.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Abc
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem
import com.helptrickbd.class1.feature.learn_hub.ui.phonics.PhonicsDisplayMode

@Composable
fun SoundboardGridView(
    items: List<PhonicsItem>,
    displayMode: PhonicsDisplayMode,
    currentlyPlayingId: String?,
    onModeToggle: () -> Unit,
    onItemClick: (PhonicsItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Subtitle & Display Mode Toggle Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = stringResource(R.string.phonics_tap_to_listen),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            FilterChip(
                selected = displayMode == PhonicsDisplayMode.WORDS,
                onClick = onModeToggle,
                label = {
                    Text(
                        text = if (displayMode == PhonicsDisplayMode.WORDS) {
                            stringResource(R.string.phonics_mode_words)
                        } else {
                            stringResource(R.string.phonics_mode_alphabet)
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = if (displayMode == PhonicsDisplayMode.WORDS) {
                            Icons.Rounded.MenuBook
                        } else {
                            Icons.Rounded.Abc
                        },
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Responsive Adaptive Grid
        val minCardSize = if (displayMode == PhonicsDisplayMode.ALPHABET) 88.dp else 145.dp

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = minCardSize),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = items,
                key = { it.id }
            ) { item ->
                PhonicsLetterCard(
                    item = item,
                    displayMode = displayMode,
                    isPlaying = currentlyPlayingId == item.id,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}
