package com.helptrickbd.class1.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
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
            setOnCompletionListener {
                _isSpeaking.value = false
            }
            setOnErrorListener { _, _, _ ->
                _isSpeaking.value = false
                true
            }
        }
    }

    suspend fun playTracingItemAudio(item: TracingItem) = withContext(Dispatchers.Main) {
        val audioPath = TracingAudioRegistry.getAudioPath(item)
        val isEnglish = item.category == TracingCategory.ENGLISH_ALPHABET || item.category == TracingCategory.ENGLISH_NUMBER

        if (audioPath.isNotBlank() && isAssetAvailable(audioPath)) {
            try {
                stop()
                context.assets.openFd(audioPath).use { afd ->
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                    _isSpeaking.value = true
                }
            } catch (_: Exception) {
                fallbackToTts(item, isEnglish)
            }
        } else {
            fallbackToTts(item, isEnglish)
        }
    }

    private fun fallbackToTts(item: TracingItem, isEnglish: Boolean) {
        speechEngine.speakTracingItem(item.character, item.wordExample, item.meaning, isEnglish)
    }

    suspend fun playPraiseAudio(isEnglish: Boolean) = withContext(Dispatchers.Main) {
        val praisePath = TracingAudioRegistry.getRandomPraiseAudioPath(isEnglish)
        if (isAssetAvailable(praisePath)) {
            try {
                stop()
                context.assets.openFd(praisePath).use { afd ->
                    mediaPlayer?.reset()
                    mediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                    _isSpeaking.value = true
                }
            } catch (_: Exception) {
                speechEngine.speakPraise(isEnglish)
            }
        } else {
            speechEngine.speakPraise(isEnglish)
        }
    }

    private fun isAssetAvailable(path: String): Boolean {
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
        mediaPlayer?.release()
        mediaPlayer = null
        speechEngine.shutdown()
    }
}
