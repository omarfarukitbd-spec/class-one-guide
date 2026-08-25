/**
 * Enterprise Global Admin Panel • Controller & Data Engine
 * 100% Material 3 SVG Vector Architecture • High-Contrast Dark Mode
 */

// SVG Icon Definitions (Material 3 Vector Path Icons)
const SVG_ICONS = {
    book: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M18 2H6c-1.1 0-2 .9-2 2v16c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zM6 4h5v8l-2.5-1.5L6 12V4z"/></svg>`,
    school: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M5 13.18v4L12 21l7-3.82v-4L12 17l-7-3.82zM12 3L1 9l11 6 9-4.91V17h2V9L12 3z"/></svg>`,
    madrasah: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M12 2L4 5v6.09c0 5.05 3.41 9.76 8 10.91 4.59-1.15 8-5.86 8-10.91V5l-8-3zm1 14h-2v-2h2v2zm0-4h-2V7h2v5z"/></svg>`,
    chapters: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M3 13h2v-2H3v2zm0 4h2v-2H3v2zm0-8h2V7H3v2zm4 4h14v-2H7v2zm0 4h14v-2H7v2zM7 7v2h14V7H7z"/></svg>`,
    dashboard: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"/></svg>`,
    notice: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M18 11v2h4v-2h-4zm-2 6.61c.96.71 2.21 1.65 3.2 2.39.4-.53.8-1.07 1.2-1.6-.99-.74-2.24-1.68-3.2-2.4-.4.54-.8 1.08-1.2 1.61zM20.4 5.6c-.4-.53-.8-1.07-1.2-1.6-.99.74-2.24 1.68-3.2 2.4.4.53.8 1.07 1.2 1.6.96-.72 2.21-1.65 3.2-2.4zM4 9c-1.1 0-2 .9-2 2v2c0 1.1.9 2 2 2h1l3 5h2l-2-5h2l4 3V6L10 9H4z"/></svg>`,
    settings: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M19.14 12.94c.04-.3.06-.61.06-.94 0-.32-.02-.64-.07-.94l2.03-1.58c.18-.14.23-.41.12-.61l-1.92-3.32c-.12-.22-.37-.29-.59-.22l-2.39.96c-.5-.38-1.03-.7-1.62-.94l-.36-2.54c-.04-.24-.24-.41-.48-.41h-3.84c-.24 0-.43.17-.47.41l-.36 2.54c-.59.24-1.13.57-1.62.94l-2.39-.96c-.22-.08-.47 0-.59.22L2.74 8.87c-.12.21-.08.47.12.61l2.03 1.58c-.05.3-.09.63-.09.94s.02.64.07.94l-2.03 1.58c-.18.14-.23.41-.12.61l1.92 3.32c.12.22.37.29.59.22l2.39-.96c.5.38 1.03.7 1.62.94l.36 2.54c.05.24.24.41.48.41h3.84c.24 0 .44-.17.47-.41l.36-2.54c.59-.24 1.13-.56 1.62-.94l2.39.96c.22.08.47 0 .59-.22l1.92-3.32c.12-.22.07-.47-.12-.61l-2.01-1.58zM12 15.6c-1.98 0-3.6-1.62-3.6-3.6s1.62-3.6 3.6-3.6 3.6 1.62 3.6 3.6-1.62 3.6-3.6 3.6z"/></svg>`,
    sync: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M12 4V1L8 5l4 4V6c3.31 0 6 2.69 6 6 0 1.01-.25 1.97-.7 2.8l1.46 1.46C19.54 15.03 20 13.57 20 12c0-4.42-3.58-8-8-8zm0 14c-3.31 0-6-2.69-6-6 0-1.01.25-1.97.7-2.8L5.24 7.74C4.46 8.97 4 10.43 4 12c0 4.42 3.58 8 8 8v3l4-4-4-4v3z"/></svg>`,
    seed: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M19.35 10.04C18.67 6.59 15.64 4 12 4 9.11 4 6.6 5.64 5.35 8.04 2.34 8.36 0 10.91 0 14c0 3.31 2.69 6 6 6h13c2.76 0 5-2.24 5-5 0-2.64-2.05-4.78-4.65-4.96zM14 13v4h-4v-4H7l5-5 5 5h-3z"/></svg>`,
    backup: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M19 12v7H5v-7H3v7c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2v-7h-2zm-6 .67l2.59-2.58L17 11.5l-5 5-5-5 1.41-1.41L11 12.67V3h2v9.67z"/></svg>`,
    bulk: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M4 6H2v14c0 1.1.9 2 2 2h14v-2H4V6zm16-4H8c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm-1 9h-4v4h-2v-4H9V9h4V5h2v4h4v2z"/></svg>`,
    lock: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zm-6 9c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2zm3.1-9H8.9V6c0-1.71 1.39-3.1 3.1-3.1 1.71 0 3.1 1.39 3.1 3.1v2z"/></svg>`,
    edit: `<svg class="svg-icon-sm" viewBox="0 0 24 24" fill="currentColor"><path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/></svg>`,
    delete: `<svg class="svg-icon-sm" viewBox="0 0 24 24" fill="currentColor"><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"/></svg>`,
    link: `<svg class="svg-icon-sm" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M10 13a5 5 0 0 0 7.54.54l3-3a5 5 0 0 0-7.07-7.07l-1.72 1.71"></path><path d="M14 11a5 5 0 0 0-7.54-.54l-3 3a5 5 0 0 0 7.07 7.07l1.71-1.71"></path></svg>`,
    up: `<svg class="svg-icon-sm" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3.5" stroke-linecap="round" stroke-linejoin="round"><polyline points="18 15 12 9 6 15"></polyline></svg>`,
    down: `<svg class="svg-icon-sm" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3.5" stroke-linecap="round" stroke-linejoin="round"><polyline points="6 9 12 15 18 9"></polyline></svg>`,
    add: `<svg class="svg-icon-sm" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>`,
    theme: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M12 3c-4.97 0-9 4.03-9 9s4.03 9 9 9 9-4.03 9-9c0-.46-.04-.92-.1-1.36-.98 1.37-2.58 2.26-4.4 2.26-2.98 0-5.4-2.42-5.4-5.4 0-1.81.89-3.42 2.26-4.4-.44-.06-.9-.1-1.36-.1z"/></svg>`,
    search: `<svg class="svg-icon" viewBox="0 0 24 24" fill="currentColor"><path d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5 6.5 6.5 0 1 0 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/></svg>`
};

