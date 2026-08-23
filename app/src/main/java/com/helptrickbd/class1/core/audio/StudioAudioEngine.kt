package com.helptrickbd.class1.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.audiofx.LoudnessEnhancer
 import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Robust Centralized Audio Engine for high-fidelity speech and sound effects.
 * Handles both local assets and TTS fallback.
 */
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

    /**
     * Plays a direct asset path. Useful for pre-recorded studio voices.
     */
    suspend fun playAsset(path: String, onComplete: (() -> Unit)? = null) = withContext(Dispatchers.Main) {
        if (path.isNotBlank() && isAssetAvailable(path)) {
            stop()
            performPlayAsset(path, onComplete)
        } else {
            onComplete?.invoke()
            _isSpeaking.value = false
        }
    }

    private fun performPlayAsset(path: String, onComplete: (() -> Unit)?) {
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

    /**
     * Fallback to Text-to-Speech if asset is missing.
     */
    fun speak(text: String, isEnglish: Boolean = false) {
        stop()
        speechEngine.speak(text, isEnglish)
        _isSpeaking.value = true
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
