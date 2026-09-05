const { onDocumentCreated } = require("firebase-functions/v2/firestore");
const admin = require("firebase-admin");

// Initialize Firebase Admin SDK
admin.initializeApp();

/**
 * Triggered when a new document is added to the `notifications_queue` collection.
 * Reads the notification data and sends an FCM push message to the specified topic.
 */
exports.sendPushNotification = onDocumentCreated("notifications_queue/{docId}", async (event) => {
    const snapshot = event.data;
    if (!snapshot) {
        console.log("No data associated with the event");
        return;
    }

    const data = snapshot.data();
    console.log("Processing new notification:", data.title);

    // Build the FCM payload
    const payload = {
        notification: {
            title: data.title,
            body: data.body,
        },
        data: {
            title: data.title,
            body: data.body,
            actionType: data.actionType || "home",
            bookId: data.bookId || "",
            showInAppNotice: String(data.showInAppNotice || "false")
        },
        topic: data.targetTopic || "nctb_all_classes"
    };

    try {
        // Send the push notification
        const response = await admin.messaging().send(payload);
        console.log("Successfully sent message:", response);

        // Update the document status to 'sent'
        return snapshot.ref.update({
            status: "sent",
            sentAt: admin.firestore.FieldValue.serverTimestamp(),
            messageId: response
        });
    } catch (error) {
        console.error("Error sending push notification:", error);
        
        // Update the document status to 'error'
        return snapshot.ref.update({
            status: "error",
            errorDetails: error.message || "Unknown error occurred"
        });
    }
});
