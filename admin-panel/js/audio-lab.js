// =============================================================
// NCTB COMMAND HUB - AUDIO & PHONICS LAB ENGINE
// =============================================================

let activeAudioElement = null;

function getStoredGeminiKey() {
    return localStorage.getItem("gemini_voice_api_key") || "";
}

function saveGeminiKey() {
    const keyInput = document.getElementById("input-gemini-key");
    if (keyInput) {
        const key = keyInput.value.trim();
        if (!key) {
            showToast("অনুগ্রহ করে Google AI Studio API Key দিন!", "warning");
            return;
        }
        localStorage.setItem("gemini_voice_api_key", key);
        showToast("Google AI Studio Key সফলভাবে সেভ হয়েছে!", "success");
    }
}

function playVowelAudio(letter) {
    const vowel = BENGALI_VOWELS.find(v => v.letter === letter);
    if (!vowel) return;

    if (activeAudioElement) {
        activeAudioElement.pause();
        activeAudioElement.currentTime = 0;
        document.querySelectorAll(".vowel-card").forEach(c => c.classList.remove("playing-audio"));
    }

    const card = document.getElementById(`vowel-card-${letter}`);
    if (card) card.classList.add("playing-audio");

    const audio = new Audio(vowel.audioUrl);
    activeAudioElement = audio;

    audio.onended = () => {
        if (card) card.classList.remove("playing-audio");
        activeAudioElement = null;
    };

    audio.onerror = () => {
        if (card) card.classList.remove("playing-audio");
        showToast(`"${letter}" এর অডিও লোড করতে সমস্যা হয়েছে!`, "error");
    };

    audio.play().catch(e => {
        console.warn("Audio play prevented:", e);
        if (card) card.classList.remove("playing-audio");
    });
}

function playKidsApplauseSound() {
    const audio = new Audio("audio/effects/applause.mp3");
    audio.play().catch(() => showToast("সাবাশ! খুব সুন্দর হয়েছে!", "info"));
}

function playMagicChimeSound() {
    const audio = new Audio("audio/effects/magic_chime.mp3");
    audio.play().catch(() => showToast("ম্যাজিক শব্দ!", "info"));
}

