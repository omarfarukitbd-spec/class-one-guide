package com.helptrickbd.class1.feature.learn_hub.ui.slate

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.feature.learn_hub.domain.audio.PhonicsAudioPlayer
import com.helptrickbd.class1.feature.learn_hub.domain.audio.SlateSoundManager
import com.helptrickbd.class1.feature.learn_hub.domain.model.slate.*
import com.helptrickbd.class1.feature.learn_hub.domain.provider.SlateTracingProvider
import com.helptrickbd.class1.feature.learn_hub.domain.util.SlateBitmapSaver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlateViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val audioPlayer = PhonicsAudioPlayer(context)
    private val soundManager = SlateSoundManager(context)

    private val _uiState = MutableStateFlow(SlateUiState())
    val uiState: StateFlow<SlateUiState> = _uiState.asStateFlow()

    private val redoStack = mutableListOf<ChalkStroke>()
    private var hasCelebrated = false

    init {
        viewModelScope.launch {
            audioPlayer.currentlyPlayingId.collect { id ->
                _uiState.update { it.copy(currentlyPlayingAudioId = id) }
            }
        }
        viewModelScope.launch {
            soundManager.isSoundEnabled.collect { enabled ->
                _uiState.update { it.copy(isSoundEnabled = enabled) }
            }
        }
        playCurrentLetterAudio()
    }

    fun onToolSelect(tool: SlateTool) { _uiState.update { it.copy(activeTool = tool) } }

    fun onBrushStyleSelect(style: BrushStyle) {
        _uiState.update { it.copy(brushStyle = style, activeTool = SlateTool.CHALK) }
    }

    fun onStrokeWidthSelect(option: StrokeWidthOption) {
        _uiState.update { it.copy(strokeWidthOption = option) }
    }

    fun onColorSelect(color: Color) {
        _uiState.update { it.copy(activeColor = color, activeTool = SlateTool.CHALK) }
    }

    fun onBoardThemeSelect(theme: SlateBoardTheme) {
        _uiState.update { it.copy(boardTheme = theme) }
    }

    fun onCategorySelect(category: SlateTracingCategory) {
        val items = SlateTracingProvider.getItemsByCategory(category)
        items.firstOrNull()?.let { onTracingItemSelect(it) }
    }

    fun onTracingItemSelect(item: SlateTracingItem) {
        redoStack.clear()
        hasCelebrated = false
        _uiState.update {
            it.copy(
                selectedCategory = item.category,
                selectedTracingItem = item,
                strokes = emptyList(),
                currentStroke = null,
                canUndo = false,
                canRedo = false,
                showCelebration = false
            )
        }
        playCurrentLetterAudio()
    }

    fun playCurrentLetterAudio() {
        val item = _uiState.value.selectedTracingItem
        item.audioPath?.let { path -> audioPlayer.play(item.id, path) }
    }

    fun onStrokeStart(offset: Offset) {
        val state = _uiState.value
        val isEraser = state.activeTool == SlateTool.ERASER
        if (!isEraser) soundManager.playChalk() else soundManager.playDuster()
        val stroke = ChalkStroke(
            id = System.currentTimeMillis(),
            points = listOf(offset),
            color = if (isEraser) state.boardTheme.boardColor else state.activeColor,
            strokeWidth = state.strokeWidthOption.strokeWidth * if (isEraser) 2.2f else 1.0f,
            brushStyle = state.brushStyle,
            isEraser = isEraser
        )
        _uiState.update { it.copy(currentStroke = stroke) }
    }

    fun onStrokeDrag(offset: Offset) {
        _uiState.update { state ->
            val cur = state.currentStroke ?: return@update state
            state.copy(currentStroke = cur.copy(points = cur.points + offset))
        }
    }

    fun onStrokeEnd() {
        val state = _uiState.value
        val cur = state.currentStroke ?: return
        redoStack.clear()
        val newStrokes = state.strokes + cur
        val shouldCelebrate = !hasCelebrated && newStrokes.size >= 8 && state.selectedTracingItem.category != SlateTracingCategory.FREEHAND
        if (shouldCelebrate) {
            hasCelebrated = true
            soundManager.playCheer()
        }
        _uiState.update {
            it.copy(
                strokes = newStrokes,
                currentStroke = null,
                canUndo = true,
                canRedo = false,
                showCelebration = shouldCelebrate
            )
        }
    }

    fun undo() {
        val state = _uiState.value
        if (state.strokes.isNotEmpty()) {
            val last = state.strokes.last()
            redoStack.add(last)
            val updated = state.strokes.dropLast(1)
            soundManager.playDuster()
            _uiState.update { it.copy(strokes = updated, canUndo = updated.isNotEmpty(), canRedo = true) }
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val item = redoStack.removeAt(redoStack.lastIndex)
            val updated = _uiState.value.strokes + item
            soundManager.playChalk()
            _uiState.update { it.copy(strokes = updated, canUndo = true, canRedo = redoStack.isNotEmpty()) }
        }
    }

    fun promptClearSlate() {
        if (_uiState.value.strokes.isNotEmpty()) _uiState.update { it.copy(showClearDialog = true) }
    }

    fun confirmClearSlate() {
        redoStack.clear()
        hasCelebrated = false
        soundManager.playDuster()
        _uiState.update { it.copy(strokes = emptyList(), currentStroke = null, canUndo = false, canRedo = false, showClearDialog = false) }
    }

    fun dismissClearDialog() { _uiState.update { it.copy(showClearDialog = false) } }
    fun dismissCelebration() { _uiState.update { it.copy(showCelebration = false) } }
    fun dismissSaveSuccess() { _uiState.update { it.copy(showSaveSuccess = false) } }
    fun toggleSound() { soundManager.toggleSound() }
    fun toggleGuideAnimation() { _uiState.update { it.copy(showGuideAnimation = !it.showGuideAnimation) } }

    fun triggerCelebration() {
        soundManager.playCheer()
        _uiState.update { it.copy(showCelebration = true) }
    }

    fun saveDrawingToGallery(width: Int = 1080, height: Int = 1080) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = SlateBitmapSaver.saveSlateImage(
                context = context,
                strokes = _uiState.value.strokes,
                theme = _uiState.value.boardTheme,
                canvasWidth = width,
                canvasHeight = height
            )
            if (success) {
                _uiState.update { it.copy(showSaveSuccess = true) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
        soundManager.release()
    }
}
