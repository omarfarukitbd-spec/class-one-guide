/**
 * Firebase Firestore Configuration for Global Admin Panel
 * 
 * Account: omarfarukitbd@gmail.com
 * Project: books-hub-6e7b8
 */

const firebaseConfig = {
    apiKey: "AIzaSyDIVxnaPdE_NwRhJMoltdFslBCM59HAtRk",
    authDomain: "books-hub-6e7b8.firebaseapp.com",
    projectId: "books-hub-6e7b8",
    storageBucket: "books-hub-6e7b8.firebasestorage.app",
    messagingSenderId: "879247421687",
    appId: "1:879247421687:web:a9e0e2bbd8efd88dcfe3f2"
};

let db = null;
let auth = null;

try {
    if (typeof firebase !== 'undefined') {
        firebase.initializeApp(firebaseConfig);
        db = firebase.firestore();
        auth = firebase.auth();
        console.log("Firebase Firestore successfully initialized!");
    }
} catch (e) {
    console.warn("Firebase initialization notice:", e);
}
