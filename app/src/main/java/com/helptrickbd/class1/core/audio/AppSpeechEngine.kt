package com.helptrickbd.class1.core.audio

import android.content.Context
import android.media.AudioAttributes
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSpeechEngine @Inject constructor(
    @ApplicationContext private val context: Context
) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val speechParams = Bundle().apply {
        putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f) // 100% crystal loud volume
    }

    private val banglaPraisePhrases = listOf(
        "সাবাশ! চমৎকার হয়েছে!",
        "দারুণ! তুমি পেরেছ!",
        "বাহ! খুব সুন্দর হয়েছে!",
        "অসাধারণ লেখা!"
    )

    private val englishPraisePhrases = listOf(
        "Well done! Excellent!",
        "Great job! You did it!",
        "Awesome! Beautiful writing!"
    )

    init {
        // Prefer Google TTS for natural Neural female voice, fallback to default
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
        // Sweet, cheerful, and distinct female teacher tone for kids
        tts?.setPitch(1.22f)
        tts?.setSpeechRate(0.78f)

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
                    name.contains("female") || name.contains("#female") ||
                            name.contains("bn-bd-x") || voice.features.contains("gender=female")
                } ?: availableVoices.firstOrNull { it.locale.language == targetLang }

            femaleVoice?.let { tts?.voice = it }
        } catch (_: Exception) {}
    }

    fun speakTracingItem(character: String, wordExample: String, meaning: String, isEnglish: Boolean) {
        if (!isInitialized) return
        applyBestFemaleVoice(isEnglish)

        // Clear pauses between character and sentence for effortless learning
        val speechText = if (meaning.isNotBlank()) {
            "$character , $wordExample । $meaning"
        } else {
            "$character , $wordExample"
        }

        tts?.speak(speechText, TextToSpeech.QUEUE_FLUSH, speechParams, "TRACING_ITEM_UTTERANCE")
    }

    fun speakPraise(isEnglish: Boolean) {
        if (!isInitialized) return
        applyBestFemaleVoice(isEnglish)

        val praise = if (isEnglish) englishPraisePhrases.random() else banglaPraisePhrases.random()
        tts?.speak(praise, TextToSpeech.QUEUE_FLUSH, speechParams, "PRAISE_UTTERANCE")
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
