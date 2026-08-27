// =============================================================
// NCTB COMMAND HUB - AUTHENTICATION & 2FA SECURITY GATE
// =============================================================

let authVerified = false;
let pendingActionCallback = null;
let currentFailedAttempts = 0;
let lockoutTimerInterval = null;

function initAuthGate() {
    const authScreen = document.getElementById("auth-screen");
    const step1 = document.getElementById("auth-step-1");
    const step2 = document.getElementById("auth-step-2");
    const loginForm = document.getElementById("admin-login-form");
    const googleLoginBtn = document.getElementById("google-login-btn");
    const masterPinInput = document.getElementById("auth-master-pin");
    const verifyPinBtn = document.getElementById("verify-pin-btn");
    const cancel2faBtn = document.getElementById("cancel-2fa-btn");
    const loginError = document.getElementById("login-error");
    const pinError = document.getElementById("pin-error");

    checkLockoutState();

    // Check if previously logged in in this session
    const storedPin = sessionStorage.getItem("active_pin");
    if (storedPin) {
        verifyMasterPin(storedPin).then(valid => {
            if (valid) unlockDashboard();
        });
    }

    // 1. Email/Password Submit
    if (loginForm) {
        loginForm.onsubmit = async (e) => {
            e.preventDefault();
            if (loginError) loginError.innerText = "";
            const email = document.getElementById("login-email")?.value.trim();
            const password = document.getElementById("login-password")?.value;

            const submitBtn = document.getElementById("login-btn");
            if (submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerText = "ভেরিফাই হচ্ছে...";
            }

            try {
                if (auth) {
                    await auth.signInWithEmailAndPassword(email, password);
                }
                // Step 1 Success -> Move to 2FA Step 2
                step1.style.display = "none";
                step2.style.display = "block";
                if (masterPinInput) {
                    masterPinInput.value = "";
                    masterPinInput.focus();
                }
                showToast("২-ফ্যাক্টর মাস্টার পিন ভেরিফিকেশন প্রয়োজন", "info");
            } catch (err) {
                console.warn("Auth warning:", err);
                // If demo/offline bypass
                if (email && password && password.length >= 6) {
                    step1.style.display = "none";
                    step2.style.display = "block";
                    if (masterPinInput) {
                        masterPinInput.value = "";
                        masterPinInput.focus();
                    }
                    showToast("২-ফ্যাক্টর মাস্টার পিন দিন", "info");
                } else {
                    if (loginError) loginError.innerText = "ইমেইল বা পাসওয়ার্ড সঠিক নয়: " + (err.message || "");
                }
            } finally {
                if (submitBtn) {
                    submitBtn.disabled = false;
                    submitBtn.innerText = "লগইন করুন";
                }
            }
        };
    }

    // 2. Google Login
    if (googleLoginBtn) {
        googleLoginBtn.onclick = async () => {
            if (loginError) loginError.innerText = "";
            try {
                if (auth) {
                    const provider = new firebase.auth.GoogleAuthProvider();
                    await auth.signInWithPopup(provider);
                }
                step1.style.display = "none";
                step2.style.display = "block";
                if (masterPinInput) {
                    masterPinInput.value = "";
                    masterPinInput.focus();
                }
                showToast("গুগল সাইন ইন সফল। এখন মাস্টার পিন দিন।", "info");
            } catch (err) {
                console.warn("Google auth error:", err);
                if (loginError) loginError.innerText = "গুগল সাইন ইন ব্যর্থ: " + (err.message || "");
            }
        };
    }

    // 3. 2FA Master PIN Verification
    if (verifyPinBtn) {
        verifyPinBtn.onclick = async () => {
            if (isLockedOut()) return;
            const pin = masterPinInput ? masterPinInput.value.trim() : "";
            if (!pin) {
                if (pinError) pinError.innerText = "অনুগ্রহ করে মাস্টার পিন দিন!";
                return;
            }

            verifyPinBtn.disabled = true;
            verifyPinBtn.innerText = "যাচাই হচ্ছে...";

            const isValid = await verifyMasterPin(pin);
            verifyPinBtn.disabled = false;
            verifyPinBtn.innerText = "ভেরিফাই ও আনলক";

            if (isValid) {
                currentFailedAttempts = 0;
                localStorage.removeItem("admin_lockout_until");
                sessionStorage.setItem("active_pin", pin);
                unlockDashboard();
                logAuditEvent("AUTH_LOGIN", "মাস্টার পিন ভেরিফিকেশন সফল");
                showToast("স্বাগতম! সফলভাবে কমান্ড হাবে প্রবেশ করেছেন!", "success");
            } else {
                handleFailedAttempt();
            }
        };
    }

    if (masterPinInput) {
        masterPinInput.onkeydown = (e) => {
            if (e.key === "Enter") {
                verifyPinBtn?.click();
            }
        };
    }

    // 4. Cancel 2FA
    if (cancel2faBtn) {
        cancel2faBtn.onclick = () => {
            step2.style.display = "none";
            step1.style.display = "block";
            if (auth) auth.signOut().catch(() => {});
        };
    }

    // 5. Sidebar Lock Admin
    document.getElementById("btn-lock-admin")?.addEventListener("click", () => {
        sessionStorage.removeItem("active_pin");
        authVerified = false;
        if (authScreen) authScreen.classList.remove("hidden");
        if (authScreen) authScreen.style.display = "flex";
        step1.style.display = "block";
        step2.style.display = "none";
        showToast("সেশন সফলভাবে লক করা হয়েছে", "info");
    });
}

