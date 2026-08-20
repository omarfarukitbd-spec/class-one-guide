package com.helptrickbd.class1.feature.home.data.datasource

import com.helptrickbd.class1.feature.home.domain.model.*

object MadrasahBooksData {
    val books = listOf(
        Book(
            bookId = "madr_quran",
            title = "কুরআন মাজীদ ও তাজবীদ",
            subtitle = "ইবতেদায়ী ১ম শ্রেণি",
            curriculum = Curriculum.MADRASAH,
            availableVersions = listOf(LanguageVersion.BANGLA),
            progressPercent = 0.70f,
            chapters = listOf(
                Chapter(
                    chapterId = "mq_c1",
                    unitNo = "অধ্যায় ১",
                    title = "আরবি হরফ ও মাখরাজ পরিচিতি",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("mr1", "মূল বই পড়ুন", "quran_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("mr2", "গাইডবুক পড়ুন", "quran_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("mr3", "মডেল টেস্ট", "quran_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                ),
                Chapter(
                    chapterId = "mq_c2",
                    unitNo = "অধ্যায় ২",
                    title = "হরকত, তানভীন ও জজম",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("mr4", "মূল বই পড়ুন", "quran_u2_text.pdf", ResourceType.TEXTBOOK),
                        Resource("mr5", "গাইডবুক পড়ুন", "quran_u2_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("mr6", "মডেল টেস্ট", "quran_u2_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        ),
        Book(
            bookId = "madr_aqaid",
            title = "আকাইদ ও ফিকহ",
            subtitle = "ইসলামিক বিশ্বাস ও প্রাথমিক বিধান",
            curriculum = Curriculum.MADRASAH,
            availableVersions = listOf(LanguageVersion.BANGLA),
            progressPercent = 0.50f,
            chapters = listOf(
                Chapter(
                    chapterId = "ma_c1",
                    unitNo = "অধ্যায় ১",
                    title = "ঈমান ও তাওহীদ",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("mr7", "মূল বই পড়ুন", "aqaid_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("mr8", "গাইডবুক পড়ুন", "aqaid_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("mr9", "মডেল টেস্ট", "aqaid_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                ),
                Chapter(
                    chapterId = "ma_c2",
                    unitNo = "অধ্যায় ২",
                    title = "অজু ও নামাজের প্রাথমিক নিয়ম",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("mr10", "মূল বই পড়ুন", "aqaid_u2_text.pdf", ResourceType.TEXTBOOK),
                        Resource("mr11", "গাইডবুক পড়ুন", "aqaid_u2_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("mr12", "মডেল টেস্ট", "aqaid_u2_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        ),
        Book(
            bookId = "madr_arabic",
            title = "আল-লুগাতুল আরাবিয়্যাহ (আরবি)",
            subtitle = "সহজ আরবি ভাষা শিক্ষা",
            curriculum = Curriculum.MADRASAH,
            availableVersions = listOf(LanguageVersion.BANGLA),
            progressPercent = 0.30f,
            chapters = listOf(
                Chapter(
                    chapterId = "mar_c1",
                    unitNo = "আদ-দারসুল আউয়াল",
                    title = "পরিচয় ও সম্ভাষণ",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("mr13", "মূল বই পড়ুন", "arabic_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("mr14", "গাইডবুক পড়ুন", "arabic_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("mr15", "মডেল টেস্ট", "arabic_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        ),
        Book(
            bookId = "madr_bangla",
            title = "আমার বাংলা বই (মাদ্রাসা)",
            subtitle = "ইবতেদায়ী ১ম শ্রেণি",
            curriculum = Curriculum.MADRASAH,
            availableVersions = listOf(LanguageVersion.BANGLA),
            progressPercent = 0.45f,
            chapters = listOf(
                Chapter(
                    chapterId = "mb_c1",
                    unitNo = "ইউনিট ১",
                    title = "বর্ণমালা ও ছড়া",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("mr16", "মূল বই পড়ুন", "madr_bangla_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("mr17", "গাইডবুক পড়ুন", "madr_bangla_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("mr18", "মডেল টেস্ট", "madr_bangla_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        ),
        Book(
            bookId = "madr_math",
            title = "প্রাথমিক গণিত (মাদ্রাসা)",
            subtitle = "ইবতেদায়ী ১ম শ্রেণি",
            curriculum = Curriculum.MADRASAH,
            availableVersions = listOf(LanguageVersion.BANGLA),
            progressPercent = 0.20f,
            chapters = listOf(
                Chapter(
                    chapterId = "mm_c1",
                    unitNo = "অধ্যায় ১",
                    title = "সংখ্যা গণনা ও হিসাব",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("mr19", "মূল বই পড়ুন", "madr_math_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("mr20", "গাইডবুক পড়ুন", "madr_math_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("mr21", "মডেল টেস্ট", "madr_math_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        )
    )
}
