package com.helptrickbd.class1.feature.karchihno.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.audio.SoundFxHelper
import com.helptrickbd.class1.core.audio.StudioAudioEngine
import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem
import com.helptrickbd.class1.feature.karchihno.domain.usecase.GetKarChihnoItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KarChihnoViewModel @Inject constructor(
    private val getKarChihnoItemsUseCase: GetKarChihnoItemsUseCase,
    private val studioAudioEngine: StudioAudioEngine,
    private val soundFxHelper: SoundFxHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(KarChihnoUiState())
    val uiState: StateFlow<KarChihnoUiState> = _uiState.asStateFlow()

    init {
        loadKarChihnoItems()
        viewModelScope.launch {
            studioAudioEngine.isSpeaking.collect { speaking ->
                _uiState.value = _uiState.value.copy(isSpeaking = speaking)
            }
        }
    }

    private fun loadKarChihnoItems() {
        val items = getKarChihnoItemsUseCase()
        val firstItem = items.firstOrNull()
        _uiState.value = _uiState.value.copy(
            items = items,
            selectedItem = firstItem
        )
        firstItem?.let { playSignAudio(it) }
    }

    fun selectItem(item: KarChihnoItem) {
        if (_uiState.value.selectedItem == item) {
            playSignAudio(item)
            return
        }
        _uiState.value = _uiState.value.copy(selectedItem = item)
        playSignAudio(item)
    }

    fun playSignAudio(item: KarChihnoItem) {
        viewModelScope.launch {
            soundFxHelper.playBubbleClick()
            studioAudioEngine.playDirectAsset(item.signAudioPath)
        }
    }

    fun playSpellAudio(item: KarChihnoItem) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSpellingPlaying = true)
            studioAudioEngine.playDirectAsset(item.spellAudioPath) {
                if (item.wordAudioPath.isNotBlank() && item.wordAudioPath != item.spellAudioPath) {
                    viewModelScope.launch {
                        studioAudioEngine.playDirectAsset(item.wordAudioPath) {
                            _uiState.value = _uiState.value.copy(isSpellingPlaying = false)
                        }
                    }
                } else {
                    _uiState.value = _uiState.value.copy(isSpellingPlaying = false)
                }
            }
        }
    }

    fun playWordAudio(item: KarChihnoItem) {
        viewModelScope.launch {
            studioAudioEngine.playDirectAsset(item.wordAudioPath)
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

    override fun onCleared() {
        super.onCleared()
        studioAudioEngine.stop()
    }
}