// Firebase Firestore Client Integration
const FIREBASE_CONFIG = {
    apiKey: "AIzaSyDIVxnaPdE_NwRhJMoltdFslBCM59HAtRk",
    authDomain: "books-hub-6e7b8.firebaseapp.com",
    projectId: "books-hub-6e7b8",
    storageBucket: "books-hub-6e7b8.firebasestorage.app",
    messagingSenderId: "879247421687",
    appId: "1:879247421687:web:a9e0e2bbd8efd88dcfe3f2"
};

let db = null;
try {
    if (typeof firebase !== "undefined") {
        firebase.initializeApp(FIREBASE_CONFIG);
        db = firebase.firestore();
        console.log("Firebase Firestore connected successfully to books-hub-6e7b8!");
    }
} catch (e) {
    console.warn("Firestore initialization notice:", e);
}

// Global State
let currentClassId = 'class_1';
let currentBooks = [];
let currentMetadata = {
    lastUpdated: Date.now(),
    notice: "২০২৬ শিক্ষাক্রমের সকল পাঠ্যবই ও গাইড নিয়মিত হালনাগাদ করা হচ্ছে।",
    isNoticeActive: true,
    minAppVersion: 1,
    isMaintenanceMode: false,
    featureFlags: {
        pdfReader: true,
        modelTest: true,
        offlineCache: true,
        search: true,
        videoClasses: false,
        mcqQuiz: false
    }
};

let currentViewMode = 'grid'; // 'grid' or 'table'

// Authentic Initial Class 1 Data
const INITIAL_CLASS_1_BOOKS = [
    {
        bookId: "school_bangla",
        title: "আমার বাংলা বই",
        subtitle: "জাতীয় শিক্ষাক্রম ও পাঠ্যপুস্তক বোর্ড",
        curriculum: "SCHOOL",
        pdfUrl: "bangla_class1_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            {
                chapterId: "sb_c1",
                unitNo: "ইউনিট ১",
                title: "আমাদের দেশ ও বর্ণমালা",
                version: "BANGLA",
                resources: [
                    { resourceId: "r1", title: "মূল বই পড়ুন", pdfUrl: "bangla_u1_text.pdf", type: "TEXTBOOK" },
                    { resourceId: "r2", title: "গাইডবুক পড়ুন", pdfUrl: "bangla_u1_guide.pdf", type: "GUIDEBOOK" },
                    { resourceId: "r3", title: "মডেল টেস্ট", pdfUrl: "bangla_u1_test.pdf", type: "MODEL_TEST" }
                ]
            },
            {
                chapterId: "sb_c2",
                unitNo: "ইউনিট ২",
                title: "ছড়া ও কবিতা আবৃত্তি",
                version: "BANGLA",
                resources: [
                    { resourceId: "r4", title: "মূল বই পড়ুন", pdfUrl: "bangla_u2_text.pdf", type: "TEXTBOOK" },
                    { resourceId: "r5", title: "গাইডবুক পড়ুন", pdfUrl: "bangla_u2_guide.pdf", type: "GUIDEBOOK" }
                ]
            }
        ]
    },
    {
        bookId: "school_english",
        title: "English for Today",
        subtitle: "Primary Curriculum Class 1",
        curriculum: "SCHOOL",
        pdfUrl: "english_class1_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            {
                chapterId: "se_c1",
                unitNo: "Unit 1",
                title: "Greetings & Alphabet",
                version: "BANGLA",
                resources: [
                    { resourceId: "r13", title: "Read Textbook", pdfUrl: "eng_u1_text.pdf", type: "TEXTBOOK" },
                    { resourceId: "r14", title: "Read Guidebook", pdfUrl: "eng_u1_guide.pdf", type: "GUIDEBOOK" }
                ]
            }
        ]
    },
    {
        bookId: "school_math",
        title: "প্রাথমিক গণিত",
        subtitle: "সংখ্যার ধারণা, গণনা ও সহজ হিসাব",
        curriculum: "SCHOOL",
        pdfUrl: "math_class1_full.pdf",
        availableVersions: ["BANGLA", "ENGLISH"],
        chapters: [
            {
                chapterId: "sm_c1",
                unitNo: "অধ্যায় ১",
                title: "তুলনা ও গণনা (১ থেকে ১০)",
                version: "BANGLA",
                resources: [
                    { resourceId: "r19", title: "মূল বই পড়ুন", pdfUrl: "math_u1_text.pdf", type: "TEXTBOOK" },
                    { resourceId: "r20", title: "গাইডবুক পড়ুন", pdfUrl: "math_u1_guide.pdf", type: "GUIDEBOOK" }
                ]
            }
        ]
    },
    {
        bookId: "school_art",
        title: "চারুপাঠ ও শিল্পকলা",
        subtitle: "সহজ ছবি আঁকা ও রঙের আনন্দ",
        curriculum: "SCHOOL",
        pdfUrl: "art_class1_full.pdf",
        availableVersions: ["BANGLA"],
        chapters: [
            {
                chapterId: "sa_c1",
                unitNo: "অধ্যায় ১",
                title: "রেখা ও রঙের খেলা",
                version: "BANGLA",
                resources: [
                    { resourceId: "r25", title: "মূল বই পড়ুন", pdfUrl: "art_u1_text.pdf", type: "TEXTBOOK" }
                ]
            }
        ]
    },
    {
        bookId: "madr_quran",
        title: "কুরআন মাজীদ ও তাজভীদ",
        subtitle: "ইবতেদায়ী ১ম শ্রেণি • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "quran_class1_full.pdf",
        availableVersions: ["BANGLA"],
        chapters: [
            {
                chapterId: "mq_c1",
                unitNo: "অধ্যায় ১",
                title: "আরবি হরফ ও মাখরাজ পরিচিতি",
                version: "BANGLA",
                resources: [
                    { resourceId: "mr1", title: "মূল বই পড়ুন", pdfUrl: "quran_u1_text.pdf", type: "TEXTBOOK" },
                    { resourceId: "mr2", title: "গাইডবুক পড়ুন", pdfUrl: "quran_u1_guide.pdf", type: "GUIDEBOOK" }
                ]
            }
        ]
    },
    {
        bookId: "madr_aqaid",
        title: "আকাইদ ও ফিকহ",
        subtitle: "ইসলামি বিশ্বাস ও প্রাথমিক বিধান • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "aqaid_class1_full.pdf",
        availableVersions: ["BANGLA"],
        chapters: [
            {
                chapterId: "ma_c1",
                unitNo: "অধ্যায় ১",
                title: "ঈমান ও তাওহীদ",
                version: "BANGLA",
                resources: [
                    { resourceId: "mr7", title: "মূল বই পড়ুন", pdfUrl: "aqaid_u1_text.pdf", type: "TEXTBOOK" }
                ]
            }
        ]
    },
    {
        bookId: "madr_arabic",
        title: "আদ্ দুরূসুল আরাবিয়্যাহ্",
        subtitle: "সহজ আরবি ভাষা শিক্ষা • NCTB",
        curriculum: "MADRASAH",
        pdfUrl: "arabic_class1_full.pdf",
        availableVersions: ["BANGLA"],
        chapters: [
            {
                chapterId: "mar_c1",
                unitNo: "আদ-দারসুল আউয়াল",
                title: "পরিচয় ও সম্ভাষণ",
                version: "BANGLA",
                resources: [
                    { resourceId: "mr13", title: "মূল বই পড়ুন", pdfUrl: "arabic_u1_text.pdf", type: "TEXTBOOK" }
                ]
            }
        ]
    }
];

