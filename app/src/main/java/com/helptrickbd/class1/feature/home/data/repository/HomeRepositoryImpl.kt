package com.helptrickbd.class1.feature.home.data.repository

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import com.helptrickbd.class1.core.di.IoDispatcher
import com.helptrickbd.class1.core.sync.domain.repository.CloudSyncRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao,
    private val seeder: DatabaseSeeder,
    private val syncCloudDataUseCase: SyncCloudDataUseCase,
    private val syncRepository: CloudSyncRepository,
    private val networkMonitor: NetworkMonitor,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : HomeRepository {

    init {
        val scope = CoroutineScope(ioDispatcher)
        scope.launch {
            seeder.seedIfNeeded()
            syncCloudDataUseCase()
        }

        // Auto-sync whenever internet connectivity is restored
        scope.launch {
            networkMonitor.isOnline.collect { isOnline ->
                if (isOnline) {
                    syncCloudDataUseCase()
                }
            }
        }
    }

    override fun getCloudNotice(): Flow<String?> {
        return syncRepository.getCachedNoticeFlow()
    }

    override fun getBooks(curriculum: Curriculum): Flow<List<Book>> {
        return bookDao.getBooksByCurriculum(curriculum)
            .onStart { seeder.seedIfNeeded() }
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

    override fun searchBooksAndChapters(query: String, curriculum: Curriculum): Flow<List<SearchResult>> {
        val cleanQuery = query.trim()
        val booksFlow = if (cleanQuery.isEmpty()) {
            bookDao.getBooksByCurriculum(curriculum)
        } else {
            bookDao.getAllBooksFlow()
        }

        return combine(
            booksFlow,
            chapterDao.getAllChaptersFlow()
        ) { books, allChapters ->
            if (cleanQuery.isEmpty()) {
                return@combine books.map { SearchResult(book = it.toDomain()) }
            }

            val chaptersByBook = allChapters.groupBy { it.bookId }
            val results = mutableListOf<SearchResult>()

            for (bookEntity in books) {
                val bookChapters = chaptersByBook[bookEntity.bookId] ?: emptyList()
                val domainBook = bookEntity.toDomain(bookChapters.map { it.toDomain() })

                val isBookMatch = bookEntity.title.contains(cleanQuery, ignoreCase = true) ||
                        bookEntity.subtitle?.contains(cleanQuery, ignoreCase = true) == true

                val matchedChapter = bookChapters.firstOrNull { ch ->
                    ch.title.contains(cleanQuery, ignoreCase = true) ||
                            ch.unitNo.contains(cleanQuery, ignoreCase = true) ||
                            ch.resources.any { it.title.contains(cleanQuery, ignoreCase = true) }
                }

                if (isBookMatch || matchedChapter != null) {
                    results.add(
                        SearchResult(
                            book = domainBook,
                            matchedUnitNo = matchedChapter?.unitNo,
                            matchedChapterTitle = matchedChapter?.title,
                            matchedChapterId = matchedChapter?.chapterId
                        )
                    )
                }
            }
            results
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
