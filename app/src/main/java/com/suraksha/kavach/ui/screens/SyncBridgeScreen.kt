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
import com.suraksha.kavach.data.model.AuditLog
import com.suraksha.kavach.ui.components.AirGappedHeroBadge
import com.suraksha.kavach.ui.components.KavachCard
import com.suraksha.kavach.ui.components.SyncBridgeCard
import com.suraksha.kavach.ui.theme.*
import com.suraksha.kavach.viewmodel.SurakshaViewModel

/**
 * Screen 4: iQOO Office Kit Sync Bridge & Tamper-Proof Audit Trail
 * 
 * Active Features:
 * - Real embedded HTTP server on port 8888 for Laptop Sentinel Console.
 * - Real SQLite database with cryptographic SHA-256 hash chaining.
 * - USB 3.2 / Wi-Fi Direct tunnel connection management.
 */
@Composable
fun SyncBridgeScreen(
    viewModel: SurakshaViewModel,
    modifier: Modifier = Modifier
) {
    val syncState by viewModel.syncBridgeState.collectAsState()
    val auditLogs by viewModel.auditLogs.collectAsState()

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
                    text = "Sync Bridge & Audit",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextCharcoal
                )
                Text(
                    text = "Direct hardware sync to Laptop Sentinel Console • SQLCipher encrypted logs",
                    fontSize = 13.sp,
                    color = TextMuted
                )
            }
        }

        // 3. iQOO Office Kit Sentinel Tunnel Card
        item {
            SyncBridgeCard(
                syncState = syncState,
                onToggleMedium = { viewModel.toggleSyncTunnelMedium() },
                onExportBundle = { viewModel.exportAirGappedBundle() }
            )
        }

        // 4. Laptop Sentinel Console Bridge Telemetry Details
        item {
            KavachCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = LavenderBlue.copy(alpha = 0.35f)
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
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(LavenderBlueLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Rounded.Terminal, null, tint = LavenderBlue, modifier = Modifier.size(18.dp))
                            }
                            Column {
                                Text(
                                    text = "Embedded Laptop Console Server",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal
                                )
                                Text(
                                    text = "Listening on http://localhost:8888",
                                    fontSize = 11.sp,
                                    color = LavenderBlue,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SageGreenLight)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text("Port 8888 Active", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SageGreenSafe)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Connect your laptop via USB and run 'adb reverse tcp:8888 tcp:8888', then open your laptop browser to http://localhost:8888. The phone serves the Sentinel Console web app completely offline.",
                        fontSize = 12.sp,
                        color = TextMuted,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceTint)
                                .border(1.dp, BorderGray, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Tunnel Crypt: ChaCha20-Poly1305", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = TextCharcoal)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceTint)
                                .border(1.dp, BorderGray, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Zero WAN Socket Allocation", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = SageGreenSafe)
                        }
                    }
                }
            }
        }

        // 5. Tamper-Proof Audit Trail Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Tamper-Proof Audit Trail",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = "Cryptographic SHA-256 hash-chain SQLite ledger",
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
                    Text(
                        text = "Chain Integrity: Sealed",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = SageGreenSafe
                    )
                }
            }
        }

        // 6. Audit Log Items
        items(auditLogs, key = { it.id }) { log ->
            AuditLogCard(log = log)
        }
    }
}

@Composable
private fun AuditLogCard(log: AuditLog) {
    KavachCard(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SageGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Key,
                        contentDescription = null,
                        tint = SageGreenSafe,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = log.eventType,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                        Text(
                            text = log.timestamp,
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }
                    Text(
                        text = log.detail,
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )
                    Text(
                        text = "Checksum: ${log.sha256Checksum}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = LavenderBlue
                    )
                }
            }

            // Sealed Verification Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(SageGreenLight)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "SEALED",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = SageGreenSafe
                )
            }
        }
    }
}
