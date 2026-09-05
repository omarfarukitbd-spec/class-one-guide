# 📌 PROJECT STATUS & MASTER ARCHITECTURE GUIDE

> **CRITICAL RULE FOR ALL AI AGENTS:** 
> You **MUST** read this file thoroughly before suggesting new features, planning implementations, or modifying existing systems. Do not re-invent the wheel or suggest features that are already marked as "COMPLETED" below.

## 🏗️ 1. App Architecture & Core Stack
- **Architecture:** Clean Architecture + MVVM + MVI (StateFlow)
- **UI Toolkit:** Jetpack Compose (100% Stateless UIs)
- **Dependency Injection:** Dagger Hilt (`@HiltViewModel`, `AnalyticsModule`, etc.)
- **Local Database:** Room Database (`BookDao`, `ChapterDao`). This is the **Single Source of Truth (SSOT)** for all UI screens.
- **Backend/Cloud:** Firebase Firestore, Firebase Analytics, Firebase Crashlytics.
- **Admin Panel:** A custom Web Admin Panel (HTML/JS) resides in the `admin-panel/` directory.

## 🔄 2. Data Sync Engine (Offline-First)
The app uses an advanced Zero-Bandwidth Delta Sync engine:
- **How it works:** The web Admin Panel writes data to Firestore (`classes/class_1` document).
- **App Side:** `SyncCloudDataUseCase.kt` reads from Firestore, maps `RemoteBookDto` to `BookEntity`, and performs an **Atomic Upsert** into Room DB.
- **Trigger:** Sync is triggered automatically on App Launch (`HomeRepositoryImpl.kt` init) AND whenever internet is restored via `NetworkMonitor`.
- **UI Reaction:** The UI only observes Room (`Flow<List<Book>>`). When the sync finishes, the UI updates instantly without any loading spinners blocking the user.

## 🛡️ 3. Military-Grade PDF DRM System
The app has a highly secure custom PDF viewer. Do NOT use generic PDF libraries or Intents.
- **Downloading:** `PdfDownloader.kt` streams the PDF directly to `context.cacheDir` (Scoped Storage).
- **Encryption:** `PdfCryptoEngine.kt` uses `AndroidKeyStore` to encrypt the file (AES-256 GCM) on the fly. No plain PDFs ever exist on disk.
- **Rendering:** `PdfRendererEngine.kt` decrypts the file temporarily into memory, renders Bitmaps via `android.graphics.pdf.PdfRenderer`, and shreds the memory/file upon `close()`.
- **UI:** `PdfViewerScreen.kt` supports Lazy loading, Pitch-to-Zoom, and Dark Mode (Inverted Colors).

## 📊 4. Monitoring (Analytics & Crashlytics)
- `AnalyticsTracker` and `CrashReporter` interfaces are fully implemented via Firebase.
- They are injected into `HomeViewModel`, `SubjectDetailViewModel`, `PdfViewerViewModel`, and `SyncCloudDataUseCase` to track screen views, PDF page turns, and log silent sync exceptions.

---

## ✅ 5. CURRENT FEATURES STATE (What is built vs What is pending)

