// =============================================================
// NCTB COMMAND HUB - AUDIO & PHONICS LAB ENGINE
// =============================================================

let activeAudioElement = null;

function getStoredGeminiKey() {
    return localStorage.getItem("gemini_voice_api_key") || "";
}

function saveGeminiKey() {
    const keyInput = document.getElementById("input-gemini-key");
    if (!keyInput || !keyInput.value.trim()) {
        showToast("অনুগ্রহ করে Google AI Studio API Key দিন!", "warning");
        return;
    }
    localStorage.setItem("gemini_voice_api_key", keyInput.value.trim());
    showToast("Google AI Studio Key সফলভাবে সেভ হয়েছে!", "success");
}

function stopCurrentAudio() {
    if (activeAudioElement) {
        activeAudioElement.pause();
        activeAudioElement.currentTime = 0;
        document.querySelectorAll(".vowel-card").forEach(c => c.classList.remove("playing-audio"));
        document.querySelectorAll(".vowel-char-badge").forEach(b => b.style.transform = "");
        activeAudioElement = null;
    }
}

function playSound(url, onEnd, onError) {
    stopCurrentAudio();
    const audio = new Audio(url);
    activeAudioElement = audio;
    audio.onended = () => { stopCurrentAudio(); if (onEnd) onEnd(); };
    audio.onerror = () => { stopCurrentAudio(); if (onError) onError(); };
    audio.play().catch(e => { console.warn("Audio error:", e); stopCurrentAudio(); });
}

function playLetterAudio(letter, type) {
    const list = type === 'vowels' ? BENGALI_VOWELS : BENGALI_CONSONANTS;
    const item = list.find(v => v.letter === letter);
    if (!item) return;

    const badge = document.getElementById(`vowel-badge-${letter}`);
    if (badge) badge.style.transform = "scale(1.15)";
    playSound(item.letterAudioUrl || item.audioUrl, () => {
        if (badge) badge.style.transform = "";
    }, () => {
        if (badge) badge.style.transform = "";
        showToast(`"${letter}" এর উচ্চারণ অডিও পাওয়া যায়নি!`, "error");
    });
}

function playPhonicsAudio(letter, type) {
    const list = type === 'vowels' ? BENGALI_VOWELS : BENGALI_CONSONANTS;
    const item = list.find(v => v.letter === letter);
    if (!item) return;

    const card = document.getElementById(`vowel-card-${letter}`);
    if (card) card.classList.add("playing-audio");
    playSound(item.audioUrl, () => {
        if (card) card.classList.remove("playing-audio");
    }, () => {
        if (card) card.classList.remove("playing-audio");
        showToast(`"${letter}" এর ছড়া অডিও লোড করতে সমস্যা হয়েছে!`, "error");
    });
}

function playWordAudio(letter, type) {
    const list = type === 'vowels' ? BENGALI_VOWELS : BENGALI_CONSONANTS;
    const item = list.find(v => v.letter === letter);
    if (!item || !item.wordAudioUrl) return;

    playSound(item.wordAudioUrl, null, () => {
        showToast(`"${item.word}" এর শব্দ উচ্চারণ অডিও লোড করতে সমস্যা হয়েছে!`, "error");
    });
}

function playKidsApplauseSound() {
    new Audio("audio/effects/applause.mp3").play().catch(() => showToast("সাবাশ! খুব সুন্দর হয়েছে!", "info"));
}

function playMagicChimeSound() {
    new Audio("audio/effects/magic_chime.mp3").play().catch(() => showToast("ম্যাজিক শব্দ!", "info"));
}

async function callGeminiVoiceEngine() {
    const apiKey = document.getElementById("input-gemini-key")?.value.trim() || getStoredGeminiKey();
    if (!apiKey) {
        showToast("Google AI Studio Key দিন!", "warning");
        document.getElementById("input-gemini-key")?.focus();
        return;
    }
    const textToSpeak = document.getElementById("input-gemini-prompt")?.value.trim() || "অ তে অজগর! অজগরটি আসছে তেড়ে!";
    const btn = document.getElementById("btn-gemini-speak");
    if (btn) {
        btn.disabled = true;
        btn.innerHTML = `<span class="spinner-border spinner-border-sm"></span> জেনারেট হচ্ছে...`;
    }
    try {
        const response = await fetch(`https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-tts-preview:generateContent?key=${apiKey}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                contents: [{ parts: [{ text: textToSpeak }] }],
                generationConfig: {
                    responseModalities: ["AUDIO"],
                    speechConfig: { voiceConfig: { prebuiltVoiceConfig: { voiceName: "Zephyr" } } }
                }
            })
        });
        if (!response.ok) throw new Error(`API Error (${response.status})`);
        const data = await response.json();
        const base64Audio = data.candidates?.[0]?.content?.parts?.[0]?.inlineData?.data;
        if (!base64Audio) throw new Error("অডিও ডাটা পাওয়া যায়নি");
        new Audio(`data:audio/wav;base64,${base64Audio}`).play();
        showToast("Gemini ভয়েস সফলভাবে প্লে হয়েছে!", "success");
    } catch (e) {
        console.error("Gemini Voice Error:", e);
        showToast(`এরর: ${e.message}`, "error");
    } finally {
        if (btn) {
            btn.disabled = false;
            btn.innerHTML = `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 14.5v-9l6 4.5-6 4.5z"/></svg><span>Google AI দিয়ে কথা বলান</span>`;
        }
    }
}

