package com.helptrickbd.class1.feature.subject_detail.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import kotlinx.coroutines.delay

class SubjectRepositoryImpl(
    private val firestore: FirebaseFirestore? = null
) : SubjectRepository {

    override suspend fun getBooks(subjectId: String): Result<List<Book>> {
        delay(400) // Simulating network response
        return Result.success(
            listOf(
                Book(
                    bookId = "book_1",
                    bookName = "Main Textbook (NCTB Edition)",
                    bookType = "Textbook",
                    pdfUrl = "https://example.com/books/textbook.pdf"
                ),
                Book(
                    bookId = "book_2",
                    bookName = "Complete Solution & Guide Book",
                    bookType = "Guide",
                    pdfUrl = "https://example.com/books/guide.pdf"
                ),
                Book(
                    bookId = "book_3",
                    bookName = "Model Question & Practice Paper",
                    bookType = "Practice",
                    pdfUrl = "https://example.com/books/practice.pdf"
                )
            )
        )
    }
}