### 🟢 FULLY COMPLETED (Do not suggest building these)
1. **Web Admin Panel (`admin-panel/`)** - Books & Chapters CRUD, Audio Lab, Settings Control, Backup & Restore.
2. **Cloud Sync Engine** - Fully integrated with Room.
3. **App Core Screens** - Splash Screen, Main App Navigation, Kids Zone Dashboard.
4. **Home Screen** - Curriculum Selector (School/Madrasah), Layout Switcher (Grid/List), Theme Selector, Search Bar (Debounced).
5. **Subject Details Screen** - Dynamic chapter lists based on selected `LanguageVersion` (Bangla/English).
6. **Secure PDF Viewer** - Pitch-to-Zoom, DRM Encryption, Secure Shredding, Dark Mode.
7. **Favorites Screen** - UI and Logic for managing favorite books.
8. **Settings & Notifications UI** - In-app UI for app settings and viewing offline notifications.
9. **Analytics & Crashlytics** - Realtime tracking and error logging.
10. **Security Setup** - `FLAG_SECURE` prevents screenshots/recording.
11. **Architecture Hardening** - Zero-data-loss validation in Sync engine and corrupted cache prevention in PDF Downloader.
12. **UI State Architecture (Phase 1 Fixes)** - Enforced 4-State UI models (`Empty` state) and one-off Event Channels across all screens including Subject Details.
13. **Sync Engine Hardening (Phase 2 Fixes)** - Enforced Room `@Transaction` for atomic upserts, removed unsafe 50% pruning heuristic, and added graceful network exception handling.
14. **Hardcoded Strings Cleanup (Phase 3 Fixes)** - Extracted all hardcoded UI and ViewModel strings into `strings.xml` for Home and Subject Detail screens.
15. **UI Polish & Previews (Phase 4 Fixes)** - Added Material 3 Previews with mock data and accessibility labels for all Subject Detail components.
16. **Kids Zone UI Refactoring & Material 3 Icon Conversion** - 100% zero-emoji enforcement, adaptive responsive grid (`GridCells.Adaptive`), Glassmorphism cards with `bounceClick`, and localized strings in `strings.xml`.
17. **Phonics Learning Screen & Audio Player (Vector Artwork Architecture)** - 50 letters soundboard (11 vowels + 39 consonants), dedicated vector drawables for special letters, extracted crisp letter-only audio from Hatekhori APK for rapid alphabet soundboard, lifecycle-aware zero-leak audio player, pulsating sound-wave animations, and child-friendly letter detail sheets.
18. **Illustrated Phonics & Cinematic Living Motion (সচিত্র বর্ণমালা ও লাইভ অ্যানিমেশন আর্কিটেকচার - ৫০/৫০ সম্পন্ন)** - বাংলা বর্ণমালার ৫০টি বর্ণের (১১টি স্বরবর্ণ ও ৩৯টি ব্যঞ্জনবর্ণ) প্রতিটির জন্য শতভাগ কপিরাইট-মুক্ত ও নিখুঁত হাই-রেজোলিউশন বাস্তব ফটোগ্রাফি (`illust_1.jpg` থেকে `illust_50.jpg`) সংযুক্ত করা হয়েছে। Jetpack Compose `LiveIllustrationCanvas`-এর মাধ্যমে সিনেমাটিক ব্রিদিং স্কেল (Breathing Zoom), প্যারালাক্স ফ্লোটিং (Parallax Float), ৪ পাশের ডায়নামিক গ্রেডিয়েন্ট গ্লো বর্ডার এবং ছড়া আবৃত্তির সাথে সাথে লাইভ অ্যাকশন প্রতিফলন সক্রিয় করা হয়েছে।
19. **Bengali Grapheme Cluster & Syllable Architecture (কার চিহ্ন ও পরাশ্রয়ী বর্ণ সমাধান সম্পন্ন)** - বাংলা বর্ণমালার কার চিহ্ন (া, ি, ী ইত্যাদি) এবং পরাশ্রয়ী বর্ণসমূহের (`ং`, `ঃ`, `ঁ`) বিচ্ছিন্ন হয়ে ডটেড সার্কেল (◌া, ◌ি, ◌ঃ) প্রদর্শনের সমস্যা সমূলে সমাধান করা হয়েছে। `BengaliClusterUtil` ইঞ্জিনের মাধ্যমে দল/সিলেবল গ্রুপিং এবং ডেডিকেটেড ভেক্টর আর্টওয়ার্কের মাধ্যমে সচিত্র বই, শব্দ তৈরির ল্যাব ও স্ক্রাবার স্ট্রিপে ১০০% পরিচ্ছন্ন, নিখুঁত ও ডটেড-সার্কেল-মুক্ত আধুনিক বইয়ের পাতা নিশ্চিত করা হয়েছে।
20. **100% Unique 3D Storybook Artworks (৫০/৫০ সচিত্র বই আর্টওয়ার্ক সম্পূর্ণ সম্পন্ন)** - বাংলা বর্ণমালার ৫০টি বর্ণের (১১টি স্বরবর্ণ + ৩৯টি ব্যঞ্জনবর্ণ: ক থেকে ঁ) প্রতিটির জন্য শিশুদের ৩ডি পিকচারবুক শৈলীতে (Pixar/Disney 3D Animated Style) সম্পূর্ণ মৌলিক, মনকাড়া ও প্রাণবন্ত ইলাস্ট্রেশন জেনারেট করে `app/src/main/assets/images/illustrations/illust_1.jpg` থেকে `illust_50.jpg` সফলভাবে প্রতিস্থাপন করা হয়েছে। ৫০টি ছবির মোট সাইজ মাত্র ১.৮১ MB (গড়ে ৩৭ KB), যা ১০০% ল্যাগ-মুক্ত, দ্রুতগতির এবং সম্পূর্ণ নিজস্ব একটি আধুনিক সচিত্র বইয়ের অভিজ্ঞতা নিশ্চিত করেছে।
21. **Global 5-Tier Back Navigation System & Root Exit Confirmation (গ্লোবাল ব্যাক নেভিগেশন ও কনফার্মেশন পপআপ সম্পন্ন)** - ব্যাক বাটনে ক্লিক করলে অপ্রত্যাশিতভাবে হোমপেজে চলে যাওয়া কিংবা অ্যাপ বন্ধ হয়ে যাওয়ার সমস্যা স্থায়ীভাবে সমাধান করা হয়েছে। আধুনিক অ্যান্ড্রয়েডের ৫-স্তরের অনুক্রমিক ব্যাক নেভিগেশন আর্কিটেকচার বাস্তবায়ন করা হয়েছে:
    - **লেভেল ১ (ওভারলে ও শীট):** বর্ণমালার ডিটেইল বটম শীট, পিডিএফ রিডারের থাম্বনেইল/বুকমার্ক/সেটিংস শীট এবং হোমপেজের সার্চ কোয়েরি সক্রিয় থাকলে ব্যাক বাটন প্রথমে শীট বা সার্চ ক্লিয়ার করে।
    - **লেভেল ২ (সাব-মোড):** সচিত্র বই বা শব্দ তৈরির ল্যাবে থাকলে ব্যাক বাটন প্রথমে মূল সাউন্ডবোর্ডে ফিরে যায়।
    - **লেভেল ৩ (স্ক্রিন পপ):** কোনো সাব-মোড বা শীট না থাকলে টপ বার এবং সিস্টেম ব্যাক একই সাথে পূর্ববর্তী স্ক্রিনে ফিরে আসে (`popBackStack()`)।
    - **লেভেল ৪ (ট্যাব ও সাব-মোড হিস্ট্রি স্ট্যাক):** বটম বারের একাধিক ট্যাবে (হোম, কিডস জোন, পছন্দ, সেটিংস) এবং সাব-মোডে (সাউন্ডবোর্ড, সচিত্র বই, শব্দ ল্যাব) `rememberSaveable` যুক্ত করা হয়েছে। ফলে চাইল্ড স্ক্রিন থেকে ফিরলে মেমোরি ক্লিয়ার হয়ে হোমে জাম্প না করে পূর্ববর্তী ভিজিট করা নির্দিষ্ট স্ক্রিন ও ট্যাবে ফিরে আসে।
    - **লেভেল ৫ (রুট এক্সিট কনফার্মেশন):** হোমপেজে ব্যাক বাটন চাপলে সরাসরি অ্যাপ বন্ধ না হয়ে সুন্দর মেটেরিয়াল ৩ কনফার্মেশন ডায়ালগ (`ExitConfirmationDialog`) প্রদর্শিত হয়।
    - **সিমেট্রিক ব্যাক নেভিগেশন:** কিডস জোন, পছন্দ ও সেটিংসের টপ বারে ব্যাক বাটন যুক্ত করা হয়েছে, যা সিস্টেম জেসচারের মতোই হুবহু কাজ করে।
    - ভবিষ্যতে যেকোনো এজেন্ট যাতে এই আর্কিটেকচার মেনে কোড করতে পারে সেজন্য `GLOBAL_NAVIGATION_GUIDE.md` তৈরি ও `AGENTS.md`-তে সংযোজন করা হয়েছে।
