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

const BENGALI_VOWELS = [
    { letter: "অ", name: "স্বর অ", word: "অজগর", sentence: "অ তে অজগর! অজগরটি আসছে তেড়ে!", color: "#10B981", audioUrl: "audio/shorboborno/vowel_1_o.mp3" },
    { letter: "আ", name: "স্বর আ", word: "আম", sentence: "আ তে আম! আমটি আমি খাব পেড়ে!", color: "#F59E0B", audioUrl: "audio/shorboborno/vowel_2_aa.mp3" },
    { letter: "ই", name: "হ্রস্ব ই", word: "ইলিশ", sentence: "ই তে ইলিশ! ইলিশ ভাজা খেতে মজা!", color: "#06B6D4", audioUrl: "audio/shorboborno/vowel_3_i.mp3" },
    { letter: "ঈ", name: "দীর্ঘ ঈ", word: "ঈগল", sentence: "ঈ তে ঈগল! ঈগল পাখি আকাশে ওড়ে!", color: "#8B5CF6", audioUrl: "audio/shorboborno/vowel_4_ee.mp3" },
    { letter: "উ", name: "হ্রস্ব উ", word: "উট", sentence: "উ তে উট! উট চলেছে মরুর দেশে!", color: "#EC4899", audioUrl: "audio/shorboborno/vowel_5_u.mp3" },
    { letter: "ঊ", name: "দীর্ঘ ঊ", word: "ঊষা", sentence: "ঊ তে ঊষা! ঊষার আলো মিষ্টি আলো!", color: "#F97316", audioUrl: "audio/shorboborno/vowel_6_oo.mp3" },
    { letter: "ঋ", name: "ঋ", word: "ঋষি", sentence: "ঋ তে ঋষি! ঋষি মশাই বসেন ধ্যানে!", color: "#14B8A6", audioUrl: "audio/shorboborno/vowel_7_ri.mp3" },
    { letter: "এ", name: "এ", word: "একতারা", sentence: "এ তে একতারা! একতারাটি বাজে বেশ!", color: "#3B82F6", audioUrl: "audio/shorboborno/vowel_8_e.mp3" },
    { letter: "ঐ", name: "ঐ", word: "ঐরাবত", sentence: "ঐ তে ঐরাবত! ঐরাবত হাতি চলে হেলেদুলে!", color: "#6366F1", audioUrl: "audio/shorboborno/vowel_9_oi.mp3" },
    { letter: "ও", name: "ও", word: "ওল", sentence: "ও তে ওল! ওল খেলে কিন্তু ধরবে গলা!", color: "#84CC16", audioUrl: "audio/shorboborno/vowel_10_o.mp3" },
    { letter: "ঔ", name: "ঔ", word: "ঔষধ", sentence: "ঔ তে ঔষধ! ঔষধ খেলে রোগ সারে!", color: "#E11D48", audioUrl: "audio/shorboborno/vowel_11_ou.mp3" }
];

