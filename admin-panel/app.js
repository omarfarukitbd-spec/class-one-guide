// =============================================================
// NCTB COMMAND HUB - ROOT APP ORCHESTRATOR
// =============================================================

window.addEventListener("DOMContentLoaded", () => {
    initAuthGate();
    initTheme();
    initNavigation();
    initAppModals();
    initEventListeners();
    initClassCloneModal();
    renderVowelsGrid();
    renderConsonantsGrid();
    loadClassData(currentClassId);
});

function initTheme() {
    const savedTheme = localStorage.getItem("admin_theme") || "dark";
    document.documentElement.setAttribute("data-theme", savedTheme);
    const themeBtn = document.getElementById("btn-theme-toggle") || document.getElementById("btn-toggle-theme");
    if (themeBtn) {
        themeBtn.onclick = () => {
            const current = document.documentElement.getAttribute("data-theme");
            const next = current === "dark" ? "light" : "dark";
            document.documentElement.setAttribute("data-theme", next);
            localStorage.setItem("admin_theme", next);
        };
    }
}

function initNavigation() {
    const navItems = document.querySelectorAll(".nav-item[data-tab]");
    navItems.forEach(item => {
        item.addEventListener("click", () => {
            const targetTab = item.getAttribute("data-tab");
            navItems.forEach(n => n.classList.remove("active"));
            item.classList.add("active");

            document.querySelectorAll(".tab-section, .tab-content").forEach(tab => {
                tab.classList.remove("active");
            });

            const activeTabContent = document.getElementById(targetTab);
            if (activeTabContent) {
                activeTabContent.classList.add("active");
            }
        });
    });
}

function initEventListeners() {
    document.getElementById("class-selector")?.addEventListener("change", (e) => {
        loadClassData(e.target.value);
    });

    document.getElementById("btn-view-grid")?.addEventListener("click", () => {
        currentViewMode = 'grid';
        document.getElementById("btn-view-grid")?.classList.add("btn-primary");
        document.getElementById("btn-view-grid")?.classList.remove("btn-outline");
        document.getElementById("btn-view-table")?.classList.remove("btn-primary");
        document.getElementById("btn-view-table")?.classList.add("btn-outline");
        renderBooks();
    });

    document.getElementById("btn-view-table")?.addEventListener("click", () => {
        currentViewMode = 'table';
        document.getElementById("btn-view-table")?.classList.add("btn-primary");
        document.getElementById("btn-view-table")?.classList.remove("btn-outline");
        document.getElementById("btn-view-grid")?.classList.remove("btn-primary");
        document.getElementById("btn-view-grid")?.classList.add("btn-outline");
        renderBooks();
    });

    const syncBtn = document.getElementById("btn-sync-all") || document.getElementById("btn-broadcast-sync");
    syncBtn?.addEventListener("click", triggerBroadcastSync);

    const seedBtn = document.getElementById("btn-seed-class1") || document.getElementById("btn-seed-data");
    seedBtn?.addEventListener("click", seedClass1Data);

    document.getElementById("btn-export-backup")?.addEventListener("click", exportBackupJSON);
    
    const importInput = document.getElementById("import-file-input") || document.getElementById("btn-import-backup");
    importInput?.addEventListener("change", (e) => {
        if (e.target.files && e.target.files[0]) importBackupJSON(e.target.files[0]);
    });

    const searchInput = document.getElementById("global-search") || document.getElementById("global-search-input");
    if (searchInput) {
        searchInput.addEventListener("input", (e) => {
            const query = e.target.value.toLowerCase().trim();
            if (!query) {
                renderBooks(currentBooks);
                return;
            }
            const filtered = currentBooks.filter(b => 
                b.title.toLowerCase().includes(query) || 
                (b.subtitle && b.subtitle.toLowerCase().includes(query)) ||
                b.chapters?.some(c => c.title.toLowerCase().includes(query))
            );
            renderBooks(filtered);
        });
    }

    const currFilter = document.getElementById("curriculum-filter") || document.getElementById("filter-curriculum");
    currFilter?.addEventListener("change", (e) => {
        const val = e.target.value;
        if (val === "ALL") renderBooks(currentBooks);
        else renderBooks(currentBooks.filter(b => b.curriculum === val));
    });
}

