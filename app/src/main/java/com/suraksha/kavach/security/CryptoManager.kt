package com.suraksha.kavach.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.KeyStore
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * CryptoManager provides 100% on-device hardware-backed cryptography
 * using AES-256-GCM bound to the Android Hardware KeyStore (HWID).
 * 
 * Cryptographic Specifications:
 * - Key Length: 256 bits (AES-256)
 * - Block Mode: Galois/Counter Mode (GCM)
 * - Padding: NoPadding
 * - Authentication Tag Length: 128 bits
 * - Initialization Vector (IV): 12 bytes (cryptographically secure random per encryption)
 * - Hardware Backing: AndroidKeyStore (StrongBox Keymaster / Secure Element where available)
 */
class CryptoManager(private val context: Context) {

    companion object {
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val AES_GCM_NOPADDING = "AES/GCM/NoPadding"
        private const val GCM_TAG_LENGTH_BITS = 128
        private const val GCM_IV_LENGTH_BYTES = 12
        const val VAULT_DIR = "vault_storage"
    }

    private val keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply {
        load(null)
    }

    private val vaultDirectory: File by lazy {
        File(context.filesDir, VAULT_DIR).apply {
            if (!exists()) mkdirs()
        }
    }

    /**
     * Generates or retrieves an AES-256 key bound to the Hardware KeyStore.
     */
    fun getOrCreateHardwareKey(alias: String): SecretKey {
        if (keyStore.containsAlias(alias)) {
            val entry = keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry
            if (entry != null) {
                return entry.secretKey
            }
        }

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )

        val keyGenSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setUserAuthenticationRequired(false)
            .setRandomizedEncryptionRequired(true)
            .build()

        keyGenerator.init(keyGenSpec)
        return keyGenerator.generateKey()
    }

    /**
     * Encrypts plaintext bytes using AES-256-GCM with hardware-backed key.
     * Output format: [12-byte IV] + [Ciphertext with 16-byte GCM Auth Tag]
     */
    fun encryptBytes(alias: String, plainBytes: ByteArray): ByteArray {
        val secretKey = getOrCreateHardwareKey(alias)
        val cipher = Cipher.getInstance(AES_GCM_NOPADDING)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv // 12 bytes generated securely
        val encryptedPayload = cipher.doFinal(plainBytes)

        val output = ByteArray(iv.size + encryptedPayload.size)
        System.arraycopy(iv, 0, output, 0, iv.size)
        System.arraycopy(encryptedPayload, 0, output, iv.size, encryptedPayload.size)
        return output
    }

    /**
     * Decrypts payload bytes using AES-256-GCM.
     * Throws AEADBadTagException if any byte or the hardware key has been tampered with.
     */
    fun decryptBytes(alias: String, encryptedPayload: ByteArray): ByteArray {
        require(encryptedPayload.size > GCM_IV_LENGTH_BYTES) { "Ciphertext too short" }

        val secretKey = getOrCreateHardwareKey(alias)
        val iv = ByteArray(GCM_IV_LENGTH_BYTES)
        val cipherText = ByteArray(encryptedPayload.size - GCM_IV_LENGTH_BYTES)

        System.arraycopy(encryptedPayload, 0, iv, 0, GCM_IV_LENGTH_BYTES)
        System.arraycopy(encryptedPayload, GCM_IV_LENGTH_BYTES, cipherText, 0, cipherText.size)

        val cipher = Cipher.getInstance(AES_GCM_NOPADDING)
        val spec = GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return cipher.doFinal(cipherText)
    }

    /**
     * Writes encrypted data directly to a physical file inside vault storage.
     */
    fun encryptToFile(alias: String, fileName: String, content: ByteArray): File {
        val encryptedBytes = encryptBytes(alias, content)
        val targetFile = File(vaultDirectory, fileName)
        FileOutputStream(targetFile).use { fos ->
            fos.write(encryptedBytes)
            fos.flush()
        }
        return targetFile
    }

    /**
     * Reads and decrypts a physical file from vault storage directly into volatile memory.
     * Returns the decrypted byte array.
     */
    fun decryptFromFile(alias: String, fileName: String): ByteArray {
        val targetFile = File(vaultDirectory, fileName)
        if (!targetFile.exists()) {
            throw IllegalArgumentException("Vault file $fileName does not exist on disk.")
        }

        val encryptedBytes = FileInputStream(targetFile).use { fis ->
            fis.readBytes()
        }
        return decryptBytes(alias, encryptedBytes)
    }

    /**
     * Computes the SHA-256 hash of a file or byte buffer for integrity auditing.
     */
    fun computeSha256(data: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(data)
        return hash.joinToString("") { "%02x".format(it) }
    }

    /**
     * Pre-populates the vault storage with real encrypted binary files if not present.
     */
    fun initializeSampleVaultFiles() {
        val sample1 = File(vaultDirectory, "defense_cad_blueprints.enc")
        if (!sample1.exists()) {
            val data = """
                [SURAKSHA KAVACH AIR-GAPPED SECURE ASSET]
                CLASSIFICATION: RESTRICTED
                ASSET_ID: CAD-VEC-9921-BLUEPRINT
                SCHEMATIC_VERSION: v4.19.0-AIRGAP
                INTEGRITY_SEAL: SHA256-VERIFIED
                PAYLOAD: Vector schematics verified. All structural load points mapped to isolated coordinate grid.
            """.trimIndent().toByteArray(Charsets.UTF_8)
            encryptToFile("HWKEY_SEC_9921_A", "defense_cad_blueprints.enc", data)
        }

        val sample2 = File(vaultDirectory, "command_keys.sec")
        if (!sample2.exists()) {
            val data = """
                [SURAKSHA KAVACH HARDWARE KEY VAULT]
                CLASSIFICATION: TOP SECRET
                KEYSTORE_ALIAS: HWKEY_STRONGBOX_01
                KEY_TYPE: Ed25519 / RSA-4096 Master Air-Gapped Signature Token
                ROOT_AUTHORITY: Suraksha Autonomous Keymaster
                PAYLOAD: 0x8F929C4410EA9932B104F5E198D4C82A109E384C
            """.trimIndent().toByteArray(Charsets.UTF_8)
            encryptToFile("HWKEY_STRONGBOX_01", "command_keys.sec", data)
        }

        val sample3 = File(vaultDirectory, "field_personnel_roster.vault")
        if (!sample3.exists()) {
            val data = """
                [SURAKSHA KAVACH OFFLINE FIELD PERSONNEL]
                CLASSIFICATION: AIR-GAPPED ONLY
                UNIT: 4th Rapid Deployment Engineering Guard
                TOTAL_RECORDS: 124 Personnel Credentials Cached
                STATUS: Fully verified in localized SQLCipher offline memory.
            """.trimIndent().toByteArray(Charsets.UTF_8)
            encryptToFile("HWKEY_SEC_9921_B", "field_personnel_roster.vault", data)
        }
    }

    fun getVaultFile(fileName: String): File {
        return File(vaultDirectory, fileName)
    }

    fun listVaultFiles(): List<File> {
        return vaultDirectory.listFiles()?.toList() ?: emptyList()
    }
}
