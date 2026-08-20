package com.helptrickbd.class1.feature.drawing.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem
import com.helptrickbd.class1.feature.drawing.domain.usecase.GetTracingItemsUseCase
import com.helptrickbd.class1.feature.drawing.ui.model.DrawingPath
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val getTracingItemsUseCase: GetTracingItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DrawingUiState())
    val uiState: StateFlow<DrawingUiState> = _uiState.asStateFlow()

    init {
        loadCategory(TracingCategory.BANGLA_VOWEL)
    }

    fun selectCategory(category: TracingCategory) {
        if (_uiState.value.selectedCategory == category) return
        loadCategory(category)
    }

    private fun loadCategory(category: TracingCategory) {
        val items = getTracingItemsUseCase(category)
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            items = items,
            selectedItem = items.firstOrNull(),
            paths = emptyList()
        )
    }

    fun selectItem(item: TracingItem) {
        _uiState.value = _uiState.value.copy(
            selectedItem = item,
            paths = emptyList()
        )
    }

    fun nextItem() {
        val currentState = _uiState.value
        val items = currentState.items
        if (items.isEmpty()) return

        val currentIndex = items.indexOf(currentState.selectedItem)
        if (currentIndex != -1 && currentIndex < items.size - 1) {
            selectItem(items[currentIndex + 1])
        }
    }

    fun previousItem() {
        val currentState = _uiState.value
        val items = currentState.items
        if (items.isEmpty()) return

        val currentIndex = items.indexOf(currentState.selectedItem)
        if (currentIndex > 0) {
            selectItem(items[currentIndex - 1])
        }
    }

    fun addPath(path: DrawingPath) {
        val updatedPaths = _uiState.value.paths + path
        _uiState.value = _uiState.value.copy(paths = updatedPaths)
    }

    fun selectColor(color: Color) {
        _uiState.value = _uiState.value.copy(
            selectedColor = color,
            isEraser = false
        )
    }

    fun setStrokeWidth(width: Float) {
        _uiState.value = _uiState.value.copy(strokeWidth = width)
    }

    fun toggleEraser() {
        _uiState.value = _uiState.value.copy(isEraser = !_uiState.value.isEraser)
    }

    fun clearCanvas() {
        _uiState.value = _uiState.value.copy(paths = emptyList())
    }

    fun toggleGuide() {
        _uiState.value = _uiState.value.copy(showGuide = !_uiState.value.showGuide)
    }
}