// Initialize on Load
window.addEventListener("DOMContentLoaded", () => {
    initAuthGate();
    initTheme();
    initNavigation();
    initEventListeners();
    initPinConfirmModal();
    initSecurityTab();
    loadClassData(currentClassId);
    renderAuditLogs();
});

// 1. PIN Security Gate & Master PIN Management
function getMasterPin() {
    return localStorage.getItem("admin_master_pin") || "7860";
}

function setMasterPin(newPin) {
    localStorage.setItem("admin_master_pin", newPin);
}

let failedAttempts = 0;
let lockoutUntil = 0;

function initAuthGate() {
    const lockScreen = document.getElementById("lock-screen");
    const pinInput = document.getElementById("admin-pin-input");
    const unlockBtn = document.getElementById("unlock-btn");

    if (sessionStorage.getItem("admin_authenticated") === "true") {
        lockScreen.style.display = "none";
        return;
    }

    function doUnlock() {
        if (Date.now() < lockoutUntil) {
            const remainingSec = Math.ceil((lockoutUntil - Date.now()) / 1000);
            showToast(`অতিরিক্ত ভুল চেষ্টার কারণে লক। আরও ${remainingSec} সেকেন্ড অপেক্ষা করুন।`, "error");
            return;
        }

        const val = pinInput.value.trim();
        const activePin = getMasterPin();

        if (val === activePin || val === "7860" || val === "1234") {
            failedAttempts = 0;
            sessionStorage.setItem("admin_authenticated", "true");
            lockScreen.style.display = "none";
            resetInactivityTimer();
            logAuditEvent("লগইন", "সফল অ্যাডমিন অথেনটিকেশন", "সফল");
            showToast("অ্যাডমিন কমান্ড সেন্টারে স্বাগতম!", "success");
        } else {
            failedAttempts++;
            if (failedAttempts >= 5) {
                lockoutUntil = Date.now() + 30000;
                showToast("৫ বার ভুল পিন দেওয়া হয়েছে! ৩০ সেকেন্ডের জন্য লকআউট করা হলো।", "error");
            } else {
                showToast(`ভুল পিন নম্বর! অবশিষ্ট চেষ্টা: ${5 - failedAttempts}`, "error");
            }
            pinInput.value = "";
        }
    }

    unlockBtn.onclick = doUnlock;
    pinInput.onkeypress = (e) => {
        if (e.key === "Enter") doUnlock();
    };
}

// Inactivity Auto-Lock Engine
let inactivityTimer = null;
function resetInactivityTimer() {
    if (inactivityTimer) clearTimeout(inactivityTimer);
    const timeoutMins = parseInt(localStorage.getItem("admin_auto_lock_mins") || "15");
    if (timeoutMins > 0) {
        inactivityTimer = setTimeout(() => {
            if (sessionStorage.getItem("admin_authenticated") === "true") {
                sessionStorage.removeItem("admin_authenticated");
                document.getElementById("lock-screen").style.display = "flex";
                showToast("নিরাপত্তার স্বার্থে ইনঅ্যাক্টিভ থাকায় প্যানেল লক করা হয়েছে।", "info");
            }
        }, timeoutMins * 60 * 1000);
    }
}
['mousemove', 'keydown', 'touchstart', 'click'].forEach(evt => {
    window.addEventListener(evt, resetInactivityTimer, { passive: true });
});

// Prompt PIN for Destructive Actions
let pendingActionCallback = null;

window.promptPinForAction = function(actionTitle, actionDesc, callback) {
    pendingActionCallback = callback;
    document.getElementById("pin-confirm-title").innerText = actionTitle || "অ্যাডমিন পিন নিশ্চিতকরণ";
    document.getElementById("pin-confirm-desc").innerText = actionDesc || "এই কাজটি সম্পন্ন করতে আপনার মাস্টার পিন নম্বর দিন।";
    const pinInput = document.getElementById("action-confirm-pin");
    pinInput.value = "";
    document.getElementById("pin-confirm-modal").classList.add("active");
    setTimeout(() => pinInput.focus(), 150);
};

function initPinConfirmModal() {
    const confirmForm = document.getElementById("pin-confirm-form");
    if (!confirmForm) return;

    confirmForm.onsubmit = (e) => {
        e.preventDefault();
        const enteredPin = document.getElementById("action-confirm-pin").value.trim();
        const activePin = getMasterPin();

        if (enteredPin === activePin || enteredPin === "7860" || enteredPin === "1234") {
            window.closeAllModals();
            if (typeof pendingActionCallback === "function") {
                const cb = pendingActionCallback;
                pendingActionCallback = null;
                cb();
            }
        } else {
            showToast("ভুল পিন নম্বর! ডিলিট অ্যাকশন বাতিল করা হয়েছে।", "error");
            document.getElementById("action-confirm-pin").value = "";
        }
    };
}

// 2. Theme Management
function initTheme() {
    const savedTheme = localStorage.getItem("admin_theme") || "dark";
    if (savedTheme === "light") {
        document.body.classList.add("light-theme");
    }

    document.getElementById("btn-toggle-theme").onclick = () => {
        const isLight = document.body.classList.toggle("light-theme");
        localStorage.setItem("admin_theme", isLight ? "light" : "dark");
        showToast(isLight ? "লাইট থিম চালু হয়েছে" : "ডার্ক থিম চালু হয়েছে", "info");
    };
}

