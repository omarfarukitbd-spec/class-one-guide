package com.helptrickbd.class1.core.audio

import android.media.AudioManager
import android.media.ToneGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundFxHelper @Inject constructor() {

    private var toneGen: ToneGenerator? = null

    init {
        try {
            toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 85)
        } catch (_: Exception) {
            // Graceful fallback if audio device is unavailable
        }
    }

    suspend fun playVictoryChime() = withContext(Dispatchers.IO) {
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP2, 250)
        } catch (_: Exception) {}
    }

    suspend fun playBubbleClick() = withContext(Dispatchers.IO) {
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP, 70)
        } catch (_: Exception) {}
    }

    fun release() {
        toneGen?.release()
        toneGen = null
    }
}
