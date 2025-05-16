package com.jihan.composeutils.ui


import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class CxAesEncryption {
    companion object {
        private const val ALGORITHM = "AES"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val TAG_LENGTH = 128
        private const val SALT_LENGTH = 16
        private const val IV_LENGTH = 12
        private const val ITERATION_COUNT = 65536
        private const val KEY_LENGTH = 256
        private const val DELIMITER = "$"
        private const val VERSION = "1"

        private fun generateRandomBytes(length: Int): ByteArray {
            return ByteArray(length).apply {
                SecureRandom().nextBytes(this)
            }
        }


        fun encryptBlocking(password: String): String {
            val salt = generateRandomBytes(SALT_LENGTH)
            val iv = generateRandomBytes(IV_LENGTH)
            val key = generateKey(password, salt)


            val cipher = Cipher.getInstance(TRANSFORMATION)
            val gcmSpec = GCMParameterSpec(TAG_LENGTH, iv)
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec)
            val ciphertext = cipher.doFinal(password.toByteArray(Charsets.UTF_8))



            val saltBase64 = Base64.encodeToString(salt, Base64.NO_WRAP)
            val ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP)
            val ciphertextBase64 = Base64.encodeToString(ciphertext, Base64.NO_WRAP)

            return "$VERSION$DELIMITER$saltBase64$DELIMITER$ivBase64$DELIMITER$ciphertextBase64"
        }


        suspend fun encrypt(password: String): String = withContext(Dispatchers.IO) {
            encryptBlocking(password)
        }


        fun verifyBlocking(password: String, hashedPassword: String): Boolean {
            try {

                val parts = hashedPassword.split(DELIMITER)
                if (parts.size != 4 || parts[0] != VERSION) {
                    return false
                }

                val salt = Base64.decode(parts[1], Base64.NO_WRAP)
                val iv = Base64.decode(parts[2], Base64.NO_WRAP)
                val ciphertext = Base64.decode(parts[3], Base64.NO_WRAP)


                val key = generateKey(password, salt)


                val cipher = Cipher.getInstance(TRANSFORMATION)
                val gcmSpec = GCMParameterSpec(TAG_LENGTH, iv)
                cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec)

                val decrypted = cipher.doFinal(ciphertext)
                val decryptedPassword = String(decrypted, Charsets.UTF_8)


                return password == decryptedPassword
            } catch (_: Exception) {

                return false
            }
        }


        suspend fun verify(password: String, hashedPassword: String): Boolean = withContext(Dispatchers.IO) {
            verifyBlocking(password, hashedPassword)
        }

        private fun generateKey(password: String, salt: ByteArray): SecretKey {
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val spec = PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH)
            val tmp = factory.generateSecret(spec)
            return SecretKeySpec(tmp.encoded, ALGORITHM)
        }
    }
}