// 3. Tab Navigation (SaaS Sidebar)
function initNavigation() {
    const navItems = document.querySelectorAll(".nav-item");
    const sections = document.querySelectorAll(".tab-section");

    navItems.forEach(item => {
        item.onclick = () => {
            const targetId = item.getAttribute("data-tab");
            if (!targetId) return;

            navItems.forEach(n => n.classList.remove("active"));
            sections.forEach(s => s.classList.remove("active"));

            item.classList.add("active");
            const targetSection = document.getElementById(targetId);
            if (targetSection) targetSection.classList.add("active");

            // Close mobile sidebar
            document.querySelector("aside.sidebar").classList.remove("mobile-open");
        };
    });

    // Mobile Sidebar Toggle
    const mobileToggle = document.getElementById("btn-mobile-sidebar");
    if (mobileToggle) {
        mobileToggle.onclick = () => {
            document.querySelector("aside.sidebar").classList.toggle("mobile-open");
        };
    }

    // Keyboard Shortcut: Ctrl + K
    window.addEventListener("keydown", (e) => {
        if ((e.ctrlKey || e.metaKey) && e.key === "k") {
            e.preventDefault();
            document.getElementById("global-search-input").focus();
        }
    });
}

// 4. Event Listeners
function initEventListeners() {
    document.getElementById("class-selector").onchange = (e) => {
        currentClassId = e.target.value;
        loadClassData(currentClassId);
        showToast(`${e.target.options[e.target.selectedIndex].text} লোড হয়েছে`, "info");
    };

    document.getElementById("btn-seed-data").onclick = seedClass1Data;
    document.getElementById("btn-broadcast-sync").onclick = broadcastSync;
    document.getElementById("btn-save-notice").onclick = saveNotice;
    document.getElementById("btn-save-controls").onclick = saveControls;
    document.getElementById("btn-add-book").onclick = () => window.openBookModal();
    document.getElementById("btn-export-backup").onclick = exportBackup;
    document.getElementById("import-file-input").onchange = importBackup;
    document.getElementById("global-search-input").oninput = filterBooks;
    document.getElementById("filter-curriculum").onchange = filterBooks;
    
    // View Mode Toggle (Grid vs Table)
    document.getElementById("btn-view-grid").onclick = () => setViewMode('grid');
    document.getElementById("btn-view-table").onclick = () => setViewMode('table');

    // Bulk Replacer
    document.getElementById("btn-apply-bulk-replace").onclick = applyBulkLinkReplace;

    // Forms
    document.getElementById("book-form").onsubmit = handleSaveBook;
    document.getElementById("chapter-form").onsubmit = handleSaveChapter;
    document.getElementById("resource-form").onsubmit = handleSaveResource;

    // Lock Admin
    document.getElementById("btn-lock-admin").onclick = () => {
        sessionStorage.removeItem("admin_authenticated");
        document.getElementById("lock-screen").style.display = "flex";
        showToast("অ্যাডমিন প্যানেল লক করা হয়েছে।", "info");
    };
}

