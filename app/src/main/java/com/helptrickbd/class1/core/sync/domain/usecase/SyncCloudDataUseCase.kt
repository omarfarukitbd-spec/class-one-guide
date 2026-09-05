package com.helptrickbd.class1.core.sync.domain.usecase

import androidx.room.withTransaction
import com.helptrickbd.class1.core.analytics.domain.CrashReporter
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.database.AppDatabase
import com.helptrickbd.class1.core.database.BookDao
import com.helptrickbd.class1.core.database.BookEntity
import com.helptrickbd.class1.core.database.ChapterDao
import com.helptrickbd.class1.core.database.ChapterEntity
import com.helptrickbd.class1.core.di.IoDispatcher
import com.helptrickbd.class1.core.sync.domain.repository.CloudSyncRepository
import com.helptrickbd.class1.core.util.StorageProvider
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion
import com.helptrickbd.class1.feature.home.domain.model.Resource
import com.helptrickbd.class1.feature.home.domain.model.ResourceType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncCloudDataUseCase @Inject constructor(
    private val syncRepository: CloudSyncRepository,
    private val database: AppDatabase,
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao,
    private val crashReporter: CrashReporter,
    private val storageProvider: StorageProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    /**
     * Executes Cloud to Local Database Sync with strict data integrity guards.
     */
    suspend operator fun invoke(forceSync: Boolean = false): Boolean = withContext(ioDispatcher) {
        if (!AppConfig.FEATURE_CLOUD_SYNC) return@withContext false

        try {
            val metadata = syncRepository.getRemoteClassMetadata(AppConfig.TARGET_CLASS_ID) ?: return@withContext false
            val localTimestamp = syncRepository.getLocalLastSyncTimestamp()

            syncRepository.saveCachedNotice(metadata.notice)
            syncRepository.saveMinAppVersion(metadata.minAppVersion)

            // 1. Timestamp Delta Guard
            // Logic Fix: Also check if local DB is actually seeded before skipping sync
            val localCount = bookDao.getBookCount()
            if (!forceSync && localCount > 0 && metadata.lastUpdated > 0 && metadata.lastUpdated <= localTimestamp) {
                return@withContext true
            }

            // 2. Fetch Remote Data
            val remoteData = syncRepository.fetchRemoteBooksWithChapters(AppConfig.TARGET_CLASS_ID)
            
            // CRITICAL PROTECTION: Zero Data Loss Guard
            // If remote returns empty but local has data, it might be an API error or partial document.
            // We ABORT sync instead of deleting local DB.
            if (remoteData.isEmpty()) {
                if (localCount > 0) {
                    crashReporter.recordException(Exception("Sync Aborted: Remote returned empty list while local has $localCount books. Preventing accidental data loss."))
                }
                return@withContext false
            }

            val existingBooksMap = bookDao.getAllBooksDirect().associateBy { it.bookId }
            val activeBookIds = remoteData.map { it.first.bookId }

            val bookEntitiesToInsert = mutableListOf<BookEntity>()
            val chapterEntitiesToInsert = mutableListOf<ChapterEntity>()
            val filesToEvict = mutableListOf<File>()

            for ((remoteBook, remoteChapters) in remoteData) {
                val existing = existingBooksMap[remoteBook.bookId]

                // Cache Eviction Logic: If PDF URL changed, old cached PDF is stale
                if (existing != null && existing.pdfUrl.isNotBlank() && existing.pdfUrl != remoteBook.pdfUrl) {
                    val oldFileName = existing.pdfUrl.substringAfterLast("/").substringBefore("?")
                    val oldFile = File(File(storageProvider.cacheDir, "pdfs"), oldFileName)
                    if (oldFile.exists()) filesToEvict.add(oldFile)
                }

                val curriculum = runCatching { Curriculum.valueOf(remoteBook.curriculum) }.getOrDefault(Curriculum.SCHOOL)
                val versions = remoteBook.availableVersions.mapNotNull {
                    runCatching { LanguageVersion.valueOf(it) }.getOrNull()
                }.ifEmpty { listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH) }

                bookEntitiesToInsert.add(
                    BookEntity(
                        bookId = remoteBook.bookId,
                        title = remoteBook.title,
                        subtitle = remoteBook.subtitle,
                        pdfUrl = remoteBook.pdfUrl,
                        coverUrl = remoteBook.coverUrl,
                        curriculum = curriculum,
                        availableVersions = versions,
                        totalChapters = remoteChapters.size,
                        isFavorite = existing?.isFavorite ?: false,
                        progressPercent = existing?.progressPercent ?: 0f,
                        lastReadPage = existing?.lastReadPage ?: 1,
                        lastReadTimestamp = existing?.lastReadTimestamp ?: 0L
                    )
                )

                for (remoteChap in remoteChapters) {
                    val chapVersion = runCatching { LanguageVersion.valueOf(remoteChap.version) }.getOrDefault(LanguageVersion.BANGLA)
                    val resources = remoteChap.resources.map { r ->
                        val resType = runCatching { ResourceType.valueOf(r.type) }.getOrDefault(ResourceType.TEXTBOOK)
                        Resource(
                            resourceId = r.resourceId,
                            title = r.title,
                            pdfUrl = r.pdfUrl,
                            type = resType,
                            iconName = r.iconName
                        )
                    }

                    val uniqueChapterId = if (remoteChap.chapterId.startsWith(remoteBook.bookId)) {
                        remoteChap.chapterId
                    } else {
                        "${remoteBook.bookId}_${remoteChap.chapterId}"
                    }

                    chapterEntitiesToInsert.add(
                        ChapterEntity(
                            chapterId = uniqueChapterId,
                            bookId = remoteBook.bookId,
                            unitNo = remoteChap.unitNo,
                            title = remoteChap.title,
                            version = chapVersion,
                            resources = resources,
                            orderIndex = remoteChap.orderIndex
                        )
                    )
                }
            }

            // 3. Atomic Database Upsert
            database.withTransaction {
                bookDao.insertBooks(bookEntitiesToInsert)
                chapterDao.insertChapters(chapterEntitiesToInsert)
                
                // Only delete stale records if we have a healthy remote list (already guarded above)
                bookDao.deleteBooksNotIn(activeBookIds)
                chapterDao.deleteChaptersForBooksNotIn(activeBookIds)
            }

            // 4. Safe Cleanup
            filesToEvict.forEach { if (it.exists()) it.delete() }

            val syncTime = if (metadata.lastUpdated > 0) metadata.lastUpdated else System.currentTimeMillis()
            syncRepository.saveLocalLastSyncTimestamp(syncTime)

            true
        } catch (e: Exception) {
            val errorType = when(e) {
                is IOException -> "Network"
                is TimeoutException -> "Timeout"
                else -> "Unknown"
            }
            crashReporter.recordException(Exception("Sync Error [$errorType]: ${e.message}", e))
            false
        }
    }
}
