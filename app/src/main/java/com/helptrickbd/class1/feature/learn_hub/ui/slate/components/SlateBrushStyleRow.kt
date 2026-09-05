package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.BrushStyle
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.StrokeWidthOption

@Composable
fun SlateBrushStyleRow(
    selectedBrush: BrushStyle,
    selectedWidth: StrokeWidthOption,
    isEraserMode: Boolean,
    onBrushSelect: (BrushStyle) -> Unit,
    onWidthSelect: (StrokeWidthOption) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        if (!isEraserMode) {
            items(BrushStyle.entries) { brush ->
                FilterChip(
                    selected = selectedBrush == brush,
                    onClick = { onBrushSelect(brush) },
                    label = { Text(stringResource(brush.titleRes), fontSize = 12.sp) },
                    leadingIcon = {
                        val icon = when (brush) {
                            BrushStyle.CHALK -> Icons.Rounded.Edit
                            BrushStyle.NEON_GLOW -> Icons.Rounded.AutoAwesome
                            BrushStyle.MARKER -> Icons.Rounded.Highlight
                            BrushStyle.PENCIL -> Icons.Rounded.Create
                        }
                        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                )
            }

            item {
                VerticalDivider(
                    modifier = Modifier
                        .height(28.dp)
                        .padding(horizontal = 2.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }

        items(StrokeWidthOption.entries) { widthOpt ->
            FilterChip(
                selected = selectedWidth == widthOpt,
                onClick = { onWidthSelect(widthOpt) },
                label = { Text(stringResource(widthOpt.labelRes), fontSize = 12.sp) },
                leadingIcon = {
                    val icon = if (isEraserMode) Icons.Rounded.AutoFixNormal else Icons.Rounded.LineWeight
                    Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            )
        }
    }
}
