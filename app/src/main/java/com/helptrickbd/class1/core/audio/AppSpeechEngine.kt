package com.helptrickbd.class1.core.audio

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.os.Bundle
import android.provider.Settings
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
        putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f)
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
        // Set higher pitch and optimal rate for sweet female tone
        tts?.setPitch(1.35f)
        tts?.setSpeechRate(0.75f)

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

            // Search for female voice codes (e.g. Google's bif/bdf/sfg/female)
            val femaleVoice = availableVoices
                .filter { it.locale.language == targetLang }
                .firstOrNull { voice ->
                    val name = voice.name.lowercase()
                    name.contains("bif") || // Bengali India Female
                    name.contains("bdf") || // Bengali Bangladesh Female
                    name.contains("female") ||
                    name.contains("#female") ||
                    name.contains("fem") ||
                    name.contains("sfg") || // English US Female
                    name.contains("tpd") || // English US Female
                    name.contains("iol") || // English US Female
                    voice.features.contains("gender=female")
                } ?: availableVoices
                    .filter { it.locale.language == targetLang }
                    .firstOrNull { !it.name.lowercase().contains("male") && !it.name.lowercase().contains("bim") && !it.name.lowercase().contains("bdm") }
                    ?: availableVoices.firstOrNull { it.locale.language == targetLang }

            femaleVoice?.let { tts?.voice = it }
        } catch (_: Exception) {}
    }

    fun speakTracingItem(character: String, wordExample: String, meaning: String, isEnglish: Boolean) {
        if (!isInitialized) return
        applyBestFemaleVoice(isEnglish)

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

    fun openTtsSettings(context: Context) {
        try {
            val intent = Intent("com.android.settings.TTS_SETTINGS").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            try {
                val fallbackIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(fallbackIntent)
            } catch (_: Exception) {}
        }
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
