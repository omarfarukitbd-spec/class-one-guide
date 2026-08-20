package com.helptrickbd.class1.core.config

/**
 * Single Source of Truth (SSOT) for App-wide Configuration.
 * 
 * To clone this app for Class 2, 3, etc., update these values.
 */
object AppConfig {
    
    // Target Class Identifier (Used for Firebase Filtering)
    const val TARGET_CLASS_ID = "class_1"
    
    // UI Configuration
    const val APP_NAME = "Class 1 Guide"
    const val DEFAULT_USER_NAME = "ছাত্র/ছাত্রী"
    
    // Remote Storage Root (GitHub PDF Hosting Base URL)
    const val PDF_STORAGE_BASE_URL = "https://raw.githubusercontent.com/omarfarukitbd-spec/class-one-guide/main/pdfs/"

    // Feature Toggles
    const val ENABLE_DARK_MODE = true
    const val ENABLE_ADMIN_PANEL = true
    const val SHOW_STUDY_STREAK = true
}
