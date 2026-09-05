package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateBoardTheme
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateTool

@Composable
fun SlateToolbar(
    activeTool: SlateTool,
    currentTheme: SlateBoardTheme,
    canUndo: Boolean,
    canRedo: Boolean,
    isSoundEnabled: Boolean,
    onToolSelect: (SlateTool) -> Unit,
    onThemeSelect: (SlateBoardTheme) -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onClear: () -> Unit,
    onToggleSound: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showThemeMenu by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                FilledIconToggleButton(
                    checked = activeTool == SlateTool.CHALK,
                    onCheckedChange = { onToolSelect(SlateTool.CHALK) }
                ) {
                    Icon(Icons.Rounded.Brush, contentDescription = stringResource(R.string.slate_tool_chalk))
                }

                FilledIconToggleButton(
                    checked = activeTool == SlateTool.ERASER,
                    onCheckedChange = { onToolSelect(SlateTool.ERASER) }
                ) {
                    Icon(Icons.Rounded.AutoFixNormal, contentDescription = stringResource(R.string.slate_tool_eraser))
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onUndo, enabled = canUndo) {
                    Icon(Icons.Rounded.Undo, contentDescription = stringResource(R.string.slate_tool_undo))
                }

                IconButton(onClick = onRedo, enabled = canRedo) {
                    Icon(Icons.Rounded.Redo, contentDescription = stringResource(R.string.slate_tool_redo))
                }

                IconButton(onClick = onClear) {
                    Icon(
                        Icons.Rounded.DeleteSweep,
                        contentDescription = stringResource(R.string.slate_tool_clear),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onToggleSound) {
                    Icon(
                        imageVector = if (isSoundEnabled) Icons.Rounded.VolumeUp else Icons.Rounded.VolumeOff,
                        contentDescription = stringResource(
                            if (isSoundEnabled) R.string.slate_tool_sound_on else R.string.slate_tool_sound_off
                        )
                    )
                }

                Box {
                    IconButton(onClick = { showThemeMenu = true }) {
                        Icon(Icons.Rounded.Palette, contentDescription = stringResource(R.string.slate_theme_blackboard))
                    }
                    DropdownMenu(
                        expanded = showThemeMenu,
                        onDismissRequest = { showThemeMenu = false }
                    ) {
                        SlateBoardTheme.entries.forEach { theme ->
                            DropdownMenuItem(
                                text = { Text(stringResource(theme.titleRes)) },
                                onClick = {
                                    onThemeSelect(theme)
                                    showThemeMenu = false
                                },
                                leadingIcon = {
                                    Surface(
                                        modifier = Modifier.size(18.dp),
                                        shape = MaterialTheme.shapes.small,
                                        color = theme.boardColor
                                    ) {}
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
