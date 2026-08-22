package com.helptrickbd.class1.feature.karchihno.data.datasource

import com.helptrickbd.class1.feature.karchihno.domain.model.KarChihnoItem

object KarChihnoDataSource {
    val items = listOf(
        KarChihnoItem(
            id = "kc1",
            sign = "া",
            displaySign = "কা",
            vowelForm = "আ ( া )",
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
            displaySign = "কি",
            vowelForm = "ই ( ি )",
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
            displaySign = "কী",
            vowelForm = "ঈ ( ী )",
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
            displaySign = "কু",
            vowelForm = "উ ( ু )",
            name = "হ্রস্ব-উ-কার",
            fullVowel = "উ",
            exampleWord = "পুতুল",
            spellSentence = "প-এ হ্রস্ব-উ-কার পু, ত-এ হ্রস্ব-উ-কার তু, ল — পুতুল",
            signAudioPath = "audio/karchihno/kc_rosshukar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_pa_rosshukar.mp3",
            wordAudioPath = "audio/karchihno/word_putul.mp3",
            orderIndex = 4
        ),
        KarChihnoItem(
            id = "kc5",
            sign = "ূ",
            displaySign = "কূ",
            vowelForm = "ঊ ( ূ )",
            name = "দীর্ঘ-ঊ-কার",
            fullVowel = "ঊ",
            exampleWord = "ময়ূর",
            spellSentence = "ম, য়-এ দীর্ঘ-ঊ-কার য়ূ, র — ময়ূর",
            signAudioPath = "audio/karchihno/kc_dergoyukar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_untasteyo_dergoyukar.mp3",
            wordAudioPath = "audio/karchihno/word_moyur.mp3",
            orderIndex = 5
        ),
        KarChihnoItem(
            id = "kc6",
            sign = "ৃ",
            displaySign = "কৃ",
            vowelForm = "ঋ ( ৃ )",
            name = "ঋ-কার",
            fullVowel = "ঋ",
            exampleWord = "কৃষক",
            spellSentence = "ক-এ ঋ-কার কৃ, ষ, ক — কৃষক",
            signAudioPath = "audio/karchihno/kc_rekar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_ka_rekar.mp3",
            wordAudioPath = "audio/karchihno/word_krishok.mp3",
            orderIndex = 6
        ),
        KarChihnoItem(
            id = "kc7",
            sign = "ে",
            displaySign = "কে",
            vowelForm = "এ ( ে )",
            name = "এ-কার",
            fullVowel = "এ",
            exampleWord = "থেমে",
            spellSentence = "থ-এ এ-কার থে, ম-এ এ-কার মে — থেমে",
            signAudioPath = "audio/karchihno/kc_ekar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_ta_ekar.mp3",
            wordAudioPath = "audio/karchihno/word_theme.mp3",
            orderIndex = 7
        ),
        KarChihnoItem(
            id = "kc8",
            sign = "ৈ",
            displaySign = "কৈ",
            vowelForm = "ঐ ( ৈ )",
            name = "ঐ-কার",
            fullVowel = "ঐ",
            exampleWord = "সৈনিক",
            spellSentence = "স-এ ঐ-কার সৈ, ন-এ হ্রস্ব-ই-কার নি, ক — সৈনিক",
            signAudioPath = "audio/karchihno/kc_oikar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_sho_aykar.mp3",
            wordAudioPath = "audio/karchihno/kc_spell_sho_aykar.mp3",
            orderIndex = 8
        ),
        KarChihnoItem(
            id = "kc9",
            sign = "ো",
            displaySign = "কো",
            vowelForm = "ও ( ো )",
            name = "ও-কার",
            fullVowel = "ও",
            exampleWord = "চোখ",
            spellSentence = "চ-এ ও-কার চো, খ — চোখ",
            signAudioPath = "audio/karchihno/kc_okar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_cho_okar.mp3",
            wordAudioPath = "audio/karchihno/word_chokh.mp3",
            orderIndex = 9
        ),
        KarChihnoItem(
            id = "kc10",
            sign = "ৌ",
            displaySign = "কৌ",
            vowelForm = "ঔ ( ৌ )",
            name = "ঔ-কার",
            fullVowel = "ঔ",
            exampleWord = "বউ",
            spellSentence = "ব, ঔ — বউ",
            signAudioPath = "audio/karchihno/kc_ooukar.mp3",
            spellAudioPath = "audio/karchihno/kc_spell_ba_ooukar.mp3",
            wordAudioPath = "audio/karchihno/word_bou.mp3",
            orderIndex = 10
        )
    )
}