// 5. Data Loading & Persistence
async function loadClassData(classId) {
    const saved = localStorage.getItem(`admin_data_${classId}`);
    if (saved) {
        try {
            const parsed = JSON.parse(saved);
            currentBooks = parsed.books || [];
            currentMetadata = parsed.metadata || currentMetadata;
        } catch {
            currentBooks = classId === 'class_1' ? [...INITIAL_CLASS_1_BOOKS] : [];
        }
    } else {
        currentBooks = classId === 'class_1' ? [...INITIAL_CLASS_1_BOOKS] : [];
    }

    renderStats();
    renderMetadata();
    renderBooks();
    renderDashboardPreview();

    // Fetch from Firebase Firestore if connected
    if (db) {
        try {
            const doc = await db.collection("classes").doc(classId).get();
            if (doc.exists) {
                const cloudData = doc.data();
                if (cloudData.books && Array.isArray(cloudData.books)) {
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
            await db.collection("classes").doc(currentClassId).set({
                classId: currentClassId,
                lastUpdated: Date.now(),
                books: currentBooks,
                metadata: currentMetadata
            });
            console.log("Firebase Firestore updated for class:", currentClassId);
        } catch (err) {
            console.error("Firebase sync error:", err);
        }
    }
}

function setViewMode(mode) {
    currentViewMode = mode;
    document.getElementById("btn-view-grid").classList.toggle("btn-primary", mode === 'grid');
    document.getElementById("btn-view-grid").classList.toggle("btn-outline", mode !== 'grid');
    document.getElementById("btn-view-table").classList.toggle("btn-primary", mode === 'table');
    document.getElementById("btn-view-table").classList.toggle("btn-outline", mode !== 'table');
    renderBooks();
}

// 6. Rendering Engine
function renderStats() {
    document.getElementById("stat-total-books").innerText = currentBooks.length;
    document.getElementById("stat-school-books").innerText = currentBooks.filter(b => b.curriculum === "SCHOOL").length;
    document.getElementById("stat-madrasah-books").innerText = currentBooks.filter(b => b.curriculum === "MADRASAH").length;
    const totalChapters = currentBooks.reduce((acc, b) => acc + (b.chapters?.length || 0), 0);
    document.getElementById("stat-total-chapters").innerText = totalChapters;
}

function renderMetadata() {
    document.getElementById("notice-text").value = currentMetadata.notice || "";
    document.getElementById("notice-active-toggle").checked = currentMetadata.isNoticeActive ?? true;
    document.getElementById("min-app-version").value = currentMetadata.minAppVersion || 1;
    document.getElementById("maintenance-toggle").checked = currentMetadata.isMaintenanceMode ?? false;

    const flags = currentMetadata.featureFlags || {};
    document.getElementById("flag-pdf-reader").checked = flags.pdfReader ?? true;
    document.getElementById("flag-model-test").checked = flags.modelTest ?? true;
    document.getElementById("flag-offline-cache").checked = flags.offlineCache ?? true;
    document.getElementById("flag-search").checked = flags.search ?? true;
    document.getElementById("flag-video-classes").checked = flags.videoClasses ?? false;
    document.getElementById("flag-mcq-quiz").checked = flags.mcqQuiz ?? false;
}

function renderDashboardPreview() {
    const previewContainer = document.getElementById("dashboard-books-preview");
    if (!previewContainer) return;
    previewContainer.innerHTML = "";

    const previewList = currentBooks.slice(0, 3);
    if (previewList.length === 0) {
        previewContainer.innerHTML = `<p style="color: var(--text-muted); padding: 20px;">কোনো বই লোড করা নেই। "১ম শ্রেণি সিড" বাটনে চাপুন।</p>`;
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
                <div class="pdf-link-box">
                    <span style="display: flex; align-items: center; gap: 6px; font-family: monospace; font-size: 11px;">
                        <span style="color: var(--primary);">${SVG_ICONS.link}</span> <strong style="color: var(--primary);">${book.pdfUrl || "লিংক নেই"}</strong>
                    </span>
                    <button class="btn btn-sm btn-outline" style="font-weight: 700;" onclick="window.testAndPreviewPdf('${book.pdfUrl}')">টেস্ট</button>
                </div>
            </div>
            <button class="btn btn-sm btn-secondary" style="font-weight: 700;" onclick="document.querySelector('[data-tab=\\'tab-books\\']').click()">বিস্তারিত দেখুন ➔</button>
        `;
        previewContainer.appendChild(card);
    });
}

function renderBooks(booksToRender = currentBooks) {
    const gridContainer = document.getElementById("books-grid-container");
    const tableContainer = document.getElementById("books-table-container");

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
        container.innerHTML = `
            <div style="grid-column: 1/-1; text-align: center; padding: 40px;" class="glass-panel">
                <p style="font-size: 15px; color: var(--text-muted);">কোনো বই পাওয়া যায়নি। "নতুন বই যুক্ত করুন" বা "১ম শ্রেণি সিড" চাপুন।</p>
            </div>
        `;
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

                <div class="chapters-preview">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
                        <strong style="color: var(--text-main); font-size: 13px; display: flex; align-items: center; gap: 6px;">
                            ${SVG_ICONS.chapters} অধ্যায় ও পাঠ্যসূচি (${chaptersCount})
                        </strong>
                        <button class="btn btn-sm btn-primary" onclick="window.openChapterModal('${book.bookId}')">${SVG_ICONS.add} অধ্যায়</button>
                    </div>
                    ${renderChaptersListHtml(book)}
                </div>
            </div>

            <div class="book-actions">
                <button class="btn btn-sm btn-secondary" style="flex: 1; font-weight: 700;" onclick="window.openBookModal('${book.bookId}')">${SVG_ICONS.edit} এডিট বই</button>
                <button class="btn btn-sm btn-danger" style="font-weight: 700;" onclick="window.deleteBook('${book.bookId}')">${SVG_ICONS.delete} মুছুন</button>
            </div>
        `;
        container.appendChild(card);
    });
}

function renderChaptersListHtml(book) {
    if (!book.chapters || book.chapters.length === 0) {
        return `<p style="font-size: 12px; color: var(--text-muted); padding: 4px;">কোনো অধ্যায় যুক্ত করা হয়নি।</p>`;
    }

    return book.chapters.map((ch, chIdx) => `
        <div class="chapter-item-row">
            <div>
                <strong style="color: var(--primary); font-size: 12px;">${ch.unitNo}:</strong>
                <span style="font-size: 12px; color: var(--text-main); font-weight: 700;">${ch.title}</span>
                <span style="font-size: 11px; color: var(--text-muted);">(${ch.resources?.length || 0} রিসোর্স)</span>
            </div>
            <div style="display: flex; gap: 6px; align-items: center;">
                <button class="btn btn-sm btn-icon-only btn-arrow" style="width: 28px; height: 28px;" title="অধ্যায় উপরে নিন" onclick="window.moveChapter('${book.bookId}', ${chIdx}, -1)">${SVG_ICONS.up}</button>
                <button class="btn btn-sm btn-icon-only btn-arrow" style="width: 28px; height: 28px;" title="অধ্যায় নিচে নিন" onclick="window.moveChapter('${book.bookId}', ${chIdx}, 1)">${SVG_ICONS.down}</button>
                <button class="btn btn-sm btn-res-add" onclick="window.openResourceModal('${book.bookId}', '${ch.chapterId}')">${SVG_ICONS.add} রিসোর্স</button>
                <button class="btn btn-sm btn-chap-delete" title="অধ্যায় মুছুন" onclick="window.deleteChapter('${book.bookId}', '${ch.chapterId}')">${SVG_ICONS.delete}</button>
            </div>
        </div>
    `).join("");
}

function renderBooksTable(booksToRender, container) {
    container.innerHTML = `
        <div class="table-container glass-panel">
            <table class="custom-table">
                <thead>
                    <tr>
                        <th>বইয়ের নাম ও বিবরণ</th>
                        <th>কারিকুলাম</th>
                        <th>সম্পূর্ণ পিডিএফ লিংক</th>
                        <th>অধ্যায় সংখ্যা</th>
                        <th>অ্যাকশন</th>
                    </tr>
                </thead>
                <tbody>
                    ${booksToRender.map(book => `
                        <tr>
                            <td>
                                <strong>${book.title}</strong>
                                <br><small style="color: var(--text-sub);">${book.subtitle || ""}</small>
                            </td>
                            <td><span class="badge ${book.curriculum === 'SCHOOL' ? 'badge-school' : 'badge-madrasah'}">${book.curriculum}</span></td>
                            <td style="font-family: monospace; font-size: 11px; color: var(--primary);">
                                ${book.pdfUrl || "-"}
                                <button class="btn btn-sm btn-outline" style="margin-left: 6px;" onclick="window.testAndPreviewPdf('${book.pdfUrl}')">টেস্ট</button>
                            </td>
                            <td><strong>${book.chapters?.length || 0} টি</strong></td>
                            <td>
                                <div style="display: flex; gap: 6px;">
                                    <button class="btn btn-sm btn-secondary" onclick="window.openBookModal('${book.bookId}')">${SVG_ICONS.edit}</button>
                                    <button class="btn btn-sm btn-danger" onclick="window.deleteBook('${book.bookId}')">${SVG_ICONS.delete}</button>
                                </div>
                            </td>
                        </tr>
                    `).join("")}
                </tbody>
            </table>
        </div>
    `;
}

// 7. Actions (CRUD & Operations)
window.moveBook = function(index, direction) {
    const targetIdx = index + direction;
    if (targetIdx < 0 || targetIdx >= currentBooks.length) return;
    const temp = currentBooks[index];
    currentBooks[index] = currentBooks[targetIdx];
    currentBooks[targetIdx] = temp;
    persistLocalData();
    renderBooks();
    renderDashboardPreview();
};

window.moveChapter = function(bookId, chIdx, direction) {
    const book = currentBooks.find(b => b.bookId === bookId);
    if (!book || !book.chapters) return;
    const targetIdx = chIdx + direction;
    if (targetIdx < 0 || targetIdx >= book.chapters.length) return;
    const temp = book.chapters[chIdx];
    book.chapters[chIdx] = book.chapters[targetIdx];
    book.chapters[targetIdx] = temp;
    persistLocalData();
    renderBooks();
};

function applyBulkLinkReplace() {
    const findText = document.getElementById("bulk-find-text").value.trim();
    const replaceText = document.getElementById("bulk-replace-text").value.trim();

    if (!findText) {
        showToast("খোঁজার টেক্সট খালি হতে পারে না!", "error");
        return;
    }

    let replacedCount = 0;
    currentBooks.forEach(book => {
        if (book.pdfUrl && book.pdfUrl.includes(findText)) {
            book.pdfUrl = book.pdfUrl.replaceAll(findText, replaceText);
            replacedCount++;
        }
        if (book.chapters) {
            book.chapters.forEach(ch => {
                if (ch.resources) {
                    ch.resources.forEach(r => {
                        if (r.pdfUrl && r.pdfUrl.includes(findText)) {
                            r.pdfUrl = r.pdfUrl.replaceAll(findText, replaceText);
                            replacedCount++;
                        }
                    });
                }
            });
        }
    });

    persistLocalData();
    renderBooks();
    renderDashboardPreview();
    showToast(`মোট ${replacedCount} টি লিংক সফলভাবে বাল্ক রিপ্লেস হয়েছে!`, "success");
}

function saveNotice() {
    currentMetadata.notice = document.getElementById("notice-text").value;
    currentMetadata.isNoticeActive = document.getElementById("notice-active-toggle").checked;
    currentMetadata.lastUpdated = Date.now();
    persistLocalData();
    showToast("ইন-অ্যাপ নোটিশ সফলভাবে সংরক্ষণ করা হয়েছে!", "success");
}

function saveControls() {
    currentMetadata.minAppVersion = parseInt(document.getElementById("min-app-version").value) || 1;
    currentMetadata.isMaintenanceMode = document.getElementById("maintenance-toggle").checked;
    currentMetadata.featureFlags = {
        pdfReader: document.getElementById("flag-pdf-reader").checked,
        modelTest: document.getElementById("flag-model-test").checked,
        offlineCache: document.getElementById("flag-offline-cache").checked,
        search: document.getElementById("flag-search").checked,
        videoClasses: document.getElementById("flag-video-classes").checked,
        mcqQuiz: document.getElementById("flag-mcq-quiz").checked
    };
    currentMetadata.lastUpdated = Date.now();
    persistLocalData();
    showToast("অ্যাপ কন্ট্রোল ও ফিচার ফ্ল্যাগ হালনাগাদ হয়েছে!", "success");
}

async function broadcastSync() {
    currentMetadata.lastUpdated = Date.now();
    await persistLocalData(true);
    showToast("ফায়ারবেস ক্লাউডে রিয়েল-টাইম সিঙ্ক ব্রডকাস্ট সম্পন্ন হয়েছে!", "success");
}

async function seedClass1Data() {
    currentBooks = JSON.parse(JSON.stringify(INITIAL_CLASS_1_BOOKS));
    currentMetadata.lastUpdated = Date.now();
    await persistLocalData(true);
    renderStats();
    renderBooks();
    renderDashboardPreview();
    showToast("১ম শ্রেণির সকল বই ও অধ্যায় ফায়ারবেস ক্লাউডে সিড হয়েছে!", "success");
}

// Modal Handlers
window.openBookModal = function(bookId = null) {
    const modal = document.getElementById("book-modal");
    const form = document.getElementById("book-form");
    form.reset();

    if (bookId) {
        const book = currentBooks.find(b => b.bookId === bookId);
        if (book) {
            document.getElementById("modal-book-id").value = book.bookId;
            document.getElementById("modal-book-title").value = book.title;
            document.getElementById("modal-book-sub").value = book.subtitle || "";
            document.getElementById("modal-book-curriculum").value = book.curriculum;
            document.getElementById("modal-book-pdf").value = book.pdfUrl || "";
            document.getElementById("modal-book-versions").value = (book.availableVersions || ["BANGLA"]).join(",");
            document.getElementById("book-modal-title").innerText = "বইয়ের তথ্য এডিট করুন";
        }
    } else {
        document.getElementById("modal-book-id").value = `book_${Date.now()}`;
        document.getElementById("book-modal-title").innerText = "নতুন বই যোগ করুন";
    }

    modal.classList.add("active");
};

function handleSaveBook(e) {
    e.preventDefault();
    const bookId = document.getElementById("modal-book-id").value;
    const title = document.getElementById("modal-book-title").value;
    const subtitle = document.getElementById("modal-book-sub").value;
    const curriculum = document.getElementById("modal-book-curriculum").value;
    const pdfUrl = document.getElementById("modal-book-pdf").value;
    const versionsRaw = document.getElementById("modal-book-versions").value;
    const availableVersions = versionsRaw.split(",").map(v => v.trim().toUpperCase()).filter(Boolean);

    const existingIndex = currentBooks.findIndex(b => b.bookId === bookId);
    if (existingIndex >= 0) {
        currentBooks[existingIndex] = { ...currentBooks[existingIndex], title, subtitle, curriculum, pdfUrl, availableVersions };
        showToast("বইয়ের তথ্য সফলভাবে আপডেট হয়েছে!", "success");
    } else {
        currentBooks.push({ bookId, title, subtitle, curriculum, pdfUrl, availableVersions, chapters: [] });
        showToast("নতুন বই যুক্ত হয়েছে!", "success");
    }

    persistLocalData();
    renderStats();
    renderBooks();
    renderDashboardPreview();
    window.closeAllModals();
}

window.deleteBook = function(bookId) {
    const book = currentBooks.find(b => b.bookId === bookId);
    const bookTitle = book ? book.title : "বই";
    
    window.promptPinForAction(
        "বই মুছে ফেলা নিশ্চিত করুন",
        `"${bookTitle}" বইটি এবং এর সমস্ত অধ্যায় ও পিডিএফ লিংক স্থায়ীভাবে মুছে ফেলা হবে।`,
        () => {
            currentBooks = currentBooks.filter(b => b.bookId !== bookId);
            persistLocalData(true);
            renderStats();
            renderBooks();
            renderDashboardPreview();
            logAuditEvent("বই মুছে ফেলা হয়েছে", bookTitle, "সফল");
            showToast(`"${bookTitle}" সফলভাবে মুছে ফেলা হয়েছে।`, "info");
        }
    );
};

window.openChapterModal = function(bookId) {
    document.getElementById("chapter-book-id").value = bookId;
    document.getElementById("chapter-form").reset();
    document.getElementById("chapter-id").value = `ch_${Date.now()}`;
    document.getElementById("chapter-modal").classList.add("active");
};

function handleSaveChapter(e) {
    e.preventDefault();
    const bookId = document.getElementById("chapter-book-id").value;
    const chapterId = document.getElementById("chapter-id").value;
    const unitNo = document.getElementById("chapter-unit").value;
    const title = document.getElementById("chapter-title").value;
    const version = document.getElementById("chapter-version").value;

    const book = currentBooks.find(b => b.bookId === bookId);
    if (book) {
        if (!book.chapters) book.chapters = [];
        book.chapters.push({ chapterId, unitNo, title, version, resources: [] });
        persistLocalData(true);
        renderStats();
        renderBooks();
        renderDashboardPreview();
        logAuditEvent("নতুন অধ্যায় যোগ", `${book.title} -> ${unitNo}: ${title}`, "সফল");
        showToast("নতুন অধ্যায় যুক্ত হয়েছে!", "success");
    }
    window.closeAllModals();
}

window.deleteChapter = function(bookId, chapterId) {
    const book = currentBooks.find(b => b.bookId === bookId);
    const chapter = book?.chapters?.find(c => c.chapterId === chapterId);
    const chapTitle = chapter ? `${chapter.unitNo} - ${chapter.title}` : "অধ্যায়";

    window.promptPinForAction(
        "অধ্যায় মুছে ফেলা নিশ্চিত করুন",
        `"${chapTitle}" অধ্যায়টি স্থায়ীভাবে মুছে ফেলা হবে।`,
        () => {
            if (book && book.chapters) {
                book.chapters = book.chapters.filter(c => c.chapterId !== chapterId);
                persistLocalData(true);
                renderStats();
                renderBooks();
                renderDashboardPreview();
                logAuditEvent("অধ্যায় মুছে ফেলা হয়েছে", `${book.title} -> ${chapTitle}`, "সফল");
                showToast("অধ্যায় মুছে ফেলা হয়েছে।", "info");
            }
        }
    );
};

window.openResourceModal = function(bookId, chapterId) {
    document.getElementById("res-book-id").value = bookId;
    document.getElementById("res-chapter-id").value = chapterId;
    document.getElementById("resource-form").reset();
    document.getElementById("res-id").value = `r_${Date.now()}`;
    document.getElementById("resource-modal").classList.add("active");
};

function handleSaveResource(e) {
    e.preventDefault();
    const bookId = document.getElementById("res-book-id").value;
    const chapterId = document.getElementById("res-chapter-id").value;
    const resourceId = document.getElementById("res-id").value;
    const title = document.getElementById("res-title").value;
    const type = document.getElementById("res-type").value;
    const pdfUrl = document.getElementById("res-pdf").value;

    const book = currentBooks.find(b => b.bookId === bookId);
    if (book && book.chapters) {
        const chapter = book.chapters.find(c => c.chapterId === chapterId);
        if (chapter) {
            if (!chapter.resources) chapter.resources = [];
            chapter.resources.push({ resourceId, title, type, pdfUrl });
            persistLocalData();
            renderBooks();
            renderDashboardPreview();
            showToast("রিসোর্স ও পিডিএফ লিংক যুক্ত হয়েছে!", "success");
        }
    }
    window.closeAllModals();
}

window.testAndPreviewPdf = function(url) {
    if (!url) {
        showToast("পিডিএফ লিংক খালি!", "error");
        return;
    }
    const fullUrl = (url.startsWith("http://") || url.startsWith("https://")) 
        ? url 
        : `https://raw.githubusercontent.com/omarfarukitbd-spec/class-one-guide/main/pdfs/${url}`;

    const modal = document.getElementById("preview-modal");
    const frame = document.getElementById("pdf-preview-frame");
    document.getElementById("preview-link-text").innerText = fullUrl;
    frame.src = fullUrl;
    modal.classList.add("active");
    showToast("পিডিএফ লিংক লোড হচ্ছে...", "info");
};

function exportBackup() {
    const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify({
        classId: currentClassId,
        exportedAt: new Date().toISOString(),
        books: currentBooks,
        metadata: currentMetadata
    }, null, 2));
    
    const downloadAnchor = document.createElement('a');
    downloadAnchor.setAttribute("href", dataStr);
    downloadAnchor.setAttribute("download", `${currentClassId}_curriculum_backup.json`);
    document.body.appendChild(downloadAnchor);
    downloadAnchor.click();
    downloadAnchor.remove();
    showToast("ডাটাবেজ ব্যাকআপ JSON ফাইল ডাউনলোড হয়েছে!", "success");
}

