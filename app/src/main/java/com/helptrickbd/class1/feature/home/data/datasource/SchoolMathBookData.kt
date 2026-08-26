package com.helptrickbd.class1.feature.home.data.datasource

import com.helptrickbd.class1.feature.home.domain.model.*

object SchoolMathBookData {
    val book = Book(
        bookId = "school_math",
        title = "প্রাথমিক গণিত",
        subtitle = "সংখ্যার ধারণা, গণনা ও সহজ হিসাব • NCTB",
        curriculum = Curriculum.SCHOOL,
        pdfUrl = "math_class1_full.pdf",
        availableVersions = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
        progressPercent = 0.25f,
        totalChapters = 10,
        chapters = listOf(
            Chapter(
                chapterId = "sm_c1",
                unitNo = "অধ্যায় ১",
                title = "তুলনা করি (কম-বেশি, ছোট-বড়, হালকা-ভারী)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r1", "মূল বই পড়ুন", "math_u1_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r2", "সমাধান ও গাইড", "math_u1_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r3", "অধ্যায় টেস্ট", "math_u1_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c2",
                unitNo = "অধ্যায় ২",
                title = "গণনা ও সংখ্যা (১ থেকে ৫)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r4", "মূল বই পড়ুন", "math_u2_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r5", "সমাধান ও গাইড", "math_u2_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r6", "অধ্যায় টেস্ট", "math_u2_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c3",
                unitNo = "অধ্যায় ৩",
                title = "গণনা ও সংখ্যা (৬ থেকে ১০)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r7", "মূল বই পড়ুন", "math_u3_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r8", "সমাধান ও গাইড", "math_u3_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r9", "অধ্যায় টেস্ট", "math_u3_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c4",
                unitNo = "অধ্যায় ৪",
                title = "শূন্য (০) এর ধারণা ও ক্রমবাচক সংখ্যা",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r10", "মূল বই পড়ুন", "math_u4_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r11", "সমাধান ও গাইড", "math_u4_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r12", "অধ্যায় টেস্ট", "math_u4_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c5",
                unitNo = "অধ্যায় ৫",
                title = "যোগের ধারণা (১ থেকে ৯ পর্যন্ত)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r13", "মূল বই পড়ুন", "math_u5_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r14", "সমাধান ও গাইড", "math_u5_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r15", "অধ্যায় টেস্ট", "math_u5_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c6",
                unitNo = "অধ্যায় ৬",
                title = "বিয়োগের ধারণা (১ থেকে ৯ পর্যন্ত)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r16", "মূল বই পড়ুন", "math_u6_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r17", "সমাধান ও গাইড", "math_u6_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r18", "অধ্যায় টেস্ট", "math_u6_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c7",
                unitNo = "অধ্যায় ৭",
                title = "১১ থেকে ২০ পর্যন্ত সংখ্যা ও যোগ-বিয়োগ",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r19", "মূল বই পড়ুন", "math_u7_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r20", "সমাধান ও গাইড", "math_u7_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r21", "অধ্যায় টেস্ট", "math_u7_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c8",
                unitNo = "অধ্যায় ৮",
                title = "বিভিন্ন জ্যামিতিক আকৃতি (গোল, তিনকোনা, চারকোনা)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r22", "মূল বই পড়ুন", "math_u8_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r23", "সমাধান ও গাইড", "math_u8_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r24", "অধ্যায় টেস্ট", "math_u8_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c9",
                unitNo = "অধ্যায় ৯",
                title = "টাকা ও পয়সার পরিচিতি ও সহজ হিসাব",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r25", "মূল বই পড়ুন", "math_u9_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r26", "সমাধান ও গাইড", "math_u9_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r27", "অধ্যায় টেস্ট", "math_u9_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sm_c10",
                unitNo = "অধ্যায় ১০",
                title = "দিন, রাত, সময় ও সপ্তাহের ৭ দিন",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sm_r28", "মূল বই পড়ুন", "math_u10_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sm_r29", "সমাধান ও গাইড", "math_u10_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sm_r30", "অধ্যায় টেস্ট", "math_u10_test.pdf", ResourceType.MODEL_TEST)
                )
            )
        )
    )
}