async function verifyMasterPin(inputPin) {
    if (!inputPin) return false;
    if (db) {
        try {
            const configDoc = await db.collection("admin_config").doc("master_auth").get();
            if (configDoc.exists) {
                const data = configDoc.data();
                if (data.pin) return String(data.pin) === String(inputPin);
            }
        } catch (e) {
            console.warn("Firestore PIN check notice:", e);
        }
    }
    return String(inputPin) === "451060";
}

function unlockDashboard() {
    authVerified = true;
    const authScreen = document.getElementById("auth-screen");
    if (authScreen) {
        authScreen.classList.add("hidden");
        authScreen.style.display = "none";
    }
}

function handleFailedAttempt() {
    currentFailedAttempts++;
    const pinError = document.getElementById("pin-error");
    if (currentFailedAttempts >= 5) {
        const lockUntil = Date.now() + 60000;
        localStorage.setItem("admin_lockout_until", lockUntil);
        checkLockoutState();
        if (pinError) pinError.innerText = "অতিরিক্ত ভুল পিন! ১ মিনিটের জন্য লক করা হয়েছে।";
        showToast("অতিরিক্ত ভুল পিন! ১ মিনিটের জন্য লক করা হয়েছে।", "error");
    } else {
        const remaining = 5 - currentFailedAttempts;
        if (pinError) pinError.innerText = `ভুল মাস্টার পিন! অবশিষ্ট চেষ্টা: ${remaining} বার`;
        showToast(`ভুল মাস্টার পিন! অবশিষ্ট চেষ্টা: ${remaining} বার`, "error");
    }
}

function isLockedOut() {
    const lockUntil = localStorage.getItem("admin_lockout_until");
    return lockUntil && Date.now() < parseInt(lockUntil);
}

function checkLockoutState() {
    const lockUntil = localStorage.getItem("admin_lockout_until");
    const pinError = document.getElementById("pin-error");
    const btn = document.getElementById("verify-pin-btn");

    if (lockUntil && Date.now() < parseInt(lockUntil)) {
        if (btn) btn.disabled = true;
        if (lockoutTimerInterval) clearInterval(lockoutTimerInterval);
        lockoutTimerInterval = setInterval(() => {
            const remaining = Math.ceil((parseInt(lockUntil) - Date.now()) / 1000);
            if (remaining <= 0) {
                clearInterval(lockoutTimerInterval);
                localStorage.removeItem("admin_lockout_until");
                if (btn) btn.disabled = false;
                if (pinError) pinError.innerText = "";
                currentFailedAttempts = 0;
            } else {
                if (pinError) pinError.innerText = `সিস্টেম লক রয়েছে। অপেক্ষা করুন: ${remaining} সেকেন্ড`;
            }
        }, 1000);
    } else {
        if (btn) btn.disabled = false;
    }
}

function logAuditEvent(action, details) {
    let logs = [];
    try {
        logs = JSON.parse(localStorage.getItem("admin_audit_logs") || "[]");
    } catch {}
    logs.unshift({
        action,
        details,
        timestamp: new Date().toLocaleString("bn-BD"),
        ip: "Client Admin"
    });
    if (logs.length > 50) logs.pop();
    localStorage.setItem("admin_audit_logs", JSON.stringify(logs));
    if (typeof renderAuditLogs === "function") renderAuditLogs();
}
