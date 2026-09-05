/**
 * Notifications Controller
 * Handles sending Push Notifications and In-App Notices by writing to Firestore `notifications_queue` collection.
 * A Firebase Cloud Function will listen to this collection and send the actual FCM payload securely.
 */

document.addEventListener('DOMContentLoaded', () => {
    
    const btnSaveNotice = document.getElementById('btn-save-notice');
    const notifTarget = document.getElementById('notif-target');
    const notifActionType = document.getElementById('notif-action-type');
    const notifBookSelectGroup = document.getElementById('notif-book-select-group');
    const notifBookSelect = document.getElementById('notif-book-select');
    const notifTitle = document.getElementById('notif-title');
    const noticeText = document.getElementById('notice-text');
    const noticeActiveToggle = document.getElementById('notice-active-toggle');
    const fcmStatusBar = document.getElementById('fcm-status-bar');

    // Show/Hide book select based on action type
    if (notifActionType) {
        notifActionType.addEventListener('change', (e) => {
            if (e.target.value === 'book') {
                notifBookSelectGroup.style.display = 'block';
                populateBooksDropdown();
            } else {
                notifBookSelectGroup.style.display = 'none';
            }
        });
    }

    // Populate Books Dropdown (Uses window.currentBooks from books-manager.js)
    function populateBooksDropdown() {
        if (notifBookSelect && window.currentBooks) {
            notifBookSelect.innerHTML = window.currentBooks.map(book => 
                `<option value="${book.bookId}">${book.title} (${book.subtitle})</option>`
            ).join('');
        }
    }

    // Handle Send Notification Button
    if (btnSaveNotice) {
        btnSaveNotice.addEventListener('click', async () => {
            if (!notifTitle.value || !noticeText.value) {
                alert('অনুগ্রহ করে টাইটেল এবং মেসেজ দিন।');
                return;
            }

            // Confirm before sending
            const confirmSend = confirm(`আপনি কি নিশ্চিত যে আপনি ${notifTarget.value === 'all' ? 'সবাইকে' : notifTarget.value} এই নোটিফিকেশনটি পাঠাতে চান?`);
            if (!confirmSend) return;

            btnSaveNotice.disabled = true;
            btnSaveNotice.textContent = 'পাঠানো হচ্ছে...';

            try {
                // Determine FCM Topic (If target is class_1, topic is nctb_class_1)
                const targetTopic = notifTarget.value === 'all' ? 'nctb_all_classes' : `nctb_${notifTarget.value}`;
                
                const notificationData = {
                    title: notifTitle.value.trim(),
                    body: noticeText.value.trim(),
                    targetTopic: targetTopic,
                    actionType: notifActionType.value,
                    bookId: notifActionType.value === 'book' ? notifBookSelect.value : null,
                    showInAppNotice: noticeActiveToggle.checked,
                    createdAt: firebase.firestore.FieldValue.serverTimestamp(),
                    status: 'pending' // Cloud Function will change this to 'sent'
                };

                // Save to notifications_queue collection
                await db.collection('notifications_queue').add(notificationData);
                
                // If In-App notice is also checked, update the global config for in-app banner
                if (noticeActiveToggle.checked) {
                    await db.collection('config').doc('app_settings').set({
                        activeNotice: noticeText.value.trim(),
                        noticeUpdatedAt: firebase.firestore.FieldValue.serverTimestamp()
                    }, { merge: true });
                }

                alert('নোটিফিকেশন সফলভাবে পাঠানো হয়েছে! (Cloud Function ডেলিভারি করবে)');
                notifTitle.value = '';
                noticeText.value = '';
                
            } catch (error) {
                console.error("Error sending notification:", error);
                alert('এরর! নোটিফিকেশন পাঠানো যায়নি। কনসোল চেক করুন।');
            } finally {
                btnSaveNotice.disabled = false;
                btnSaveNotice.textContent = '🚀 পুশ নোটিফিকেশন সেন্ড করুন';
            }
        });
    }

    // Template Buttons Logic
    document.querySelectorAll('.template-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const type = e.target.dataset.type;
            if (type === 'book') {
                notifTitle.value = 'নতুন বই যুক্ত করা হয়েছে!';
                noticeText.value = 'অ্যাপে নতুন পাঠ্যবই যুক্ত করা হয়েছে। এখনই পড়া শুরু করুন।';
                notifActionType.value = 'book';
                notifActionType.dispatchEvent(new Event('change'));
            } else if (type === 'solution') {
                notifTitle.value = 'নতুন সমাধান গাইড!';
                noticeText.value = 'অধ্যায়ের নতুন সমাধান যুক্ত করা হয়েছে।';
                notifActionType.value = 'home';
            } else if (type === 'exam') {
                notifTitle.value = 'প্রস্তুতিমূলক পরীক্ষা';
                noticeText.value = 'আগামীকাল থেকে নতুন পরীক্ষা শুরু হবে। প্রস্তুতি নিন!';
                notifActionType.value = 'home';
            } else if (type === 'update') {
                notifTitle.value = 'জরুরী অ্যাপ আপডেট';
                noticeText.value = 'অ্যাপের নতুন ভার্সন এসেছে। নতুন ফিচার পেতে এখনই আপডেট করুন।';
                notifActionType.value = 'link';
            }
        });
    });

    // Real-time listener for Simulator (Mirror typing in input fields to simulator)
    const simTitle = document.getElementById('sim-notif-title');
    const simBody = document.getElementById('sim-notif-body');
    const simNoticeText = document.getElementById('sim-notice-text');
    const simNoticeBanner = document.getElementById('sim-notice-banner');

    if (notifTitle && simTitle) {
        notifTitle.addEventListener('input', (e) => {
            simTitle.textContent = e.target.value || 'Title';
        });
    }
    if (noticeText && simBody) {
        noticeText.addEventListener('input', (e) => {
            simBody.textContent = e.target.value || 'Message body...';
            if (simNoticeText) simNoticeText.textContent = e.target.value || 'Message body...';
        });
    }
    if (noticeActiveToggle && simNoticeBanner) {
        noticeActiveToggle.addEventListener('change', (e) => {
            simNoticeBanner.style.display = e.target.checked ? 'flex' : 'none';
        });
    }

});
