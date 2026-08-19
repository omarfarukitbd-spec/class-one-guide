# Implementation Plan: Premium Home Dashboard

এই প্ল্যানটি আপনার অ্যাপটিকে একটি সাধারণ পিডিএফ রিডার থেকে একটি প্রিমিয়াম ডিজিটাল লাইব্রেরিতে রূপান্তর করবে। আমরা ডিজাইনটিকে এতটাই ক্লিন এবং মডার্ন রাখবো যে ইউজার এটি ব্যবহার করে স্বাচ্ছন্দ্য বোধ করবে।

## User Review Required
> [!IMPORTANT]
> ১. আমি ডিফল্ট পার্পল কালার পরিবর্তন করে একটি **Professional Royal Blue & Deep Navy** থিম ব্যবহার করছি।
> ২. ডিজাইনে **Glassmorphism** (হালকা স্বচ্ছ কার্ড) ব্যবহার করা হবে যা বর্তমানে ট্রেন্ডিং।

## Proposed Changes

### [Component] Design System
আপনার অ্যাপের "লুক" পরিবর্তন করার জন্য আমরা থিম ফাইলগুলো আপডেট করবো।

#### [MODIFY] [Color.kt](file:///E:/Android/Porject/Class 1/app/src/main/java/com/helptrickbd/class1/ui/theme/Color.kt)
- ডিফল্ট কালারগুলো সরিয়ে প্রিমিয়াম ব্লু এবং গ্রে প্যালেট যোগ করা।

#### [MODIFY] [Type.kt](file:///E:/Android/Porject/Class 1/app/src/main/java/com/helptrickbd/class1/ui/theme/Type.kt)
- ক্লিনার টাইপোগ্রাফি সেট করা যা বাংলা এবং ইংরেজি দুটোর জন্যই উপযোগী।

### [Component] UI Screens
আমরা মূল ড্যাশবোর্ড স্ক্রিনটি তৈরি করবো।

#### [NEW] [DashboardScreen.kt](file:///E:/Android/Porject/Class 1/app/src/main/java/com/helptrickbd/class1/ui/DashboardScreen.kt)
- **Header:** Personalized greeting ("আসসালামু আলাইকুম, [User Name]") এবং প্রোফাইল পিকচার।
- **Resume Reading Card:** ইউজার শেষ যে বইটি যেখানে ছেড়েছে, তার প্রগ্রেস বার এবং প্রচ্ছদ।
- **Study Stats:** আজকের পড়ার সময় এবং স্ট্রিক কাউন্টার।
- **Subject Grid:** ক্লাস ১ থেকে ১২ পর্যন্ত সিলেক্ট করার জন্য স্মার্ট আইকনসহ লিস্ট।

## Verification Plan

### Automated Tests
- গ্র্যাডল বিল্ড রান করে নিশ্চিত করা যে কোনো এরর নেই।

### Manual Verification
- আপনি আপনার ফোনে অ্যাপটি রান করে দেখবেন স্ক্রিনের এলিমেন্টগুলো ঠিকমতো দেখা যাচ্ছে কিনা এবং ডিজাইনটি প্রিমিয়াম লাগছে কিনা।
