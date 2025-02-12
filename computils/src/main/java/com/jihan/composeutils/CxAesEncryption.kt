package com.jihan.composeutils

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class CxEncryption(
    private val secretKey: String,
    private val salt: ByteArray = generateRandomBytes(16),
    private val iterationCount: Int = 65536,
    private val keyLength: Int = 256
) {
    companion object {
        private const val ALGORITHM = "AES"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val TAG_LENGTH = 128

        fun generateRandomBytes(length: Int): ByteArray {
            return ByteArray(length).apply {
                SecureRandom().nextBytes(this)
            }
        }
    }

    data class EncryptedData(
        val ciphertext: ByteArray,
        val iv: ByteArray,
        val salt: ByteArray,
        val encryptedString: String
    ) {
        fun toBase64(): String {
            // Format: salt:iv:ciphertext
            val combined = salt + iv + ciphertext
            return Base64.encodeToString(combined, Base64.DEFAULT)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as EncryptedData
            return ciphertext.contentEquals(other.ciphertext) && iv.contentEquals(other.iv) && salt.contentEquals(
                other.salt
            )
        }

        override fun hashCode(): Int {
            var result = ciphertext.contentHashCode()
            result = 31 * result + iv.contentHashCode()
            result = 31 * result + salt.contentHashCode()
            return result
        }
    }

    private fun generateKey(salt: ByteArray): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(secretKey.toCharArray(), salt, iterationCount, keyLength)
        val tmp = factory.generateSecret(spec)
        return SecretKeySpec(tmp.encoded, ALGORITHM)
    }

    fun encrypt(input: String): EncryptedData {
        // Generate a random IV for each encryption
        val iv = generateRandomBytes(12) // 12 bytes IV for GCM

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKey = generateKey(salt)
        val gcmSpec = GCMParameterSpec(TAG_LENGTH, iv)

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec)
        val encrypted = cipher.doFinal(input.toByteArray(Charsets.UTF_8))

        val encryptedString = Base64.encodeToString(encrypted, Base64.DEFAULT)

        return EncryptedData(encrypted, iv, salt, encryptedString)
    }

    fun decrypt(encryptedData: EncryptedData): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKey = generateKey(encryptedData.salt)
        val gcmSpec = GCMParameterSpec(TAG_LENGTH, encryptedData.iv)

        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)
        val decrypted = cipher.doFinal(encryptedData.ciphertext)
        return String(decrypted, Charsets.UTF_8)
    }

}