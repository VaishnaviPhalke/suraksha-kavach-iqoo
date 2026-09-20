package com.suraksha.kavach.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cable
import androidx.compose.material.icons.rounded.Laptop
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suraksha.kavach.data.model.SyncBridgeState
import com.suraksha.kavach.data.model.TunnelMedium
import com.suraksha.kavach.ui.theme.*

/**
 * SyncBridgeCard prominently labels and displays the
 * "iQOO Office Kit Sentinel Tunnel" for hardware-isolated local data sync
 * between the Android device and Laptop Sentinel Console.
 */
@Composable
fun SyncBridgeCard(
    syncState: SyncBridgeState,
    onToggleMedium: () -> Unit,
    onExportBundle: () -> Unit,
    modifier: Modifier = Modifier
) {
    KavachCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = LavenderBlue.copy(alpha = 0.35f)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row with Explicit iQOO Office Kit Sentinel Tunnel branding
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(LavenderBlueLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (syncState.connectionType == TunnelMedium.USB_3_2) Icons.Rounded.Cable else Icons.Rounded.Wifi,
                            contentDescription = null,
                            tint = LavenderBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "AIR-GAPPED HARDWARE LINK",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = LavenderBlue,
                            letterSpacing = 0.5.sp
                        )
                        // EXPLICIT REQUIREMENT: Label as iQOO Office Kit Sentinel Tunnel
                        Text(
                            text = "iQOO Office Kit Sentinel Tunnel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    }
                }

                // Status Chip (Active Local Link)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(SageGreenLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Tunnel Active",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SageGreenSafe
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Connection Target Info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceTint)
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Laptop,
                        contentDescription = null,
                        tint = TextCharcoal,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "Host Endpoint: ${syncState.connectedHost}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextCharcoal
                        )
                        Text(
                            text = "Local Point-to-Point Socket (WAN Internet Route Disabled)",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Telemetry Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Transfer Rate
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceTint)
                        .padding(10.dp)
                ) {
                    Text("PHY SPEED", fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = TextMuted)
                    Text("${syncState.transferRateMbps} Mbps", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text("USB 3.2 Full-Duplex", fontSize = 10.sp, color = TextLight)
                }

                // Packets Synced
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceTint)
                        .padding(10.dp)
                ) {
                    Text("PACKETS SYNCED", fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = TextMuted)
                    Text("${syncState.packetsSynced}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SageGreenSafe)
                    Text("Zero Packets Dropped", fontSize = 10.sp, color = TextLight)
                }

                // Latency
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceTint)
                        .padding(10.dp)
                ) {
                    Text("LATENCY", fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = TextMuted)
                    Text("${syncState.latencyMs} ms", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextCharcoal)
                    Text("Direct DMA bus", fontSize = 10.sp, color = TextLight)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Actions: Toggle Medium (USB / Wi-Fi Direct) & Export Audit Bundle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onToggleMedium,
                    modifier = Modifier.weight(1f).height(42.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextCharcoal
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(BorderGray)
                    )
                ) {
                    Text(
                        text = if (syncState.connectionType == TunnelMedium.USB_3_2) "Switch to Wi-Fi Direct" else "Switch to USB 3.2",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = onExportBundle,
                    modifier = Modifier.weight(1f).height(42.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LavenderBlue,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Rounded.Sync, null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Export Bundle", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
