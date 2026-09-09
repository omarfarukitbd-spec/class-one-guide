package com.helptrickbd.class1.feature.learn_hub.domain.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Robust Zero-Latency Audio Manager for Kids Quiz Game.
 * Combines SoundPool for instant feedback and MediaPlayer for voice prompts.
 */
class QuizSoundManager(private val context: Context) {

    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(3)
        .setAudioAttributes(audioAttributes)
        .build()

    private var correctSoundId = 0
    private var wrongSoundId = 0
    private var cheerSoundId = 0
    private var clapSoundId = 0
    private var isLoaded = false

    private var promptPlayer: MediaPlayer? = null

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    init {
        soundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) isLoaded = true
        }
        loadSfx()
    }

    private fun loadSfx() {
        try {
            context.assets.openFd("audio/quiz/vowel_correct.mp3").use { afd ->
                correctSoundId = soundPool.load(afd.fileDescriptor, afd.startOffset, afd.length, 1)
            }
            context.assets.openFd("audio/quiz/vowel_wrong.mp3").use { afd ->
                wrongSoundId = soundPool.load(afd.fileDescriptor, afd.startOffset, afd.length, 1)
            }
            context.assets.openFd("audio/sfx/cheer_sparkle.wav").use { afd ->
                cheerSoundId = soundPool.load(afd.fileDescriptor, afd.startOffset, afd.length, 1)
            }
            context.assets.openFd("audio/sfx/clapping.wav").use { afd ->
                clapSoundId = soundPool.load(afd.fileDescriptor, afd.startOffset, afd.length, 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playCorrectSound() {
        if (_isMuted.value || !isLoaded || correctSoundId == 0) return
        soundPool.play(correctSoundId, 1f, 1f, 1, 0, 1f)
    }

    fun playWrongSound() {
        if (_isMuted.value || !isLoaded || wrongSoundId == 0) return
        soundPool.play(wrongSoundId, 0.85f, 0.85f, 1, 0, 1f)
    }

    fun playComboCheer() {
        if (_isMuted.value || !isLoaded || cheerSoundId == 0) return
        soundPool.play(cheerSoundId, 1f, 1f, 1, 0, 1f)
    }

    fun playClappingCelebration() {
        if (_isMuted.value || !isLoaded || clapSoundId == 0) return
        soundPool.play(clapSoundId, 1f, 1f, 1, 0, 1f)
    }

    fun playVoicePrompt(assetPath: String?, onComplete: (() -> Unit)? = null) {
        if (_isMuted.value || assetPath.isNullOrBlank()) {
            onComplete?.invoke()
            return
        }
        stopVoicePrompt()
        try {
            val afd = context.assets.openFd(assetPath)
            promptPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build()
                )
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                prepare()
                setOnCompletionListener {
                    onComplete?.invoke()
                }
                setOnErrorListener { _, _, _ ->
                    onComplete?.invoke()
                    true
                }
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onComplete?.invoke()
        }
    }

    fun stopVoicePrompt() {
        try {
            promptPlayer?.stop()
            promptPlayer?.release()
        } catch (_: Exception) {}
        promptPlayer = null
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
        if (_isMuted.value) stopVoicePrompt()
    }

    fun release() {
        stopVoicePrompt()
        soundPool.release()
    }
}
