package com.suraksha.kavach.data.model

/**
 * AuditLog represents an immutable, encrypted SQLCipher activity record
 * signed with HMAC-SHA256 for tamper-proof verification.
 */
data class AuditLog(
    val id: String,
    val timestamp: String,
    val eventType: String,
    val detail: String,
    val sha256Checksum: String,
    val isVerified: Boolean = true,
    val securityLevel: String = "LEVEL_3_AIRGAP"
)

/**
 * SyncBridgeState represents the live local connection state of the
 * "iQOO Office Kit Sentinel Tunnel" (local USB 3.2 or Wi-Fi Direct P2P link).
 */
data class SyncBridgeState(
    val tunnelName: String = "iQOO Office Kit Sentinel Tunnel",
    val connectionType: TunnelMedium = TunnelMedium.USB_3_2,
    val isConnected: Boolean = true,
    val connectedHost: String = "Laptop Sentinel Console (Local P2P)",
    val transferRateMbps: Float = 480.0f,
    val packetsSynced: Long = 14280,
    val lastSyncTimestamp: String = "Just now",
    val latencyMs: Float = 1.2f,
    val isAutoSyncActive: Boolean = true
)

enum class TunnelMedium(val displayName: String) {
    USB_3_2("USB 3.2 Type-C (Hardware Isolated)"),
    WIFI_DIRECT("Wi-Fi Direct P2P (Offline Local Bridge)")
}
