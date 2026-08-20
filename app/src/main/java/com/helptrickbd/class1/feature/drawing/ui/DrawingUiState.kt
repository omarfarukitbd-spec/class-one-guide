package com.helptrickbd.class1.feature.drawing.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem
import com.helptrickbd.class1.feature.drawing.ui.model.DrawingPath

@Immutable
data class DrawingUiState(
    val categories: List<TracingCategory> = TracingCategory.entries,
    val selectedCategory: TracingCategory = TracingCategory.BANGLA_VOWEL,
    val items: List<TracingItem> = emptyList(),
    val selectedItem: TracingItem? = null,
    val paths: List<DrawingPath> = emptyList(),
    val selectedColor: Color = Color(0xFFFFEB3B), // Kids love bright yellow chalk
    val strokeWidth: Float = 14f,
    val isEraser: Boolean = false,
    val showGuide: Boolean = true
)
