# 🌐 মাল্টি-ক্লাস স্কেলেবিলিটি, সেন্ট্রালাইজড অ্যাডমিন প্যানেল ও রিমোট সিএমএস আর্কিটেকচার ব্লুপ্রিন্ট

এই ডকুমেন্টটি ব্যাখ্যা করে কিভাবে এই **Class 1 Guide** মাস্টার প্রজেক্টটি ব্যবহার করে বাংলাদেশের প্রথম শ্রেণি থেকে দশম শ্রেণি (SSC) পর্যন্ত সকল ক্লাসের জন্য আলাদা আলাদা স্বতন্ত্র অ্যাপ তৈরি করা যাবে এবং একটি **কেন্দ্রীভূত গ্লোবাল অ্যাডমিন প্যানেল (Global Admin Panel / Remote CMS)** থেকে সকল অ্যাপের পিডিএফ, বই, ইউনিট ও লেসন সম্পূর্ণ ডাইনামিকালি নিয়ন্ত্রণ করা যাবে।

---

## ১. আর্কিটেকচার ভিশন ও কোর দর্শন (Core Vision)

```
                       ┌────────────────────────────────────────────────────────┐
                       │     🌐 সেন্ট্রালাইজড অ্যাডমিন প্যানেল (Web CMS)        │
                       │  - নতুন বই ও পিডিএফ আপলোড / এডিট / ডিলিট              │
                       │  - নতুন অধ্যায় (Unit) ও পাঠ (Lesson) যুক্ত করা          │
                       │  - সকল ক্লাসে ইনস্ট্যান্ট নোটিশ ও আপডেট পুশ           │
                       └───────────────────────────┬────────────────────────────┘
                                                   │
                                                   ▼
                       ┌────────────────────────────────────────────────────────┐
                       │     ☁️ ক্লাউড ডাটাবেস (Cloud Firestore / Backend)      │
                       │     Collection: /nctb_classes/{classId}/books          │
                       └───────────────────────────┬────────────────────────────┘
                                                   │ (Background Delta Sync)
              ┌────────────────────────────────────┼────────────────────────────────────┐
              ▼                                    ▼                                    ▼
   📱 Class 1 Guide App                 📱 Class 2 Guide App                 📱 Class 9-10 Guide App
┌───────────────────────────┐        ┌───────────────────────────┐        ┌───────────────────────────┐
│ • Local Room Database     │        │ • Local Room Database     │        │ • Local Room Database     │
│ • Native PDF Streamer     │        │ • Native PDF Streamer     │        │ • Native PDF Streamer     │
│ • Core Feature Set        │        │ • Core Feature Set        │        │ • + MCQ Quiz Module       │
│ • 100% Offline Usability  │        │ • 100% Offline Usability  │        │ • + Formula Sheets        │
└───────────────────────────┘        └───────────────────────────┘        └───────────────────────────┘
```

---

## ২. সেন্ট্রালাইজড অ্যাডমিন প্যানেল ও ক্লাউড ডাটা স্কিমা (Admin Panel Schema)

অ্যাডমিন প্যানেল থেকে আপনি যেকোনো সময় যেকোনো ক্লাসের ডাটা রিয়েল-টাইমে ম্যানেজ করতে পারবেন। কোনো অ্যাপ পুনরায় প্লে স্টোরে আপডেট না দিয়েও বইয়ের ভুল পিডিএফ লিংক পরিবর্তন, নতুন চ্যাপ্টার যুক্ত করা বা অপ্রয়োজনীয় বই মুছে ফেলা সম্ভব।

### 🗄️ Firestore ডাটা স্ট্রাকচার (Cloud Schema SSOT):

```
📂 nctb_classes (Root Collection)
 └── 📄 class_1 (Class Document)
      └── 📂 books (Collection)
           └── 📄 book_bangla_1 (Book Document)
                ├── bookId: "book_bangla_1"
                ├── title: "আমার বাংলা বই"
                ├── subtitle: "প্রথম শ্রেণি"
                ├── pdfUrl: "https://raw.githubusercontent.com/.../bangla.pdf"
                ├── coverUrl: "https://.../cover.png"
                ├── curriculum: "SCHOOL" // SCHOOL বা MADRASAH
                ├── availableVersions: ["BANGLA", "ENGLISH"]
                ├── lastModified: 1771500000000 (Timestamp)
                └── 📂 chapters (Subcollection)
                     └── 📄 chapter_unit_1
                          ├── chapterId: "unit_1"
                          ├── unitNo: "ইউনিট ১"
                          ├── title: "আমাদের কথা ও ছবি দেখি"
                          ├── version: "BANGLA"
                          ├── orderIndex: 1
                          └── resources: [
                                { "title": "মূল বই পড়ুন", "pdfUrl": "...", "type": "TEXTBOOK" },
                                { "title": "গাইডবুক পড়ুন", "pdfUrl": "...", "type": "GUIDEBOOK" }
                              ]
```

