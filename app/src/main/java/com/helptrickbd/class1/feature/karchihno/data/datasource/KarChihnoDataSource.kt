package com.helptrickbd.class1.feature.karchihno.data.datasource

import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem

object KarChihnoDataSource {
    val items = listOf(
        KarChihnoItem(
            id = "kc1",
            sign = "া",
            name = "আ-কার",
            fullVowel = "আ",
            exampleWord = "পাখা",
            spellSentence = "প-এ আ-কার পা, খ-এ আ-কার খা — পাখা",
            signAudioPath = "audio/karchihno/kc_akar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_pa_akar.mp3",
            wordAudioPath = "audio/karchihno/word_pakha.mp3",
            orderIndex = 1
        ),
        KarChihnoItem(
            id = "kc2",
            sign = "ি",
            name = "হ্রস্ব-ই-কার",
            fullVowel = "ই",
            exampleWord = "ইলিশ",
            spellSentence = "হ্রস্ব-ই-কার দিয়ে শব্দ গঠন",
            signAudioPath = "audio/vowels/bn_vowel_03.mp3",
            spellAudioPath = "audio/rhymes/bn_rhyme_03.mp3",
            wordAudioPath = "audio/rhymes/bn_rhyme_03.mp3",
            orderIndex = 2
        ),
        KarChihnoItem(
            id = "kc3",
            sign = "ী",
            name = "দীর্ঘ-ঈ-কার",
            fullVowel = "ঈ",
            exampleWord = "নদী",
            spellSentence = "দ-এ দীর্ঘ-ঈ-কার দী — নদী",
            signAudioPath = "audio/karchihno/kc_dergoyekar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_da_dergoyekar.mp3",
            wordAudioPath = "audio/karchihno/word_nodi.mp3",
            orderIndex = 3
        ),
        KarChihnoItem(
            id = "kc4",
            sign = "ু",
            name = "হ্রস্ব-উ-কার",
            fullVowel = "উ",
            exampleWord = "পুতুল",
            spellSentence = "প-এ হ্রস্ব-উ-কার পু — পুতুল",
            signAudioPath = "audio/karchihno/kc_rosshukar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_pa_rosshukar.mp3",
            wordAudioPath = "audio/karchihno/word_putul.mp3",
            orderIndex = 4
        ),
        KarChihnoItem(
            id = "kc5",
            sign = "ূ",
            name = "দীর্ঘ-ঊ-কার",
            fullVowel = "ঊ",
            exampleWord = "ময়ূর",
            spellSentence = "য়-এ দীর্ঘ-ঊ-কার য়ূ — ময়ূর",
            signAudioPath = "audio/karchihno/kc_dergoyukar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_untasteyo_dergoyukar.mp3",
            wordAudioPath = "audio/karchihno/word_moyur.mp3",
            orderIndex = 5
        ),
        KarChihnoItem(
            id = "kc6",
            sign = "ৃ",
            name = "ঋ-কার",
            fullVowel = "ঋ",
            exampleWord = "কৃষক",
            spellSentence = "ক-এ ঋ-কার কৃ — কৃষক",
            signAudioPath = "audio/karchihno/kc_rekar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_ka_rekar.mp3",
            wordAudioPath = "audio/karchihno/word_krishok.mp3",
            orderIndex = 6
        ),
        KarChihnoItem(
            id = "kc7",
            sign = "ে",
            name = "এ-কার",
            fullVowel = "এ",
            exampleWord = "থেমে",
            spellSentence = "থ-এ এ-কার থে — থেমে",
            signAudioPath = "audio/karchihno/kc_ekar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_ta_ekar.mp3",
            wordAudioPath = "audio/karchihno/word_theme.mp3",
            orderIndex = 7
        ),
        KarChihnoItem(
            id = "kc8",
            sign = "ৈ",
            name = "ঐ-কার",
            fullVowel = "ঐ",
            exampleWord = "ছেলে",
            spellSentence = "ছ-এ ঐ-কার ছৈ — ছেলে",
            signAudioPath = "audio/karchihno/kc_oikar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_sho_aykar.mp3",
            wordAudioPath = "audio/karchihno/word_chele.mp3",
            orderIndex = 8
        ),
        KarChihnoItem(
            id = "kc9",
            sign = "ো",
            name = "ও-কার",
            fullVowel = "ও",
            exampleWord = "চোখ",
            spellSentence = "চ-এ ও-কার চো — চোখ",
            signAudioPath = "audio/karchihno/kc_okar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_cho_okar.mp3",
            wordAudioPath = "audio/karchihno/word_chokh.mp3",
            orderIndex = 9
        ),
        KarChihnoItem(
            id = "kc10",
            sign = "ৌ",
            name = "ঔ-কার",
            fullVowel = "ঔ",
            exampleWord = "বউ",
            spellSentence = "ব-এ ঔ-কার বৌ — বউ",
            signAudioPath = "audio/karchihno/kc_ooukar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_ba_ooukar.mp3",
            wordAudioPath = "audio/karchihno/word_bou.mp3",
            orderIndex = 10
        )
    )
}