const BENGALI_CONSONANTS = [
    { id: 1, letter: "ক", name: "ক", word: "কলম", sentence: "ক তে কলম! কলম দিয়ে লেখা যায়", color: "#10B981", audioUrl: "audio/banjonborno/consonant_1_k.mp3", isReady: true },
    { id: 2, letter: "খ", name: "খ", word: "খরগোশ", sentence: "খ তে খরগোশ! খরগোশ তুই খেতে আয়", color: "#06B6D4", audioUrl: "audio/banjonborno/consonant_2_kh.mp3", isReady: true },
    { id: 3, letter: "গ", name: "গ", word: "গরু", sentence: "গ তে গরু! গরুর দুধে পুষ্টি আছে", color: "#8B5CF6", audioUrl: "audio/banjonborno/consonant_3_g.mp3", isReady: true },
    { id: 4, letter: "ঘ", name: "ঘ", word: "ঘড়ি", sentence: "ঘ তে ঘড়ি! ঘড়ি রাখো হাতের কাছে", color: "#EC4899", audioUrl: "audio/banjonborno/consonant_4_gh.mp3", isReady: true },
    { id: 5, letter: "ঙ", name: "ঙ", word: "ব্যাঙ", sentence: "ঙ তে ব্যাঙ! ব্যাঙ ডাকে বর্ষায়", color: "#F59E0B", audioUrl: "audio/banjonborno/consonant_5_ng.mp3", isReady: true },
    { id: 6, letter: "চ", name: "চ", word: "চাঁদ", sentence: "চ তে চাঁদ! চাঁদ উঠেছে আকাশ পানে", color: "#14B8A6", audioUrl: "audio/banjonborno/consonant_6_ch.mp3", isReady: true },
    { id: 7, letter: "ছ", name: "ছ", word: "ছাতা", sentence: "ছ তে ছাতা! ছাতা লাগে বৃষ্টি হলে", color: "#3B82F6", audioUrl: "audio/banjonborno/consonant_7_chh.mp3", isReady: true },
    { id: 8, letter: "জ", name: "বর্গীয় জ", word: "জাহাজ", sentence: "জ তে জাহাজ! জাহাজ চলে সাগর জলে", color: "#6366F1", audioUrl: "audio/banjonborno/consonant_8_j.mp3", isReady: true },
    { id: 9, letter: "ঝ", name: "ঝ", word: "ঝিনুক", sentence: "ঝ তে ঝিনুক! ঝিনুক থেকে মুক্তা মিলে", color: "#84CC16", audioUrl: "audio/banjonborno/consonant_9_jh.mp3", isReady: true },
    { id: 10, letter: "ঞ", name: "ঞ", word: "মিঞা", sentence: "ঞ তে মিঞা! মিঞা ভাইয়ের দাড়ি গালে", color: "#E11D48", audioUrl: "audio/banjonborno/consonant_10_yno.mp3", isReady: true },
    { id: 11, letter: "ট", name: "ট", word: "টিয়া", sentence: "ট তে টিয়া! টিয়া পাখির ঠোঁটটি লাল", color: "#10B981", audioUrl: "audio/banjonborno/consonant_11_t.mp3", isReady: true },
    { id: 12, letter: "ঠ", name: "ঠ", word: "ঠেলাগাড়ি", sentence: "ঠ তে ঠেলাগাড়ি! ঠেলাগাড়ি টানে মাল", color: "#06B6D4", audioUrl: "audio/banjonborno/consonant_12_th.mp3", isReady: true },
    { id: 13, letter: "ড", name: "ড", word: "ডিম", sentence: "ড তে ডিম! ডিমে অনেক শক্তি আছে", color: "#8B5CF6", audioUrl: "audio/banjonborno/consonant_13_d.mp3", isReady: true },
    { id: 14, letter: "ঢ", name: "ঢ", word: "ঢোল", sentence: "ঢ তে ঢোল! ঢোল বাজালে শব্দ আসে", color: "#EC4899", audioUrl: "audio/banjonborno/consonant_14_dh.mp3", isReady: true },
    { id: 15, letter: "ণ", name: "মূর্ধন্য ণ", word: "হরিণ", sentence: "ণ তে হরিণ! হরিণ থাকে বনের ধারে", color: "#F59E0B", audioUrl: "audio/banjonborno/consonant_15_n_murdhanya.mp3", isReady: true },
    { id: 16, letter: "ত", name: "ত", word: "তরমুজ", sentence: "ত তে তরমুজ! তরমুজ খাব মজা করে", color: "#14B8A6", audioUrl: "audio/banjonborno/consonant_16_t.mp3", isReady: true },
    { id: 17, letter: "থ", name: "থ", word: "থালা", sentence: "থ তে থালা! থালার খাবার খাবে খুকি", color: "#3B82F6", audioUrl: "audio/banjonborno/consonant_17_th.mp3", isReady: true },
    { id: 18, letter: "দ", name: "দ", word: "দাদু", sentence: "দ তে দাদু! দাদুর চোখে চশমা রয়", color: "#6366F1", audioUrl: "audio/banjonborno/consonant_18_d.mp3", isReady: true },
    { id: 19, letter: "ধ", name: "ধ", word: "ধান", sentence: "ধ তে ধান! ধান ফলিয়ে আমরা বাঁচি", color: "#84CC16", audioUrl: "audio/banjonborno/consonant_19_dh.mp3", isReady: true },
    { id: 20, letter: "ন", name: "দন্ত্য ন", word: "নৌকা", sentence: "ন তে নৌকা! নৌকা চালায় নয়ন মাঝি", color: "#E11D48", audioUrl: "audio/banjonborno/consonant_20_n.mp3", isReady: true },
    { id: 21, letter: "প", name: "প", word: "পাখি", sentence: "প তে পাখি! পাখি গান গায় গাছে গাছে", color: "#10B981", audioUrl: "audio/banjonborno/consonant_21_p.mp3", isReady: true },
    { id: 22, letter: "ফ", name: "ফ", word: "ফুল", sentence: "ফ তে ফুল! ফুলেতে মধু আছে", color: "#06B6D4", audioUrl: "audio/banjonborno/consonant_22_ph.mp3", isReady: true },
    { id: 23, letter: "ব", name: "ব", word: "বই", sentence: "ব তে বই! বই পড়লে জ্ঞান বাড়ে", color: "#8B5CF6", audioUrl: "audio/banjonborno/consonant_23_b.mp3", isReady: true },
    { id: 24, letter: "ভ", name: "ভ", word: "ভাল্লুক", sentence: "ভ তে ভাল্লুক! ভাল্লুক থাকে নদীর ধারে", color: "#EC4899", audioUrl: "audio/banjonborno/consonant_24_bh.mp3", isReady: true },
    { id: 25, letter: "ম", name: "ম", word: "ময়ূর", sentence: "ম তে ময়ূর! ময়ূর নাচে তালে তালে", color: "#F59E0B", audioUrl: "audio/banjonborno/consonant_25_m.mp3", isReady: true },
    { id: 26, letter: "য", name: "অন্তঃস্থ য", word: "যব", sentence: "য তে যব! যব ভালো হয় আটা হলে", color: "#14B8A6", audioUrl: "audio/banjonborno/consonant_26_j.mp3", isReady: true },
    { id: 27, letter: "র", name: "ব-এ শূন্য র", word: "রাজহাঁস", sentence: "র তে রাজহাঁস! রাজহাঁসের গলা বড়", color: "#3B82F6", audioUrl: "audio/banjonborno/consonant_27_r.mp3", isReady: true },
    { id: 28, letter: "ল", name: "ল", word: "লেবু", sentence: "ল তে লেবু! লেবুর শরবত খেতে ভালো", color: "#6366F1", audioUrl: "audio/banjonborno/consonant_28_l.mp3", isReady: true },
    { id: 29, letter: "শ", name: "তালব্য শ", word: "শাপলা", sentence: "শ তে শাপলা! শাপলা ফুটে দিঘির জলে", color: "#84CC16", audioUrl: "audio/banjonborno/consonant_29_sh.mp3", isReady: true },
    { id: 30, letter: "ষ", name: "মূর্ধন্য ষ", word: "ষাঁড়", sentence: "ষ তে ষাঁড়! ষাঁড় খুশি ঘাস পেলে", color: "#E11D48", audioUrl: "audio/banjonborno/consonant_30_sh_murdhanya.mp3", isReady: true },
    { id: 31, letter: "স", name: "দন্ত্য স", word: "সিংহ", sentence: "স তে সিংহ! সিংহ হলো বনের রাজা", color: "#10B981", audioUrl: "audio/banjonborno/consonant_31_s.mp3", isReady: true },
    { id: 32, letter: "হ", name: "হ", word: "হাতি", sentence: "হ তে হাতি! হাতি চলে ধীরে ধীরে", color: "#06B6D4", audioUrl: "audio/banjonborno/consonant_32_h.mp3", isReady: true },
    { id: 33, letter: "ড়", name: "ড-এ বিন্দু ড়", word: "গাড়ি", sentence: "ড় তে গাড়ি! গাড়ি নিয়ে ঘুরি চলো", color: "#8B5CF6", audioUrl: "audio/banjonborno/consonant_33_rh.mp3", isReady: true },
    { id: 34, letter: "ঢ়", name: "ঢ-এ বিন্দু ঢ়", word: "আষাঢ়", sentence: "ঢ় তে আষাঢ়! আষাঢ় মাসে বৃষ্টি হয়", color: "#EC4899", audioUrl: "audio/banjonborno/consonant_34_rh_dh.mp3", isReady: true },
    { id: 35, letter: "য়", name: "অন্তঃস্থ য়", word: "পায়রা", sentence: "য় তে পায়রা! পায়রাটি দাঁড়িয়ে রয়", color: "#F59E0B", audioUrl: "audio/banjonborno/consonant_35_y.mp3", isReady: true },
    { id: 36, letter: "ৎ", name: "খণ্ড ত", word: "উৎসব", sentence: "ৎ তে উৎসব! উৎসব আনন্দ দেয়", color: "#14B8A6", audioUrl: "audio/banjonborno/consonant_36_khondo_t.mp3", isReady: true },
    { id: 37, letter: "ং", name: "অনুস্বার", word: "রংধনু", sentence: "ং তে রংধনু! রংধনু বাঁকা হয়", color: "#3B82F6", audioUrl: "audio/banjonborno/consonant_37_onushor.mp3", isReady: true },
    { id: 38, letter: "ঃ", name: "বিসর্গ", word: "দুঃখ", sentence: "ঃ তে দুঃখ! দুঃখ দেবে না কারো মনে", color: "#6366F1", audioUrl: "audio/banjonborno/consonant_38_bishorgo.mp3", isReady: true },
    { id: 39, letter: "ঁ", name: "চন্দ্রবিন্দু", word: "চাঁদ", sentence: "ঁ তে চাঁদ! চাঁদ উঠেছে আকাশ পানে", color: "#E11D48", audioUrl: "audio/banjonborno/consonant_39_chondrobindu.mp3", isReady: true }
];

