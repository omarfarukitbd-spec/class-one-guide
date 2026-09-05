package com.helptrickbd.class1.core.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfCryptoEngine @Inject constructor() {

    companion object {
        private const val KEY_ALIAS = "PdfDRMKey_Class1"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val TAG_LENGTH_BIT = 128
        private const val IV_LENGTH_BYTE = 12
    }

    private val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }

    init {
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            generateKey()
        }
    }

    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val keySpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        keyGenerator.init(keySpec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        return keyStore.getKey(KEY_ALIAS, null) as SecretKey
    }

    /**
     * Wraps an OutputStream with AES-256 GCM Encryption.
     * Writes the IV (Initialization Vector) at the very beginning of the stream.
     */
    fun getEncryptingOutputStream(outputStream: OutputStream): OutputStream {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        
        val iv = cipher.iv
        if (iv.size != IV_LENGTH_BYTE) {
            throw IllegalStateException("Unexpected IV length: ${iv.size}")
        }
        
        // Write IV to the start of the file
        outputStream.write(iv)
        
        return CipherOutputStream(outputStream, cipher)
    }

    /**
     * Wraps an InputStream with AES-256 GCM Decryption.
     * Reads the IV from the very beginning of the stream.
     */
    fun getDecryptingInputStream(inputStream: InputStream): InputStream {
        val iv = ByteArray(IV_LENGTH_BYTE)
        var bytesRead = 0
        while (bytesRead < IV_LENGTH_BYTE) {
            val read = inputStream.read(iv, bytesRead, IV_LENGTH_BYTE - bytesRead)
            if (read == -1) {
                throw IllegalStateException("File is too short to contain IV")
            }
            bytesRead += read
        }

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(TAG_LENGTH_BIT, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)

        return CipherInputStream(inputStream, cipher)
    }
}
