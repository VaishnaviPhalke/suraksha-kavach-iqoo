package com.suraksha.kavach.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.suraksha.kavach.data.model.StepStatus
import com.suraksha.kavach.ui.components.AirGappedHeroBadge
import com.suraksha.kavach.ui.components.CameraXPreviewView
import com.suraksha.kavach.ui.components.KavachCard
import com.suraksha.kavach.ui.components.VerificationStepCard
import com.suraksha.kavach.ui.theme.*
import com.suraksha.kavach.viewmodel.SurakshaViewModel

/**
 * Screen 2: Access Control & Verification Gate
 * 
 * Functional Implementations:
 * 1. Factor 1: Optical QR verification with real CameraX binding & animated reticle.
 * 2. Factor 2: Native Android BiometricPrompt framework invocation.
 * 3. Factor 3: Local Haversine GPS spherical boundary calculation.
 */
@Composable
fun AccessGateScreen(
    viewModel: SurakshaViewModel,
    activity: FragmentActivity,
    modifier: Modifier = Modifier
) {
    val gateState by viewModel.gateState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmOffWhite)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
    ) {
        // 1. Persistent Air-Gapped Badge
        item {
            AirGappedHeroBadge(outboundCount = 0, npuActive = true)
        }

        // 2. Screen Header
        item {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = "Access Control Gate",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextCharcoal
                )
                Text(
                    text = "3-Factor multi-modal identity verification • Evaluated 100% offline",
                    fontSize = 13.sp,
                    color = TextMuted
                )
            }
        }

        // 3. Gate Progression Summary Card
        item {
            val verifiedCount = listOf(
                gateState.cameraQrStatus == StepStatus.VERIFIED,
                gateState.biometricStatus == StepStatus.VERIFIED,
                gateState.geoFenceStatus == StepStatus.VERIFIED
            ).count { it }

            KavachCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = if (gateState.allFactorsVerified) SageGreenSafe.copy(alpha = 0.5f) else LavenderBlue.copy(alpha = 0.3f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (gateState.allFactorsVerified) "GATE FULLY UNLOCKED" else "VERIFICATION PROGRESS ($verifiedCount OF 3)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (gateState.allFactorsVerified) SageGreenSafe else LavenderBlue,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = if (gateState.allFactorsVerified) "Hardware Vault Token Granted" else "Complete all 3 checks to unlock",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                        Text(
                            text = if (gateState.allFactorsVerified) "Session ID: ${gateState.sessionTokenId} active" else "Air-gapped session expires after 15 minutes.",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }

                    if (gateState.allFactorsVerified) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(SageGreenLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.LockOpen, null, tint = SageGreenSafe, modifier = Modifier.size(26.dp))
                        }
                    }
                }
            }
        }

        // 4. Factor 1: Camera Recipient QR Scan (CameraX)
        item {
            VerificationStepCard(
                stepNumber = 1,
                title = "Camera Recipient QR Scan",
                subtitle = "Optical asymmetric verification key",
                icon = Icons.Rounded.QrCodeScanner,
                status = gateState.cameraQrStatus,
                detailText = gateState.scannedRecipientHash?.let { "Recipient Signature: $it" },
                actionButtonText = "Start CameraX Optical Scan",
                onActionClick = { viewModel.triggerCameraQrScan() },
                extraContent = {
                    CameraXPreviewView(
                        isScanning = gateState.cameraQrStatus == StepStatus.VERIFYING
                    )
                }
            )
        }

        // 5. Factor 2: Biometric Authentication (Native BiometricPrompt)
        item {
            VerificationStepCard(
                stepNumber = 2,
                title = "Biometric Authentication",
                subtitle = "StrongBox Android BiometricPrompt",
                icon = Icons.Rounded.Fingerprint,
                status = gateState.biometricStatus,
                detailText = gateState.biometricMethodUsed?.let { "Status: $it verified." },
                actionButtonText = "Launch Native BiometricPrompt",
                onActionClick = { viewModel.triggerBiometricAuth(activity) }
            )
        }

        // 6. Factor 3: Geo-Fence Validation (Haversine GPS)
        item {
            VerificationStepCard(
                stepNumber = 3,
                title = "Geo-Fence Validation",
                subtitle = "Haversine GPS boundary check",
                icon = Icons.Rounded.LocationOn,
                status = gateState.geoFenceStatus,
                detailText = if (gateState.geoFenceStatus == StepStatus.VERIFIED) {
                    "Haversine calculation: ${gateState.distanceMeters}m from bunker perimeter (Limit: 50.0m). Coordinates verified."
                } else null,
                actionButtonText = "Compute Haversine Perimeter",
                onActionClick = { viewModel.triggerGeoFenceCheck() },
                extraContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceTint)
                            .padding(10.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Distance to Bunker:", fontSize = 11.sp, color = TextMuted)
                                Text("${gateState.distanceMeters} m inside safe zone", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SageGreenSafe)
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Formula:", fontSize = 11.sp, color = TextMuted)
                                Text("Pure Kotlin Haversine (Zero Cloud)", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = LavenderBlue)
                            }
                        }
                    }
                }
            )
        }

        // 7. Reset Gate Session Button
        if (gateState.allFactorsVerified) {
            item {
                OutlinedButton(
                    onClick = { viewModel.resetAccessGate() },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(BorderGray)
                    )
                ) {
                    Icon(Icons.Rounded.Refresh, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Clear Gate Session & Re-lock", fontSize = 12.sp, color = TextCharcoal)
                }
            }
        }
    }
}
