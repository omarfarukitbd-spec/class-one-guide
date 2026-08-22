package com.helptrickbd.class1.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.audiofx.LoudnessEnhancer
import com.helptrickbd.class1.feature.drawing.domain.audio.TracingAudioRegistry
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory
import com.helptrickbd.class1.feature.drawing.domain.model.TracingItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudioAudioEngine @Inject constructor(
    @ApplicationContext private val context: Context,
    private val speechEngine: AppSpeechEngine
) {
    private var mediaPlayer: MediaPlayer? = null
    private var loudnessEnhancer: LoudnessEnhancer? = null
    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    init {
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setVolume(1.0f, 1.0f)
            setOnCompletionListener { _isSpeaking.value = false }
            setOnErrorListener { _, _, _ ->
                _isSpeaking.value = false
                true
            }
        }
        try {
            mediaPlayer?.audioSessionId?.let { sessionId ->
                loudnessEnhancer = LoudnessEnhancer(sessionId).apply {
                    setTargetGain(1200) // +12 dB hardware boost for punchy sound
                    enabled = true
                }
            }
        } catch (_: Exception) {}
    }

    suspend fun playTracingItemAudio(item: TracingItem) = withContext(Dispatchers.Main) {
        val letterPath = TracingAudioRegistry.getAudioPath(item)
        val rhymePath = TracingAudioRegistry.getRhymeAudioPath(item)
        val isEnglish = item.category == TracingCategory.ENGLISH_ALPHABET || item.category == TracingCategory.ENGLISH_NUMBER

        if (letterPath.isNotBlank() && isAssetAvailable(letterPath)) {
            stop()
            playAsset(letterPath) {
                if (rhymePath.isNotBlank() && isAssetAvailable(rhymePath)) {
                    playAsset(rhymePath, null)
                } else {
                    _isSpeaking.value = false
                }
            }
        } else if (rhymePath.isNotBlank() && isAssetAvailable(rhymePath)) {
            stop()
            playAsset(rhymePath, null)
        } else {
            fallbackToTts(item, isEnglish)
        }
    }

    suspend fun playRhymeOnly(item: TracingItem) = withContext(Dispatchers.Main) {
        val rhymePath = TracingAudioRegistry.getRhymeAudioPath(item)
        if (rhymePath.isNotBlank() && isAssetAvailable(rhymePath)) {
            stop()
            playAsset(rhymePath, null)
        } else {
            playTracingItemAudio(item)
        }
    }

    suspend fun playDirectAsset(path: String, onComplete: (() -> Unit)? = null) = withContext(Dispatchers.Main) {
        if (path.isNotBlank() && isAssetAvailable(path)) {
            stop()
            playAsset(path, onComplete)
        } else {
            onComplete?.invoke()
        }
    }

    private fun playAsset(path: String, onComplete: (() -> Unit)?) {
        try {
            context.assets.openFd(path).use { afd ->
                mediaPlayer?.reset()
                mediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                mediaPlayer?.setVolume(1.0f, 1.0f)
                mediaPlayer?.setOnCompletionListener {
                    if (onComplete != null) {
                        onComplete()
                    } else {
                        _isSpeaking.value = false
                    }
                }
                mediaPlayer?.prepare()
                mediaPlayer?.start()
                _isSpeaking.value = true
            }
        } catch (_: Exception) {
            _isSpeaking.value = false
            onComplete?.invoke()
        }
    }

    private fun fallbackToTts(item: TracingItem, isEnglish: Boolean) {
        speechEngine.speakTracingItem(item.character, item.wordExample, item.meaning, isEnglish)
    }

    suspend fun playPraiseAudio(isEnglish: Boolean) = withContext(Dispatchers.Main) {
        val praisePath = TracingAudioRegistry.getRandomPraiseAudioPath(isEnglish)
        if (isAssetAvailable(praisePath)) {
            stop()
            playAsset(praisePath, null)
        } else {
            speechEngine.speakPraise(isEnglish)
        }
    }

    fun isAssetAvailable(path: String): Boolean {
        return try {
            context.assets.openFd(path).close()
            true
        } catch (_: Exception) {
            false
        }
    }

    fun stop() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
        } catch (_: Exception) {}
        speechEngine.stop()
        _isSpeaking.value = false
    }

    fun release() {
        stop()
        loudnessEnhancer?.release()
        loudnessEnhancer = null
        mediaPlayer?.release()
        mediaPlayer = null
        speechEngine.shutdown()
    }
}
