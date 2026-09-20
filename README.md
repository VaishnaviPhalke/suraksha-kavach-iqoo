<div align="center">

# 🛡️ Suraksha Kavach (सुरक्षा कवच)
### Enterprise-Grade On-Device Security & Hardware-Encrypted Vault for Air-Gapped Operations

[![Android](https://img.shields.io/badge/Platform-Android%2014%20(API%2034)-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20%2F%20Material%203-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Air-Gapped](https://img.shields.io/badge/Network-100%25%20Air--Gapped%20(0%20Outbound)-10B981?style=flat-square)](https://github.com)
[![NPU](https://img.shields.io/badge/AI%20Hardware-Snapdragon%20NPU%20(Int8)-6366F1?style=flat-square)](https://qualcomm.com)
[![Cryptography](https://img.shields.io/badge/Encryption-AES--256--GCM%20(Hardware%20KeyStore)-F59E0B?style=flat-square)](https://developer.android.com/reference/android/security/keystore/KeyGenParameterSpec)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](LICENSE)

<br/>

**Suraksha Kavach** is an autonomous, on-device security shield engineered for defense, legal, diplomatic, and mission-critical environments. Built without reliance on external cloud servers, it combines **Android Hardware KeyStore (HWID)** cryptography, **Snapdragon NPU** edge intelligence, **multi-modal 3-factor physical gating**, and the **iQOO Office Kit Sentinel Tunnel** for isolated desktop telemetry.

[Interactive Web Demo](https://github.com) • [Architecture Guide](#-system-architecture) • [Getting Started](#-getting-started) • [Video Script](WALKTHROUGH_SCRIPT.md)

</div>

---

## 📌 Executive Summary

### The Problem
Traditional data security models depend heavily on cloud repositories (AWS, Google Drive, Azure) and remote telemetry APIs. In contested, confidential, or field environments, this paradigm presents severe vulnerabilities:
- **Cloud Interception & Data Leaks**: Exfiltration of sensitive files, defense blueprints, or internal records.
- **Network Dependency**: Complete loss of access when cellular/Wi-Fi infrastructure fails or is jammed.
- **Physical Device Theft**: Compromise of local files if storage keys are software-stored and extractable.

### The Solution
**Suraksha Kavach** enforces true **zero-cloud isolation**. The application explicitly excludes network permissions, executing all cryptographic primitives, anomaly evaluations, and biometric validations locally on device silicon:
1. **Zero Outbound Sockets**: `android.permission.INTERNET` is strictly omitted from the application manifest.
2. **Silicon-Rooted Encryption**: AES-256-GCM keys reside exclusively within the device's Secure Element (SE) / StrongBox Keymaster.
3. **Hardware Acceleration**: The Snapdragon NPU processes localized sensory inputs to compute dynamic threat scores without external server inference.

---

## 🏛️ System Architecture

```
                                  SURAKSHA KAVACH ARCHITECTURE
                                  
   +-----------------------------------------------------------------------------------------+
   |                                ANDROID DEVICE RUNTIME                                   |
   |                                                                                         |
   |   [ User Interface ]  --->  Jetpack Compose • Material 3 Light Theme (Warm #FDFBF7)    |
   |                                                                                         |
   |   [ Security Gating ] --->  3-Factor Multi-Modal Verifier:                              |
   |                             ├── Factor 1: Optical QR Viewfinder (CameraX Framebuffer)   |
   |                             ├── Factor 2: Hardware BiometricPrompt (StrongBox Keymaster)|
   |                             └── Factor 3: Spherical Haversine GPS (Zero-Cloud Geofence) |
   |                                                                                         |
   |   [ Cryptography ]    --->  AES-256-GCM Hardware Vault (filesDir/vault_storage/)        |
   |                             ├── Keys generated via AndroidKeyStore (HWID-bound)         |
   |                             └── Volatile memory decryption with GCM tag verification    |
   |                                                                                         |
   |   [ Edge Sentinel ]   --->  Snapdragon NPU Local Reasoning Model:                       |
   |                             ├── Dynamic sensory risk scoring (Nominal Score: 0.02)      |
   |                             ├── Baseband Airplane Mode & battery thermal monitors       |
   |                             └── On-device Text-To-Speech (TTS) auditory alert prompts   |
   |                                                                                         |
   |   [ Audit Ledger ]    --->  SQLite with Cryptographic SHA-256 Hash Chaining             |
   |                                                                                         |
   |   [ Embedded Tunnel ] --->  Local HTTP Server (Port 8888) over USB 3.2 / Wi-Fi Direct   |
   +-----------------------------------------------------------------------------------------+
                                                |
                                      iQOO Office Kit Tunnel
                                  (Local Hardware Link | No WAN)
                                                |
   +-----------------------------------------------------------------------------------------+
   |                             LAPTOP SENTINEL CONSOLE (PORT 3000)                         |
   |                                                                                         |
   |   • Standalone Node.js Desktop Companion Engine                                         |
   |   • Real-Time Cryptographic Hash-Chain Verification                                     |
   |   • Live Telemetry Streaming (480 Mbps Direct DMA PHY)                                  |
   |   • Remote Emergency Lockdown Command Dispatch                                          |
   +-----------------------------------------------------------------------------------------+
```

---

## ✨ Key Capabilities

### 1. 100% Air-Gapped Verification (Zero Cloud)
The UI prominently anchors a real-time **"Air-Gapped | 0 Outbound Requests"** hero status badge across all views. Because `android.permission.INTERNET` is omitted from `AndroidManifest.xml`, network sockets cannot be initialized at the OS layer. The system operates natively in Airplane Mode.

### 2. Hardware-Bound File Vault (AES-256-GCM + HWID)
- Files stored on disk (`context.filesDir/vault_storage/`) are encrypted using **AES-256 in Galois/Counter Mode (GCM)** with 128-bit authentication tags.
- Keys are generated via `KeyGenParameterSpec` inside the **AndroidKeyStore** and cryptographically bound to the device's unique physical hardware ID (HWID).
- Plaintext is released solely into volatile memory buffers and instantly wiped upon re-locking.

### 3. 3-Factor Multi-Modal Access Gate
Access to restricted assets requires three simultaneous, decentralized factors:
- **Factor 1 (Camera Recipient Check)**: An isolated `CameraX` framebuffer scans the recipient's optical public key without cloud image routing.
- **Factor 2 (Biometric Authentication)**: Native AndroidX `BiometricPrompt` enforces Class-3 hardware fingerprint or 3D facial verification.
- **Factor 3 (Haversine Geo-Fence)**: Pure Kotlin spherical trigonometry computes physical distance from authorized facility coordinates (e.g. 8.4m within safe perimeter) without map API queries.

### 4. Snapdragon NPU AI Threat Sentinel
Instead of delegating audit analysis to remote models, an on-device quantized INT8 model runs directly on the **Snapdragon NPU**. It correlates sensory signals—baseband radio status, device thermals, access frequency, and memory bus metrics—to output a dynamic **Risk Score (0.02 - Low)** with localized threat reasoning.

### 5. Hands-Free Auditory Threat Defense
If suspicious physical access or consecutive gating failures occur, the phone triggers an offline challenge using `android.speech.tts.TextToSpeech`:
> *"Unusual access detected. Confirm or Revoke?"*

Operators can respond hands-free or tap **"Confirm Identity"** (approves access) or **"Revoke Token"** (immediately invalidates keys, locks the vault, and purges volatile memory).

### 6. iQOO Office Kit Sentinel Tunnel
Screen 4 explicitly labels and establishes a local hardware bridge—the **iQOO Office Kit Sentinel Tunnel**—connecting the phone to a laptop over USB 3.2 Type-C (480 Mbps) or local Wi-Fi Direct P2P. A dedicated embedded server on **Port 8888** streams live audit blocks directly to the laptop's Sentinel Console with zero internet requirement.

### 7. Tamper-Proof Cryptographic Hash-Chain Ledger
Audit events are recorded in an encrypted SQLite database using a blockchain-style hash chain:
$$\text{Current Hash} = \text{SHA-256}(\text{Prev Hash} \parallel \text{Timestamp} \parallel \text{EventType} \parallel \text{Details})$$
Any retroactive record alteration or record injection immediately breaks the cryptographic signature chain, triggering an automated integrity alert.

---

## 🎨 Design Philosophy & Light Aesthetic

Suraksha Kavach deliberately departs from the dark, green-matrix "hacker" visual tropes common in cybersecurity utilities. Instead, it adopts a human-centered, consumer-friendly light aesthetic:

| Design Token | Value | Applied To |
|---|---|---|
| **Background Surface** | `#FDFBF7` | Warm, non-fatiguing warm off-white canvas |
| **Card Containers** | `#FFFFFF` | Elevated cards with smooth **20dp rounded corners** |
| **Subtle Border** | `#E2E8F0` | 1dp border outlining cards for high visual contrast |
| **Safe Accent** | `#10B981` | Sage Green for healthy status, verified gates, and low risk |
| **System Labels** | `#6366F1` | Soft Lavender Blue for NPU diagnostics and hardware tags |
| **Alert / Caution** | `#F59E0B` / `#F43F5E` | Soft Amber and Soft Rose for warnings and emergency revocation |

---

## 📱 The 4 Core Screens

| Screen | Focus | Primary Components |
|---|---|---|
| **1. Dashboard ("My Shield")** | System Health & Vault | Health Gauge (98%), NPU Core Latency (0.02ms), AES-256-GCM File List, and "Lock New File" dynamic modal |
| **2. Access Control Gate** | 3-Factor Multi-Modal Access | CameraX Optical Viewfinder, BiometricPrompt Hook, and Haversine Perimeter Distance Card |
| **3. AI Threat Sentinel** | Edge Reasoning & Voice Defense | Circular Risk Meter (0.02), Offline TTS Audio Trigger, Waveform Visualizer, and Local Anomaly Logs |
| **4. Sync Bridge & Audit** | Desktop P2P Link & Ledger | iQOO Office Kit Sentinel Tunnel (480 Mbps PHY), Port 8888 Status, and SHA-256 Sealed Audit Ledger |

---

## 📂 Repository Structure

```
suraksha_kavach/
├── app/
│   ├── build.gradle.kts                   # Compose BOM, Material 3, CameraX, Biometrics, Crypto
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml        # Air-Gapped configuration (NO INTERNET PERMISSION)
│       │   ├── res/values/                # colors.xml, strings.xml, themes.xml
│       │   └── java/com/suraksha/kavach/
│       │       ├── MainActivity.kt        # FragmentActivity single-activity host
│       │       ├── ui/
│       │       │   ├── theme/             # Color.kt, Shape.kt (20dp), Type.kt, Theme.kt
│       │       │   ├── components/        # AirGappedHeroBadge, RiskMeter, CameraXPreviewView, etc.
│       │       │   └── screens/           # Dashboard, AccessGate, ThreatSentinel, SyncBridge
│       │       ├── security/
│       │       │   ├── CryptoManager.kt   # AES-256-GCM AndroidKeyStore hardware file I/O
│       │       │   ├── BiometricAuthManager.kt # AndroidX BiometricPrompt implementation
│       │       │   ├── GeoFenceCalculator.kt   # Pure Kotlin Haversine distance formula
│       │       │   ├── VoiceSecurityController.kt # Text-To-Speech alert engine
│       │       │   └── ThreatEngine.kt    # Real-time sensory anomaly evaluation
│       │       ├── server/
│       │       │   └── SentinelHttpServer.kt   # Embedded HTTP server for laptop bridge (Port 8888)
│       │       ├── data/
│       │       │   ├── db/AuditDatabaseHelper.kt # Cryptographic SHA-256 hash-chain SQLite ledger
│       │       │   └── model/             # VaultItem, AccessGateState, SentinelTelemetry, AuditLog
│       │       └── viewmodel/
│       │           └── SurakshaViewModel.kt    # Unidirectional StateFlow orchestrator
│       └── test/java/com/suraksha/kavach/
│           ├── CryptoManagerTest.kt       # AES-256-GCM round-trip & tamper detection tests
│           ├── AuditHashChainTest.kt      # Hash-chain integrity validation tests
│           └── GeoFenceCalculatorTest.kt  # Haversine distance verification tests
│
├── laptop_console/                        # Desktop Companion (Runs on Laptop)
│   ├── server.js                          # Standalone Node.js server (Port 3000 <-> Port 8888)
│   ├── package.json
│   └── start_console.bat                  # One-click desktop launcher
│
├── docs/                                  # GitHub Pages Live Web Prototype
│   └── index.html                         # Full interactive browser simulation
├── gradle/wrapper/                        # Gradle 8.7 wrapper binaries & properties
├── gradlew.bat                            # CLI build runner for Windows
├── run_prototype.bat                      # One-click runner for local browser testing
├── WALKTHROUGH_SCRIPT.md                  # 60–90s video presentation script
└── README.md                              # Technical documentation
```

---

## 🚀 Getting Started

### Prerequisites
- **Android Device**: Android 8.0+ (API 26+) with hardware biometrics. Recommended: API 34 with Snapdragon NPU.
- **Laptop / PC**: Windows, macOS, or Linux with Node.js installed (for the Sentinel Console).
- **IDE**: Android Studio (Koala / Ladybug / Iguana) with Android SDK 34.

---

### Step 1: Run the Laptop Sentinel Console

1. Navigate to the desktop console directory:
   ```bash
   cd laptop_console
   ```
2. Start the local console server:
   ```bash
   node server.js
   ```
   *(Or double-click `start_console.bat` on Windows)*
3. Open `http://localhost:3000` in your laptop browser to load the dashboard.

---

### Step 2: Build & Install the Android App

1. Open **Android Studio** and choose **Open**, selecting the `suraksha_kavach` project root.
2. Connect your Android device via USB with **USB Debugging** enabled in Developer Options.
3. Select your device in Android Studio's target selector and click **Run ▶️** (`Shift + F10`).
4. The application will compile, install, and execute natively on your device.

---

### Step 3: Establish the P2P Hardware Bridge

In a terminal on your laptop, configure the ADB reverse socket:
```bash
adb reverse tcp:8888 tcp:8888
```
The laptop console at `http://localhost:3000` will immediately detect the phone's embedded server on Port 8888, establishing the hardware-isolated **iQOO Office Kit Sentinel Tunnel**.

---

## 🧪 Automated Testing

Run the included unit test suite to verify cryptographic guarantees:

```bash
# Windows
gradlew.bat testDebugUnitTest

# macOS / Linux
./gradlew testDebugUnitTest
```

### Verified Test Cases:
- **`CryptoManagerTest`**: Confirms that tampering with a single byte of AES-256-GCM ciphertext immediately triggers an `AEADBadTagException`.
- **`AuditHashChainTest`**: Validates that modifying historical log entries causes `verifyChainIntegrity()` to fail.
- **`GeoFenceCalculatorTest`**: Confirms spherical coordinate accuracy within 8.4 meters of authorized boundaries.

---

## 🔒 Security & Privacy Notice

Suraksha Kavach is engineered with privacy-by-design principles:
- **Zero Remote Telemetry**: No third-party analytics (Google Analytics, Firebase, Mixpanel) are integrated.
- **Hardware Isolation**: Plaintext keys never touch user-space heap memory longer than required for GCM stream decryption.
- **No Cloud Dependency**: Works in full Airplane Mode with all network interfaces disengaged.

---

## 📄 License

Suraksha Kavach is open-source software licensed under the [Apache License, Version 2.0](LICENSE).