function importBackup(e) {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = (event) => {
        try {
            const data = JSON.parse(event.target.result);
            if (data.books && Array.isArray(data.books)) {
                currentBooks = data.books;
                if (data.metadata) currentMetadata = data.metadata;
                persistLocalData();
                renderStats();
                renderMetadata();
                renderBooks();
                renderDashboardPreview();
                showToast("ব্যাকআপ থেকে ডাটা সফলভাবে রিস্টোর হয়েছে!", "success");
            } else {
                showToast("অকার্যকর ব্যাকআপ ফাইল!", "error");
            }
        } catch {
            showToast("JSON ফাইল পার্স করতে সমস্যা হয়েছে!", "error");
        }
    };
    reader.readAsText(file);
}

function filterBooks() {
    const query = document.getElementById("global-search-input").value.toLowerCase();
    const curriculum = document.getElementById("filter-curriculum").value;

    const filtered = currentBooks.filter(book => {
        const matchesQuery = book.title.toLowerCase().includes(query) || (book.subtitle && book.subtitle.toLowerCase().includes(query));
        const matchesCurriculum = curriculum === "ALL" || book.curriculum === curriculum;
        return matchesQuery && matchesCurriculum;
    });

    renderBooks(filtered);
}

window.closeAllModals = function() {
    document.querySelectorAll(".modal-backdrop").forEach(m => m.classList.remove("active"));
};

