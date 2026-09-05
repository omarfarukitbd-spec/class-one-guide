package com.helptrickbd.class1.feature.learn_hub.ui.phonics

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.helptrickbd.class1.core.navigation.Screen
import com.helptrickbd.class1.feature.learn_hub.data.datasource.ConsonantsData
import com.helptrickbd.class1.feature.learn_hub.data.datasource.VowelsData
import com.helptrickbd.class1.feature.learn_hub.domain.audio.PhonicsAudioPlayer
import com.helptrickbd.class1.feature.learn_hub.domain.model.PhonicsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PhonicsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val audioPlayer = PhonicsAudioPlayer(context)
    private val route: Screen.Phonics = savedStateHandle.toRoute()

    private val _selectedTab = MutableStateFlow(
        if (route.type.contains("consonant")) PhonicsTab.CONSONANTS else PhonicsTab.VOWELS
    )
    private val _displayMode = MutableStateFlow(
        if (route.type.contains("words")) PhonicsDisplayMode.WORDS else PhonicsDisplayMode.ALPHABET
    )
    private val _screenMode = MutableStateFlow(PhonicsScreenMode.SOUNDBOARD)
    private val _detailItem = MutableStateFlow<PhonicsItem?>(null)

    val uiState: StateFlow<PhonicsUiState> = combine(
        _selectedTab,
        _displayMode,
        _screenMode,
        _detailItem,
        audioPlayer.currentlyPlayingId
    ) { tab, mode, screenMode, detail, playingId ->
        val items = if (tab == PhonicsTab.VOWELS) {
            VowelsData.getVowels()
        } else {
            ConsonantsData.getConsonants()
        }
        PhonicsUiState(
            selectedTab = tab,
            displayMode = mode,
            screenMode = screenMode,
            items = items,
            currentlyPlayingId = playingId,
            detailItem = detail
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PhonicsUiState()
    )

    fun onTabSelected(tab: PhonicsTab) {
        if (_selectedTab.value != tab) {
            audioPlayer.stop()
            _selectedTab.value = tab
            _detailItem.value = null
        }
    }

    fun onScreenModeSelected(mode: PhonicsScreenMode) {
        if (_screenMode.value != mode) {
            audioPlayer.stop()
            _screenMode.value = mode
            _detailItem.value = null
        }
    }

    fun onModeToggle() {
        _displayMode.update { current ->
            if (current == PhonicsDisplayMode.ALPHABET) PhonicsDisplayMode.WORDS else PhonicsDisplayMode.ALPHABET
        }
    }

    fun onItemClick(item: PhonicsItem) {
        val audioPath = if (_displayMode.value == PhonicsDisplayMode.ALPHABET) {
            item.letterAudioPath ?: item.audioAssetPath
        } else {
            item.wordAudioPath ?: item.audioAssetPath
        }
        audioPlayer.play(item.id, audioPath)
    }

    fun onDetailOpen(item: PhonicsItem) {
        _detailItem.value = item
        audioPlayer.play(item.id, item.audioAssetPath)
    }

    fun onDetailDismiss() {
        _detailItem.value = null
    }

    fun stopAudio() {
        audioPlayer.stop()
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}
