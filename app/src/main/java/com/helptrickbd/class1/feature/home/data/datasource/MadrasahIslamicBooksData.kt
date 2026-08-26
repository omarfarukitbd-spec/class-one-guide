package com.helptrickbd.class1.feature.home.data.datasource

import com.helptrickbd.class1.feature.home.domain.model.*

object MadrasahIslamicBooksData {
    val quranBook = Book(
        bookId = "madr_quran",
        title = "কুরআন মাজীদ ও তাজভীদ",
        subtitle = "ইবতেদায়ী ১ম শ্রেণি • NCTB",
        curriculum = Curriculum.MADRASAH,
        pdfUrl = "quran_class1_full.pdf",
        availableVersions = listOf(LanguageVersion.BANGLA),
        progressPercent = 0.70f,
        totalChapters = 5,
        chapters = listOf(
            Chapter(
                chapterId = "mq_c1",
                unitNo = "অধ্যায় ১",
                title = "আরবি হরফ ও মাখরাজ পরিচিতি (২৯টি হরফ)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("mq_r1", "মূল বই পড়ুন", "quran_u1_text.pdf", ResourceType.TEXTBOOK),
                    Resource("mq_r2", "সহজ তাজভীদ গাইড", "quran_u1_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("mq_r3", "হরফ টেস্ট", "quran_u1_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "mq_c2",
                unitNo = "অধ্যায় ২",
                title = "হরকত ও তানভীন শিক্ষা (যবর, যের, পেশ)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("mq_r4", "মূল বই পড়ুন", "quran_u2_text.pdf", ResourceType.TEXTBOOK),
                    Resource("mq_r5", "সহজ তাজভীদ গাইড", "quran_u2_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("mq_r6", "অধ্যায় টেস্ট", "quran_u2_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "mq_c3",
                unitNo = "অধ্যায় ৩",
                title = "জজম (সাকিন) ও তাশদীদ এর নিয়ম",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("mq_r7", "মূল বই পড়ুন", "quran_u3_text.pdf", ResourceType.TEXTBOOK),
                    Resource("mq_r8", "সহজ তাজভীদ গাইড", "quran_u3_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("mq_r9", "অধ্যায় টেস্ট", "quran_u3_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "mq_c4",
                unitNo = "অধ্যায় ৪",
                title = "মাদের হরফ ও গুন্নাহর প্রাথমিক শিক্ষা",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("mq_r10", "মূল বই পড়ুন", "quran_u4_text.pdf", ResourceType.TEXTBOOK),
                    Resource("mq_r11", "সহজ তাজভীদ গাইড", "quran_u4_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("mq_r12", "অধ্যায় টেস্ট", "quran_u4_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "mq_c5",
                unitNo = "অধ্যায় ৫",
                title = "ছোট সূরাসমূহ (সূরা ফাতিহা, ইখলাস, নাস ও ফালাক)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("mq_r13", "মূল বই পড়ুন", "quran_u5_text.pdf", ResourceType.TEXTBOOK),
                    Resource("mq_r14", "সহজ তাজভীদ গাইড", "quran_u5_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("mq_r15", "সূরা হিফজ টেস্ট", "quran_u5_test.pdf", ResourceType.MODEL_TEST)
                )
            )
        )
    )

    val aqaidBook = Book(
        bookId = "madr_aqaid",
        title = "আকাইদ ও ফিকহ",
        subtitle = "ইসলামি বিশ্বাস ও প্রাথমিক বিধান • NCTB",
        curriculum = Curriculum.MADRASAH,
        pdfUrl = "aqaid_class1_full.pdf",
        availableVersions = listOf(LanguageVersion.BANGLA),
        progressPercent = 0.50f,
        totalChapters = 4,
        chapters = listOf(
            Chapter(
                chapterId = "ma_c1",
                unitNo = "অধ্যায় ১",
                title = "ঈমান ও তাওহীদ (আল্লাহর পরিচয় ও কালিমাহ)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("ma_r1", "মূল বই পড়ুন", "aqaid_u1_text.pdf", ResourceType.TEXTBOOK),
                    Resource("ma_r2", "প্রশ্নোত্তর গাইড", "aqaid_u1_guide.pdf", ResourceType.GUIDEBOOK)
                )
            ),
            Chapter(
                chapterId = "ma_c2",
                unitNo = "অধ্যায় ২",
                title = "পবিত্রতা ও ওযূর সহজ নিয়ম",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("ma_r3", "মূল বই পড়ুন", "aqaid_u2_text.pdf", ResourceType.TEXTBOOK),
                    Resource("ma_r4", "প্রশ্নোত্তর গাইড", "aqaid_u2_guide.pdf", ResourceType.GUIDEBOOK)
                )
            ),
            Chapter(
                chapterId = "ma_c3",
                unitNo = "অধ্যায় ৩",
                title = "পাঁচ ওয়াক্ত সালাত ও দোয়াসমূহ",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("ma_r5", "মূল বই পড়ুন", "aqaid_u3_text.pdf", ResourceType.TEXTBOOK),
                    Resource("ma_r6", "প্রশ্নোত্তর গাইড", "aqaid_u3_guide.pdf", ResourceType.GUIDEBOOK)
                )
            ),
            Chapter(
                chapterId = "ma_c4",
                unitNo = "অধ্যায় ৪",
                title = "দৈনন্দিন ইসলামি আদব ও শিষ্টাচার",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("ma_r7", "মূল বই পড়ুন", "aqaid_u4_text.pdf", ResourceType.TEXTBOOK),
                    Resource("ma_r8", "প্রশ্নোত্তর গাইড", "aqaid_u4_guide.pdf", ResourceType.GUIDEBOOK)
                )
            )
        )
    )