### 🎛️ অ্যাডমিন প্যানেলের ক্ষমতা ও কার্যক্রম:
1. **বইয়ের সম্পূর্ণ নিয়ন্ত্রণ (Book CRUD):** যেকোনো ক্লাসের নতুন বই এন্ট্রি, কভার ছবি পরিবর্তন, শিরোনাম সংশোধন বা এক ক্লিকে বই ডিলিট করা।
2. **চ্যাপ্টার ও লেসন ম্যানেজমেন্ট (Chapter & Resource CRUD):** বইয়ের ভেতরে নতুন অধ্যায় যুক্ত করা, লেসনের নাম ও পিডিএফ লিংক আপডেট করা।
3. **সংস্করণ সাপোর্ট (Language Versioning):** বাংলা ভার্সন এবং ইংরেজি ভার্সনের জন্য আলাদা আলাদা চ্যাপ্টার ও পিডিএফ লিংক যুক্ত করা।
4. **ইনস্ট্যান্ট পুশ নোটিফিকেশন ও নোটিশ বোর্ড:** পরীক্ষার সময় বা নতুন বই আসলে সকল ক্লাসের ছাত্র-ছাত্রীদের মোবাইল অ্যাপে ডাইনামিক নোটিশ পুশ করা।

---

## ৩. ক্লাউড-টু-রুম অফলাইন ফার্স্ট সিঙ্ক ইঞ্জিন (Cloud-to-Room Sync Pipeline)

অ্যাপটি **Offline-First** আর্কিটেকচার মেনে চলে। ব্যবহারকারী অনলাইনে থাকলে ব্যাকগ্রাউন্ডে ক্লাউড থেকে ডাটা সিঙ্ক হবে, কিন্তু অফলাইনে থাকলেও অ্যাপ ১০০% নিরবচ্ছিন্নভাবে চলবে:

```
[App Starts / Network Connected]
              │
              ▼
[Check Remote Firestore Metadata] ──► (কোনো পরিবর্তন আছে কি?)
              │                                │
             হ্যাঁ                             না
              │                                │
              ▼                                ▼
[Fetch Updated Books/Chapters]          [Use Cached Room DB]
              │                                │
              ▼                                │
[Insert/Update into Room DB (SSOT)]            │
              │                                │
              └───────────────┬────────────────┘
                              ▼
                   [UI Reacts Instantly]
```

---

## ৪. ক্লাস-অনুযায়ী প্লাগঅ্যাবল ফিচার ম্যাট্রিক্স (Class Feature Matrix)

ছোট ক্লাসের অ্যাপ এবং বড় ক্লাসের অ্যাপের ফিচার এক হবে না। তাই `AppConfig.kt`-এ **Pluggable Feature Flags** রাখা হয়েছে যাতে প্রতিটি ক্লাসের জন্য প্রয়োজন অনুযায়ী মডিউল অন/অফ করা যায়:

| ফিচার / মডিউল | ক্লাস ১ – ৫ (প্রাথমিক) | ক্লাস ৬ – ৮ (জুনিয়র) | ক্লাস ৯ – ১০ / SSC (মাধ্যমিক) | কন্ট্রোল সুইচ (`AppConfig.kt`) |
| :--- | :---: | :---: | :---: | :--- |
| **Native PDF Reader** |  |  |  | `FEATURE_PDF_READER = true` |
| **Offline Cache Manager** |  |  |  | `FEATURE_OFFLINE_CACHE = true` |
| **Reading Progress Tracker**|  |  |  | `FEATURE_READING_PROGRESS = true` |
| **Language Switcher (Bangla/Eng)**|  |  |  | `FEATURE_LANGUAGE_SWITCH = true` |
| **MCQ Practice & Quizzes** | ❌ |  |  | `FEATURE_MCQ_QUIZ = true/false` |
| **Video Classes / Animations** | ❌ |  |  | `FEATURE_VIDEO_CLASSES = true/false` |
| **Formula & Math Cheat Sheet**| ❌ | ❌ |  | `FEATURE_FORMULA_SHEET = true/false` |
| **Board Question Solutions** | ❌ | ❌ |  | `FEATURE_BOARD_QUESTIONS = true/false` |

