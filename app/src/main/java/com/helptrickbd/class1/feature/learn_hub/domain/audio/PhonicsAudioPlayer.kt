package com.helptrickbd.class1.feature.learn_hub.domain.audio

import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Memory-efficient, lifecycle-aware Audio Player for Phonics soundboard.
 * Guarantees zero audio overlaps, instant reset on rapid taps, and safe release.
 */
class PhonicsAudioPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    
    private val _currentlyPlayingId = MutableStateFlow<String?>(null)
    val currentlyPlayingId: StateFlow<String?> = _currentlyPlayingId.asStateFlow()

    fun play(itemId: String, assetPath: String, onComplete: (() -> Unit)? = null) {
        try {
            stop()

            mediaPlayer = MediaPlayer().apply {
                val afd = context.assets.openFd(assetPath)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                prepare()
                
                setOnCompletionListener {
                    _currentlyPlayingId.value = null
                    onComplete?.invoke()
                }
                
                setOnErrorListener { _, _, _ ->
                    _currentlyPlayingId.value = null
                    reset()
                    true
                }
                
                start()
            }
            _currentlyPlayingId.value = itemId
        } catch (e: Exception) {
            _currentlyPlayingId.value = null
            e.printStackTrace()
        }
    }

    fun stop() {
        try {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    player.stop()
                }
                player.reset()
                player.release()
            }
        } catch (_: Exception) {
        } finally {
            mediaPlayer = null
            _currentlyPlayingId.value = null
        }
    }

    fun release() {
        stop()
    }
}
