package com.helptrickbd.class1.feature.learn_hub.domain.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Ultra-low latency SoundPool audio manager for Slate sensory sound effects.
 */
class SlateSoundManager(private val context: Context) {

    private val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(4)
        .setAudioAttributes(audioAttributes)
        .build()

    private var chalkSoundId = 0
    private var dusterSoundId = 0
    private var cheerSoundId = 0
    private var isLoaded = false

    private val _isSoundEnabled = MutableStateFlow(true)
    val isSoundEnabled: StateFlow<Boolean> = _isSoundEnabled.asStateFlow()

    init {
        soundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) isLoaded = true
        }
        loadSounds()
    }

    private fun loadSounds() {
        try {
            val afd1 = context.assets.openFd("audio/sfx/chalk_scratch.wav")
            chalkSoundId = soundPool.load(afd1.fileDescriptor, afd1.startOffset, afd1.length, 1)
            afd1.close()

            val afd2 = context.assets.openFd("audio/sfx/duster_wipe.wav")
            dusterSoundId = soundPool.load(afd2.fileDescriptor, afd2.startOffset, afd2.length, 1)
            afd2.close()

            val afd3 = context.assets.openFd("audio/sfx/cheer_sparkle.wav")
            cheerSoundId = soundPool.load(afd3.fileDescriptor, afd3.startOffset, afd3.length, 1)
            afd3.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleSound(): Boolean {
        val newState = !_isSoundEnabled.value
        _isSoundEnabled.value = newState
        return newState
    }

    fun playChalk() {
        if (!_isSoundEnabled.value || chalkSoundId == 0) return
        soundPool.play(chalkSoundId, 0.4f, 0.4f, 1, 0, 1.0f)
    }

    fun playDuster() {
        if (!_isSoundEnabled.value || dusterSoundId == 0) return
        soundPool.play(dusterSoundId, 0.7f, 0.7f, 1, 0, 1.0f)
    }

    fun playCheer() {
        if (!_isSoundEnabled.value || cheerSoundId == 0) return
        soundPool.play(cheerSoundId, 0.85f, 0.85f, 2, 0, 1.0f)
    }

    fun release() {
        try {
            soundPool.release()
        } catch (_: Exception) {}
    }
}
