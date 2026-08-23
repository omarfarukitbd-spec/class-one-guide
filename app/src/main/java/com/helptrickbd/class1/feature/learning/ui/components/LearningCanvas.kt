package com.helptrickbd.class1.feature.learning.ui.components

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.helptrickbd.class1.feature.learning.domain.model.DrawingPath

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LearningCanvas(
    paths: List<DrawingPath>,
    onPathDrawn: (DrawingPath) -> Unit,
    modifier: Modifier = Modifier,
    currentColor: Color = Color.Black,
    strokeWidth: Float = 12f
) {
    val currentPath = remember { Path() }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        currentPath.moveTo(event.x, event.y)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        currentPath.lineTo(event.x, event.y)
                    }
                    MotionEvent.ACTION_UP -> {
                        val newPath = Path().apply { addPath(currentPath) }
                        onPathDrawn(DrawingPath(newPath, currentColor, strokeWidth))
                        currentPath.reset()
                    }
                }
                true
            }
    ) {
        // Draw existing paths
        paths.forEach { drawingPath ->
            drawPath(
                path = drawingPath.path,
                color = drawingPath.color,
                style = Stroke(
                    width = drawingPath.strokeWidth,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
        
        // Draw currently drawing path
        drawPath(
            path = currentPath,
            color = currentColor,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
