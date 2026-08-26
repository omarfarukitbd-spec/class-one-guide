package com.helptrickbd.class1.core.sync.domain.usecase

import android.content.Context
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.database.BookDao
import com.helptrickbd.class1.core.database.BookEntity
import com.helptrickbd.class1.core.database.ChapterDao
import com.helptrickbd.class1.core.database.ChapterEntity
import com.helptrickbd.class1.core.sync.domain.repository.CloudSyncRepository
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion
import com.helptrickbd.class1.feature.home.domain.model.Resource
import com.helptrickbd.class1.feature.home.domain.model.ResourceType
import dagger.hilt.android.qualifiers.ApplicationContext
import com.helptrickbd.class1.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncCloudDataUseCase @Inject constructor(
    private val syncRepository: CloudSyncRepository,
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(forceSync: Boolean = false): Boolean = withContext(ioDispatcher) {
        if (!AppConfig.FEATURE_CLOUD_SYNC) return@withContext false

        try {
            val metadata = syncRepository.getRemoteClassMetadata(AppConfig.TARGET_CLASS_ID) ?: return@withContext false
            val localTimestamp = syncRepository.getLocalLastSyncTimestamp()

            // Save remote notice locally for immediate UI display
            syncRepository.saveCachedNotice(metadata.notice)

            // 1. Timestamp Delta Optimization (0 bandwidth wasted if up to date)
            if (!forceSync && metadata.lastUpdated > 0 && metadata.lastUpdated <= localTimestamp) {
                return@withContext true
            }

            // 2. Fetch remote books and chapters
            val remoteData = syncRepository.fetchRemoteBooksWithChapters(AppConfig.TARGET_CLASS_ID)
            if (remoteData.isEmpty()) return@withContext false

            val existingBooksMap = bookDao.getAllBooksDirect().associateBy { it.bookId }
            val activeBookIds = remoteData.map { it.first.bookId }

            val bookEntitiesToInsert = mutableListOf<BookEntity>()
            val chapterEntitiesToInsert = mutableListOf<ChapterEntity>()

            for ((remoteBook, remoteChapters) in remoteData) {
                val existing = existingBooksMap[remoteBook.bookId]

                // Cache eviction if pdfUrl has changed
                if (existing != null && existing.pdfUrl.isNotBlank() && existing.pdfUrl != remoteBook.pdfUrl) {
                    val oldFileName = existing.pdfUrl.substringAfterLast("/").substringBefore("?")
                    val oldFile = File(File(context.cacheDir, "pdfs"), oldFileName)
                    if (oldFile.exists()) oldFile.delete()
                }

                val curriculum = runCatching { Curriculum.valueOf(remoteBook.curriculum) }.getOrDefault(Curriculum.SCHOOL)
                val versions = remoteBook.availableVersions.mapNotNull {
                    runCatching { LanguageVersion.valueOf(it) }.getOrNull()
                }.ifEmpty { listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH) }

                // Preserve user's local reading progress and favorites
                val mergedBook = BookEntity(
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
                bookEntitiesToInsert.add(mergedBook)

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

                    chapterEntitiesToInsert.add(
                        ChapterEntity(
                            chapterId = remoteChap.chapterId,
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

            // 3. Atomic Database Upsert & Stale Data Pruning
            bookDao.insertBooks(bookEntitiesToInsert)
            chapterDao.insertChapters(chapterEntitiesToInsert)
            bookDao.deleteBooksNotIn(activeBookIds)
            chapterDao.deleteChaptersForBooksNotIn(activeBookIds)

            // 4. Update sync timestamp
            val syncTime = if (metadata.lastUpdated > 0) metadata.lastUpdated else System.currentTimeMillis()
            syncRepository.saveLocalLastSyncTimestamp(syncTime)

            true
        } catch (_: Exception) {
            false
        }
    }
}
