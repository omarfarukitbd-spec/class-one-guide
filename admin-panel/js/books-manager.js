// =============================================================
// NCTB COMMAND HUB - BOOKS & CHAPTERS MANAGEMENT ENGINE
// =============================================================

let currentClassId = 'class_1';
let currentBooks = [];
let currentMetadata = {
    notice: "২০২৬ শিক্ষাক্রমের সকল পাঠ্যবই ও সমাধান নিয়মিত হালনাগাদ করা হচ্ছে।",
    isNoticeActive: true,
    minAppVersion: 1,
    isMaintenanceMode: false,
    githubRepo: "omarfarukitbd-spec/nctb-class-1",
    featureFlags: {
        pdfReader: true,
        modelTest: true,
        offlineCache: true,
        search: true,
        videoClasses: false,
        mcqQuiz: false
    }
};

let currentViewMode = 'grid';

async function loadClassData(classId) {
    currentClassId = classId;
    const saved = localStorage.getItem(`admin_data_${classId}`);
    if (saved) {
        try {
            const parsed = JSON.parse(saved);
            currentBooks = (parsed.books && parsed.books.length > 0) ? parsed.books : (classId === 'class_1' ? JSON.parse(JSON.stringify(INITIAL_CLASS_1_BOOKS)) : []);
            currentMetadata = parsed.metadata || currentMetadata;
        } catch {
            currentBooks = classId === 'class_1' ? JSON.parse(JSON.stringify(INITIAL_CLASS_1_BOOKS)) : [];
        }
    } else {
        currentBooks = classId === 'class_1' ? JSON.parse(JSON.stringify(INITIAL_CLASS_1_BOOKS)) : [];
    }

    if (!currentBooks || currentBooks.length === 0) {
        currentBooks = classId === 'class_1' ? JSON.parse(JSON.stringify(INITIAL_CLASS_1_BOOKS)) : [];
    }

    renderStats();
    renderMetadata();
    renderBooks();
    renderDashboardPreview();

    if (db) {
        try {
            const doc = await db.collection("classes").doc(classId).get();
            if (doc.exists) {
                const cloudData = doc.data();
                if (cloudData.books && Array.isArray(cloudData.books) && cloudData.books.length > 0) {
                    currentBooks = cloudData.books;
                    if (cloudData.metadata) currentMetadata = cloudData.metadata;
                    localStorage.setItem(`admin_data_${currentClassId}`, JSON.stringify({
                        books: currentBooks,
                        metadata: currentMetadata
                    }));
                    renderStats();
                    renderMetadata();
                    renderBooks();
                    renderDashboardPreview();
                } else if (classId === 'class_1' && currentBooks.length > 0) {
                    persistLocalData(true);
                }
            }
        } catch (err) {
            console.warn("Firestore fetch notice:", err);
        }
    }
}

async function persistLocalData(syncToCloud = true) {
    localStorage.setItem(`admin_data_${currentClassId}`, JSON.stringify({
        books: currentBooks,
        metadata: currentMetadata
    }));

    if (syncToCloud && db) {
        try {
            const activePin = sessionStorage.getItem("active_pin") || "";
            await db.collection("admin_config").doc("current_session").set({
                pin: activePin,
                valid_until: Date.now() + 60000
            });

            await db.collection("classes").doc(currentClassId).set({
                classId: currentClassId,
                lastUpdated: Date.now(),
                books: currentBooks,
                metadata: currentMetadata
            });
        } catch (err) {
            console.error("Firebase sync error:", err);
            showToast("ক্লাউড ডাটাবেস সিঙ্ক ত্রুটি: " + (err.message || "পারমিশন নেই"), "error");
        }
    }
}

function renderStats() {
    const totalSpan = document.getElementById("stat-total-books");
    if (totalSpan) totalSpan.innerText = currentBooks.length;
    const schoolSpan = document.getElementById("stat-school-books");
    if (schoolSpan) schoolSpan.innerText = currentBooks.filter(b => b.curriculum === "SCHOOL").length;
    const madrSpan = document.getElementById("stat-madrasah-books");
    if (madrSpan) madrSpan.innerText = currentBooks.filter(b => b.curriculum === "MADRASAH").length;
    const totalChapters = currentBooks.reduce((acc, b) => acc + (b.chapters?.length || 0), 0);
    const chapSpan = document.getElementById("stat-total-chapters");
    if (chapSpan) chapSpan.innerText = totalChapters;
    updatePayloadMetrics();
}

function renderBooks(booksToRender = currentBooks) {
    const gridContainer = document.getElementById("books-grid-container");
    const tableContainer = document.getElementById("books-table-container");
    if (!gridContainer || !tableContainer) return;

    if (currentViewMode === 'grid') {
        gridContainer.style.display = "grid";
        tableContainer.style.display = "none";
        renderBooksGrid(booksToRender, gridContainer);
    } else {
        gridContainer.style.display = "none";
        tableContainer.style.display = "block";
        renderBooksTable(booksToRender, tableContainer);
    }
}

