package com.helptrickbd.class1.feature.pdf_viewer.domain.engine

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.LruCache
import com.helptrickbd.class1.core.security.PdfCryptoEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.RandomAccessFile

/**
 * Thread-safe Native PDF Renderer with Global resolution-aware Bitmap Caching.
 * Enhanced with military-grade shredding, RAM optimization, and memory pressure awareness.
 */
class PdfRendererEngine(
    private val context: Context,
    private val cacheDir: File,
    private val pdfFile: File,
    private val cryptoEngine: PdfCryptoEngine
) : ComponentCallbacks2 {

    companion object {
        // Global caches shared across all instances to prevent OOM
        // Key: "filePath_pageIndex_targetWidth"
        private val globalMemoryCache = object : LruCache<String, Bitmap>(12) {
            override fun entryRemoved(evicted: Boolean, key: String?, oldValue: Bitmap?, newValue: Bitmap?) {
                // Bitmaps are not recycled to avoid crashing in-flight UI
            }
        }

        private val globalThumbnailCache = object : LruCache<String, Bitmap>(48) {
            override fun entryRemoved(evicted: Boolean, key: String?, oldValue: Bitmap?, newValue: Bitmap?) {}
        }
        
        fun clearGlobalCaches() {
            globalMemoryCache.evictAll()
            globalThumbnailCache.evictAll()
        }
    }

    private var fileDescriptor: ParcelFileDescriptor? = null
    private var renderer: PdfRenderer? = null
    private var tempDecryptedFile: File? = null
    private val mutex = Mutex()
    private val engineId = pdfFile.absolutePath

    init {
        context.registerComponentCallbacks(this)
        openRenderer()
    }

    private fun openRenderer() {
        if (pdfFile.exists() && pdfFile.length() > 0) {
            // Use nanoTime for unique session to prevent collisions
            tempDecryptedFile = File(cacheDir, "sec_sess_${System.nanoTime()}.tmp")

            try {
                val fileInputStream = FileInputStream(pdfFile)
                val decryptingStream = cryptoEngine.getDecryptingInputStream(fileInputStream)
                val fileOutputStream = FileOutputStream(tempDecryptedFile)

                val buffer = ByteArray(16 * 1024)
                try {
                    var bytesRead: Int
                    while (decryptingStream.read(buffer).also { bytesRead = it } != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead)
                    }
                    fileOutputStream.flush()
                } finally {
                    buffer.fill(0) // Security: zero out sensitive RAM buffer
                    fileOutputStream.close()
                    decryptingStream.close()
                    fileInputStream.close()
                }

                fileDescriptor = ParcelFileDescriptor.open(tempDecryptedFile, ParcelFileDescriptor.MODE_READ_ONLY)
                
                // SECURITY: Delete from disk filesystem immediately while open (inode stays alive)
                tempDecryptedFile?.delete()
                
                fileDescriptor?.let {
                    renderer = PdfRenderer(it)
                }
            } catch (e: Exception) {
                cleanupResources()
                e.printStackTrace()
            }
        }
    }

    val pageCount: Int
        get() = renderer?.pageCount ?: 0

    suspend fun renderPage(pageIndex: Int, targetWidth: Int): Bitmap? = withContext(Dispatchers.IO) {
        if (pageIndex < 0 || pageIndex >= pageCount) return@withContext null

        val cacheKey = "${engineId}_${pageIndex}_$targetWidth"
        globalMemoryCache.get(cacheKey)?.let { cachedBitmap ->
            if (!cachedBitmap.isRecycled) return@withContext cachedBitmap
        }

        mutex.withLock {
            globalMemoryCache.get(cacheKey)?.let { cached ->
                if (!cached.isRecycled) return@withContext cached
            }

            val currentRenderer = renderer ?: return@withContext null

            try {
                val page = currentRenderer.openPage(pageIndex)
                val width = targetWidth.coerceAtLeast(1)
                val height = ((width.toFloat() / page.width.toFloat()) * page.height).toInt().coerceAtLeast(1)

                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                bitmap.eraseColor(Color.WHITE)

                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()

                globalMemoryCache.put(cacheKey, bitmap)
                bitmap
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun renderThumbnail(pageIndex: Int, targetWidth: Int = 250): Bitmap? = withContext(Dispatchers.IO) {
        if (pageIndex < 0 || pageIndex >= pageCount) return@withContext null

        val cacheKey = "thumb_${engineId}_$pageIndex"
        globalThumbnailCache.get(cacheKey)?.let { cached ->
            if (!cached.isRecycled) return@withContext cached
        }

        mutex.withLock {
            globalThumbnailCache.get(cacheKey)?.let { cached ->
                if (!cached.isRecycled) return@withContext cached
            }

            val currentRenderer = renderer ?: return@withContext null

            try {
                val page = currentRenderer.openPage(pageIndex)
                val width = targetWidth.coerceAtLeast(1)
                val height = ((width.toFloat() / page.width.toFloat()) * page.height).toInt().coerceAtLeast(1)

                // Optimized RGB_565 for thumbnails (50% RAM saving)
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                bitmap.eraseColor(Color.WHITE)

                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()

                globalThumbnailCache.put(cacheKey, bitmap)
                bitmap
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun onTrimMemory(level: Int) {
        if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW) {
            clearGlobalCaches()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {}
    override fun onLowMemory() {
        clearGlobalCaches()
    }

    fun close() {
        context.unregisterComponentCallbacks(this)
        cleanupResources()
    }

    private fun cleanupResources() {
        try {
            renderer?.close()
            fileDescriptor?.close()
        } catch (_: Exception) {
        } finally {
            renderer = null
            fileDescriptor = null
            shredTempFile()
        }
    }

    /**
     * Military-grade shredding by overwriting file content with zeros before deletion.
     */
    private fun shredTempFile() {
        tempDecryptedFile?.let { file ->
            if (file.exists()) {
                try {
                    val length = file.length()
                    if (length > 0) {
                        RandomAccessFile(file, "rws").use { raf ->
                            val zeros = ByteArray(16 * 1024)
                            var written: Long = 0
                            while (written < length) {
                                val toWrite = (length - written).coerceAtMost(zeros.size.toLong()).toInt()
                                raf.write(zeros, 0, toWrite)
                                written += toWrite
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    file.delete()
                }
            }
        }
        tempDecryptedFile = null
    }
}
