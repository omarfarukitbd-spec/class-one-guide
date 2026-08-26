package com.helptrickbd.class1.feature.home.data.datasource

import com.helptrickbd.class1.feature.home.domain.model.*

object SchoolEnglishBookData {
    val book = Book(
        bookId = "school_english",
        title = "English for Today",
        subtitle = "Primary Curriculum Class 1 • NCTB",
        curriculum = Curriculum.SCHOOL,
        pdfUrl = "english_class1_full.pdf",
        availableVersions = listOf(LanguageVersion.BANGLA, LanguageVersion.ENGLISH),
        progressPercent = 0.40f,
        totalChapters = 10,
        chapters = listOf(
            Chapter(
                chapterId = "se_c1",
                unitNo = "Unit 1",
                title = "Greetings & Farewells (Hello, Hi, Goodbye)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r1", "Read Textbook", "eng_u1_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r2", "Guide & Translation", "eng_u1_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r3", "Unit Model Test", "eng_u1_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c2",
                unitNo = "Unit 2",
                title = "Alphabet & Phonics (Letters A to F)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r4", "Read Textbook", "eng_u2_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r5", "Guide & Translation", "eng_u2_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r6", "Unit Model Test", "eng_u2_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c3",
                unitNo = "Unit 3",
                title = "Numbers & Counting (Numbers 1 to 5)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r7", "Read Textbook", "eng_u3_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r8", "Guide & Translation", "eng_u3_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r9", "Unit Model Test", "eng_u3_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c4",
                unitNo = "Unit 4",
                title = "Alphabet & Words (Letters G to L)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r10", "Read Textbook", "eng_u4_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r11", "Guide & Translation", "eng_u4_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r12", "Unit Model Test", "eng_u4_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c5",
                unitNo = "Unit 5",
                title = "Classroom Commands & Action Verbs",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r13", "Read Textbook", "eng_u5_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r14", "Guide & Translation", "eng_u5_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r15", "Unit Model Test", "eng_u5_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c6",
                unitNo = "Unit 6",
                title = "Alphabet & Words (Letters M to R)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r16", "Read Textbook", "eng_u6_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r17", "Guide & Translation", "eng_u6_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r18", "Unit Model Test", "eng_u6_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c7",
                unitNo = "Unit 7",
                title = "Numbers & Quantities (Numbers 6 to 10)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r19", "Read Textbook", "eng_u7_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r20", "Guide & Translation", "eng_u7_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r21", "Unit Model Test", "eng_u7_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c8",
                unitNo = "Unit 8",
                title = "Alphabet & Words (Letters S to Z)",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r22", "Read Textbook", "eng_u8_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r23", "Guide & Translation", "eng_u8_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r24", "Unit Model Test", "eng_u8_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c9",
                unitNo = "Unit 9",
                title = "Colours, Shapes & Nature",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r25", "Read Textbook", "eng_u9_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r26", "Guide & Translation", "eng_u9_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r27", "Unit Model Test", "eng_u9_test.pdf", ResourceType.MODEL_TEST)
                )
            ),
            Chapter(
                chapterId = "se_c10",
                unitNo = "Unit 10",
                title = "Rhymes, Stories & Review Activities",
                version = LanguageVersion.BANGLA,
                resources = listOf(
                    Resource("se_r28", "Read Textbook", "eng_u10_text.pdf", ResourceType.TEXTBOOK),
                    Resource("se_r29", "Guide & Translation", "eng_u10_guide.pdf", ResourceType.GUIDEBOOK),
                    Resource("se_r30", "Unit Model Test", "eng_u10_test.pdf", ResourceType.MODEL_TEST)
                )
            )
        )
    )
}
