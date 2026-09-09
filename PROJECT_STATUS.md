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
23. **World-Class Digital Slate & Pure Chalkboard Architecture (বিশ্বমানের ডিজিটাল স্লেট ও বিশুদ্ধ চক-বোর্ড ড্রয়িং সম্পন্ন)** - শিশুদের বিভ্রান্তিকর হাত ঘোরানো বা কৃত্রিম নির্দেশনা ব্যবস্থা সম্পূর্ণরূপে অপসারিত করে বাস্তবসম্মত ও পরিচ্ছন্ন স্লেট-চক অভিজ্ঞতা নিশ্চিত করা হয়েছে:
    - **আসল বাংলা বর্ণমালা ও আকার স্টেনসিল (`SlateCanvas`):** স্লেটের ব্যাকগ্রাউন্ডে খাঁটি বাংলা ফন্টের স্পষ্ট, সুন্দর ও মার্জিত চক-স্টেনসিল ফুটিয়ে তোলা হয়েছে, যাতে শিশুরা বর্ণের ওপর স্বাধীনভাবে চক চালাতে পারে।
    - **বাস্তব চক ও মাল্টি-বোর্ড ফিজিক্স:** ৪টি বোর্ড থিম (ক্লাসিক ব্ল্যাকবোর্ড, গ্রিন বোর্ড, ম্যাজিক নিয়ন, আর্ট পেপার), ৩টি সাইজ এবং ৪টি ব্রাশ স্টাইল (চক, নিয়ন, মার্কার, পেন্সিল)।
    - **সম্পূর্ণ ফিচার সমৃদ্ধ ও ল্যাগ-মুক্ত:** আনডু, রিডু, স্লেট পরিষ্কারের কনফার্মেশন ডায়ালগ, চক স্ক্র্যাচ ও সুইশ সাউন্ড এফেক্ট, রিওয়ার্ড সেলিব্রেশন অ্যানিমেশন এবং হাই-রেজোলিউশন পিকচার সেভিং।
    - **জিরো ড্যাংলার ও গোল্ডেন রুল মান্যতা:** নির্দেশনা সংক্রান্ত সমস্ত অপ্রয়োজনীয় ফাইল ও ওভারলে সম্পূর্ণ ডিলিট করা হয়েছে এবং কোডবেস ১০০% ক্লিন ও ত্রুটিমুক্ত।
24. **Bornoporichoy APK Reverse Engineering & Asset Organization (বর্ণপরিচয় অ্যাপ ডিকম্পাইল ও পূর্ণাঙ্গ এসেট লাইব্রেরি সম্পন্ন)** - বর্ণপরিচয় XAPK (`বর্ণপরিচয়+–+Bangla+Alphabet_4.5.2.6_APKPure.xapk`) সফলভাবে আনপ্যাক করে JADX দিয়ে ১০০% সোর্স কোড ও রিসোর্স ডিকম্পাইল করা হয়েছে। অডিও (৬১টি বর্ণ উচ্চারণ, ৩৯টি ছড়া, ২৫টি কুইজ সাউন্ড), ৫২টি ইলাস্ট্রেশন এবং ৫টি বাংলা ফন্ট `decompiled_bornoporichoy/organized_assets/`-এ সুশৃঙ্খলভাবে সাজানো হয়েছে। অ্যাপটির Jetpack Compose আর্কিটেকচার, ড্যাশড স্টেনসিল স্লেট, SoundPool অডিও ইঞ্জিন এবং কুইজ গেমের বিস্তারিত স্টাডি গাইড `APP_ARCHITECTURE_AND_STUDY_GUIDE.md` ফাইলে যুক্ত করা হয়েছে।
25. **Reading Analytics & Chapter Completion Architecture (রিডিং অ্যানালিটিক্স ও অধ্যায় বুকমার্ক টিক সম্পন্ন)** - প্রতিটি অধ্যায়ের পড়া শেষ হলে অধ্যায় কার্ডে সবুজ টিক চিহ্নযুক্ত বুকমার্ক (`Icons.Rounded.BookmarkAdded`), ব্যবহারকারীর জন্য ক্লিক করে টগলের স্বাধীনতা, পিডিএফ রিডারে শেষ পৃষ্ঠায় পৌঁছালে স্বয়ংক্রিয় কমপ্লিশন সেভ, সাবজেক্ট ডিটেইল পেজে রিয়েলটাইম ভিজ্যুয়াল অ্যানালিটিক্স ইন্ডিকেটর (`SubjectReadingAnalyticsCard`), বড় বাংলা সংখ্যায় শতকরা হার ও অনুপাত কাউন্টার, এবং রুম ডাটাবেজ (SSOT)-এর মাধ্যমে হোম স্ক্রিনের কার্ডসমূহে (`BookListCard`, `BookGridCard`, `ResumeReadingSection`) স্বয়ংক্রিয় প্রোগ্রেস আপডেট শতভাগ সম্পন্ন হয়েছে।
26. **Repository Cleanliness & Firebase Cache Exclusion (.gitignore ও ক্যাশ নিয়ন্ত্রণ সম্পন্ন)** - ফায়ারবেস ডিপ্লয়মেন্ট ক্যাশ (`.firebase/hosting.*.cache`) এবং ডিবাগ লগসমূহ গিট ট্র্যাকিং থেকে বাদ দিয়ে `.gitignore`-এ `.firebase/` যুক্ত করা হয়েছে এবং গিট ইনডেক্স থেকে ক্যাশ ফাইলটি সম্পূর্ণ আনট্র্যাক করে রিপোজিটরি পরিচ্ছন্ন রাখা হয়েছে।

### 🟡 PARTIALLY COMPLETED / IN PROGRESS
*(বর্তমানে কোনো আংশিক কাজ পেন্ডিং নেই। সকল কোর মডিউল শতভাগ কার্যকর।)*

### 🔴 PENDING / NEXT UP (Suggest these if asked)
1. **Kids Zone Bengali Quiz & Sound Game:** বর্ণপরিচয় ও সাউন্ডবোর্ড অডিওর উপর ভিত্তি করে ছোটদের জন্য ইন্টারেক্টিভ কুইজ গেম ও সাউন্ড পাজল যুক্ত করা।
2. **Daily Study Streak & Motivational Badges:** শিক্ষার্থীদের প্রতিদিন পড়ার উৎসাহ দিতে স্টাডি স্ট্রিক ও অর্জন মেডেল সিস্টেম।
3. **Production Release:** Generating Signed APK / App Bundle for Google Play Store.

---
*Last Updated: 2026-09-09*