async function callGeminiVoiceEngine() {
    const apiKey = document.getElementById("input-gemini-key")?.value.trim() || getStoredGeminiKey();
    if (!apiKey) {
        showToast("Google AI Studio Key দিন!", "warning");
        document.getElementById("input-gemini-key")?.focus();
        return;
    }
    const promptInput = document.getElementById("input-gemini-prompt");
    const textToSpeak = promptInput ? promptInput.value.trim() : "অ তে অজগর! অজগরটি আসছে তেড়ে!";
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

        if (!response.ok) {
            const err = await response.text();
            throw new Error(`API Error (${response.status}): ${err}`);
        }

        const data = await response.json();
        const base64Audio = data.candidates?.[0]?.content?.parts?.[0]?.inlineData?.data;
        if (!base64Audio) throw new Error("অডিও ডাটা পাওয়া যায়নি");

        const audio = new Audio(`data:audio/wav;base64,${base64Audio}`);
        audio.play();
        showToast("Google AI Studio ভয়েস সফলভাবে প্লে হয়েছে!", "success");
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

window.downloadVowelAudio = function(letter) {
    const vowel = BENGALI_VOWELS.find(v => v.letter === letter);
    if (!vowel || !vowel.audioUrl) return;
    const a = document.createElement("a");
    a.href = vowel.audioUrl;
    a.download = `shorboborno_${vowel.letter}.mp3`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
};

window.downloadConsonantAudio = function(letter) {
    const cons = BENGALI_CONSONANTS.find(c => c.letter === letter);
    if (!cons || !cons.audioUrl) return;
    const a = document.createElement("a");
    a.href = cons.audioUrl;
    a.download = `banjonborno_${cons.letter}.mp3`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
};

window.switchAudioSubtab = function(type) {
    const btnVowels = document.getElementById("btn-tab-vowels");
    const btnCons = document.getElementById("btn-tab-consonants");
    const vowelsGrid = document.getElementById("vowels-grid");
    const consGrid = document.getElementById("consonants-grid");

    if (type === 'vowels') {
        btnVowels?.classList.add("btn-primary");
        btnVowels?.classList.remove("btn-outline");
        btnCons?.classList.remove("btn-primary");
        btnCons?.classList.add("btn-outline");
        if (vowelsGrid) vowelsGrid.style.display = "grid";
        if (consGrid) consGrid.style.display = "none";
    } else {
        btnCons?.classList.add("btn-primary");
        btnCons?.classList.remove("btn-outline");
        btnVowels?.classList.remove("btn-primary");
        btnVowels?.classList.add("btn-outline");
        if (consGrid) {
            consGrid.style.display = "grid";
            renderConsonantsGrid();
        }
        if (vowelsGrid) vowelsGrid.style.display = "none";
    }
};

function playConsonantAudio(letter) {
    const cons = BENGALI_CONSONANTS.find(c => c.letter === letter);
    if (!cons) return;

    if (activeAudioElement) {
        activeAudioElement.pause();
        activeAudioElement.currentTime = 0;
        document.querySelectorAll(".vowel-card, .consonant-card").forEach(c => c.classList.remove("playing-audio"));
    }

    const card = document.getElementById(`consonant-card-${cons.id}`);
    if (card) card.classList.add("playing-audio");

    const audio = new Audio(cons.audioUrl);
    activeAudioElement = audio;

    audio.onended = () => {
        if (card) card.classList.remove("playing-audio");
        activeAudioElement = null;
    };

    audio.onerror = () => {
        if (card) card.classList.remove("playing-audio");
        showToast(`"${letter}" এর অডিও ফাইলটি এখনো তৈরি হয়নি বা লোড করা যায়নি!`, "warning");
    };

    audio.play().catch(e => {
        console.warn("Consonant audio play prevented:", e);
        if (card) card.classList.remove("playing-audio");
        showToast(`"${letter}" অডিও প্লে করা যায়নি (ফাইলটি তৈরি করা বাকি)।`, "warning");
    });
}

function renderVowelsGrid() {
    const container = document.getElementById("vowels-grid");
    if (!container) return;

    container.innerHTML = BENGALI_VOWELS.map(v => `
        <div id="vowel-card-${v.letter}" class="glass-panel vowel-card" style="padding: 16px; border-radius: var(--radius-lg); position: relative; transition: all 0.2s ease;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px;">
                <div style="display: flex; align-items: center; gap: 10px;">
                    <div class="vowel-char-badge" style="width: 44px; height: 44px; font-size: 24px; font-weight: 900; background: var(--primary-container); color: var(--primary); border-radius: 12px; display: flex; align-items: center; justify-content: center; border: 1.5px solid var(--primary-border);">
                        ${v.letter}
                    </div>
                    <div>
                        <div style="font-size: 16px; font-weight: 800; color: var(--text-main);">${v.word}</div>
                        <div style="font-size: 11px; color: var(--text-muted);">${v.name}</div>
                    </div>
                </div>
                <button type="button" class="btn btn-sm btn-primary" onclick="playVowelAudio('${v.letter}')" style="border-radius: 999px; padding: 6px 14px; font-size: 11px; gap: 4px;">
                    <svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02zM14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z"/></svg>
                    <span>প্লে</span>
                </button>
            </div>
            <div style="background: rgba(255, 255, 255, 0.03); border: 1px solid var(--border-glass); border-radius: var(--radius-md); padding: 10px 12px; font-size: 13px; font-weight: 600; color: var(--text-sub); line-height: 1.4; margin-bottom: 12px;">
                ${v.sentence}
            </div>
            <div style="display: flex; gap: 6px; align-items: center;">
                <input type="text" id="vowel-audio-url-${v.letter}" class="custom-input" placeholder="MP3 লিংক..." value="${v.audioUrl || ''}" style="font-family: monospace; font-size: 10px; padding: 4px 8px; flex: 1;">
                <button type="button" class="btn btn-sm btn-outline" onclick="window.downloadVowelAudio('${v.letter}')" style="font-size: 10px; padding: 4px 8px;" title="MP3 ডাউনলোড">
                    <svg class="svg-icon-xs" viewBox="0 0 24 24"><path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z"/></svg>
                </button>
            </div>
        </div>
    `).join("");

    const btnApplause = document.getElementById("btn-play-applause");
    if (btnApplause) btnApplause.onclick = playKidsApplauseSound;
    const btnChime = document.getElementById("btn-play-chime");
    if (btnChime) btnChime.onclick = playMagicChimeSound;
    const btnSaveGemini = document.getElementById("btn-save-gemini-key");
    if (btnSaveGemini) btnSaveGemini.onclick = saveGeminiKey;
    const btnGeminiSpeak = document.getElementById("btn-gemini-speak");
    if (btnGeminiSpeak) btnGeminiSpeak.onclick = callGeminiVoiceEngine;
    const inputGemini = document.getElementById("input-gemini-key");
    if (inputGemini) inputGemini.value = getStoredGeminiKey();
}

function renderConsonantsGrid() {
    const container = document.getElementById("consonants-grid");
    if (!container || typeof BENGALI_CONSONANTS === "undefined") return;

    container.innerHTML = BENGALI_CONSONANTS.map(c => `
        <div id="consonant-card-${c.id}" class="glass-panel vowel-card consonant-card" style="padding: 16px; border-radius: var(--radius-lg); position: relative; transition: all 0.2s ease; ${!c.isReady ? 'opacity: 0.85;' : ''}">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px;">
                <div style="display: flex; align-items: center; gap: 10px;">
                    <div class="vowel-char-badge" style="width: 44px; height: 44px; font-size: 24px; font-weight: 900; background: ${c.isReady ? 'var(--primary-container)' : 'var(--bg-surface-elevated)'}; color: ${c.isReady ? 'var(--primary)' : 'var(--text-dim)'}; border-radius: 12px; display: flex; align-items: center; justify-content: center; border: 1.5px solid ${c.isReady ? 'var(--primary-border)' : 'var(--border-glass)'};">
                        ${c.letter}
                    </div>
                    <div>
                        <div style="display: flex; align-items: center; gap: 6px;">
                            <div style="font-size: 16px; font-weight: 800; color: var(--text-main);">${c.word}</div>
                            ${c.isReady ? '<span class="badge" style="font-size: 9px; padding: 2px 6px; background: var(--primary-container); color: var(--primary);">রেডি</span>' : '<span class="badge" style="font-size: 9px; padding: 2px 6px; background: var(--bg-surface-elevated); color: var(--text-dim);">বাকি</span>'}
                        </div>
                        <div style="font-size: 11px; color: var(--text-muted);">${c.name}</div>
                    </div>
                </div>
                <button type="button" class="btn btn-sm ${c.isReady ? 'btn-primary' : 'btn-outline'}" onclick="playConsonantAudio('${c.letter}')" style="border-radius: 999px; padding: 6px 14px; font-size: 11px; gap: 4px;">
                    <svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02zM14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z"/></svg>
                    <span>${c.isReady ? 'প্লে' : 'টেস্ট'}</span>
                </button>
            </div>
            <div style="background: rgba(255, 255, 255, 0.03); border: 1px solid var(--border-glass); border-radius: var(--radius-md); padding: 10px 12px; font-size: 13px; font-weight: 600; color: var(--text-sub); line-height: 1.4; margin-bottom: 12px;">
                ${c.sentence}
            </div>
            <div style="display: flex; gap: 6px; align-items: center;">
                <input type="text" id="consonant-audio-url-${c.id}" class="custom-input" placeholder="MP3 লিংক..." value="${c.audioUrl || ''}" style="font-family: monospace; font-size: 10px; padding: 4px 8px; flex: 1;">
                <button type="button" class="btn btn-sm btn-outline" onclick="window.downloadConsonantAudio('${c.letter}')" style="font-size: 10px; padding: 4px 8px;" title="MP3 ডাউনলোড" ${!c.isReady ? 'disabled' : ''}>
                    <svg class="svg-icon-xs" viewBox="0 0 24 24"><path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z"/></svg>
                </button>
            </div>
        </div>
    `).join("");
}