function initAppModals() {
    window.openBookModal = function(bookId = null) {
        const modal = document.getElementById("book-modal");
        const form = document.getElementById("book-form");
        if (!modal || !form) return;
        form.reset();

        if (bookId) {
            const book = currentBooks.find(b => b.bookId === bookId);
            if (book) {
                document.getElementById("modal-book-id").value = book.bookId;
                document.getElementById("modal-book-title").value = book.title;
                document.getElementById("modal-book-sub").value = book.subtitle || "";
                document.getElementById("modal-book-curriculum").value = book.curriculum;
                document.getElementById("modal-book-pdf").value = book.pdfUrl || "";
            }
        } else {
            document.getElementById("modal-book-id").value = "";
        }
        modal.classList.add("active");
    };

    window.closeModal = function(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) modal.classList.remove("active");
    };

    const bookForm = document.getElementById("book-form");
    if (bookForm) {
        bookForm.onsubmit = async (e) => {
            e.preventDefault();
            const bookId = document.getElementById("modal-book-id").value;
            const title = document.getElementById("modal-book-title").value.trim();
            const subtitle = document.getElementById("modal-book-sub").value.trim();
            const curriculum = document.getElementById("modal-book-curriculum").value;
            const pdfUrl = document.getElementById("modal-book-pdf").value.trim();

            if (bookId) {
                const book = currentBooks.find(b => b.bookId === bookId);
                if (book) {
                    book.title = title;
                    book.subtitle = subtitle;
                    book.curriculum = curriculum;
                    book.pdfUrl = pdfUrl;
                }
            } else {
                currentBooks.push({
                    bookId: `book_${Date.now()}`,
                    title,
                    subtitle,
                    curriculum,
                    pdfUrl,
                    availableVersions: ["BANGLA"],
                    chapters: []
                });
            }
            await persistLocalData(true);
            window.closeModal("book-modal");
            renderStats();
            renderBooks();
            renderDashboardPreview();
            showToast("বই সফলভাবে সংরক্ষিত হয়েছে!", "success");
        };
    }

    const pinConfirmForm = document.getElementById("pin-confirm-form");
    if (pinConfirmForm) {
        pinConfirmForm.onsubmit = async (e) => {
            e.preventDefault();
            const pin = document.getElementById("action-confirm-pin").value.trim();
            const isValid = await verifyMasterPin(pin);
            if (isValid) {
                window.closeModal("pin-confirm-modal");
                if (pendingActionCallback) {
                    await pendingActionCallback();
                    pendingActionCallback = null;
                }
            } else {
                showToast("ভুল মাস্টার পিন!", "error");
            }
        };
    }
}

window.moveBook = async function(index, direction) {
    const targetIdx = index + direction;
    if (targetIdx < 0 || targetIdx >= currentBooks.length) return;
    const temp = currentBooks[index];
    currentBooks[index] = currentBooks[targetIdx];
    currentBooks[targetIdx] = temp;
    await persistLocalData(true);
    renderBooks();
};

window.deleteBook = function(bookId) {
    pendingActionCallback = async () => {
        currentBooks = currentBooks.filter(b => b.bookId !== bookId);
        await persistLocalData(true);
        renderStats();
        renderBooks();
        renderDashboardPreview();
        showToast("বইটি সফলভাবে মুছে ফেলা হয়েছে!", "success");
    };
    document.getElementById("pin-confirm-title").innerText = "বই ডিলিট নিশ্চিত করুন";
    document.getElementById("pin-confirm-desc").innerText = "বইটি এবং এর সকল অধ্যায় ডিলিট করতে মাস্টার পিন দিন।";
    document.getElementById("action-confirm-pin").value = "";
    document.getElementById("pin-confirm-modal").classList.add("active");
    setTimeout(() => document.getElementById("action-confirm-pin")?.focus(), 150);
};

window.testAndPreviewPdf = function(pdfUrl) {
    if (!pdfUrl) {
        showToast("কোনো পিডিএফ লিংক নেই!", "warning");
        return;
    }
    window.open(pdfUrl, "_blank");
};

function renderDashboardPreview() {
    const previewContainer = document.getElementById("dashboard-books-preview");
    if (!previewContainer) return;
    previewContainer.innerHTML = "";
    const previewList = currentBooks.slice(0, 3);
    if (previewList.length === 0) {
        previewContainer.innerHTML = `<p style="color: var(--text-muted); padding: 20px;">কোনো বই লোড করা নেই।</p>`;
        return;
    }
    previewList.forEach(book => {
        const card = document.createElement("div");
        card.className = "book-card glass-panel";
        card.innerHTML = `
            <div>
                <span class="badge ${book.curriculum === 'SCHOOL' ? 'badge-school' : 'badge-madrasah'}">
                    ${book.curriculum === 'SCHOOL' ? 'সাধারণ স্কুল' : 'ইবতেদায়ী মাদ্রাসা'}
                </span>
                <h3 class="book-title">${book.title}</h3>
                <p class="book-sub">${book.subtitle || ""}</p>
            </div>
            <button class="btn btn-sm btn-secondary" onclick="document.querySelector('[data-tab=\\'tab-books\\']').click()">বিস্তারিত দেখুন</button>
        `;
        previewContainer.appendChild(card);
    });
}

function showToast(message, type = "info") {
    const toast = document.createElement("div");
    toast.className = `toast toast-${type}`;
    toast.style.cssText = `
        position: fixed; bottom: 24px; right: 24px; z-index: 9999;
        background: ${type === 'success' ? '#10B981' : type === 'error' ? '#EF4444' : '#3B82F6'};
        color: #fff; padding: 12px 20px; border-radius: 8px; font-weight: 600; font-size: 14px;
        box-shadow: 0 10px 25px rgba(0,0,0,0.3); transition: all 0.3s ease;
    `;
    toast.innerText = message;
    document.body.appendChild(toast);
    setTimeout(() => {
        toast.style.opacity = "0";
        setTimeout(() => toast.remove(), 300);
    }, 3500);
}
