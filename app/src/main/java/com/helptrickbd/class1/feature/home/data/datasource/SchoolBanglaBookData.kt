package com.helptrickbd.class1.feature.home.data.datasource

import com.helptrickbd.class1.feature.home.domain.model.*

object SchoolBanglaBookData {
    val book = Book(
        bookId = "school_bangla",
        title = "আমার বাংলা বই",
        subtitle = "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড (NCTB)",
        curriculum = Curriculum.SCHOOL,
        pdfUrl = "bangla_class1_full.pdf",
        availableVersions = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
        progressPercent = 0.65f,
        totalChapters = 10,
        chapters = listOf(
            Chapter(
                chapterId = "sb_c1",
                unitNo = "ইউনিট ১",
                title = "আমার পরিচয় ও বিদ্যালয়",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r1", "মূল বই পড়ুন", "bangla_u1_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r2", "গাইডবুক পড়ুন", "bangla_u1_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r3", "মডেল টেস্ট", "bangla_u1_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c2",
                unitNo = "ইউনিট ২",
                title = "বর্ণ শিখি: স্বরবর্ণ (অ থেকে ঔ)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r4", "মূল বই পড়ুন", "bangla_u2_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r5", "গাইডবুক পড়ুন", "bangla_u2_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r6", "মডেল টেস্ট", "bangla_u2_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c3",
                unitNo = "ইউনিট ৩",
                title = "বর্ণ শিখি: ব্যঞ্জনবর্ণ - ১ম ভাগ (ক থেকে ঞ)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r7", "মূল বই পড়ুন", "bangla_u3_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r8", "গাইডবুক পড়ুন", "bangla_u3_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r9", "মডেল টেস্ট", "bangla_u3_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c4",
                unitNo = "ইউনিট ৪",
                title = "বর্ণ শিখি: ব্যঞ্জনবর্ণ - ২য় ভাগ (ট থেকে ন)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r10", "মূল বই পড়ুন", "bangla_u4_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r11", "গাইডবুক পড়ুন", "bangla_u4_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r12", "মডেল টেস্ট", "bangla_u4_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c5",
                unitNo = "ইউনিট ৫",
                title = "বর্ণ শিখি: ব্যঞ্জনবর্ণ - ৩য় ভাগ (প থেকে ম)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r13", "মূল বই পড়ুন", "bangla_u5_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r14", "গাইডবুক পড়ুন", "bangla_u5_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r15", "মডেল টেস্ট", "bangla_u5_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c6",
                unitNo = "ইউনিট ৬",
                title = "বর্ণ শিখি: ব্যঞ্জনবর্ণ - ৪র্থ ভাগ (য থেকে ঁ)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r16", "মূল বই পড়ুন", "bangla_u6_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r17", "গাইডবুক পড়ুন", "bangla_u6_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r18", "মডেল টেস্ট", "bangla_u6_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c7",
                unitNo = "ইউনিট ৭",
                title = "কারচিহ্ন ও সহজ শব্দ গঠন (া থেকে ৌ)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r19", "মূল বই পড়ুন", "bangla_u7_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r20", "গাইডবুক পড়ুন", "bangla_u7_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r21", "মডেল টেস্ট", "bangla_u7_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c8",
                unitNo = "ইউনিট ৮",
                title = "মজার মজার ছড়া ও কবিতা আবৃত্তি",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r22", "মূল বই পড়ুন", "bangla_u8_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r23", "গাইডবুক পড়ুন", "bangla_u8_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r24", "মডেল টেস্ট", "bangla_u8_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c9",
                unitNo = "ইউনিট ৯",
                title = "শিক্ষণীয় গল্প ও নীতিশিক্ষা",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r25", "মূল বই পড়ুন", "bangla_u9_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r26", "গাইডবুক পড়ুন", "bangla_u9_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r27", "মডেল টেস্ট", "bangla_u9_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "sb_c10",
                unitNo = "ইউনিট ১০",
                title = "আমাদের প্রিয় বাংলাদেশ ও জাতীয় প্রতীক",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("sb_r28", "মূল বই পড়ুন", "bangla_u10_text.pdf", ResourceType.TEXTBOOK),
                    Resource("sb_r29", "গাইডবুক পড়ুন", "bangla_u10_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("sb_r30", "মডেল টেস্ট", "bangla_u10_test.pdf", ResourceType.MODEL_TEST)
                )
            )
        )
    )
}
