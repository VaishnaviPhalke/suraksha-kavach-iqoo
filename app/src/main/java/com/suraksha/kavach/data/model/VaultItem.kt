package com.suraksha.kavach.data.model

/**
 * VaultItem represents an air-gapped file payload protected by AES-256-GCM
 * cryptographically bound to the Android Hardware KeyStore (HWID).
 */
data class VaultItem(
    val id: String,
    val fileName: String,
    val fileSizeReadable: String,
    val encryptionType: String = "AES-256-GCM (Hardware Keystore)",
    val hardwareKeyAlias: String,
    val isDecrypted: Boolean = false,
    val lastAccessedTime: String,
    val classification: VaultClassification = VaultClassification.RESTRICTED,
    val payloadPreview: String = ""
)

enum class VaultClassification(val label: String) {
    RESTRICTED("Restricted"),
    SECRET("Top Secret"),
    AIR_GAPPED_ONLY("Air-Gapped Only")
}
