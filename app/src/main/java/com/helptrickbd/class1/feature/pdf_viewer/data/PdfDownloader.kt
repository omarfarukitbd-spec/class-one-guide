package com.helptrickbd.class1.feature.pdf_viewer.data

import android.content.Context
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.DownloadState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import com.helptrickbd.class1.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles chunked streaming of PDF files to local disk cache.
 * Guarantees 100% offline-first availability.
 */
@Singleton
class PdfDownloader @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    private val pdfCacheDir: File
        get() = File(context.cacheDir, "pdfs").apply {
            if (!exists()) mkdirs()
        }

    fun downloadPdf(url: String): Flow<DownloadState> = flow {
        if (url.isBlank()) {
            emit(DownloadState.Error("পিডিএফ লিংক পাওয়া যায়নি"))
            return@flow
        }

        val fullUrl = resolveFullUrl(url)
        val fileName = generateSafeFileName(url)
        val targetFile = File(pdfCacheDir, fileName)

        // 1. Instant Cache Hit (Offline-First)
        if (targetFile.exists() && targetFile.length() > 1024) {
            emit(DownloadState.Success(targetFile))
            return@flow
        }

        // 2. Stream from Remote Storage / CDN
        val tempFile = File(pdfCacheDir, "$fileName.tmp")
        var connection: HttpURLConnection? = null

        try {
            emit(DownloadState.Progress(0.05f))
            val urlConnection = URL(fullUrl).openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 15000
            urlConnection.readTimeout = 25000
            urlConnection.connect()
            connection = urlConnection

            if (urlConnection.responseCode !in 200..299) {
                emit(DownloadState.Error("সার্ভার থেকে ফাইল লোড করা যায়নি (${urlConnection.responseCode})"))
                return@flow
            }

            val totalBytes = urlConnection.contentLength
            val inputStream = urlConnection.inputStream
            val outputStream = FileOutputStream(tempFile)

            val buffer = ByteArray(8 * 1024)
            var bytesRead: Int
            var totalBytesRead = 0L

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
                totalBytesRead += bytesRead
                if (totalBytes > 0) {
                    val progress = (totalBytesRead.toFloat() / totalBytes.toFloat()).coerceIn(0.1f, 0.99f)
                    emit(DownloadState.Progress(progress))
                }
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            // Rename temp file to target file atomically
            if (tempFile.renameTo(targetFile) || (targetFile.delete() && tempFile.renameTo(targetFile))) {
                emit(DownloadState.Success(targetFile))
            } else {
                emit(DownloadState.Success(tempFile))
            }
        } catch (e: Exception) {
            if (tempFile.exists()) tempFile.delete()
            emit(DownloadState.Error(e.localizedMessage ?: "ইন্টারনেট সংযোগ চেক করুন"))
        } finally {
            connection?.disconnect()
        }
    }.flowOn(ioDispatcher)

    private fun resolveFullUrl(url: String): String {
        return if (url.startsWith("http://", ignoreCase = true) || url.startsWith("https://", ignoreCase = true)) {
            url
        } else {
            "${AppConfig.PDF_STORAGE_BASE_URL.trimEnd('/')}/${url.trimStart('/')}"
        }
    }

    private fun generateSafeFileName(url: String): String {
        val rawName = url.substringAfterLast("/").substringBefore("?")
        val sanitized = rawName.replace(Regex("[^a-zA-Z0-9._-]"), "_")
        return if (sanitized.endsWith(".pdf", ignoreCase = true)) {
            sanitized
        } else {
            "book_${url.hashCode()}.pdf"
        }
    }
}