22. **Phonics Dedicated Word Audio & Zero-Emoji Architecture (শব্দ উচ্চারণ অডিও ও সম্পূর্ণ ইমোজি-মুক্তকরণ সম্পন্ন)** - স্বরবর্ণ ও ব্যঞ্জনবর্ণের ৫০টি শব্দের জন্য ১-২ সেকেন্ডের ৫০টি স্বতন্ত্র ও নিখুঁত বাংলা শব্দ উচ্চারণ MP3 তৈরি করে `assets/audio/words/` এ যুক্ত করা হয়েছে (বিকল্প ১)। সাউন্ডবোর্ডে শব্দ মোডে টগল করলে কার্ডে ট্যাপ করলে এখন সম্পূর্ণ বাক্য না বলে সরাসরি নির্দিষ্ট শব্দটি উচ্চারিত হয়। পাশাপাশি সব ধরনের বিশ্রী ইমোজি ও মিসম্যাচড আইকন অপসারণ করে সাউন্ডবোর্ড কার্ডে পরিচ্ছন্ন মেটেরিয়াল ৩ চিপ এবং ডিটেইল শীটে ৫০টি ৩ডি ইলাস্ট্রেশনের হাই-রেজোলিউশন সার্কুলার থাম্বনেইল যুক্ত করা হয়েছে। একই সাথে ওয়েব অ্যাডমিন প্যানেল থেকেও সব ইমোজি মুক্ত করে স্বয়ংক্রিয়ভাবে ফায়ারবেসে হোস্ট করা হয়েছে।
23. **World-Class Digital Slate & Bengali Handwriting Tracing Canvas (বিশ্বমানের ডিজিটাল স্লেট ও নিখুঁত বর্ণ ট্রেসিং সম্পন্ন)** - প্রথম শ্রেণির শিশুদের জন্য বাস্তবসম্মত চক-বোর্ড ফিজিক্স এবং শতভাগ নিখুঁত বাংলা বর্ণমালা হ্যান্ডরাইটিং ট্রেসিং আর্কিটেকচার সফলভাবে সম্পন্ন হয়েছে:
    - **পিক্সেল-পারফেক্ট গ্লিফ অ্যালাইনমেন্ট ইঞ্জিন (`SlateGlyphHelper`):** অ্যান্ড্রয়েড ফন্ট ইঞ্জিনের টেক্সট বাউন্ডস ও অ্যাডভান্স প্রস্থের সাথে ক্যানভাসের অঙ্কিত বর্ণের অক্ষ ও বাউন্ডস ১০০.০% নিখুঁতভাবে সিঙ্ক করা হয়েছে। ফলে বর্ণের কালি এবং ট্রেসিং গাইডের নির্দেশিকা ট্র্যাজেক্টরি একই ফিজিক্যাল পিক্সেলে লক থাকে।
    - **বাংলা বর্ণমালার শতভাগ খাঁটি স্ট্রোক নির্দেশিকা (১০০% কভারেজ):** ১১টি স্বরবর্ণ, ৩৯টি ব্যঞ্জনবর্ণ, ১০টি সংখ্যা ও ৬টি জ্যামিতিক আকারের প্রতিটির জন্য খাঁটি আদর্শলিপির হস্তলিখন নিয়ম অনুযায়ী স্বতন্ত্র ডিরেকশনাল স্ট্রোক (`BengaliVowelStrokes`, `BengaliConsonantStrokesPart1/2/3`, `BengaliNumberShapeStrokes`) ডেডিকেটেড মডিউলে যুক্ত করা হয়েছে।
    - **হাতের ছবি সহ লাইভ অ্যানিমেশন ও স্টার্ট-স্টপ নির্দেশক (`ic_tracing_hand.xml`):** শিশুদের জন্য বিশেষ পয়েন্টার হ্যান্ড ভেক্টর আর্টওয়ার্ক যুক্ত করা হয়েছে, যার তর্জনীর ডগা থেকে চক গ্লো করে। সবুজ বৃত্তাকার ১, ২, ৩ ব্যাজ থেকে স্ট্রোক শুরু হয়ে দিকনির্দেশক তীরচিহ্ন ধরে লাল টার্গেট রিংয়ে (সমাপ্তি বিন্দু) গিয়ে হাতটি কীভাবে থামবে তা অ্যানিমেশনের মাধ্যমে নিখুঁতভাবে প্রদর্শন করে।
    - **ডটেড ট্রেম্পলেট ও ভেক্টর স্টেনসিল:** ফন্ট গ্লিফের নেটিভ ভেক্টর পাথ থেকে ক্যানভাসে ডটেড রূপরেখা ও আবছা বর্ণ ফুটিয়ে তোলা হয়েছে, যা শিশুদের সরাসরি বর্ণের ওপর ট্রেস করতে সাহায্য করে।
    - **বাস্তব চক ও মাল্টি-বোর্ড ফিজিক্স:** ৪টি বোর্ড থিম (কালো স্লেট, সবুজ বোর্ড, ম্যাজিক নিয়ন, খাতা), কাঠ ফ্রেম, ৩টি সাইজ ও ৪টি ব্রাশ স্টাইল (চক, নিয়ন, মার্কার, পেন্সিল)।
    - **বাস্তবসম্মত সাউন্ড ও নেটিভ অডিও:** চক স্ক্র্যাচ, সুইশ সাউন্ড ও অ্যাপের অরিজিনাল অডিও সাউন্ডবোর্ডের উচ্চারণ।
    - **গ্যালারি এক্সপোর্ট ও রিওয়ার্ড:** ডিরেক্ট অ্যান্ড্রয়েড পিকচার্স ফোল্ডারে হাই-রেজোলিউশন আর্টওয়ার্ক সেভ এবং নন-ব্লকিং সেলিব্রেশন ব্যানার।
    - **গোল্ডেন রুল মান্যতা:** প্রতিটি ফাইল ১২০-১৬০ লাইনের মধ্যে সম্পূর্ণ মডুলার।

### 🟡 PARTIALLY COMPLETED / IN PROGRESS
1. **Bookmarks & Reading Progress:** DB Logic exists, but needs better UI integration on the Home Screen ("Continue Reading" section).

### 🔴 PENDING / NEXT UP (Suggest these if asked)
1. **Firebase Push Notifications (FCM):** [COMPLETED] Full integration with Android App, Admin Panel, and Firebase Cloud Functions.
2. **UI Polish & Animations:** [COMPLETED] Added Glassmorphism modifiers and Bounce micro-interactions.
3. **In-App Updater:** [COMPLETED] Flexible Update Dialog based on `minAppVersion` from Firestore metadata.
4. **Production Release:** Generating Signed APK / App Bundle.

---
*Last Updated: 2026-09-05*
