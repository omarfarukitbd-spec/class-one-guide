// =============================================================
// NCTB COMMAND HUB - BACKUP, RESTORE & CLOUD SYNC
// =============================================================

function exportBackupJSON() {
    const data = {
        exportedAt: new Date().toISOString(),
        classId: currentClassId,
        books: currentBooks,
        metadata: currentMetadata
    };
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: "application/json" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `nctb_${currentClassId}_backup_${new Date().toISOString().split("T")[0]}.json`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    showToast("সফলভাবে ব্যাকআপ JSON ডাউনলোড হয়েছে!", "success");
}

function importBackupJSON(file) {
    if (!file) return;
    const reader = new FileReader();
    reader.onload = async (e) => {
        try {
            const data = JSON.parse(e.target.result);
            if (data.books && Array.isArray(data.books)) {
                currentBooks = data.books;
                if (data.metadata) currentMetadata = data.metadata;
                await persistLocalData(true);
                renderStats();
                renderBooks();
                renderMetadata();
                renderDashboardPreview();
                showToast("সফলভাবে ব্যাকআপ থেকে ডাটা রিস্টোর করা হয়েছে!", "success");
            } else {
                showToast("অকার্যকর ব্যাকআপ ফাইল ফরম্যাট!", "error");
            }
        } catch (err) {
            showToast("JSON পার্স করতে ব্যর্থ: " + err.message, "error");
        }
    };
    reader.readAsText(file);
}

async function triggerBroadcastSync() {
    const syncBtn = document.getElementById("btn-sync-all");
    if (syncBtn) {
        syncBtn.disabled = true;
        syncBtn.innerHTML = `<span>🔄 সিঙ্ক হচ্ছে...</span>`;
    }
    await persistLocalData(true);
    if (syncBtn) {
        syncBtn.disabled = false;
        syncBtn.innerHTML = `<svg class="svg-icon-sm" viewBox="0 0 24 24"><path d="M12 4V1L8 5l4 4V6c3.31 0 6 2.69 6 6 0 1.01-.25 1.97-.7 2.8l1.46 1.46C19.54 15.03 20 13.57 20 12c0-4.42-3.58-8-8-8zm0 14c-3.31 0-6-2.69-6-6 0-1.01.25-1.97.7-2.8L5.24 7.74C4.46 8.97 4 10.43 4 12c0 4.42 3.58 8 8 8v3l4-4-4-4v3z"/></svg><span>ব্রডকাস্ট সিঙ্ক</span>`;
    }
    showToast("ফায়ারবেস ক্লাউডে রিয়েল-টাইম সিঙ্ক ব্রডকাস্ট সম্পন্ন হয়েছে!", "success");
}

async function seedClass1Data() {
    pendingActionCallback = async () => {
        currentBooks = JSON.parse(JSON.stringify(INITIAL_CLASS_1_BOOKS));
        currentMetadata.lastUpdated = Date.now();
        await persistLocalData(true);
        renderStats();
        renderBooks();
        renderDashboardPreview();
        showToast("১ম শ্রেণির সকল বই ও অধ্যায় ফায়ারবেস ক্লাউডে সিড হয়েছে!", "success");
    };
    const titleEl = document.getElementById("pin-confirm-title");
    if (titleEl) titleEl.innerText = "ডাটাবেস ওভাররাইট নিশ্চিত করুন";
    const descEl = document.getElementById("pin-confirm-desc");
    if (descEl) descEl.innerText = "এই অ্যাকশনটি বর্তমান ক্লাসের সকল বই ডিলিট করে সম্পূর্ণ ১০টি করে ইউনিট বসিয়ে দেবে। নিশ্চিত করতে মাস্টার পিন দিন।";
    const pinInput = document.getElementById("action-confirm-pin");
    if (pinInput) pinInput.value = "";
    const modal = document.getElementById("pin-confirm-modal");
    if (modal) modal.classList.add("active");
    setTimeout(() => pinInput?.focus(), 150);
}

function updatePayloadMetrics() {
    const payloadBytes = new Blob([JSON.stringify({ books: currentBooks, metadata: currentMetadata })]).size;
    const kb = (payloadBytes / 1024).toFixed(1);
    const limitKb = 1000;
    const percent = Math.min((kb / limitKb) * 100, 100).toFixed(1);

    const payloadText = document.getElementById("payload-size-text");
    if (payloadText) payloadText.innerText = `${kb} KB / ${limitKb} KB (${percent}%)`;
    const payloadBar = document.getElementById("payload-progress-bar");
    if (payloadBar) payloadBar.style.width = `${percent}%`;
}
