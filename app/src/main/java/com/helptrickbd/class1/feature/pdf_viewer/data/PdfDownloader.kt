package com.helptrickbd.class1.feature.pdf_viewer.data

import android.os.StatFs
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.di.IoDispatcher
import com.helptrickbd.class1.core.security.PdfCryptoEngine
import com.helptrickbd.class1.core.util.StorageProvider
import com.helptrickbd.class1.feature.pdf_viewer.domain.model.DownloadState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Handles chunked streaming of PDF files to local disk cache with DRM encryption.
 * Optimized with Disk Space Guard and Atomic File Operations.
 */
@Singleton
class PdfDownloader @Inject constructor(
    private val storageProvider: StorageProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val cryptoEngine: PdfCryptoEngine
) {
    private val pdfCacheDir: File
        get() = File(storageProvider.cacheDir, "pdfs").apply {
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

        // 1. Instant Cache Hit (Offline-First SSOT)
        if (targetFile.exists() && targetFile.length() > 1024) {
            emit(DownloadState.Success(targetFile))
            return@flow
        }

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
                emit(DownloadState.Error("সার্ভার থেকে ফাইল লোড করা যায়নি (Error: ${urlConnection.responseCode})"))
                return@flow
            }

            val totalBytes = urlConnection.contentLength.toLong()
            
            // Logic Fix: Disk Space Guard (Ensure at least 2x size available for temp + final copy)
            if (totalBytes > 0 && !hasEnoughSpace(totalBytes * 2)) {
                emit(DownloadState.Error("আপনার ফোনে পর্যাপ্ত জায়গা নেই। দয়া করে কিছু মেমোরি খালি করুন।"))
                return@flow
            }

            val inputStream = urlConnection.inputStream
            val fileOutputStream = FileOutputStream(tempFile)
            val outputStream = cryptoEngine.getEncryptingOutputStream(fileOutputStream)

            val buffer = ByteArray(16 * 1024)
            var bytesRead: Int
            var totalBytesRead = 0L

            outputStream.use { encryptedOut ->
                inputStream.use { input ->
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        encryptedOut.write(buffer, 0, bytesRead)
                        totalBytesRead += bytesRead
                        if (totalBytes > 0) {
                            val progress = (totalBytesRead.toFloat() / totalBytes.toFloat()).coerceIn(0.1f, 0.99f)
                            emit(DownloadState.Progress(progress))
                        }
                    }
                }
            }
            
            // SECURITY: Verify file completeness before removing .tmp extension
            if (totalBytes > 0 && totalBytesRead < totalBytes) {
                throw Exception("ডাউনলোড সম্পূর্ণ হয়নি। সংযোগ বিচ্ছিন্ন হয়েছে।")
            }

            // Atomic Commit: Move temp to final location
            if (targetFile.exists()) targetFile.delete()
            if (tempFile.renameTo(targetFile)) {
                emit(DownloadState.Success(targetFile))
            } else {
                // Fallback for some filesystems
                tempFile.copyTo(targetFile, overwrite = true)
                tempFile.delete()
                emit(DownloadState.Success(targetFile))
            }
        } catch (e: Exception) {
            if (tempFile.exists()) tempFile.delete()
            emit(DownloadState.Error(e.message ?: "ইন্টারনেট সংযোগ চেক করুন"))
        } finally {
            connection?.disconnect()
        }
    }.flowOn(ioDispatcher)

    private fun hasEnoughSpace(requiredBytes: Long): Boolean {
        return try {
            val stat = StatFs(storageProvider.cacheDir.absolutePath)
            val availableBytes = stat.availableBlocksLong * stat.blockSizeLong
            availableBytes > requiredBytes
        } catch (_: Exception) {
            true // Fallback to avoid blocking on older APIs if StatFs fails
        }
    }

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
        val baseName = if (sanitized.endsWith(".pdf", ignoreCase = true)) {
            sanitized
        } else {
            "book_${url.hashCode()}.pdf"
        }
        return "$baseName.enc"
    }
}
