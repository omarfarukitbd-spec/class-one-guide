package com.helptrickbd.class1.feature.home.data.datasource

import com.helptrickbd.class1.feature.home.domain.model.*

object SchoolBooksData {
    val books = listOf(
        SchoolBanglaBookData.book,
        SchoolEnglishBookData.book,
        SchoolMathBookData.book,
        Book(
            bookId = "school_art",
            title = "চারুপাঠ ও শিল্পকলা",
            subtitle = "সহজ ছবি আঁকা ও রঙের আনন্দ",
            curriculum = Curriculum.SCHOOL,
            pdfUrl = "art_class1_full.pdf",
            availableVersions = listOf(LanguageVersion.BANGLA),
            progressPercent = 0.10f,
            chapters = listOf(
                Chapter(
                    chapterId = "sa_c1",
                    unitNo = "অধ্যায় ১",
                    title = "রেখা ও রঙের খেলা",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("r25", "মূল বই পড়ুন", "art_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r26", "গাইডবুক পড়ুন", "art_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r27", "মডেল টেস্ট", "art_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        )
    )
}
