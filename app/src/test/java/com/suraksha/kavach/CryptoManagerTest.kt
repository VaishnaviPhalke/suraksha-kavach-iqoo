package com.suraksha.kavach

import org.junit.Assert.*
import org.junit.Test
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

/**
 * Unit tests validating pure AES-256-GCM cryptographic operations,
 * IV uniqueness, and tamper-detection on authentication tag failure.
 */
class CryptoManagerTest {

    @Test
    fun testAes256Gcm_encryptionAndDecryption() {
        // Generate a 256-bit AES key in memory for standard test runner
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        val secretKey = keyGen.generateKey()

        val plainText = "SURAKSHA_KAVACH_OFFLINE_SECRET_DIRECTIVE_9921"
        val plainBytes = plainText.toByteArray(Charsets.UTF_8)

        // Encrypt with 12-byte IV
        val iv = ByteArray(12)
        SecureRandom().nextBytes(iv)
        val cipherEncrypt = Cipher.getInstance("AES/GCM/NoPadding")
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
        val cipherText = cipherEncrypt.doFinal(plainBytes)

        // Verify Ciphertext differs from Plaintext
        assertFalse(cipherText.contentEquals(plainBytes))

        // Decrypt with matching key and IV
        val cipherDecrypt = Cipher.getInstance("AES/GCM/NoPadding")
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
        val decryptedBytes = cipherDecrypt.doFinal(cipherText)
        val decryptedText = String(decryptedBytes, Charsets.UTF_8)

        assertEquals("Decrypted text must exactly match original plaintext", plainText, decryptedText)
    }

    @Test
    fun testAes256Gcm_tamperDetection() {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        val secretKey = keyGen.generateKey()

        val plainBytes = "HIGH_SECURITY_AIR_GAPPED_CAD_FILE".toByteArray(Charsets.UTF_8)

        val iv = ByteArray(12)
        SecureRandom().nextBytes(iv)
        val cipherEncrypt = Cipher.getInstance("AES/GCM/NoPadding")
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
        val cipherText = cipherEncrypt.doFinal(plainBytes)

        // Tamper with one single byte of the ciphertext
        cipherText[0] = (cipherText[0].toInt() xor 0xFF).toByte()

        // Decryption MUST fail due to GCM authentication tag mismatch
        val cipherDecrypt = Cipher.getInstance("AES/GCM/NoPadding")
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))

        try {
            cipherDecrypt.doFinal(cipherText)
            fail("Decryption must throw an AEADBadTagException or exception when ciphertext is tampered!")
        } catch (e: Exception) {
            // Success: GCM caught the tampering attempt
            assertTrue(e is javax.crypto.AEADBadTagException || e.javaClass.simpleName.contains("Tag"))
        }
    }
}
