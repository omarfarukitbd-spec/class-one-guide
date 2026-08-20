package com.helptrickbd.class1.feature.subject_detail.data.repository

import com.helptrickbd.class1.core.database.BookDao
import com.helptrickbd.class1.core.database.BookEntity
import com.helptrickbd.class1.core.database.ChapterDao
import com.helptrickbd.class1.core.database.ChapterEntity
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Chapter
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
    private val chapterDao: ChapterDao
) : SubjectRepository {

    override fun getBookDetail(bookId: String): Flow<Book?> {
        return combine(
            bookDao.getBookById(bookId),
            chapterDao.getChaptersForBook(bookId)
        ) { bookEntity, chapterEntities ->
            bookEntity?.toDomain(chapterEntities.map { it.toDomain() })
        }
    }

    override suspend fun toggleFavorite(bookId: String, isFavorite: Boolean) {
        bookDao.toggleFavorite(bookId, isFavorite)
    }

    private fun BookEntity.toDomain(chapters: List<Chapter>): Book {
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