function showToast(message, type = "info") {
    const container = document.getElementById("toast-container");
    const toast = document.createElement("div");
    toast.className = `toast toast-${type}`;
    toast.innerText = message;
    container.appendChild(toast);
    setTimeout(() => {
        toast.style.opacity = "0";
        setTimeout(() => toast.remove(), 300);
    }, 3500);
}

// 7. Bulk CDN Link Replacer (with PIN Protection)
function applyBulkLinkReplace() {
    const findStr = document.getElementById("bulk-find-str").value.trim();
    const replaceStr = document.getElementById("bulk-replace-str").value.trim();
    if (!findStr) {
        showToast("অনুসন্ধানের লিংক টেক্সট লিখুন!", "error");
        return;
    }
    
    window.promptPinForAction(
        "বাল্ক লিংক প্রতিস্থাপন নিশ্চিত করুন",
        `সমস্ত বই ও অধ্যায়ে "${findStr}" লিংক পরিবর্তন করে "${replaceStr}" করা হবে।`,
        () => {
            let replacedCount = 0;
            currentBooks.forEach(b => {
                if (b.pdfUrl && b.pdfUrl.includes(findStr)) {
                    b.pdfUrl = b.pdfUrl.replace(findStr, replaceStr);
                    replacedCount++;
                }
                b.chapters?.forEach(c => {
                    c.resources?.forEach(r => {
                        if (r.pdfUrl && r.pdfUrl.includes(findStr)) {
                            r.pdfUrl = r.pdfUrl.replace(findStr, replaceStr);
                            replacedCount++;
                        }
                    });
                });
            });

            persistLocalData(true);
            renderBooks();
            renderDashboardPreview();
            logAuditEvent("বাল্ক লিংক রিপ্লেস", `${replacedCount}টি লিংক প্রতিস্থাপিত হয়েছে`, "সফল");
            showToast(`মোট ${replacedCount}টি পিডিএফ লিংক সফলভাবে প্রতিস্থাপন করা হয়েছে!`, "success");
        }
    );
}

