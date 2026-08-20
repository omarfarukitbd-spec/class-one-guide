package com.helptrickbd.class1.feature.pdf_viewer.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
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

                    Image(
                        bitmap = currentBitmap.asImageBitmap(),
                        contentDescription = "পৃষ্ঠা ${pageIndex + 1}",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                        colorFilter = colorFilter
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(360.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
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
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "পৃষ্ঠা ${pageIndex + 1}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
