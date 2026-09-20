# Suraksha Kavach (सुरक्षा कवच)
### 100% Air-Gapped On-Device Security & Vault Prototype for Android (Jetpack Compose / Material 3)

---

## 🌟 Overview & Core Architecture Principles

**Suraksha Kavach** is an enterprise-grade, human-designed security utility that operates entirely offline. Designed with a warm, friendly, minimal aesthetic, it eliminates dark hacker motifs and complex tech clutter in favor of high visual clarity, intuitive feedback, and delightful ergonomics.

### 🛡️ Three Critical Architectural Highlights

1. **Air-Gapped Verification**:
   - The UI prominently displays an active **"Air-Gapped | 0 Outbound Requests"** hero status badge persistently across all screens.
   - `android.permission.INTERNET` is **strictly excluded** from `AndroidManifest.xml`, ensuring hardware-enforced isolation from cloud vectors.
   - All AI inference, anomaly detection, and cryptographic locks run locally on the **Snapdragon NPU** and **Android Hardware KeyStore (HWID)**.

2. **iQOO Office Kit Sentinel Tunnel**:
   - Screen 4 (Sync Bridge) explicitly establishes and labels the local USB 3.2 Type-C / Wi-Fi Direct link as the **iQOO Office Kit Sentinel Tunnel**.
   - Enables real-time local telemetry streaming, packet throughput metrics (480 Mbps), and encrypted audit bundle export to a laptop Sentinel Console dashboard with zero cloud or WAN dependency.

3. **Multi-Modal 3-Factor Gate & Hands-Free Voice Sentinel**:
   - Verification combines **CameraX Recipient QR Scan**, **BiometricPrompt** (StrongBox-backed), and localized **Haversine GPS boundary calculation** (e.g. 8.4m within zone).
   - An offline Voice Sentinel offers hands-free auditory alert prompts: *"Unusual access detected. Confirm or Revoke?"* with real-time override triggers.

---

## 🎨 Design Direction & Color Palette

- **Background**: Warm Off-White (`#FDFBF7`)
- **Card Surfaces**: Pure Card White (`#FFFFFF`) with smooth **20dp rounded corners** and subtle border (`#E2E8F0`)
- **Primary Safe Accent**: Sage Green (`#10B981`) for verified states and healthy scores
- **System Tags**: Soft Lavender Blue (`#6366F1`)
- **Alert / Caution**: Soft Amber (`#F59E0B`) & Soft Rose (`#F43F5E`)
- **Typography**: Clean, rounded, human-designed typography with generous spacing.

---

## 📱 The 4 Light-Themed Screens

### Screen 1: Dashboard ("My Shield")
- **Air-Gapped Hero Badge**: Live indicator showing `Air-Gapped | 0 Outbound Requests`.
- **System Health Score**: Clean circular gauge indicating `98/100 (Safe & Isolated)`.
- **Snapdragon NPU Edge Card**: 0.02ms inference latency, quantized Int8 local reasoning model, 0 socket allocations.
- **Hardware-Encrypted Vault Cards**: AES-256-GCM encrypted file cards (`defense_cad_blueprints.enc`, `command_keys.sec`, `field_personnel_roster.vault`) bound to Hardware KeyStore aliases with one-tap on-device decryption and lock.

### Screen 2: Access Control & Verification Gate
- **Factor 1 - Camera Recipient QR Scan**: CameraX viewfinder preview placeholder with animated laser line and recipient key verification.
- **Factor 2 - Biometric Authentication**: Android `BiometricPrompt` hook with tactile verification chip.
- **Factor 3 - Geo-Fence Validation**: Pure Kotlin Haversine formula calculation verifying operator is 8.4m inside the authorized 50m bunker zone.
- **Session Token Unlock**: "Authorize Air-Gapped Session" action activated only upon full 3-factor verification.

### Screen 3: AI Threat Sentinel
- **Risk Score Meter**: Visual circular ring displaying `Risk Score: 0.02 (Low)` running local INT8 LLM reasoning.
- **Hands-Free Voice Alerts & Revocation**:
  - Live animated waveform.
  - Text-to-Speech prompt: *"Unusual access detected. Confirm or Revoke?"*
  - Dedicated action triggers: **"Confirm Identity"** (Sage Green) & **"Revoke Token & Purge"** (Soft Rose).
- **Live Local NPU Anomaly Logs**: Acoustic side-channel monitor, Direct Memory Access (DMA) integrity checks, and firewall drop counters.

### Screen 4: iQOO Office Kit Sync Bridge & Audit Trail
- **iQOO Office Kit Sentinel Tunnel**: Explicitly branded card showing high-speed USB 3.2 (480 Mbps) or Wi-Fi Direct local bridge to Laptop Sentinel Console.
- **Tamper-Proof Audit Trail**: Encrypted SQLCipher activity logs with HMAC-SHA256 checksums and cryptographic verification seals.
- **Air-Gapped Bundle Export**: One-tap local export to laptop dashboard.

