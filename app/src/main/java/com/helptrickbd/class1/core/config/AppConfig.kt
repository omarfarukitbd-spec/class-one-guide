package com.helptrickbd.class1.core.config

import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.LanguageVersion

/**
 * Single Source of Truth (SSOT) for App-wide Configuration, Multi-Class Scaling & Cloud Admin Panel.
 * 
 * To clone this codebase for other classes (Class 2, 3, 4 ... 10), update these settings.
 */
object AppConfig {
    
    // ==========================================
    // 1. Target Class Identifier & Metadata
    // ==========================================
    const val TARGET_CLASS_ID = "class_1"
    const val CLASS_NAME_BANGLA = "প্রথম শ্রেণি"
    const val ACADEMIC_YEAR = "২০২৬"
    const val APP_NAME = "Class 1 Guide"
    const val DEFAULT_USER_NAME = "শিক্ষার্থী"
    
    // ==========================================
    // 2. Default Curriculum & Language Settings
    // ==========================================
    val DEFAULT_CURRICULUM = Curriculum.SCHOOL
    val DEFAULT_LANGUAGE_VERSION = LanguageVersion.BANGLA
    
    // ==========================================
    // 3. Remote Storage & Cloud Admin Sync Engine
    // ==========================================
    // Base CDN / GitHub Storage for PDF Streaming
    const val PDF_STORAGE_BASE_URL = "https://raw.githubusercontent.com/omarfarukitbd-spec/class-one-guide/main/pdfs/"
    
    // Cloud Firestore Collection Structure (Global Admin Panel SSOT)
    const val CLOUD_ROOT_COLLECTION = "classes"
    const val CLOUD_BOOKS_COLLECTION = "books"
    const val CLOUD_CHAPTERS_COLLECTION = "chapters"
    
    // ==========================================
    // 4. Class-Specific Pluggable Feature Flags
    // ==========================================
    // Core Features (Available in Class 1)
    const val FEATURE_PDF_READER = true
    const val FEATURE_OFFLINE_CACHE = true
    const val FEATURE_READING_PROGRESS = true
    const val FEATURE_DARK_MODE = true
    const val FEATURE_NAVIGATION_DRAWER = true
    const val FEATURE_CLOUD_SYNC = true
    const val FEATURE_SEARCH = true
    const val SEARCH_DEBOUNCE_MS = 250L             // 250ms debounce for lightning fast search without UI frame drops
    const val SEARCH_MIN_QUERY_LENGTH = 1
    const val SEARCH_MAX_SUGGESTIONS = 8
    const val FEATURE_PDF_BOOKMARKS = true
    const val FEATURE_PDF_THUMBNAIL_PREVIEW = true
    const val FEATURE_PDF_READING_THEMES = true
    const val FEATURE_PDF_HORIZONTAL_SCROLL = true
    const val FEATURE_LAYOUT_SWITCHER = true
    const val FEATURE_PUSH_NOTIFICATIONS = true
    const val FEATURE_IN_APP_INBOX = true
    const val FEATURE_DAILY_STUDY_REMINDER = true
    const val FEATURE_CRASHLYTICS = true
    const val FEATURE_ANALYTICS = true
    const val FEATURE_KIDS_ZONE = true
    const val FEATURE_PHONICS_AUDIO = true
    const val FEATURE_SLATE_CANVAS = true

    val DEFAULT_LAYOUT_MODE = com.helptrickbd.class1.feature.home.domain.model.LayoutMode.GRID
    
    // Notification & FCM Topics (SSOT)
    const val FCM_TOPIC_CLASS = "nctb_class_1"
    const val FCM_TOPIC_GLOBAL = "nctb_all_classes"
    
    // ==========================================
    // 5. Enterprise Security, DRM & Anti-Theft Protection
    // ==========================================
    // অ্যান্ড্রয়েড স্টুডিওতে প্রিভিউ / ডিভাইস মিররিং দেখার জন্য false রাখুন।
    // প্লে স্টোর রিলিজ বিল্ডের সময় true করে দিলে ১০০% অ্যান্টি-স্ক্রিনশট সক্রিয় হবে।
    const val FLAG_SECURE_ENABLED = false

    const val ROOT_DETECTION_ENABLED = true          // Proactive Root & Magisk integrity check
    const val FRIDA_TAMPER_PROTECTION_ENABLED = true  // Anti-Hooking & Memory tampering shield
    const val ENCRYPTED_PDF_CACHE_ENABLED = true     // AES-256 GCM in-memory & scoped cache protection
    const val SECURE_MEMORY_SHREDDING_ENABLED = true // Auto-zeroing RAM buffers on session close
}
