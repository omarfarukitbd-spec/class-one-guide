package com.helptrickbd.class1.feature.drawing.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.drawing.ui.components.DrawingControlsBar
import com.helptrickbd.class1.feature.drawing.ui.model.DrawingPath

@Composable
fun DrawingSlateScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paths = remember { mutableStateListOf<DrawingPath>() }
    var currentPath by remember { mutableStateOf<Path?>(null) }
    var selectedColor by remember { mutableStateOf(Color.White) }
    var isEraser by remember { mutableStateOf(false) }

    val slateBackground = Color(0xFF1E2430)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(slateBackground)
    ) {
        StandardTopBar(
            title = "ডিজিটাল লেখার স্লেট",
            subtitle = "বর্ণমালা ও সংখ্যা আঁকার ক্যানভাস",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationClick = onBackClick
        )

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(isEraser, selectedColor) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                val path = Path().apply { moveTo(offset.x, offset.y) }
                                currentPath = path
                            },
                            onDrag = { change, _ ->
                                currentPath?.lineTo(change.position.x, change.position.y)
                            },
                            onDragEnd = {
                                currentPath?.let {
                                    paths.add(
                                        DrawingPath(
                                            path = it,
                                            color = if (isEraser) slateBackground else selectedColor,
                                            strokeWidth = if (isEraser) 40f else 10f,
                                            isEraser = isEraser
                                        )
                                    )
                                }
                                currentPath = null
                            },
                            onDragCancel = { currentPath = null }
                        )
                    }
            ) {
                paths.forEach { dp ->
                    drawPath(
                        path = dp.path,
                        color = dp.color,
                        style = Stroke(
                            width = dp.strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                currentPath?.let {
                    drawPath(
                        path = it,
                        color = if (isEraser) slateBackground else selectedColor,
                        style = Stroke(
                            width = if (isEraser) 40f else 10f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            DrawingControlsBar(
                selectedColor = selectedColor,
                isEraser = isEraser,
                onColorSelected = {
                    selectedColor = it
                    isEraser = false
                },
                onToggleEraser = { isEraser = !isEraser },
                onClear = { paths.clear() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
