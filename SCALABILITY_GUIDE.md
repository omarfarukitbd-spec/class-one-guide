# Multi-Class Scalability & White-Label Guide

এই ডকুমেন্টটি ব্যাখ্যা করে কিভাবে এই মাস্টার প্রজেক্টটি ব্যবহার করে খুব সহজে অন্য যেকোনো ক্লাসের (Class 2, 3, 4...) জন্য আলাদা আলাদা অ্যাপ তৈরি করা যাবে।

## ১. আর্কিটেকচার ভিশন (Template Architecture)
এই অ্যাপটি একটি **"Master Template"** হিসেবে ডিজাইন করা হয়েছে। এর মূল লক্ষ্য হলো—কোড পরিবর্তন না করেই শুধুমাত্র কনফিগারেশন এবং ডাটাবেস পরিবর্তন করে নতুন অ্যাপ লঞ্চ করা।

## ২. ডাইনামিক কন্টেন্ট কন্ট্রোল (Dynamic Workflow)
আপনার অ্যাপের ডাটা ফ্লো হবে নিচের মতো:
1. **PDF Hosting:** বইয়ের সব পিডিএফ ফাইল **GitHub**-এ থাকবে (যা আপনি অলরেডি প্ল্যান করেছেন)।
2. **Admin Panel:** আপনি একটি ওয়েব প্যানেল বা অ্যাপ-বেজড অ্যাডমিন প্যানেল ব্যবহার করে **Firebase Firestore**-এ ডাটা আপডেট করবেন।
   - ডাটা ফরম্যাট: `{"class": "Class 2", "subject": "Math", "pdfUrl": "github_link_here"}`
3. **App Side:** অ্যাপটি ওপেন হওয়ার সময় তার নিজস্ব `CLASS_ID` অনুযায়ী ফায়ারবেস থেকে সব ডাটা রিয়েল-টাইমে নিয়ে আসবে।

## ৩. নতুন ক্লাসের জন্য অ্যাপ তৈরির ধাপ (Cloning Guide)
যখন আপনি Class 1 থেকে Class 2 অ্যাপ বানাতে চাইবেন:

### ক. গ্লোবাল কনফিগারেশন পরিবর্তন
`core/config/AppConfig.kt` (অথবা SSOT ফাইল) থেকে নিচের ভ্যালুগুলো পরিবর্তন করতে হবে:
```kotlin
val TARGET_CLASS_ID = "class_2"
val APP_PRIMARY_COLOR = Color(0xFF...) // যদি আলাদা কালার চান
```

### খ. প্যাকেজ নেম ও স্ট্রিংস
- `build.gradle.kts`-এ গিয়ে `applicationId` পরিবর্তন করতে হবে (যেমন: `com.helptrickbd.class2`).
- `strings.xml`-এ অ্যাপের নাম পরিবর্তন করতে হবে।

## ৪. এজেন্টদের জন্য বিশেষ নির্দেশ (Instructions for AI Agents)
ভবিষ্যতে যেকোনো এজেন্ট যখন এই প্রজেক্টে কাজ করবে, তারা নিচের নিয়মগুলো মেনে চলতে বাধ্য:
- **No Hardcoding:** কখনোই কোনো স্ক্রিনে সরাসরি ক্লাসের নাম বা পিডিএফ লিংক হার্ডকোড করা যাবে না। সবসময় `AppConfig` বা `Repository` থেকে ডাটা নিতে হবে।
- **Generic Components:** এমনভাবে UI কম্পোনেন্ট বানাতে হবে যাতে তা যেকোনো বিষয়ের (Subject) জন্য মানানসই হয়।
- **Global Themes:** থিমের কালার এবং স্টাইল সবসময় `Theme.kt` থেকে আসতে হবে যাতে এক জায়গায় পরিবর্তন করলে পুরো অ্যাপে তা কাজ করে।

## ৫. ফিউচার রোডম্যাপ
- [ ] Firebase Firestore integration for real-time updates.
- [ ] GitHub direct PDF stream support.
- [ ] Centralized Analytics for all class apps.
