package com.helptrickbd.class1.core.audio

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Text-to-Speech engine optimized for Bengali and English learning.
 */
@Singleton
class AppSpeechEngine @Inject constructor(
    @ApplicationContext private val context: Context
) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val speechParams = Bundle().apply {
        putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f)
    }

    private val banglaPraisePhrases = listOf(
        "সাবাশ! চমৎকার হয়েছে!",
        "দারুণ! তুমি পেরেছ!",
        "বাহ! খুব সুন্দর হয়েছে!",
        "অসাধারণ!"
    )

    private val englishPraisePhrases = listOf(
        "Well done! Excellent!",
        "Great job! You did it!",
        "Awesome! Beautiful!"
    )

    init {
        initTtsEngine()
    }

    private fun initTtsEngine() {
        tts = try {
            TextToSpeech(context, this, "com.google.android.tts")
        } catch (_: Exception) {
            TextToSpeech(context, this)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            setupVoiceAttributes()
            applyBestFemaleVoice(isEnglish = false)
        }
    }

    private fun setupVoiceAttributes() {
        tts?.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
        )
        // Optimal female tone
        tts?.setPitch(1.3f)
        tts?.setSpeechRate(0.85f)

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) { _isSpeaking.value = true }
            override fun onDone(utteranceId: String?) { _isSpeaking.value = false }
            override fun onError(utteranceId: String?) { _isSpeaking.value = false }
        })
    }

    private fun applyBestFemaleVoice(isEnglish: Boolean) {
        val targetLocale = if (isEnglish) Locale.US else Locale("bn", "BD")
        val result = tts?.setLanguage(targetLocale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.language = if (isEnglish) Locale.ENGLISH else Locale.getDefault()
        }

        try {
            val availableVoices = tts?.voices ?: emptySet()
            val targetLang = targetLocale.language

            val femaleVoice = availableVoices
                .filter { it.locale.language == targetLang }
                .firstOrNull { voice ->
                    val name = voice.name.lowercase()
                    name.contains("female") || name.contains("fem") || 
                    name.contains("bif") || name.contains("bdf") ||
                    name.contains("sfg") || voice.features.contains("gender=female")
                } ?: availableVoices.firstOrNull { it.locale.language == targetLang }

            femaleVoice?.let { tts?.voice = it }
        } catch (_: Exception) {}
    }

    /**
     * Speaks the given text.
     */
    fun speak(text: String, isEnglish: Boolean = false) {
        if (!isInitialized || text.isBlank()) return
        applyBestFemaleVoice(isEnglish)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, speechParams, "LEARNING_UTTERANCE")
    }

    fun speakPraise(isEnglish: Boolean) {
        val praise = if (isEnglish) englishPraisePhrases.random() else banglaPraisePhrases.random()
        speak(praise, isEnglish)
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