---

## 📂 Project Structure

```
suraksha_kavach/
├── app/
│   ├── build.gradle.kts                  // Jetpack Compose BOM, Material 3, Biometrics, CameraX, Crypto
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml       // NO INTERNET PERMISSION (Air-Gapped guarantee)
│       │   ├── res/values/               // colors.xml (#FDFBF7, #10B981, #6366F1), strings.xml, themes.xml
│       │   └── java/com/suraksha/kavach/
│       │       ├── MainActivity.kt       // Single activity host with 4-tab NavigationBar
│       │       ├── ui/
│       │       │   ├── theme/            // Color.kt, Shape.kt (20dp), Type.kt, Theme.kt
│       │       │   ├── components/
│       │       │   │   ├── AirGappedHeroBadge.kt    // "Air-Gapped | 0 Outbound Requests" badge
│       │       │   │   ├── KavachCard.kt            // 20dp rounded surface with subtle border
│       │       │   │   ├── RiskScoreMeter.kt        // Circular gauge for 0.02 Risk Score
│       │       │   │   ├── VerificationStepCard.kt  // 3-Factor step card
│       │       │   │   ├── VoiceInteractionCard.kt  // TTS prompt + Confirm/Revoke triggers
│       │       │   │   └── SyncBridgeCard.kt        // iQOO Office Kit Sentinel Tunnel card
│       │       │   └── screens/
│       │       │       ├── DashboardScreen.kt       // Screen 1: "My Shield"
│       │       │       ├── AccessGateScreen.kt      // Screen 2: 3-Factor Verification Gate
│       │       │       ├── ThreatSentinelScreen.kt  // Screen 3: AI Threat Sentinel
│       │       │       └── SyncBridgeScreen.kt      // Screen 4: iQOO Office Kit Sync & Audit
│       │       ├── viewmodel/
│       │       │   └── SurakshaViewModel.kt         // Unidirectional Data Flow state management
│       │       ├── security/
│       │       │   ├── CryptoManager.kt             // AES-256-GCM + AndroidKeyStore HWID binding
│       │       │   ├── GeoFenceCalculator.kt        // Haversine GPS spherical formula
│       │       │   └── VoiceSecurityController.kt   // On-device speech synthesis & alerts
│       │       └── data/model/
│       │           ├── VaultItem.kt
│       │           ├── AccessGateState.kt
│       │           ├── SentinelTelemetry.kt
│       │           └── AuditLog.kt
│       └── test/java/com/suraksha/kavach/
│           └── GeoFenceCalculatorTest.kt            // Unit tests for Haversine calculations
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

## 📹 Video Walkthrough Guide (60–90 Seconds Script)

1. **[0:00 - 0:15] Code Architecture in Android Studio**:
   - Briefly showcase the clean Android Studio project structure in `suraksha_kavach/`.
   - Point out `AndroidManifest.xml` proving `android.permission.INTERNET` is completely absent.
   - Highlight `CryptoManager.kt` (AES-256-GCM Hardware KeyStore) and `GeoFenceCalculator.kt` (Haversine formula).

2. **[0:15 - 0:30] Device Running in Airplane Mode**:
   - Show the device status bar in **Airplane Mode** (no cellular, no Wi-Fi WAN).
   - Point to the persistent **"Air-Gapped | 0 Outbound Requests"** hero badge atop Screen 1 ("My Shield").
   - Demonstrate unlocking an AES-256-GCM encrypted file (`defense_cad_blueprints.enc`) directly in volatile RAM.

3. **[0:30 - 0:50] 3-Factor Gate & Snapdragon NPU Sentinel**:
   - Switch to Screen 2 (Access Gate): Demonstrate CameraX QR scan target, Biometric confirmation, and localized Haversine distance verification (8.4m inside zone).
   - Switch to Screen 3 (Sentinel): Highlight the **0.02 Low Risk Score** and trigger the Hands-Free Voice alert (*"Unusual access detected. Confirm or Revoke?"*).

4. **[0:50 - 1:15] iQOO Office Kit Sentinel Tunnel**:
   - Switch to Screen 4 (Sync Bridge): Feature the **iQOO Office Kit Sentinel Tunnel** transferring telemetry at 480 Mbps over USB 3.2 Type-C to the Laptop Sentinel Console.
   - Highlight the tamper-proof SQLCipher audit trail with sealed HMAC-SHA256 signatures.

---

## 🚀 How to Build & Open in Android Studio

1. Open **Android Studio** (Hedgehog, Iguana, or Ladybug/Koala).
2. Choose **Open an Existing Project** and navigate to:
   ```
   C:\Users\ll\.gemini\antigravity\scratch\suraksha_kavach
   ```
3. Allow Gradle to sync dependencies.
4. Select an Android device or emulator running API 26+ (recommended API 34 with Hardware Fingerprint/Biometrics support).
5. Run the `app` target.
