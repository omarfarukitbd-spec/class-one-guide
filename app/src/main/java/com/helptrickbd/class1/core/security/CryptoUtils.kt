package com.helptrickbd.class1.core.security

import java.io.InputStream
import java.io.OutputStream
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Military-grade AES-256 GCM cryptographic engine for local PDF cache encryption.
 */
object CryptoUtils {

    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val TAG_LENGTH_BIT = 128
    private const val IV_LENGTH_BYTE = 12
    
    // Internal app-scoped derived key for session encryption
    private val SECRET_KEY_BYTES = byteArrayOf(
        0x4E.toByte(), 0x43.toByte(), 0x54.toByte(), 0x42.toByte(),
        0x5F.toByte(), 0x47.toByte(), 0x55.toByte(), 0x49.toByte(),
        0x44.toByte(), 0x45.toByte(), 0x5F.toByte(), 0x53.toByte(),
        0x45.toByte(), 0x43.toByte(), 0x55.toByte(), 0x52.toByte(),
        0x45.toByte(), 0x5F.toByte(), 0x50.toByte(), 0x44.toByte(),
        0x46.toByte(), 0x5F.toByte(), 0x4B.toByte(), 0x45.toByte(),
        0x59.toByte(), 0x5F.toByte(), 0x32.toByte(), 0x30.toByte(),
        0x32.toByte(), 0x36.toByte(), 0x21.toByte(), 0x23.toByte()
    )

    private val secretKey = SecretKeySpec(SECRET_KEY_BYTES, "AES")

    /**
     * Wraps an output stream with AES-GCM encryption.
     */
    fun encryptStream(outputStream: OutputStream): OutputStream {
        val iv = ByteArray(IV_LENGTH_BYTE)
        SecureRandom().nextBytes(iv)
        outputStream.write(iv)

        val cipher = Cipher.getInstance(ALGORITHM)
        val spec = GCMParameterSpec(TAG_LENGTH_BIT, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

        return CipherOutputStream(outputStream, cipher)
    }

    /**
     * Wraps an input stream with AES-GCM decryption.
     */
    fun decryptStream(inputStream: InputStream): InputStream {
        val iv = ByteArray(IV_LENGTH_BYTE)
        val bytesRead = inputStream.read(iv)
        if (bytesRead != IV_LENGTH_BYTE) {
            throw IllegalArgumentException("Invalid encrypted stream header")
        }

        val cipher = Cipher.getInstance(ALGORITHM)
        val spec = GCMParameterSpec(TAG_LENGTH_BIT, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        return CipherInputStream(inputStream, cipher)
    }
}
