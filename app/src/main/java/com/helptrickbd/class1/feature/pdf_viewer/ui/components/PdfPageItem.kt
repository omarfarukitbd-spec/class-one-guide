package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.pdf_viewer.domain.engine.PdfRendererEngine
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.PdfReadingTheme

val InvertedColorMatrix = ColorMatrix(
    floatArrayOf(
        -1.0f,  0.0f,  0.0f, 0.0f, 255f,
         0.0f, -1.0f,  0.0f, 0.0f, 255f,
         0.0f,  0.0f, -1.0f, 0.0f, 255f,
         0.0f,  0.0f,  0.0f, 1.0f,   0f
    )
)

val SepiaColorMatrix = ColorMatrix(
    floatArrayOf(
        0.393f * 1.1f, 0.769f * 1.1f, 0.189f * 1.1f, 0f, 20f,
        0.349f * 1.05f, 0.686f * 1.05f, 0.168f * 1.05f, 0f, 15f,
        0.272f * 0.9f, 0.534f * 0.9f, 0.131f * 0.9f, 0f, 5f,
        0.000f, 0.000f, 0.000f, 1f, 0f
    )
)

@Composable
fun PdfPageItem(
    pageIndex: Int,
    engine: PdfRendererEngine?,
    readingTheme: PdfReadingTheme,
    modifier: Modifier = Modifier
) {
    var scale by remember(pageIndex) { mutableFloatStateOf(1f) }
    var offset by remember(pageIndex) { mutableStateOf(Offset.Zero) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val targetWidth = constraints.maxWidth.coerceAtLeast(1)

            val pageBitmap by produceState<Bitmap?>(initialValue = null, key1 = pageIndex, key2 = targetWidth) {
                value = engine?.renderPage(pageIndex, targetWidth)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val currentBitmap = pageBitmap
                if (currentBitmap != null && !currentBitmap.isRecycled) {
                    val colorFilter = when (readingTheme) {
                        PdfReadingTheme.LIGHT -> null
                        PdfReadingTheme.DARK_INVERTED -> ColorFilter.colorMatrix(InvertedColorMatrix)
                        PdfReadingTheme.SEPIA_WARM -> ColorFilter.colorMatrix(SepiaColorMatrix)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .pointerInput(pageIndex) {
                                detectTransformGestures { _, pan, zoom, _ ->
                                    scale = (scale * zoom).coerceIn(1f, 4f)
                                    if (scale > 1f) {
                                        val maxX = (size.width * (scale - 1)) / 2f
                                        val maxY = (size.height * (scale - 1)) / 2f
                                        offset = Offset(
                                            x = (offset.x + pan.x).coerceIn(-maxX, maxX),
                                            y = (offset.y + pan.y).coerceIn(-maxY, maxY)
                                        )
                                    } else {
                                        offset = Offset.Zero
                                    }
                                }
                            }
                            .pointerInput(pageIndex) {
                                detectTapGestures(
                                    onDoubleTap = {
                                        scale = if (scale > 1f) 1f else 2.2f
                                        offset = Offset.Zero
                                    }
                                )
                            }
                    ) {
                        Image(
                            bitmap = currentBitmap.asImageBitmap(),
                            contentDescription = "পৃষ্ঠা ${pageIndex + 1}",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    translationX = offset.x
                                    translationY = offset.y
                                },
                            colorFilter = colorFilter
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(380.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 3.dp
                        )
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "পৃষ্ঠা ${pageIndex + 1}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