window.BENGALI_VOWELS = BENGALI_VOWELS;
window.BENGALI_CONSONANTS = BENGALI_CONSONANTS;

const INITIAL_CLASS_1_BOOKS = [
    {
        bookId: "school_bangla",
        title: "আমার বাংলা বই",
        subtitle: "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড (NCTB)",
        curriculum: "SCHOOL",
        pdfUrl: "bangla_class1_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            { chapterId: "sb_c1", unitNo: "ইউনিট ১", title: "আমার পরিচয় ও বিদ্যালয়", version: "BANGLA", resources: [{ resourceId: "r1", title: "মূল বই পড়ুন", pdfUrl: "bangla_u1_text.pdf", type: "TEXTBOOK" }, { resourceId: "r2", title: "গাইডবুক পড়ুন", pdfUrl: "bangla_u1_guide.pdf", type: "GUIDEBOOK" }, { resourceId: "r3", title: "মডেল টেস্ট", pdfUrl: "bangla_u1_test.pdf", type: "MODEL_TEST" }] },
            { chapterId: "sb_c2", unitNo: "ইউনিট ২", title: "বর্ণ শিখি: স্বরবর্ণ (অ থেকে ঔ)", version: "BANGLA", resources: [{ resourceId: "r4", title: "মূল বই পড়ুন", pdfUrl: "bangla_u2_text.pdf", type: "TEXTBOOK" }, { resourceId: "r5", title: "গাইডবুক পড়ুন", pdfUrl: "bangla_u2_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "sb_c3", unitNo: "ইউনিট ৩", title: "বর্ণ শিখি: ব্যঞ্জনবর্ণ - ১ম ভাগ (ক থেকে ঞ)", version: "BANGLA", resources: [{ resourceId: "r6", title: "মূল বই পড়ুন", pdfUrl: "bangla_u3_text.pdf", type: "TEXTBOOK" }, { resourceId: "r7", title: "গাইডবুক পড়ুন", pdfUrl: "bangla_u3_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "sb_c4", unitNo: "ইউনিট ৪", title: "বর্ণ শিখি: ব্যঞ্জনবর্ণ - ২য় ভাগ (ট থেকে ন)", version: "BANGLA", resources: [{ resourceId: "r8", title: "মূল বই পড়ুন", pdfUrl: "bangla_u4_text.pdf", type: "TEXTBOOK" }, { resourceId: "r9", title: "গাইডবুক পড়ুন", pdfUrl: "bangla_u4_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "sb_c5", unitNo: "ইউনিট ৫", title: "বর্ণ শিখি: ব্যঞ্জনবর্ণ - ৩য় ভাগ (প থেকে ম)", version: "BANGLA", resources: [{ resourceId: "r10", title: "মূল বই পড়ুন", pdfUrl: "bangla_u5_text.pdf", type: "TEXTBOOK" }, { resourceId: "r11", title: "গাইডবুক পড়ুন", pdfUrl: "bangla_u5_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "sb_c6", unitNo: "ইউনিট ৬", title: "বর্ণ শিখি: ব্যঞ্জনবর্ণ - ৪র্থ ভাগ (য থেকে ঁ)", version: "BANGLA", resources: [{ resourceId: "r12", title: "মূল বই পড়ুন", pdfUrl: "bangla_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c7", unitNo: "ইউনিট ৭", title: "কারচিহ্ন ও সহজ শব্দ গঠন (া থেকে ৌ)", version: "BANGLA", resources: [{ resourceId: "r13", title: "মূল বই পড়ুন", pdfUrl: "bangla_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c8", unitNo: "ইউনিট ৮", title: "মজার মজার ছড়া ও কবিতা আবৃত্তি", version: "BANGLA", resources: [{ resourceId: "r14", title: "মূল বই পড়ুন", pdfUrl: "bangla_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c9", unitNo: "ইউনিট ৯", title: "শিক্ষণীয় গল্প ও নীতিশিক্ষা", version: "BANGLA", resources: [{ resourceId: "r15", title: "মূল বই পড়ুন", pdfUrl: "bangla_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sb_c10", unitNo: "ইউনিট ১০", title: "আমাদের প্রিয় বাংলাদেশ ও জাতীয় প্রতীক", version: "BANGLA", resources: [{ resourceId: "r16", title: "মূল বই পড়ুন", pdfUrl: "bangla_u10_text.pdf", type: "TEXTBOOK" }] }
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
            { chapterId: "se_c1", unitNo: "Unit 1", title: "Greetings & Farewells (Hello, Hi, Goodbye)", version: "BANGLA", resources: [{ resourceId: "se_r1", title: "Read Textbook", pdfUrl: "eng_u1_text.pdf", type: "TEXTBOOK" }, { resourceId: "se_r2", title: "Guide & Translation", pdfUrl: "eng_u1_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "se_c2", unitNo: "Unit 2", title: "Alphabet & Phonics (Letters A to F)", version: "BANGLA", resources: [{ resourceId: "se_r3", title: "Read Textbook", pdfUrl: "eng_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c3", unitNo: "Unit 3", title: "Numbers & Counting (Numbers 1 to 5)", version: "BANGLA", resources: [{ resourceId: "se_r4", title: "Read Textbook", pdfUrl: "eng_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c4", unitNo: "Unit 4", title: "Alphabet & Words (Letters G to L)", version: "BANGLA", resources: [{ resourceId: "se_r5", title: "Read Textbook", pdfUrl: "eng_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c5", unitNo: "Unit 5", title: "Classroom Commands & Action Verbs", version: "BANGLA", resources: [{ resourceId: "se_r6", title: "Read Textbook", pdfUrl: "eng_u5_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c6", unitNo: "Unit 6", title: "Alphabet & Words (Letters M to R)", version: "BANGLA", resources: [{ resourceId: "se_r7", title: "Read Textbook", pdfUrl: "eng_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c7", unitNo: "Unit 7", title: "Numbers & Quantities (Numbers 6 to 10)", version: "BANGLA", resources: [{ resourceId: "se_r8", title: "Read Textbook", pdfUrl: "eng_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c8", unitNo: "Unit 8", title: "Alphabet & Words (Letters S to Z)", version: "BANGLA", resources: [{ resourceId: "se_r9", title: "Read Textbook", pdfUrl: "eng_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c9", unitNo: "Unit 9", title: "Colours, Shapes & Nature", version: "BANGLA", resources: [{ resourceId: "se_r10", title: "Read Textbook", pdfUrl: "eng_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "se_c10", unitNo: "Unit 10", title: "Rhymes, Stories & Review Activities", version: "BANGLA", resources: [{ resourceId: "se_r11", title: "Read Textbook", pdfUrl: "eng_u10_text.pdf", type: "TEXTBOOK" }] }
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
            { chapterId: "sm_c1", unitNo: "অধ্যায় ১", title: "তুলনা করি (কম-বেশি, ছোট-বড়, হালকা-ভারী)", version: "BANGLA", resources: [{ resourceId: "sm_r1", title: "মূল বই পড়ুন", pdfUrl: "math_u1_text.pdf", type: "TEXTBOOK" }, { resourceId: "sm_r2", title: "সমাধান ও গাইড", pdfUrl: "math_u1_guide.pdf", type: "GUIDEBOOK" }] },
            { chapterId: "sm_c2", unitNo: "অধ্যায় ২", title: "গণনা ও সংখ্যা (১ থেকে ৫)", version: "BANGLA", resources: [{ resourceId: "sm_r3", title: "মূল বই পড়ুন", pdfUrl: "math_u2_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c3", unitNo: "অধ্যায় ৩", title: "গণনা ও সংখ্যা (৬ থেকে ১০)", version: "BANGLA", resources: [{ resourceId: "sm_r4", title: "মূল বই পড়ুন", pdfUrl: "math_u3_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c4", unitNo: "অধ্যায় ৪", title: "শূন্য (০) এর ধারণা ও ক্রমবাচক সংখ্যা", version: "BANGLA", resources: [{ resourceId: "sm_r5", title: "মূল বই পড়ুন", pdfUrl: "math_u4_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c5", unitNo: "অধ্যায় ৫", title: "যোগের ধারণা (১ থেকে ৯ পর্যন্ত)", version: "BANGLA", resources: [{ resourceId: "sm_r6", title: "মূল বই পড়ুন", pdfUrl: "math_u5_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c6", unitNo: "অধ্যায় ৬", title: "বিয়োগের ধারণা (১ থেকে ৯ পর্যন্ত)", version: "BANGLA", resources: [{ resourceId: "sm_r7", title: "মূল বই পড়ুন", pdfUrl: "math_u6_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c7", unitNo: "অধ্যায় ৭", title: "১১ থেকে ২০ পর্যন্ত সংখ্যা ও যোগ-বিয়োগ", version: "BANGLA", resources: [{ resourceId: "sm_r8", title: "মূল বই পড়ুন", pdfUrl: "math_u7_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c8", unitNo: "অধ্যায় ৮", title: "বিভিন্ন জ্যামিতিক আকৃতি (গোল, তিনকোনা, চারকোনা)", version: "BANGLA", resources: [{ resourceId: "sm_r9", title: "মূল বই পড়ুন", pdfUrl: "math_u8_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c9", unitNo: "অধ্যায় ৯", title: "টাকা ও পয়সার পরিচিতি ও সহজ হিসাব", version: "BANGLA", resources: [{ resourceId: "sm_r10", title: "মূল বই পড়ুন", pdfUrl: "math_u9_text.pdf", type: "TEXTBOOK" }] },
            { chapterId: "sm_c10", unitNo: "অধ্যায় ১০", title: "দিন, রাত, সময় ও সপ্তাহের ৭ দিন", version: "BANGLA", resources: [{ resourceId: "sm_r11", title: "মূল বই পড়ুন", pdfUrl: "math_u10_text.pdf", type: "TEXTBOOK" }] }
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
    }
];