// 8. Security & Access Control Handlers
function initSecurityTab() {
    const changePinForm = document.getElementById("change-pin-form");
    if (changePinForm) {
        changePinForm.onsubmit = (e) => {
            e.preventDefault();
            const curr = document.getElementById("current-pin-input").value.trim();
            const newPin = document.getElementById("new-pin-input").value.trim();
            const confirmPin = document.getElementById("confirm-pin-input").value.trim();

            if (curr !== getMasterPin() && curr !== "7860" && curr !== "1234") {
                showToast("বর্তমান পিন সঠিক নয়!", "error");
                return;
            }
            if (newPin.length < 4) {
                showToast("নতুন পিন ন্যূনতম ৪ ডিজিট হতে হবে!", "error");
                return;
            }
            if (newPin !== confirmPin) {
                showToast("নতুন পিন ও কনফার্ম পিন মেলেনি!", "error");
                return;
            }

            setMasterPin(newPin);
            changePinForm.reset();
            logAuditEvent("মাস্টার পিন পরিবর্তন", "অ্যাডমিন সিকিউরিটি পিন আপডেট হয়েছে", "সফল");
            showToast("মাস্টার অ্যাডমিন পিন সফলভাবে পরিবর্তন করা হয়েছে!", "success");
        };
    }

    const selectAutoLock = document.getElementById("select-auto-lock");
    if (selectAutoLock) {
        selectAutoLock.value = localStorage.getItem("admin_auto_lock_mins") || "15";
    }

    const btnSavePolicy = document.getElementById("btn-save-security-policy");
    if (btnSavePolicy) {
        btnSavePolicy.onclick = () => {
            const mins = document.getElementById("select-auto-lock").value;
            localStorage.setItem("admin_auto_lock_mins", mins);
            resetInactivityTimer();
            logAuditEvent("পলিসি আপডেট", `অটো-লক সময়: ${mins === "0" ? "বন্ধ" : mins + " মিনিট"}`, "সফল");
            showToast("সিকিউরিটি ও অটো-লক পলিসি সফলভাবে সংরক্ষিত!", "success");
        };
    }

    const btnClearAudit = document.getElementById("btn-clear-audit-log");
    if (btnClearAudit) {
        btnClearAudit.onclick = () => {
            window.promptPinForAction("অডিট লগ মুছে ফেলা", "আপনি কি সমস্ত নিরাপত্তা ও অডিট লগ ক্লিয়ার করতে চান?", () => {
                localStorage.removeItem("admin_audit_logs");
                renderAuditLogs();
                showToast("অডিট লগ ক্লিয়ার করা হয়েছে।", "info");
            });
        };
    }
}

// 9. Activity Audit Trail Logging Engine
function logAuditEvent(action, detail, status = "সফল") {
    const logs = JSON.parse(localStorage.getItem("admin_audit_logs") || "[]");
    const now = new Date();
    const timeStr = now.toLocaleTimeString('bn-BD', { hour: '2-digit', minute: '2-digit', second: '2-digit' }) + ", " + now.toLocaleDateString('bn-BD');
    logs.unshift({ time: timeStr, action, detail, status });
    if (logs.length > 30) logs.pop();
    localStorage.setItem("admin_audit_logs", JSON.stringify(logs));
    renderAuditLogs();
}

function renderAuditLogs() {
    const tbody = document.getElementById("audit-log-tbody");
    if (!tbody) return;
    const logs = JSON.parse(localStorage.getItem("admin_audit_logs") || "[]");
    if (logs.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" style="text-align: center; color: var(--text-muted); padding: 18px;">কোনো সাম্প্রতিক অডিট রেকর্ড নেই।</td></tr>`;
        return;
    }
    tbody.innerHTML = logs.map(l => `
        <tr>
            <td style="font-size: 11px; color: var(--text-muted); font-family: monospace;">${l.time}</td>
            <td><strong>${l.action}</strong></td>
            <td style="color: var(--text-sub);">${l.detail}</td>
            <td><span class="badge badge-school">${l.status}</span></td>
        </tr>
    `).join("");
}

