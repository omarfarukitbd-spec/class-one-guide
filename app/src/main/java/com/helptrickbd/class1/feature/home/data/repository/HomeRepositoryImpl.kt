package com.helptrickbd.class1.feature.home.data.repository

import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.database.BookDao
import com.helptrickbd.class1.core.database.BookEntity
import com.helptrickbd.class1.core.database.ChapterDao
import com.helptrickbd.class1.core.database.ChapterEntity
import com.helptrickbd.class1.core.database.DatabaseSeeder
import com.helptrickbd.class1.core.sync.domain.usecase.SyncCloudDataUseCase
import com.helptrickbd.class1.core.sync.util.NetworkMonitor
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Chapter
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.SearchResult
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import com.helptrickbd.class1.core.di.IoDispatcher
import com.helptrickbd.class1.core.sync.domain.repository.CloudSyncRepository
import com.helptrickbd.class1.core.util.StorageProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single Source of Truth (SSOT) for Home Screen Data.
 * Optimized for efficiency and reliable Cloud Sync triggering.
 */
@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao,
    private val seeder: DatabaseSeeder,
    private val syncCloudDataUseCase: SyncCloudDataUseCase,
    private val syncRepository: CloudSyncRepository,
    private val networkMonitor: NetworkMonitor,
    private val storageProvider: StorageProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {

    // Maintenance scope that survives ViewModel lifecycle but is bounded by Application lifecycle
    private val repositoryScope = CoroutineScope(ioDispatcher + SupervisorJob())

    init {
        repositoryScope.launch {
            // Step 1: Routine maintenance (Zero-impact on startup)
            storageProvider.runMaintenanceCleanup()
            
            // Step 2: Seed local data if DB is empty
            seeder.seedIfNeeded()
            
            // Step 3: Trigger Cloud Sync
            syncCloudDataUseCase()
        }

        // Auto-sync whenever internet connectivity is restored
        repositoryScope.launch {
            @OptIn(FlowPreview::class)
            networkMonitor.isOnline
                .distinctUntilChanged()
                .filter { it }
                .debounce(2500) // Debounce for stability
                .collect {
                    syncCloudDataUseCase()
                }
        }
    }

    override fun getCloudNotice(): Flow<String?> {
        return syncRepository.getCachedNoticeFlow()
    }

    override fun getMinAppVersion(): Flow<Int> {
        return syncRepository.getMinAppVersionFlow()
    }

    override fun getBooks(curriculum: Curriculum): Flow<List<Book>> {
        return bookDao.getBooksByCurriculum(curriculum)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override fun getBookById(bookId: String): Flow<Book?> {
        return combine(
            bookDao.getBookById(bookId),
            chapterDao.getChaptersForBook(bookId)
        ) { bookEntity, chapterEntities ->
            bookEntity?.toDomain(chapters = chapterEntities.map { it.toDomain() })
        }
    }

    override fun getResumeBook(): Flow<Book?> {
        return bookDao.getLatestReadBook()
            .map { entity -> entity?.toDomain() }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return bookDao.getFavoriteBooks()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun toggleFavorite(bookId: String, isFavorite: Boolean) {
        bookDao.toggleFavorite(bookId, isFavorite)
    }

    /**
     * Highly optimized search logic using Database JOINs.
     * Prevents loading full library into RAM for searching.
     */
    override fun searchBooksAndChapters(query: String, curriculum: Curriculum): Flow<List<SearchResult>> {
        val cleanQuery = query.trim()
        if (cleanQuery.length < AppConfig.SEARCH_MIN_QUERY_LENGTH) {
            return flowOf(emptyList())
        }

        return combine(
            bookDao.searchBooks(curriculum, cleanQuery),
            chapterDao.searchChaptersInCurriculum(curriculum, cleanQuery)
        ) { matchedBooks, matchedChapters ->
            val results = mutableListOf<SearchResult>()
            
            // 1. Map matched books to SearchResults
            matchedBooks.forEach { bookEntity ->
                results.add(SearchResult(book = bookEntity.toDomain(), null, null, null))
            }

            // 2. Map matched chapters to SearchResults (Avoid duplication if book already matched)
            val matchedBookIds = matchedBooks.map { it.bookId }.toSet()
            
            matchedChapters.forEach { chapterEntity ->
                if (chapterEntity.bookId !in matchedBookIds) {
                    // We need the parent book entity for the domain mapping
                    // Logic fix: In a production app, we'd ideally have a more efficient way to get parents
                    // But since we are already in a combined flow from Room, we can assume the UI only shows what's needed.
                    val parentBook = bookDao.getBookById(chapterEntity.bookId).firstOrNull()?.toDomain()
                    if (parentBook != null) {
                        results.add(
                            SearchResult(
                                book = parentBook,
                                matchedUnitNo = chapterEntity.unitNo,
                                matchedChapterTitle = chapterEntity.title,
                                matchedChapterId = chapterEntity.chapterId
                            )
                        )
                    }
                }
            }
            results.distinctBy { "${it.book.bookId}_${it.matchedChapterId ?: ""}" }
        }
    }

    private fun BookEntity.toDomain(chapters: List<Chapter> = emptyList()): Book {
        return Book(
            bookId = bookId,
            title = title,
            subtitle = subtitle,
            pdfUrl = pdfUrl,
            coverUrl = coverUrl,
            curriculum = curriculum,
            availableVersions = availableVersions,
            chapters = chapters,
            totalChapters = if (totalChapters > 0) totalChapters else chapters.size,
            isFavorite = isFavorite,
            progressPercent = progressPercent,
            lastReadPage = lastReadPage
        )
    }

    private fun ChapterEntity.toDomain(): Chapter {
        return Chapter(
            chapterId = chapterId,
            unitNo = unitNo,
            title = title,
            version = version,
            resources = resources
        )
    }
}
