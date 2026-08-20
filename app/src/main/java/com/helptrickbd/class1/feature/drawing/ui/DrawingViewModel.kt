package com.helptrickbd.class1.feature.drawing.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.audio.SoundFxHelper
import com.helptrickbd.class1.core.audio.StudioAudioEngine
import com.helptrickbd.class1.feature.drawing.domain.model.CelebrationState
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem
import com.helptrickbd.class1.feature.drawing.domain.usecase.GetTracingItemsUseCase
import com.helptrickbd.class1.feature.drawing.ui.model.DrawingPath
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val getTracingItemsUseCase: GetTracingItemsUseCase,
    private val studioAudioEngine: StudioAudioEngine,
    private val soundFxHelper: SoundFxHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(DrawingUiState())
    val uiState: StateFlow<DrawingUiState> = _uiState.asStateFlow()

    init {
        loadCategory(TracingCategory.BANGLA_VOWEL)
        viewModelScope.launch {
            studioAudioEngine.isSpeaking.collect { speaking ->
                _uiState.value = _uiState.value.copy(isSpeaking = speaking)
            }
        }
    }

    fun selectCategory(category: TracingCategory) {
        if (_uiState.value.selectedCategory == category) return
        loadCategory(category)
    }

    private fun loadCategory(category: TracingCategory) {
        val items = getTracingItemsUseCase(category)
        val firstItem = items.firstOrNull()
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            items = items,
            selectedItem = firstItem,
            paths = emptyList(),
            celebrationState = CelebrationState(isCelebrating = false)
        )
        if (firstItem != null && _uiState.value.isAutoSpeechEnabled) {
            speakItem(firstItem)
        }
    }

    fun selectItem(item: TracingItem) {
        _uiState.value = _uiState.value.copy(
            selectedItem = item,
            paths = emptyList(),
            celebrationState = CelebrationState(isCelebrating = false)
        )
        if (_uiState.value.isAutoSpeechEnabled) {
            speakItem(item)
        }
    }

    fun nextItem() {
        val items = _uiState.value.items
        if (items.isEmpty()) return
        val currentIndex = items.indexOf(_uiState.value.selectedItem)
        if (currentIndex != -1 && currentIndex < items.size - 1) {
            selectItem(items[currentIndex + 1])
        }
    }

    fun previousItem() {
        val items = _uiState.value.items
        if (items.isEmpty()) return
        val currentIndex = items.indexOf(_uiState.value.selectedItem)
        if (currentIndex > 0) {
            selectItem(items[currentIndex - 1])
        }
    }

    fun speakCurrentItem() {
        _uiState.value.selectedItem?.let { speakItem(it) }
    }

    private fun speakItem(item: TracingItem) {
        viewModelScope.launch {
            studioAudioEngine.playTracingItemAudio(item)
        }
    }

    fun triggerCelebration() {
        val isEnglish = _uiState.value.selectedCategory == TracingCategory.ENGLISH_ALPHABET || _uiState.value.selectedCategory == TracingCategory.ENGLISH_NUMBER
        _uiState.value = _uiState.value.copy(
            celebrationState = CelebrationState(
                isCelebrating = true,
                starsEarned = 3,
                praiseMessage = if (isEnglish) "Well done! Excellent!" else "সাবাশ! চমৎকার হয়েছে!"
            )
        )
        viewModelScope.launch {
            soundFxHelper.playVictoryChime()
            studioAudioEngine.playPraiseAudio(isEnglish)
            delay(3200)
            _uiState.value = _uiState.value.copy(
                celebrationState = CelebrationState(isCelebrating = false)
            )
        }
    }

    fun dismissCelebration() {
        _uiState.value = _uiState.value.copy(celebrationState = CelebrationState(isCelebrating = false))
    }

    fun addPath(path: DrawingPath) {
        _uiState.value = _uiState.value.copy(paths = _uiState.value.paths + path)
    }

    fun selectColor(color: Color) {
        _uiState.value = _uiState.value.copy(selectedColor = color, isEraser = false)
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

    override fun onCleared() {
        super.onCleared()
        studioAudioEngine.stop()
    }
}
