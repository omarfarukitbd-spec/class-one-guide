package com.helptrickbd.class1.core.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
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
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            tts?.setSpeechRate(0.85f)
            tts?.setPitch(1.1f)
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }
                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                }
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                }
            })
        }
    }

    fun speakTracingItem(character: String, wordExample: String, meaning: String, isEnglish: Boolean) {
        if (!isInitialized) return

        val locale = if (isEnglish) Locale.US else Locale("bn", "BD")
        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.language = if (isEnglish) Locale.ENGLISH else Locale.getDefault()
        }

        val speechText = if (meaning.isNotBlank()) {
            "$character... $wordExample। $meaning"
        } else {
            "$character... $wordExample"
        }

        tts?.speak(speechText, TextToSpeech.QUEUE_FLUSH, null, "TRACING_ITEM_UTTERANCE")
    }

    fun speakPraise(isEnglish: Boolean) {
        if (!isInitialized) return

        val locale = if (isEnglish) Locale.US else Locale("bn", "BD")
        tts?.setLanguage(locale)

        val praise = if (isEnglish) {
            englishPraisePhrases.random()
        } else {
            banglaPraisePhrases.random()
        }

        tts?.speak(praise, TextToSpeech.QUEUE_FLUSH, null, "PRAISE_UTTERANCE")
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