function renderBooksGrid(booksToRender, container) {
    container.innerHTML = "";
    if (booksToRender.length === 0) {
        container.innerHTML = `<div style="grid-column: 1/-1; text-align: center; padding: 40px;" class="glass-panel"><p style="font-size: 15px; color: var(--text-muted);">কোনো বই পাওয়া যায়নি। "নতুন বই যুক্ত করুন" বা "১ম শ্রেণি সিড" চাপুন।</p></div>`;
        return;
    }

    booksToRender.forEach((book, bookIdx) => {
        const isSchool = book.curriculum === "SCHOOL";
        const badgeClass = isSchool ? "badge-school" : "badge-madrasah";
        const badgeText = isSchool ? "সাধারণ স্কুল" : "ইবতেদায়ী মাদ্রাসা";
        const chaptersCount = book.chapters?.length || 0;

        const card = document.createElement("div");
        card.className = "book-card glass-panel";
        card.innerHTML = `
            <div>
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
                    <span class="badge ${badgeClass}">${badgeText}</span>
                    <div style="display: flex; gap: 6px;">
                        <button class="btn btn-sm btn-icon-only btn-arrow" title="বইটি উপরে নিন" onclick="window.moveBook(${bookIdx}, -1)">${SVG_ICONS.up}</button>
                        <button class="btn btn-sm btn-icon-only btn-arrow" title="বইটি নিচে নিন" onclick="window.moveBook(${bookIdx}, 1)">${SVG_ICONS.down}</button>
                    </div>
                </div>
                <h3 class="book-title">${book.title}</h3>
                <p class="book-sub">${book.subtitle || "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড"}</p>
                <div class="pdf-link-box">
                    <span style="display: flex; align-items: center; gap: 6px; font-family: monospace; font-size: 11px;">
                        <span style="color: var(--primary);">${SVG_ICONS.link}</span> <strong style="color: var(--primary);">${book.pdfUrl || "লিংক নেই"}</strong>
                    </span>
                    <button class="btn btn-sm btn-outline" style="font-weight: 700;" onclick="window.testAndPreviewPdf('${book.pdfUrl}')">টেস্ট</button>
                </div>
                <div style="font-size: 12px; color: var(--text-muted); margin-bottom: 12px;">অধ্যায় সংখ্যা: <strong>${chaptersCount} টি</strong></div>
            </div>
            <div style="display: flex; gap: 8px; margin-top: 14px;">
                <button class="btn btn-sm btn-secondary" style="flex: 1;" onclick="window.openChapterModal('${book.bookId}')">অধ্যায়সমূহ (${chaptersCount})</button>
                <button class="btn btn-sm btn-outline" onclick="window.openBookModal('${book.bookId}')">${SVG_ICONS.edit}</button>
                <button class="btn btn-sm btn-danger" onclick="window.deleteBook('${book.bookId}')">${SVG_ICONS.delete}</button>
            </div>
        `;
        container.appendChild(card);
    });
}

function renderBooksTable(booksToRender, container) {
    container.innerHTML = "";
    if (booksToRender.length === 0) {
        container.innerHTML = `<div style="text-align: center; padding: 40px;" class="glass-panel"><p style="font-size: 15px; color: var(--text-muted);">কোনো বই পাওয়া যায়নি।</p></div>`;
        return;
    }
    const table = document.createElement("table");
    table.className = "custom-table";
    table.innerHTML = `
        <thead>
            <tr>
                <th>ক্রম</th>
                <th>বইয়ের নাম ও বিবরণ</th>
                <th>কারিকুলাম</th>
                <th>পিডিএফ লিংক</th>
                <th>অধ্যায়</th>
                <th>অ্যাকশন</th>
            </tr>
        </thead>
        <tbody>
            ${booksToRender.map((book, idx) => `
                <tr>
                    <td>${idx + 1}</td>
                    <td><strong>${book.title}</strong><div style="font-size: 11px; color: var(--text-muted);">${book.subtitle || ""}</div></td>
                    <td><span class="badge ${book.curriculum === 'SCHOOL' ? 'badge-school' : 'badge-madrasah'}">${book.curriculum === 'SCHOOL' ? 'স্কুল' : 'মাদ্রাসা'}</span></td>
                    <td><span style="font-family: monospace; font-size: 11px; color: var(--primary);">${book.pdfUrl || "—"}</span></td>
                    <td>${book.chapters?.length || 0} টি</td>
                    <td>
                        <div style="display: flex; gap: 6px;">
                            <button class="btn btn-sm btn-secondary" onclick="window.openChapterModal('${book.bookId}')">অধ্যায়</button>
                            <button class="btn btn-sm btn-outline" onclick="window.openBookModal('${book.bookId}')">${SVG_ICONS.edit}</button>
                            <button class="btn btn-sm btn-danger" onclick="window.deleteBook('${book.bookId}')">${SVG_ICONS.delete}</button>
                        </div>
                    </td>
                </tr>
            `).join("")}
        </tbody>
    `;
    container.appendChild(table);
}

window.closeAllModals = function() {
    document.querySelectorAll(".modal-backdrop").forEach(m => m.classList.remove("active"));
};

window.quickFillCdn = function(targetId, type) {
    const el = document.getElementById(targetId);
    if (!el) return;
    const fileName = el.value.trim().split("/").pop() || "sample.pdf";
    const repo = currentMetadata.githubRepo || "omarfarukitbd-spec/nctb-class-1";
    if (type === "JSDELIVR") {
        el.value = `https://cdn.jsdelivr.net/gh/${repo}@main/pdfs/${fileName}`;
    } else if (type === "RAW") {
        el.value = `https://raw.githubusercontent.com/${repo}/main/pdfs/${fileName}`;
    }
};

window.openChapterModal = function(bookId) {
    const book = currentBooks.find(b => b.bookId === bookId);
    if (!book) return;
    const modal = document.getElementById("chapter-modal");
    const form = document.getElementById("chapter-form");
    if (!modal || !form) return;
    form.reset();
    document.getElementById("chapter-book-id").value = bookId;
    modal.classList.add("active");
};

window.openResourceModal = function(bookId, chapterId) {
    const modal = document.getElementById("resource-modal");
    const form = document.getElementById("resource-form");
    if (!modal || !form) return;
    form.reset();
    document.getElementById("res-book-id").value = bookId;
    document.getElementById("res-chapter-id").value = chapterId;
    modal.classList.add("active");
};