    val arabicBook = Book(
        bookId = "madr_arabic",
        title = "আদ্ দুরূসুল আরাবিয়্যাহ্",
        subtitle = "সহজ আরবি ভাষা শিক্ষা • NCTB",
        curriculum = Curriculum.MADRASAH,
        pdfUrl = "arabic_class1_full.pdf",
        availableVersions = listOf(LanguageVersion.BANGLA),
        progressPercent = 0.30f,
        totalChapters = 3,
        chapters = listOf(
            Chapter(
                chapterId = "mar_c1",
                unitNo = "আদ-দারসুল আউয়াল",
                title = "আত্মপরিচয় ও সম্ভাষণ (মারহাবান, কাইফা হালুক)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("mar_r1", "মূল বই পড়ুন", "arabic_u1_text.pdf", ResourceType.TEXTBOOK),
                    Resource("mar_r2", "আরবি ব্যাকরণ ও শব্দার্থ", "arabic_u1_guide.pdf", ResourceType.GUIDEBOOK)
                )
            ),
            Chapter(
                chapterId = "mar_c2",
                unitNo = "আদ-দারসুস সানী",
                title = "বিদ্যালয় ও সহপাঠী (হাযা কিতাবুন, হাযা ক্বলমুন)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("mar_r3", "মূল বই পড়ুন", "arabic_u2_text.pdf", ResourceType.TEXTBOOK),
                    Resource("mar_r4", "আরবি ব্যাকরণ ও শব্দার্থ", "arabic_u2_guide.pdf", ResourceType.GUIDEBOOK)
                )
            ),
            Chapter(
                chapterId = "mar_c3",
                unitNo = "আদ-দারসুস সালিস",
                title = "ফলমূল ও পশুপাখির নাম (তুফফাহুন, আসাদুন)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("mar_r5", "মূল বই পড়ুন", "arabic_u3_text.pdf", ResourceType.TEXTBOOK),
                    Resource("mar_r6", "আরবি ব্যাকরণ ও শব্দার্থ", "arabic_u3_guide.pdf", ResourceType.GUIDEBOOK)
                )
            )
        )
    )
}
