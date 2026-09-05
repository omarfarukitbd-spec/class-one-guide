package com.helptrickbd.class1.feature.learn_hub.domain.util

import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.toArgb
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.ChalkStroke
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.SlateBoardTheme

object SlateBitmapSaver {

    fun saveSlateImage(
        context: Context,
        strokes: List<ChalkStroke>,
        theme: SlateBoardTheme,
        canvasWidth: Int = 1080,
        canvasHeight: Int = 1080
    ): Boolean {
        return try {
            val bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            canvas.drawColor(theme.boardColor.toArgb())

            val paint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
            }

            for (stroke in strokes) {
                val pts = stroke.points
                if (pts.isEmpty()) continue

                paint.strokeWidth = stroke.strokeWidth
                paint.color = stroke.color.toArgb()

                if (pts.size == 1) {
                    canvas.drawCircle(pts[0].x, pts[0].y, paint.strokeWidth / 2f, paint)
                    continue
                }

                val path = Path()
                path.moveTo(pts[0].x, pts[0].y)
                for (i in 1 until pts.size) {
                    val p0 = pts[i - 1]
                    val p1 = pts[i]
                    path.quadTo(p0.x, p0.y, (p0.x + p1.x) / 2f, (p0.y + p1.y) / 2f)
                }
                path.lineTo(pts.last().x, pts.last().y)
                canvas.drawPath(path, paint)
            }

            val filename = "Slate_${System.currentTimeMillis()}.jpg"
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Class1_Slate")
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: return false

            resolver.openOutputStream(uri)?.use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }

            bitmap.recycle()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
