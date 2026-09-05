package com.helptrickbd.class1.feature.home.data.datasource

import com.helptrickbd.class1.feature.home.domain.model.*

object MadrasahBooksData {
    val books = listOf(
        MadrasahIslamicBooksData.quranBook,
        MadrasahIslamicBooksData.aqaidBook,
        MadrasahIslamicBooksData.arabicBook,
        SchoolBanglaBookData.book.copy(
            bookId = "madr_bangla",
            title = "আমার বাংলা বই (ইবতেদায়ী)",
            subtitle = "ইবতেদায়ী ১ম শ্রেণি • NCTB",
            curriculum = Curriculum.MADRASAH,
            pdfUrl = "madr_bangla_full.pdf",
            progressPercent = 0.45f,
            chapters = SchoolBanglaBookData.book.chapters.map { ch ->
                ch.copy(
                    chapterId = "mb_${ch.chapterId}",
                    resources = ch.resources.map { r -> r.copy(resourceId = "mb_${r.resourceId}") }
                )
            }
        ),
        SchoolEnglishBookData.book.copy(
            bookId = "madr_english",
            title = "English for Today (ইবতেদায়ী)",
            subtitle = "Primary Curriculum Class 1 • NCTB",
            curriculum = Curriculum.MADRASAH,
            pdfUrl = "madr_english_full.pdf",
            progressPercent = 0.35f,
            chapters = SchoolEnglishBookData.book.chapters.map { ch ->
                ch.copy(
                    chapterId = "me_${ch.chapterId}",
                    resources = ch.resources.map { r -> r.copy(resourceId = "me_${r.resourceId}") }
                )
            }
        ),
        SchoolMathBookData.book.copy(
            bookId = "madr_math",
            title = "প্রাথমিক গণিত (ইবতেদায়ী)",
            subtitle = "ইবতেদায়ী ১ম শ্রেণি • NCTB",
            curriculum = Curriculum.MADRASAH,
            pdfUrl = "madr_math_full.pdf",
            progressPercent = 0.20f,
            chapters = SchoolMathBookData.book.chapters.map { ch ->
                ch.copy(
                    chapterId = "mm_${ch.chapterId}",
                    resources = ch.resources.map { r -> r.copy(resourceId = "mm_${r.resourceId}") }
                )
            }
        )
    )
}
