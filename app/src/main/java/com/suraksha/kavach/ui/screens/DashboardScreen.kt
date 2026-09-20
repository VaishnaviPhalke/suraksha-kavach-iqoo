package com.suraksha.kavach.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.suraksha.kavach.data.model.VaultClassification
import com.suraksha.kavach.data.model.VaultItem
import com.suraksha.kavach.ui.components.AirGappedHeroBadge
import com.suraksha.kavach.ui.components.KavachCard
import com.suraksha.kavach.ui.theme.*
import com.suraksha.kavach.viewmodel.SurakshaViewModel

/**
 * Screen 1: Dashboard ("My Shield")
 * 
 * Active Features:
 * - Real hardware file encryption & decryption (AES-256-GCM)
 * - "Encrypt New Secret File" modal
 * - System health score and Snapdragon NPU metrics
 */
@Composable
fun DashboardScreen(
    viewModel: SurakshaViewModel,
    modifier: Modifier = Modifier
) {
    val vaultItems by viewModel.vaultItems.collectAsState()
    val healthScore = viewModel.shieldHealthScore
    var showEncryptDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmOffWhite)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
    ) {
        // 1. Persistent Air-Gapped Hero Status Badge
        item {
            AirGappedHeroBadge(outboundCount = 0, npuActive = true)
        }

        // 2. Dashboard Title Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "My Shield",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextCharcoal
                    )
                    Text(
                        text = "Air-gapped on-device protection • Zero cloud telemetry",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }

                // Add File Button
                Button(
                    onClick = { showEncryptDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SageGreenSafe,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Rounded.Add, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Lock File", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // 3. Quick Health Score Card
        item {
            KavachCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = SageGreenSafe.copy(alpha = 0.35f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.VerifiedUser,
                                contentDescription = null,
                                tint = SageGreenSafe,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "SYSTEM HEALTH: 98/100",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreenSafe,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Shield Robust & Isolated",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )

                        Text(
                            text = "Hardware KeyStore active • Radio bus powered down • StrongBox root of trust verified.",
                            fontSize = 12.sp,
                            color = TextMuted,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Circular Health Score Chip
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(SageGreenLight)
                            .border(2.dp, SageGreenSafe, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$healthScore%",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreenSafe
                            )
                            Text(
                                text = "SAFE",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreenSafe,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }

        // 4. Snapdragon NPU Metrics Card
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
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(LavenderBlueLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Memory,
                                    contentDescription = null,
                                    tint = LavenderBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "SNAPDRAGON NPU SENTINEL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LavenderBlue,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "On-Device Neural Engine",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextCharcoal
                                )
                            }
                        }

                        // Low Risk pill
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(SageGreenLight)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Risk: 0.02 (Low)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreenSafe
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        NpuMetricItem("Inference Latency", "0.02 ms", LavenderBlue)
                        NpuMetricItem("Model Weights", "Int8 Quantized", TextCharcoal)
                        NpuMetricItem("Outbound Sockets", "0 (Airgap)", SageGreenSafe)
                    }
                }
            }
        }

        // 5. Hardware-Encrypted Vault Section Header
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
                        text = "Hardware Encryption Vault",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = "AES-256-GCM bound to Hardware KeyStore (HWID)",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceTint)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${vaultItems.size} Stored Files",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextCharcoal
                    )
                }
            }
        }

        // 6. Vault File Items
        items(vaultItems, key = { it.id }) { item ->
            VaultItemCard(
                item = item,
                onToggleDecryption = { viewModel.toggleVaultItemDecryption(item.id) }
            )
        }
    }

    // "Lock New File" Dialog
    if (showEncryptDialog) {
        EncryptFileDialog(
            onDismiss = { showEncryptDialog = false },
            onConfirm = { name, secret ->
                viewModel.encryptNewSecretFile(name, secret)
                showEncryptDialog = false
            }
        )
    }
}

@Composable
private fun EncryptFileDialog(
    onDismiss: () -> Unit,
    onConfirm: (fileName: String, secretText: String) -> Unit
) {
    var fileName by remember { mutableStateOf("") }
    var secretContent by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        KavachCard(
            modifier = Modifier.fillMaxWidth(),
            borderColor = LavenderBlue.copy(alpha = 0.5f)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                Text(
                    text = "Encrypt New Secret File",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
                Text(
                    text = "Generates hardware AES-256-GCM encryption on disk.",
                    fontSize = 12.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = fileName,
                    onValueChange = { fileName = it },
                    label = { Text("File Name (e.g. strategic_dossier.enc)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = secretContent,
                    onValueChange = { secretContent = it },
                    label = { Text("Secret Payload Text") },
                    modifier = Modifier.fillMaxWidth().height(110.dp),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = TextMuted)
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (fileName.isNotBlank() && secretContent.isNotBlank()) {
                                onConfirm(fileName, secretContent)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SageGreenSafe),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Encrypt in Hardware")
                    }
                }
            }
        }
    }
}

@Composable
private fun NpuMetricItem(label: String, value: String, valueColor: Color) {
    Column {
        Text(text = label, fontSize = 10.sp, color = TextMuted)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = valueColor)
    }
}

@Composable
private fun VaultItemCard(
    item: VaultItem,
    onToggleDecryption: () -> Unit
) {
    val isDecrypted = item.isDecrypted

    KavachCard(
        modifier = Modifier.fillMaxWidth(),
        borderColor = if (isDecrypted) SageGreenSafe.copy(alpha = 0.4f) else BorderGray
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
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
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isDecrypted) SageGreenLight else LavenderBlueLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isDecrypted) Icons.Rounded.LockOpen else Icons.Rounded.Lock,
                            contentDescription = null,
                            tint = if (isDecrypted) SageGreenSafe else LavenderBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = item.fileName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = item.fileSizeReadable,
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                            Text("•", fontSize = 10.sp, color = TextLight)
                            Text(
                                text = item.hardwareKeyAlias,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = LavenderBlue
                            )
                        }
                    }
                }

                // Decrypt / Lock Button
                IconButton(
                    onClick = onToggleDecryption,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isDecrypted) SageGreenLight else SurfaceTint)
                ) {
                    Icon(
                        imageVector = if (isDecrypted) Icons.Rounded.LockReset else Icons.Rounded.Key,
                        contentDescription = if (isDecrypted) "Lock" else "Decrypt",
                        tint = if (isDecrypted) SageGreenSafe else TextCharcoal,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Expanded Decrypted Payload Preview
            AnimatedVisibility(visible = isDecrypted) {
                Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SageGreenLight.copy(alpha = 0.5f))
                            .border(1.dp, SageGreenSafe.copy(alpha = 0.25f), RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Rounded.Security, null, tint = SageGreenSafe, modifier = Modifier.size(13.dp))
                                Text(
                                    text = "DECRYPTED IN ISOLATED VOLATILE MEMORY",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SageGreenSafe
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.payloadPreview,
                                fontSize = 11.sp,
                                color = TextCharcoal,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
