// =============================================================
// NCTB COMMAND HUB - REMOTE CONTROL, CLONER & SIMULATOR
// =============================================================

function renderMetadata() {
    const noticeInput = document.getElementById("notice-text");
    if (noticeInput) noticeInput.value = currentMetadata.notice || "";
    const noticeToggle = document.getElementById("notice-active-toggle");
    if (noticeToggle) noticeToggle.checked = currentMetadata.isNoticeActive ?? true;
    const minVer = document.getElementById("min-app-version");
    if (minVer) minVer.value = currentMetadata.minAppVersion || 1;
    const maintToggle = document.getElementById("maintenance-toggle");
    if (maintToggle) maintToggle.checked = currentMetadata.isMaintenanceMode ?? false;

    const configRepoInput = document.getElementById("config-github-repo");
    if (configRepoInput) {
        configRepoInput.value = currentMetadata.githubRepo || `omarfarukitbd-spec/nctb-${currentClassId.replace('_', '-')}`;
    }

    const flags = currentMetadata.featureFlags || {};
    const setFlag = (id, val) => {
        const el = document.getElementById(id);
        if (el) el.checked = val;
    };
    setFlag("flag-pdf-reader", flags.pdfReader ?? true);
    setFlag("flag-model-test", flags.modelTest ?? true);
    setFlag("flag-offline-cache", flags.offlineCache ?? true);
    setFlag("flag-search", flags.search ?? true);
    setFlag("flag-video-classes", flags.videoClasses ?? false);
    setFlag("flag-mcq-quiz", flags.mcqQuiz ?? false);
    
    updateMobileSimulator();
}

function updateMobileSimulator() {
    const noticeBanner = document.getElementById("sim-notice-banner");
    const noticeText = document.getElementById("sim-notice-text");
    if (noticeBanner && noticeText) {
        const isNotice = currentMetadata.isNoticeActive ?? true;
        const text = currentMetadata.notice || "";
        noticeBanner.style.display = (isNotice && text.trim() !== "") ? "block" : "none";
        noticeText.innerText = text;
    }

    const simMaintenance = document.getElementById("sim-maintenance-overlay");
    if (simMaintenance) {
        simMaintenance.style.display = currentMetadata.isMaintenanceMode ? "flex" : "none";
    }

    const booksListContainer = document.getElementById("sim-books-list");
    if (booksListContainer) {
        booksListContainer.innerHTML = currentBooks.slice(0, 4).map(b => `
            <div class="sim-book-item" style="display: flex; gap: 8px; align-items: center; background: rgba(255,255,255,0.04); padding: 8px; border-radius: 8px; border: 1px solid rgba(255,255,255,0.05); margin-bottom: 6px;">
                <div style="width: 28px; height: 28px; border-radius: 6px; background: ${b.curriculum === 'SCHOOL' ? '#10B981' : '#8B5CF6'}; display: flex; align-items: center; justify-content: center; font-size: 11px; font-weight: 800; color: #fff;">
                    ${b.curriculum === 'SCHOOL' ? 'স্কুল' : 'মাদ'}
                </div>
                <div style="flex: 1; overflow: hidden;">
                    <div style="font-size: 11px; font-weight: 700; color: #fff; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">${b.title}</div>
                    <div style="font-size: 9px; color: #94A3B8;">${b.chapters?.length || 0} টি অধ্যায়</div>
                </div>
                <div style="font-size: 10px; color: #10B981; font-weight: 700;">পড়ুন ➔</div>
            </div>
        `).join("");
    }
}

function initClassCloneModal() {
    const cloneBtn = document.getElementById("btn-clone-class");
    const modal = document.getElementById("clone-modal");
    const form = document.getElementById("clone-form");
    if (!cloneBtn || !modal || !form) return;

    cloneBtn.onclick = () => modal.classList.add("active");

    form.onsubmit = async (e) => {
        e.preventDefault();
        const targetClassId = document.getElementById("clone-target-class").value;
        const targetClassName = document.getElementById("clone-target-name").value;
        const sourceData = JSON.parse(JSON.stringify(currentBooks));
        
        localStorage.setItem(`admin_data_${targetClassId}`, JSON.stringify({
            books: sourceData,
            metadata: {
                ...currentMetadata,
                notice: `${targetClassName}-এর সকল পাঠ্যবই ও সমাধান যোগ করা হয়েছে।`
            }
        }));

        if (db) {
            try {
                await db.collection("classes").doc(targetClassId).set({
                    classId: targetClassId,
                    className: targetClassName,
                    lastUpdated: Date.now(),
                    books: sourceData,
                    metadata: {
                        ...currentMetadata,
                        notice: `${targetClassName}-এর সকল পাঠ্যবই ও সমাধান যোগ করা হয়েছে।`
                    }
                });
            } catch (err) {
                console.error("Clone sync error:", err);
            }
        }

        modal.classList.remove("active");
        showToast(`🎉 সফলভাবে ${targetClassName} তৈরি ও সিঙ্ক হয়েছে!`, "success");
    };
}
