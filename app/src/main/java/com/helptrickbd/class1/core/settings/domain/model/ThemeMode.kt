package com.helptrickbd.class1.core.settings.domain.model

import androidx.compose.runtime.Immutable

@Immutable
enum class ThemeMode(val titleBangla: String) {
    SYSTEM("সিস্টেম ডিফল্ট"),
    LIGHT("লাইট মোড"),
    DARK("ডার্ক মোড")
}
