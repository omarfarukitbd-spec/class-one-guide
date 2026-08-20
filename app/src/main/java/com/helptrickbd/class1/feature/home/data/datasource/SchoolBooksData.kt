package com.helptrickbd.class1.feature.home.data.datasource

import com.helptrickbd.class1.feature.home.domain.model.*

object SchoolBooksData {
    val books = listOf(
        Book(
            bookId = "school_bangla",
            title = "আমার বাংলা বই",
            subtitle = "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড",
            curriculum = Curriculum.SCHOOL,
            availableVersions = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
            progressPercent = 0.65f,
            chapters = listOf(
                Chapter(
                    chapterId = "sb_c1",
                    unitNo = "ইউনিট ১",
                    title = "আমাদের দেশ ও বর্ণমালা",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("r1", "মূল বই পড়ুন", "bangla_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r2", "গাইডবুক পড়ুন", "bangla_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r3", "মডেল টেস্ট", "bangla_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                ),
                Chapter(
                    chapterId = "sb_c2",
                    unitNo = "ইউনিট ২",
                    title = "ছড়া ও কবিতা আবৃত্তি",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("r4", "মূল বই পড়ুন", "bangla_u2_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r5", "গাইডবুক পড়ুন", "bangla_u2_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r6", "মডেল টেস্ট", "bangla_u2_test.pdf", ResourceType.MODEL_TEST)
                    )
                ),
                Chapter(
                    chapterId = "sb_c3",
                    unitNo = "ইউনিট ৩",
                    title = "শব্দ গঠন ও বাক্য তৈরি",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("r7", "মূল বই পড়ুন", "bangla_u3_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r8", "গাইডবুক পড়ুন", "bangla_u3_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r9", "মডেল টেস্ট", "bangla_u3_test.pdf", ResourceType.MODEL_TEST)
                    )
                ),
                Chapter(
                    chapterId = "sb_c1_en",
                    unitNo = "Unit 1",
                    title = "Our Country & Alphabet (English Version)",
                    version = LanguageVersion.ENGLISH,
                    resources = listOf(
                        Resource("r10", "Read Textbook", "bangla_u1_en_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r11", "Read Guidebook", "bangla_u1_en_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r12", "Model Test", "bangla_u1_en_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        ),
        Book(
            bookId = "school_english",
            title = "English for Today",
            subtitle = "Primary Curriculum Class 1",
            curriculum = Curriculum.SCHOOL,
            availableVersions = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
            progressPercent = 0.40f,
            chapters = listOf(
                Chapter(
                    chapterId = "se_c1",
                    unitNo = "Unit 1",
                    title = "Greetings, Farewells & Alphabet",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("r13", "মূল বই পড়ুন", "eng_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r14", "গাইডবুক পড়ুন", "eng_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r15", "মডেল টেস্ট", "eng_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                ),
                Chapter(
                    chapterId = "se_c2",
                    unitNo = "Unit 2",
                    title = "Numbers, Rhymes & Sounds",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("r16", "মূল বই পড়ুন", "eng_u2_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r17", "গাইডবুক পড়ুন", "eng_u2_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r18", "মডেল টেস্ট", "eng_u2_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        ),
        Book(
            bookId = "school_math",
            title = "প্রাথমিক গণিত",
            subtitle = "সংখ্যার ধারণা, গণনা ও সহজ হিসাব",
            curriculum = Curriculum.SCHOOL,
            availableVersions = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
            progressPercent = 0.25f,
            chapters = listOf(
                Chapter(
                    chapterId = "sm_c1",
                    unitNo = "অধ্যায় ১",
                    title = "তুলনা ও গণনা (১ থেকে ১০)",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("r19", "মূল বই পড়ুন", "math_u1_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r20", "গাইডবুক পড়ুন", "math_u1_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r21", "মডেল টেস্ট", "math_u1_test.pdf", ResourceType.MODEL_TEST)
                    )
                ),
                Chapter(
                    chapterId = "sm_c2",
                    unitNo = "অধ্যায় ২",
                    title = "যোগের ধারণা ও অনুশীলন",
                    version = LanguageVersion.BANGLA,
                    resources = listOf(
                        Resource("r22", "মূল বই পড়ুন", "math_u2_text.pdf", ResourceType.TEXTBOOK),
                        Resource("r23", "গাইডবুক পড়ুন", "math_u2_guide.pdf", ResourceType.GUIDEBOOK),
                        Resource("r24", "মডেল টেস্ট", "math_u2_test.pdf", ResourceType.MODEL_TEST)
                    )
                )
            )
        ),
        Book(
            bookId = "school_art",
            title = "চারুপাঠ ও শিল্পকলা",
            subtitle = "সহজ ছবি আঁকা ও রঙের আনন্দ",
            curriculum = Curriculum.SCHOOL,
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
