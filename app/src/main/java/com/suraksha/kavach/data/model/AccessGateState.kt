package com.suraksha.kavach.data.model

/**
 * AccessGateStepState represents the progression of each factor in the
 * 3-Factor Multi-Modal Access Gate.
 */
enum class StepStatus {
    PENDING,
    VERIFYING,
    VERIFIED,
    FAILED
}

data class AccessGateState(
    // Factor 1: Camera Recipient QR Scan
    val cameraQrStatus: StepStatus = StepStatus.PENDING,
    val scannedRecipientHash: String? = null,
    val cameraPermissionGranted: Boolean = true,

    // Factor 2: Biometric Authentication
    val biometricStatus: StepStatus = StepStatus.PENDING,
    val biometricMethodUsed: String? = null,

    // Factor 3: Geo-Fence Validation
    val geoFenceStatus: StepStatus = StepStatus.PENDING,
    val currentLatitude: Double = 28.6139,
    val currentLongitude: Double = 77.2090,
    val targetBunkerLatitude: Double = 28.6141,
    val targetBunkerLongitude: Double = 28.6141, // will adjust in VM to ~8.4m
    val distanceMeters: Double = 8.4,
    val maxAllowedRadiusMeters: Double = 50.0,

    // Session status
    val isSessionTokenUnlocked: Boolean = false,
    val sessionTokenId: String? = null
) {
    val allFactorsVerified: Boolean
        get() = cameraQrStatus == StepStatus.VERIFIED &&
                biometricStatus == StepStatus.VERIFIED &&
                geoFenceStatus == StepStatus.VERIFIED
}
