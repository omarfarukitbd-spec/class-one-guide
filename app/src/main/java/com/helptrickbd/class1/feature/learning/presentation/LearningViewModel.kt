package com.helptrickbd.class1.feature.learning.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.audio.StudioAudioEngine
import com.helptrickbd.class1.feature.learning.domain.model.DrawingPath
import com.helptrickbd.class1.feature.learning.domain.model.LearningCategory
import com.helptrickbd.class1.feature.learning.domain.model.LearningItem
import com.helptrickbd.class1.feature.learning.domain.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val repository: LearningRepository,
    private val audioEngine: StudioAudioEngine
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(LearningCategory.VOWEL)
    private val _selectedItem = MutableStateFlow<LearningItem?>(null)
    private val _paths = MutableStateFlow<List<DrawingPath>>(emptyList())
    private val _selectedColor = MutableStateFlow(Color.Black)
    private val _isEraser = MutableStateFlow(false)
    
    // Quiz Internal State
    private val _quizOptions = MutableStateFlow<List<LearningItem>>(emptyList())
    private val _correctOption = MutableStateFlow<LearningItem?>(null)
    private val _score = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<LearningUiState> = combine(
        _selectedCategory,
        _selectedItem,
        _paths,
        _selectedColor,
        _isEraser,
        audioEngine.isSpeaking,
        _quizOptions,
        _correctOption,
        _score
    ) { args ->
        val category = args[0] as LearningCategory
        val item = args[1] as? LearningItem
        val paths = args[2] as List<DrawingPath>
        val color = args[3] as Color
        val eraser = args[4] as Boolean
        val speaking = args[5] as Boolean
        val quizOps = args[6] as List<LearningItem>
        val correct = args[7] as? LearningItem
        val score = args[8] as Int

        val items = repository.getItemsByCategory(category).first()
        LearningUiState.Success(
            categories = LearningCategory.entries,
            selectedCategory = category,
            items = items,
            selectedItem = item ?: items.firstOrNull(),
            paths = paths,
            selectedColor = color,
            isEraser = eraser,
            isSpeaking = speaking,
            quizOptions = quizOps,
            correctOption = correct,
            score = score
        ) as LearningUiState
    }
    .catch { e -> emit(LearningUiState.Error(e.message ?: "Unknown Error")) }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LearningUiState.Loading
    )

    fun selectCategory(category: LearningCategory) {
        _selectedCategory.value = category
        _selectedItem.value = null 
        clearCanvas()
    }

    fun selectItem(item: LearningItem) {
        _selectedItem.value = item
        speakCurrentItem()
    }

    fun speakCurrentItem() {
        val item = _selectedItem.value ?: return
        val text = if (!item.wordMeaning.isNullOrBlank()) {
            "${item.character}, ${item.wordExample}। ${item.wordMeaning}"
        } else if (!item.wordExample.isNullOrBlank()) {
            "${item.character}, ${item.wordExample}"
        } else {
            item.character
        }
        audioEngine.speak(text, isEnglish = item.category == LearningCategory.ENGLISH_ALPHABET)
    }

    fun addPath(drawingPath: DrawingPath) {
        _paths.value = _paths.value + drawingPath
    }

    fun clearCanvas() {
        _paths.value = emptyList()
    }

    fun toggleEraser() {
        _isEraser.value = !_isEraser.value
    }

    fun selectColor(color: Color) {
        _selectedColor.value = color
        _isEraser.value = false
    }

    // --- Quiz Logic ---
    fun startNewQuiz() {
        viewModelScope.launch {
            val allItems = repository.getItemsByCategory(LearningCategory.VOWEL).first()
            if (allItems.size < 4) return@launch
            
            val shuffled = allItems.shuffled()
            _quizOptions.value = shuffled.take(4)
            _correctOption.value = _quizOptions.value.random()
            
            speakCorrectOption()
        }
    }

    fun speakCorrectOption() {
        val correct = _correctOption.value ?: return
        audioEngine.speak("খুঁজে বের করো, ${correct.character}")
    }

    fun onOptionSelected(item: LearningItem) {
        if (item.id == _correctOption.value?.id) {
            _score.value += 10
            audioEngine.speak("সাবাশ! সঠিক উত্তর।")
            startNewQuiz() 
        } else {
            audioEngine.speak("উফ! ভুল হয়েছে। আবার চেষ্টা করো।")
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioEngine.stop()
    }
}
