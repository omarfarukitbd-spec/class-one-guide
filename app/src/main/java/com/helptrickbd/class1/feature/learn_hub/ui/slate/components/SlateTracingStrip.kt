package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTracingCategory
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTracingItem
import com.helptrickbd.class1.feature.learn_hub.domain.provider.SlateTracingProvider

@Composable
fun SlateTracingStrip(
    selectedCategory: SlateTracingCategory,
    selectedItem: SlateTracingItem,
    isPlayingAudio: Boolean,
    onCategorySelect: (SlateTracingCategory) -> Unit,
    onItemSelect: (SlateTracingItem) -> Unit,
    onPlayAudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = SlateTracingProvider.getItemsByCategory(selectedCategory)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ScrollableTabRow(
            selectedTabIndex = SlateTracingCategory.entries.indexOf(selectedCategory),
            edgePadding = 8.dp,
            divider = {}
        ) {
            SlateTracingCategory.entries.forEach { cat ->
                Tab(
                    selected = selectedCategory == cat,
                    onClick = { onCategorySelect(cat) },
                    text = { Text(stringResource(cat.titleRes), fontSize = 13.sp) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPlayAudio,
                enabled = selectedItem.audioPath != null,
                modifier = Modifier.padding(start = 2.dp)
            ) {
                Icon(
                    Icons.Rounded.VolumeUp,
                    contentDescription = stringResource(com.helptrickbd.class1.R.string.slate_listen_voice),
                    tint = if (selectedItem.audioPath == null) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    } else if (isPlayingAudio) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }

            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 6.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    val isSelected = selectedItem.id == item.id
                    Surface(
                        modifier = Modifier
                            .height(48.dp)
                            .defaultMinSize(minWidth = 52.dp)
                            .clickable { onItemSelect(item) },
                        shape = MaterialTheme.shapes.medium,
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                        border = if (isSelected) ButtonDefaults.outlinedButtonBorder else null
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (item.letter.isNotBlank()) item.letter else item.name,
                                fontSize = if (item.letter.isNotBlank()) 22.sp else 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
