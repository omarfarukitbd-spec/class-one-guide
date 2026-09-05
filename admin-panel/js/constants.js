// =============================================================
// NCTB COMMAND HUB - CONSTANTS & INITIAL DATA
// =============================================================

const SVG_ICONS = {
    check: `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/></svg>`,
    edit: `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/></svg>`,
    delete: `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"/></svg>`,
    add: `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/></svg>`,
    link: `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M3.9 12c0-1.71 1.39-3.1 3.1-3.1h4V7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h4v-1.9H7c-1.71 0-3.1-1.39-3.1-3.1zM8 13h8v-2H8v2zm9-6h-4v1.9h4c1.71 0 3.1 1.39 3.1 3.1s-1.39 3.1-3.1 3.1h-4V17h4c2.76 0 5-2.24 5-5s-2.24-5-5-5z"/></svg>`,
    up: `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M7.41 15.41L12 10.83l4.59 4.58L18 14l-6-6-6 6z"/></svg>`,
    down: `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M7.41 8.59L12 13.17l4.59-4.58L18 10l-6 6-6-6z"/></svg>`
};


const INITIAL_CLASS_1_BOOKS = [
    {
        bookId: "school_bangla",
        title: "আমার বাংলা বই",
        subtitle: "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড (NCTB)",
        curriculum: "SCHOOL",
        pdfUrl: "bangla_class1_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            { chapterId: "sb_c1", unitNo: "পাঠ ১", title: "আমার পরিচয়", version: "BANGLA", resources: [{ resourceId: "r1", title: "মূল বই পড়ুন", pdfUrl: "bangla_u1_text.pdf", type: "TEXTBOOK" }, { resourceId: "r2", title: "গাইডবুক পড়ুন", pdfUrl: "bangla_u1_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "sb_c2", unitNo: "পাঠ ২", title: "আমি ও আমার বিদ্যালয়", version: "BANGLA", resources: [{ resourceId: "r4", title: "মূল বই পড়ুন", pdfUrl: "bangla_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c3", unitNo: "পাঠ ৩", title: "আমরা কী কী কাজ করি", version: "BANGLA", resources: [{ resourceId: "r6", title: "মূল বই পড়ুন", pdfUrl: "bangla_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c4", unitNo: "পাঠ ৪", title: "ছড়া - আতা গাছে তোতা পাখি", version: "BANGLA", resources: [{ resourceId: "r8", title: "মূল বই পড়ুন", pdfUrl: "bangla_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c5", unitNo: "পাঠ ৫", title: "গল্প - কাক ও কলসি", version: "BANGLA", resources: [{ resourceId: "r10", title: "মূল বই পড়ুন", pdfUrl: "bangla_u5_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c6", unitNo: "পাঠ ৬", title: "বর্ণ শিখি (অ - ঔ)", version: "BANGLA", resources: [{ resourceId: "r12", title: "মূল বই পড়ুন", pdfUrl: "bangla_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c7", unitNo: "পাঠ ৭", title: "বর্ণ শিখি (ক - ঞ)", version: "BANGLA", resources: [{ resourceId: "r13", title: "মূল বই পড়ুন", pdfUrl: "bangla_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c8", unitNo: "পাঠ ৮", title: "বর্ণ শিখি (ট - ন)", version: "BANGLA", resources: [{ resourceId: "r14", title: "মূল বই পড়ুন", pdfUrl: "bangla_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c9", unitNo: "পাঠ ৯", title: "বর্ণ শিখি (প - ম)", version: "BANGLA", resources: [{ resourceId: "r15", title: "মূল বই পড়ুন", pdfUrl: "bangla_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c10", unitNo: "পাঠ ১০", title: "ছড়া - বাক বাকুম পায়রা", version: "BANGLA", resources: [{ resourceId: "r16", title: "মূল বই পড়ুন", pdfUrl: "bangla_u10_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "school_english",
        title: "English for Today",
        subtitle: "Primary Curriculum Class 1 • NCTB",
        curriculum: "SCHOOL",
        pdfUrl: "english_class1_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            { chapterId: "se_c1", unitNo: "Unit 1", title: "Greetings", version: "BANGLA", resources: [{ resourceId: "se_r1", title: "Read Textbook", pdfUrl: "eng_u1_text.pdf", type: "TEXTBOOK" }, { resourceId: "se_r2", title: "Guide & Translation", pdfUrl: "eng_u1_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "se_c2", unitNo: "Unit 2", title: "Introductions and Alphabet", version: "BANGLA", resources: [{ resourceId: "se_r3", title: "Read Textbook", pdfUrl: "eng_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c3", unitNo: "Unit 3", title: "Saying Goodbye", version: "BANGLA", resources: [{ resourceId: "se_r4", title: "Read Textbook", pdfUrl: "eng_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c4", unitNo: "Unit 4", title: "Numbers (1-3)", version: "BANGLA", resources: [{ resourceId: "se_r5", title: "Read Textbook", pdfUrl: "eng_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c5", unitNo: "Unit 5", title: "Rhyme (Two Little Black Birds)", version: "BANGLA", resources: [{ resourceId: "se_r6", title: "Read Textbook", pdfUrl: "eng_u5_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c6", unitNo: "Unit 6", title: "Alphabet (A to E)", version: "BANGLA", resources: [{ resourceId: "se_r7", title: "Read Textbook", pdfUrl: "eng_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c7", unitNo: "Unit 7", title: "Alphabet (F to J)", version: "BANGLA", resources: [{ resourceId: "se_r8", title: "Read Textbook", pdfUrl: "eng_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c8", unitNo: "Unit 8", title: "Alphabet (K to O)", version: "BANGLA", resources: [{ resourceId: "se_r9", title: "Read Textbook", pdfUrl: "eng_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c9", unitNo: "Unit 9", title: "Alphabet (P to T)", version: "BANGLA", resources: [{ resourceId: "se_r10", title: "Read Textbook", pdfUrl: "eng_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c10", unitNo: "Unit 10", title: "Alphabet (U to Z)", version: "BANGLA", resources: [{ resourceId: "se_r11", title: "Read Textbook", pdfUrl: "eng_u10_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "school_math",
        title: "প্রাথমিক গণিত",
        subtitle: "সংখ্যার ধারণা, গণনা ও সহজ হিসাব • NCTB",
        curriculum: "SCHOOL",
        pdfUrl: "math_class1_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            { chapterId: "sm_c1", unitNo: "অধ্যায় ১", title: "তুলনা করি (কম-বেশি, ছোট-বড়)", version: "BANGLA", resources: [{ resourceId: "sm_r1", title: "মূল বই পড়ুন", pdfUrl: "math_u1_text.pdf", type: "TEXTBOOK" }, { resourceId: "sm_r2", title: "সমাধান ও গাইড", pdfUrl: "math_u1_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "sm_c2", unitNo: "অধ্যায় ২", title: "গণনা (১ থেকে ৫)", version: "BANGLA", resources: [{ resourceId: "sm_r3", title: "মূল বই পড়ুন", pdfUrl: "math_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c3", unitNo: "অধ্যায় ৩", title: "সংখ্যা (১ থেকে ১০)", version: "BANGLA", resources: [{ resourceId: "sm_r4", title: "মূল বই পড়ুন", pdfUrl: "math_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c4", unitNo: "অধ্যায় ৪", title: "শূন্য (০) এর ধারণা", version: "BANGLA", resources: [{ resourceId: "sm_r5", title: "মূল বই পড়ুন", pdfUrl: "math_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c5", unitNo: "অধ্যায় ৫", title: "যোগের ধারণা", version: "BANGLA", resources: [{ resourceId: "sm_r6", title: "মূল বই পড়ুন", pdfUrl: "math_u5_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c6", unitNo: "অধ্যায় ৬", title: "বিয়োগের ধারণা", version: "BANGLA", resources: [{ resourceId: "sm_r7", title: "মূল বই পড়ুন", pdfUrl: "math_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c7", unitNo: "অধ্যায় ৭", title: "সংখ্যা (১১ থেকে ২০)", version: "BANGLA", resources: [{ resourceId: "sm_r8", title: "মূল বই পড়ুন", pdfUrl: "math_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c8", unitNo: "অধ্যায় ৮", title: "জ্যামিতিক আকৃতি", version: "BANGLA", resources: [{ resourceId: "sm_r9", title: "মূল বই পড়ুন", pdfUrl: "math_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c9", unitNo: "অধ্যায় ৯", title: "প্যাটার্ন ও পরিমাপ", version: "BANGLA", resources: [{ resourceId: "sm_r10", title: "মূল বই পড়ুন", pdfUrl: "math_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c10", unitNo: "অধ্যায় ১০", title: "বাংলাদেশের মুদ্রা ও নোট", version: "BANGLA", resources: [{ resourceId: "sm_r11", title: "মূল বই পড়ুন", pdfUrl: "math_u10_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "school_art",
        title: "চারুপাঠ ও শিল্পকলা",
        subtitle: "সহজ ছবি আঁকা ও রঙের আনন্দ • NCTB",
        curriculum: "SCHOOL",
        pdfUrl: "art_class1_full.pdf",
        availableVersions: ["BANGLA"],
        chapters: [
            { chapterId: "sa_c1", unitNo: "অধ্যায় ১", title: "রেখা ও রঙের খেলা", version: "BANGLA", resources: [{ resourceId: "r25", title: "মূল বই পড়ুন", pdfUrl: "art_u1_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "madr_quran",
        title: "কুরআন মাজীদ ও তাজভীদ",
        subtitle: "ইবতেদায়ী ১ম শ্রেণি • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "quran_class1_full.pdf",
        availableVersions: ["BANGLA"],
        chapters: [
            { chapterId: "mq_c1", unitNo: "অধ্যায় ১", title: "আরবি হরফ ও মাখরাজ পরিচিতি (২৯টি হরফ)", version: "BANGLA", resources: [{ resourceId: "mq_r1", title: "মূল বই পড়ুন", pdfUrl: "quran_u1_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mq_c2", unitNo: "অধ্যায় ২", title: "হরকত ও তানভীন শিক্ষা (যবর, যের, পেশ)", version: "BANGLA", resources: [{ resourceId: "mq_r2", title: "মূল বই পড়ুন", pdfUrl: "quran_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mq_c3", unitNo: "অধ্যায় ৩", title: "জজম (সাকিন) ও তাশদীদ এর নিয়ম", version: "BANGLA", resources: [{ resourceId: "mq_r3", title: "মূল বই পড়ুন", pdfUrl: "quran_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mq_c4", unitNo: "অধ্যায় ৪", title: "মাদের হরফ ও গুন্নাহর প্রাথমিক শিক্ষা", version: "BANGLA", resources: [{ resourceId: "mq_r4", title: "মূল বই পড়ুন", pdfUrl: "quran_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mq_c5", unitNo: "অধ্যায় ৫", title: "ছোট সূরাসমূহ (ফাতিহা, ইখলাস, নাস ও ফালাক)", version: "BANGLA", resources: [{ resourceId: "mq_r5", title: "মূল বই পড়ুন", pdfUrl: "quran_u5_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "madr_aqaid",
        title: "আকাইদ ও ফিকহ",
        subtitle: "ইসলামি বিশ্বাস ও প্রাথমিক বিধান • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "aqaid_class1_full.pdf",
        availableVersions: ["BANGLA"],
        chapters: [
            { chapterId: "ma_c1", unitNo: "অধ্যায় ১", title: "ঈমান ও তাওহীদ (আল্লাহর পরিচয় ও কালিমাহ)", version: "BANGLA", resources: [{ resourceId: "ma_r1", title: "মূল বই পড়ুন", pdfUrl: "aqaid_u1_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "ma_c2", unitNo: "অধ্যায় ২", title: "পবিত্রতা ও ওযূর সহজ নিয়ম", version: "BANGLA", resources: [{ resourceId: "ma_r2", title: "মূল বই পড়ুন", pdfUrl: "aqaid_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "ma_c3", unitNo: "অধ্যায় ৩", title: "পাঁচ ওয়াক্ত সালাত ও দোয়াসমূহ", version: "BANGLA", resources: [{ resourceId: "ma_r3", title: "মূল বই পড়ুন", pdfUrl: "aqaid_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "ma_c4", unitNo: "অধ্যায় ৪", title: "দৈনন্দিন ইসলামি আদব ও শিষ্টাচার", version: "BANGLA", resources: [{ resourceId: "ma_r4", title: "মূল বই পড়ুন", pdfUrl: "aqaid_u4_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "madr_arabic",
        title: "আদ্ দুরূসুল আরাবিয়্যাহ্",
        subtitle: "সহজ আরবি ভাষা শিক্ষা • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "arabic_class1_full.pdf",
        availableVersions: ["BANGLA"],
        chapters: [
            { chapterId: "mar_c1", unitNo: "আদ-দারসুল আউয়াল", title: "আত্মপরিচয় ও সম্ভাষণ (মারহাবান, কাইফা হালুক)", version: "BANGLA", resources: [{ resourceId: "mar_r1", title: "মূল বই পড়ুন", pdfUrl: "arabic_u1_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mar_c2", unitNo: "আদ-দারসুস সানী", title: "বিদ্যালয় ও সহপাঠী (হাযা কিতাবুন, হাযা ক্বলমুন)", version: "BANGLA", resources: [{ resourceId: "mar_r2", title: "মূল বই পড়ুন", pdfUrl: "arabic_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mar_c3", unitNo: "আদ-দারসুস সালিস", title: "ফলমূল ও পশুপাখির নাম (তুফফাহুন, আসাদুন)", version: "BANGLA", resources: [{ resourceId: "mar_r3", title: "মূল বই পড়ুন", pdfUrl: "arabic_u3_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "madr_bangla",
        title: "আমার বাংলা বই (ইবতেদায়ী)",
        subtitle: "ইবতেদায়ী ১ম শ্রেণি • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "madr_bangla_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            { chapterId: "mb_c1", unitNo: "পাঠ ১", title: "আমার পরিচয়", version: "BANGLA", resources: [{ resourceId: "mb_r1", title: "মূল বই পড়ুন", pdfUrl: "bangla_u1_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c2", unitNo: "পাঠ ২", title: "আমি ও আমার বিদ্যালয়", version: "BANGLA", resources: [{ resourceId: "mb_r4", title: "মূল বই পড়ুন", pdfUrl: "bangla_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c3", unitNo: "পাঠ ৩", title: "আমরা কী কী কাজ করি", version: "BANGLA", resources: [{ resourceId: "mb_r6", title: "মূল বই পড়ুন", pdfUrl: "bangla_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c4", unitNo: "পাঠ ৪", title: "ছড়া - আতা গাছে তোতা পাখি", version: "BANGLA", resources: [{ resourceId: "mb_r8", title: "মূল বই পড়ুন", pdfUrl: "bangla_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c5", unitNo: "পাঠ ৫", title: "গল্প - কাক ও কলসি", version: "BANGLA", resources: [{ resourceId: "mb_r10", title: "মূল বই পড়ুন", pdfUrl: "bangla_u5_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c6", unitNo: "পাঠ ৬", title: "বর্ণ শিখি (অ - ঔ)", version: "BANGLA", resources: [{ resourceId: "mb_r12", title: "মূল বই পড়ুন", pdfUrl: "bangla_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c7", unitNo: "পাঠ ৭", title: "বর্ণ শিখি (ক - ঞ)", version: "BANGLA", resources: [{ resourceId: "mb_r13", title: "মূল বই পড়ুন", pdfUrl: "bangla_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c8", unitNo: "পাঠ ৮", title: "বর্ণ শিখি (ট - ন)", version: "BANGLA", resources: [{ resourceId: "mb_r14", title: "মূল বই পড়ুন", pdfUrl: "bangla_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c9", unitNo: "পাঠ ৯", title: "বর্ণ শিখি (প - ম)", version: "BANGLA", resources: [{ resourceId: "mb_r15", title: "মূল বই পড়ুন", pdfUrl: "bangla_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mb_c10", unitNo: "পাঠ ১০", title: "ছড়া - বাক বাকুম পায়রা", version: "BANGLA", resources: [{ resourceId: "mb_r16", title: "মূল বই পড়ুন", pdfUrl: "bangla_u10_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "madr_english",
        title: "English for Today (ইবতেদায়ী)",
        subtitle: "Primary Curriculum Class 1 • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "madr_english_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            { chapterId: "me_c1", unitNo: "Unit 1", title: "Greetings", version: "BANGLA", resources: [{ resourceId: "me_r1", title: "Read Textbook", pdfUrl: "eng_u1_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c2", unitNo: "Unit 2", title: "Introductions and Alphabet", version: "BANGLA", resources: [{ resourceId: "me_r3", title: "Read Textbook", pdfUrl: "eng_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c3", unitNo: "Unit 3", title: "Saying Goodbye", version: "BANGLA", resources: [{ resourceId: "me_r4", title: "Read Textbook", pdfUrl: "eng_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c4", unitNo: "Unit 4", title: "Numbers (1-3)", version: "BANGLA", resources: [{ resourceId: "me_r5", title: "Read Textbook", pdfUrl: "eng_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c5", unitNo: "Unit 5", title: "Rhyme (Two Little Black Birds)", version: "BANGLA", resources: [{ resourceId: "me_r6", title: "Read Textbook", pdfUrl: "eng_u5_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c6", unitNo: "Unit 6", title: "Alphabet (A to E)", version: "BANGLA", resources: [{ resourceId: "me_r7", title: "Read Textbook", pdfUrl: "eng_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c7", unitNo: "Unit 7", title: "Alphabet (F to J)", version: "BANGLA", resources: [{ resourceId: "me_r8", title: "Read Textbook", pdfUrl: "eng_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c8", unitNo: "Unit 8", title: "Alphabet (K to O)", version: "BANGLA", resources: [{ resourceId: "me_r9", title: "Read Textbook", pdfUrl: "eng_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c9", unitNo: "Unit 9", title: "Alphabet (P to T)", version: "BANGLA", resources: [{ resourceId: "me_r10", title: "Read Textbook", pdfUrl: "eng_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "me_c10", unitNo: "Unit 10", title: "Alphabet (U to Z)", version: "BANGLA", resources: [{ resourceId: "me_r11", title: "Read Textbook", pdfUrl: "eng_u10_text.pdf", type: "TEXTBOOK" }] }
        ]
    },
    {
        bookId: "madr_math",
        title: "প্রাথমিক গণিত (ইবতেদায়ী)",
        subtitle: "ইবতেদায়ী ১ম শ্রেণি • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "madr_math_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            { chapterId: "mm_c1", unitNo: "অধ্যায় ১", title: "তুলনা করি (কম-বেশি, ছোট-বড়)", version: "BANGLA", resources: [{ resourceId: "mm_r1", title: "মূল বই পড়ুন", pdfUrl: "math_u1_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c2", unitNo: "অধ্যায় ২", title: "গণনা (১ থেকে ৫)", version: "BANGLA", resources: [{ resourceId: "mm_r3", title: "মূল বই পড়ুন", pdfUrl: "math_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c3", unitNo: "অধ্যায় ৩", title: "সংখ্যা (১ থেকে ১০)", version: "BANGLA", resources: [{ resourceId: "mm_r4", title: "মূল বই পড়ুন", pdfUrl: "math_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c4", unitNo: "অধ্যায় ৪", title: "শূন্য (০) এর ধারণা", version: "BANGLA", resources: [{ resourceId: "mm_r5", title: "মূল বই পড়ুন", pdfUrl: "math_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c5", unitNo: "অধ্যায় ৫", title: "যোগের ধারণা", version: "BANGLA", resources: [{ resourceId: "mm_r6", title: "মূল বই পড়ুন", pdfUrl: "math_u5_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c6", unitNo: "অধ্যায় ৬", title: "বিয়োগের ধারণা", version: "BANGLA", resources: [{ resourceId: "mm_r7", title: "মূল বই পড়ুন", pdfUrl: "math_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c7", unitNo: "অধ্যায় ৭", title: "সংখ্যা (১১ থেকে ২০)", version: "BANGLA", resources: [{ resourceId: "mm_r8", title: "মূল বই পড়ুন", pdfUrl: "math_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c8", unitNo: "অধ্যায় ৮", title: "জ্যামিতিক আকৃতি", version: "BANGLA", resources: [{ resourceId: "mm_r9", title: "মূল বই পড়ুন", pdfUrl: "math_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c9", unitNo: "অধ্যায় ৯", title: "প্যাটার্ন ও পরিমাপ", version: "BANGLA", resources: [{ resourceId: "mm_r10", title: "মূল বই পড়ুন", pdfUrl: "math_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "mm_c10", unitNo: "অধ্যায় ১০", title: "বাংলাদেশের মুদ্রা ও নোট", version: "BANGLA", resources: [{ resourceId: "mm_r11", title: "মূল বই পড়ুন", pdfUrl: "math_u10_text.pdf", type: "TEXTBOOK" }] }
        ]
    }
];
