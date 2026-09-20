package com.suraksha.kavach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.suraksha.kavach.data.model.NpuAnomalyEvent
import com.suraksha.kavach.ui.components.AirGappedHeroBadge
import com.suraksha.kavach.ui.components.KavachCard
import com.suraksha.kavach.ui.components.RiskScoreMeter
import com.suraksha.kavach.ui.components.VoiceInteractionCard
import com.suraksha.kavach.ui.theme.*
import com.suraksha.kavach.viewmodel.SurakshaViewModel

/**
 * Screen 3: AI Threat Sentinel
 * 
 * Active Features:
 * - Real Text-To-Speech (TTS) engine speaking "Unusual access detected. Confirm or Revoke?" aloud.
 * - Dynamic sensory risk score evaluation (Risk Score: 0.02 nominal).
 * - Real voice command action triggers (Confirm Identity / Revoke & Purge).
 * - Live local NPU anomaly logs.
 */
@Composable
fun ThreatSentinelScreen(
    viewModel: SurakshaViewModel,
    modifier: Modifier = Modifier
) {
    val telemetry by viewModel.sentinelTelemetry.collectAsState()

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "AI Threat Sentinel",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextCharcoal
                    )
                    Text(
                        text = "Snapdragon NPU on-device reasoning • Local anomaly detection",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }

                // Play Audio TTS Alert Button
                IconButton(
                    onClick = { viewModel.playTtsAlert() },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(SoftAmberLight)
                        .border(1.dp, SoftAmber.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.VolumeUp,
                        contentDescription = "Speak TTS Challenge",
                        tint = SoftAmber,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 3. Visual Risk Score Meter (Risk Score: 0.02 - Low)
        item {
            RiskScoreMeter(
                riskScore = telemetry.riskScore,
                riskLevel = telemetry.riskLevel
            )
        }

        // 4. Hands-Free Voice Alerts & Revocation Card
        item {
            VoiceInteractionCard(
                voiceState = telemetry.voiceListeningState,
                promptText = telemetry.voicePromptText,
                onConfirm = { viewModel.confirmVoiceIdentity() },
                onRevoke = { viewModel.revokeVoiceAccess() }
            )
        }

        // 5. NPU Neural Core Telemetry Breakdown
        item {
            KavachCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = LavenderBlue.copy(alpha = 0.3f)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(LavenderBlueLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Rounded.Analytics, null, tint = LavenderBlue, modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = "Snapdragon NPU Real-Time Diagnostics",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        }

                        TextButton(onClick = { viewModel.playTtsAlert() }) {
                            Text("🔊 Test TTS Voice", fontSize = 11.sp, color = SoftAmber, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TelemetryStatCol("NPU Core Load", "${telemetry.npuCoreUtilization}% (Nominal)", LavenderBlue)
                        TelemetryStatCol("Quantization", "Int8 Linear", TextCharcoal)
                        TelemetryStatCol("WAN Firewall", "Drop All (0 Pkts)", SageGreenSafe)
                    }
                }
            }
        }

        // 6. Live Local NPU Anomaly Logs Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Live NPU Anomaly Detection",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = "Dynamic sensory and hardware integrity monitors",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SageGreenLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("Sensors Verified", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SageGreenSafe)
                }
            }
        }

        // 7. Live Anomaly Events List
        items(telemetry.anomalyEvents, key = { it.id }) { event ->
            AnomalyEventCard(event = event)
        }
    }
}

@Composable
private fun TelemetryStatCol(label: String, value: String, color: Color) {
    Column {
        Text(text = label, fontSize = 10.sp, color = TextMuted)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
private fun AnomalyEventCard(event: NpuAnomalyEvent) {
    KavachCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = if (event.isBenign) BorderGray else SoftAmber.copy(alpha = 0.4f)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(if (event.isBenign) SageGreenLight else SoftAmberLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (event.isBenign) Icons.Rounded.Check else Icons.Rounded.Warning,
                            contentDescription = null,
                            tint = if (event.isBenign) SageGreenSafe else SoftAmber,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    Column {
                        Text(
                            text = event.checkName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                        Text(
                            text = "Time: ${event.timestamp} • Result: ${event.result}",
                            fontSize = 11.sp,
                            color = if (event.isBenign) SageGreenSafe else SoftAmber,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = event.explanation,
                fontSize = 12.sp,
                color = TextMuted,
                lineHeight = 16.sp
            )
        }
    }
}