window.downloadPhonicsAudio = function(letter, type) {
    const list = type === 'vowels' ? BENGALI_VOWELS : BENGALI_CONSONANTS;
    const phonics = list.find(v => v.letter === letter);
    if (!phonics || !phonics.audioUrl) return;
    const a = document.createElement("a");
    a.href = phonics.audioUrl;
    a.download = `${type}_${phonics.letter}.mp3`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
};

window.showPhonicsTab = function(type) {
    const btnV = document.getElementById("btn-show-vowels");
    const btnC = document.getElementById("btn-show-consonants");
    if(btnV && btnC) {
        btnV.className = type === 'vowels' ? "btn btn-primary" : "btn btn-outline";
        btnC.className = type === 'consonants' ? "btn btn-primary" : "btn btn-outline";
    }
    renderPhonicsGrid(type);
};

function renderPhonicsGrid(type = 'vowels') {
    const container = document.getElementById("vowels-grid");
    if (!container) return;
    const dataArray = type === 'vowels' ? BENGALI_VOWELS : BENGALI_CONSONANTS;

    container.innerHTML = dataArray.map(v => `
        <div id="vowel-card-${v.letter}" class="glass-panel vowel-card" style="padding: 16px; border-radius: var(--radius-lg); position: relative; transition: all 0.2s ease;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px;">
                <div style="display: flex; align-items: center; gap: 10px;">
                    <div id="vowel-badge-${v.letter}" class="vowel-char-badge" onclick="playLetterAudio('${v.letter}', '${type}')" title="বর্ণের একক উচ্চারণ শুনতে ক্লিক করুন" style="width: 44px; height: 44px; font-size: 24px; font-weight: 900; background: var(--bg-card-hover); color: var(--primary); border-radius: 12px; display: flex; align-items: center; justify-content: center; border: 1px solid var(--border-glass); cursor: pointer; transition: transform 0.15s ease;">
                        ${v.letter}
                    </div>
                    <div>
                        <div style="font-size: 16px; font-weight: 800; color: var(--text-main); cursor: pointer; display: flex; align-items: center; gap: 6px;" onclick="playWordAudio('${v.letter}', '${type}')" title="শব্দ উচ্চারণ শুনতে ক্লিক করুন">
                            <span>${v.word}</span>
                            <span style="font-size: 10px; padding: 2px 6px; background: rgba(var(--primary-rgb), 0.12); color: var(--primary); border-radius: 6px; font-weight: 600;">শব্দ শুনুন</span>
                        </div>
                        <div style="font-size: 11px; color: var(--text-muted);">${v.name}</div>
                    </div>
                </div>
                <button type="button" class="btn btn-sm btn-primary" onclick="playPhonicsAudio('${v.letter}', '${type}')" style="border-radius: 999px; padding: 6px 14px; font-size: 11px; gap: 4px;" title="সম্পূর্ণ ছড়া শুনতে ক্লিক করুন">
                    <svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02zM14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z"/></svg>
                    <span>ছড়া শুনুন</span>
                </button>
            </div>
            <div style="background: rgba(255, 255, 255, 0.03); border: 1px solid var(--border-glass); border-radius: var(--radius-md); padding: 10px 12px; font-size: 13px; font-weight: 600; color: var(--text-sub); line-height: 1.4; margin-bottom: 12px; cursor: pointer;" onclick="playPhonicsAudio('${v.letter}', '${type}')" title="ছড়া শুনতে ক্লিক করুন">
                ${v.sentence}
            </div>
            <div style="display: flex; gap: 6px; align-items: center;">
                <input type="text" id="vowel-audio-url-${v.letter}" class="custom-input" placeholder="MP3 লিংক..." value="${v.audioUrl || ''}" style="font-family: monospace; font-size: 10px; padding: 4px 8px; flex: 1;">
                <button type="button" class="btn btn-sm btn-outline" onclick="window.downloadPhonicsAudio('${v.letter}', '${type}')" style="font-size: 10px; padding: 4px 8px;" title="MP3 ডাউনলোড"><svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z"/></svg></button>
            </div>
        </div>
    `).join("");

    const ids = [
        ["btn-play-applause", playKidsApplauseSound],
        ["btn-play-chime", playMagicChimeSound],
        ["btn-save-gemini-key", saveGeminiKey],
        ["btn-gemini-speak", callGeminiVoiceEngine]
    ];
    ids.forEach(([id, fn]) => {
        const el = document.getElementById(id);
        if (el) el.onclick = fn;
    });
    const inputGemini = document.getElementById("input-gemini-key");
    if (inputGemini) inputGemini.value = getStoredGeminiKey();
}