> [!TIP]
> বড় ক্লাসের জন্য যখন কোনো নতুন ফিচার (যেমন: MCQ কুইজ) তৈরি করবেন, তা `feature/mcq_quiz` প্যাকেজে আলাদাভাবে তৈরি করবেন। এর ফলে ছোট ক্লাসের অ্যাপে কোনো অপ্রয়োজনীয় কোড বা বাগ প্রবেশ করবে না।

---

## ৫. মাত্র ৫ মিনিটে নতুন ক্লাসের অ্যাপ তৈরির গাইড (Step-by-Step Cloning Blueprint)

যখন আপনি Class 1 থেকে Class 2 বা Class 9 এর অ্যাপ তৈরি করবেন:

### ধাপ ১: `AppConfig.kt` পরিবর্তন (১ম মিনিট)
`core/config/AppConfig.kt`-এ গিয়ে শুধুমাত্র ক্লাসের আইডি ও ফিচার ফ্ল্যাগ সেট করুন:
```kotlin
const val TARGET_CLASS_ID = "class_2" // অথবা "class_9"
const val CLASS_NAME_BANGLA = "দ্বিতীয় শ্রেণি"
const val APP_NAME = "Class 2 Guide"
const val FEATURE_MCQ_QUIZ = false // বড় ক্লাসের জন্য true
```

### ধাপ ২: প্যাকেজ ও অ্যাপ আইডি পরিবর্তন (২য় মিনিট)
- `app/build.gradle.kts`-এ:
  ```kotlin
  applicationId = "com.helptrickbd.class2"
  ```
- `app/src/main/res/values/strings.xml`-এ:
  ```xml
  <string name="app_name">Class 2 Guide</string>
  ```

### ধাপ ৩: ডাটাবেস ইনিশিয়ালাইজেশন (৩য় মিনিট)
- `feature/home/data/datasource/` ফোল্ডারে Class 2-এর প্রাথমিক অফলাইন বইয়ের তালিকা (`SchoolBooksData.kt`) দিয়ে দিন (যা ১ম বার চালুর সময় Room-এ সিড হবে)।
- অথবা ক্লাউড ফায়ারবেসে `class_2` ডকুমেন্টের আন্ডারে বই এন্ট্রি দিন।

### ধাপ ৪: থিম কালার বা ব্র্যান্ডিং (ঐচ্ছিক - ৪র্থ মিনিট)
- চাইলে `Color.kt` ও `Theme.kt`-এ প্রাইমারি কালার সামান্য পরিবর্তন করে প্রতিটি ক্লাসের আলাদা ভিজ্যুয়াল ব্র্যান্ডিং দিতে পারেন।

### ধাপ ৫: বিল্ড ও রিলিজ (৫ম মিনিট)
- `./gradlew assembleRelease` রান করে সরাসরি প্লে স্টোরে পাবলিশ করুন!

---

## ৬. সকল এআই এজেন্ট ও ডেভেলপারদের জন্য বাধ্যতামূলক রুলস (Mandatory Rules)

ভবিষ্যতে যেকোনো ডেভেলপার বা এআই এজেন্ট এই প্রজেক্টে বা এর ক্লোন করা প্রজেক্টে কাজ করার সময় নিচের নিয়মগুলো কঠোরভাবে মেনে চলবে:

1. **জিরো হার্ডকোডিং (Zero Hardcoding):** কোনো স্ক্রিন বা কম্পোজেবলে সরাসরি ক্লাসের নাম বা পিডিএফ লিংক হার্ডকোড করা সম্পূর্ণ নিষিদ্ধ। সবসময় `AppConfig.kt` বা `Repository` থেকে ডাটা নিতে হবে।
2. **মডিউলার ও প্লাগঅ্যাবল ডিজাইন:** কোনো নতুন ক্লাস-স্পেসিফিক ফিচার যুক্ত করলে তা আলাদা `feature/` প্যাকেজে করতে হবে এবং `AppConfig.FEATURE_*` দিয়ে নিয়ন্ত্রিত হতে হবে।
3. **রুম ডাটাবেসকে SSOT রাখা:** দূরবর্তী ক্লাউড ডাটা যাই আসুক না কেন, তা আগে লোকাল Room Database-এ সেভ হবে এবং UI সবসময় লোকাল রুম ডাটাবেসের `Flow`-কে পর্যবেক্ষণ করবে।
4. **১০০% বাংলায় রিপোর্টিং ও ডকুমেন্টেশন:** সমস্ত প্ল্যান, ওয়াকথ্রু এবং এজেন্ট আউটপুট সম্পূর্ণ বাংলায় উপস্থাপন করতে হবে।
