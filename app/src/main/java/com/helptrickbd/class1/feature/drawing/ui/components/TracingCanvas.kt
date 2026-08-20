package com.helptrickbd.class1.feature.drawing.ui.components

import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem
import com.helptrickbd.class1.feature.drawing.ui.model.DrawingPath

val SlateCanvasBackground = Color(0xFF181D27)

@Composable
fun TracingCanvas(
    paths: List<DrawingPath>,
    selectedItem: TracingItem?,
    selectedCategory: TracingCategory,
    showGuide: Boolean,
    selectedColor: Color,
    strokeWidth: Float,
    isEraser: Boolean,
    onPathDrawn: (DrawingPath) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPath by remember { mutableStateOf<Path?>(null) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(isEraser, selectedColor, strokeWidth) {
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
                            onPathDrawn(
                                DrawingPath(
                                    path = it,
                                    color = if (isEraser) SlateCanvasBackground else selectedColor,
                                    strokeWidth = if (isEraser) strokeWidth * 3f else strokeWidth,
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
        // Draw background guideline grids if in tracing mode
        if (selectedCategory != TracingCategory.FREE_DRAW && showGuide) {
            val midY = size.height / 2f
            drawLine(
                color = Color.White.copy(alpha = 0.15f),
                start = Offset(40f, midY),
                end = Offset(size.width - 40f, midY),
                strokeWidth = 2f
            )

            // Draw large dotted character in center
            selectedItem?.let { item ->
                val nativeCanvas = drawContext.canvas.nativeCanvas
                val text = item.character
                val textSizePx = size.width.coerceAtMost(size.height) * 0.55f

                val fillPaint = Paint().apply {
                    textSize = textSizePx
                    textAlign = Paint.Align.CENTER
                    color = android.graphics.Color.WHITE
                    alpha = 40
                    isAntiAlias = true
                    typeface = Typeface.DEFAULT_BOLD
                }

                val strokePaint = Paint().apply {
                    textSize = textSizePx
                    textAlign = Paint.Align.CENTER
                    color = android.graphics.Color.WHITE
                    alpha = 110
                    style = Paint.Style.STROKE
                    strokeWidth = 5f
                    pathEffect = DashPathEffect(floatArrayOf(16f, 14f), 0f)
                    isAntiAlias = true
                    typeface = Typeface.DEFAULT_BOLD
                }

                val baselineY = midY - ((fillPaint.descent() + fillPaint.ascent()) / 2f)
                val centerX = size.width / 2f

                nativeCanvas.drawText(text, centerX, baselineY, fillPaint)
                nativeCanvas.drawText(text, centerX, baselineY, strokePaint)
            }
        }

        // Draw previously saved paths
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

        // Draw active drawing path
        currentPath?.let {
            drawPath(
                path = it,
                color = if (isEraser) SlateCanvasBackground else selectedColor,
                style = Stroke(
                    width = if (isEraser) strokeWidth * 3f else strokeWidth,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}
