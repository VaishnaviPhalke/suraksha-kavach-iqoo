package com.suraksha.kavach.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suraksha.kavach.data.model.VoiceState
import com.suraksha.kavach.ui.theme.*

/**
 * VoiceInteractionCard implements the Hands-Free Voice Alerts & Revocation UI.
 * 
 * Features:
 * - Text-To-Speech prompt: "Unusual access detected. Confirm or Revoke?"
 * - Animated audio waveform visualization.
 * - On-device voice action triggers: "Confirm Identity" & "Revoke Token & Purge".
 */
@Composable
fun VoiceInteractionCard(
    voiceState: VoiceState,
    promptText: String,
    onConfirm: () -> Unit,
    onRevoke: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Dynamic waveform heights for voice visualization
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val bar1Height by infiniteTransition.animateFloat(
        initialValue = 8f, targetValue = 28f,
        animationSpec = infiniteRepeatable(tween(400, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar1"
    )
    val bar2Height by infiniteTransition.animateFloat(
        initialValue = 14f, targetValue = 36f,
        animationSpec = infiniteRepeatable(tween(350, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar2"
    )
    val bar3Height by infiniteTransition.animateFloat(
        initialValue = 6f, targetValue = 22f,
        animationSpec = infiniteRepeatable(tween(450, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bar3"
    )

    KavachCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = when (voiceState) {
            VoiceState.CONFIRMED -> SageGreenSafe.copy(alpha = 0.4f)
            VoiceState.REVOKED -> SoftRose.copy(alpha = 0.4f)
            else -> SoftAmber.copy(alpha = 0.5f)
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row
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
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SoftAmberLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.RecordVoiceOver,
                            contentDescription = null,
                            tint = SoftAmber,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "HANDS-FREE SENTINEL",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftAmber,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Voice Alerts & Revocation",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    }
                }

                // Waveform or status icon
                if (voiceState == VoiceState.AWAITING_INPUT || voiceState == VoiceState.PROMPTING) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceTint)
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Box(Modifier.width(3.dp).height(bar1Height.dp).background(SoftAmber, CircleShape))
                        Box(Modifier.width(3.dp).height(bar2Height.dp).background(SoftAmber, CircleShape))
                        Box(Modifier.width(3.dp).height(bar3Height.dp).background(SoftAmber, CircleShape))
                        Box(Modifier.width(3.dp).height(bar1Height.dp).background(SoftAmber, CircleShape))
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Text-to-Speech Prompt Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        when (voiceState) {
                            VoiceState.CONFIRMED -> SageGreenLight
                            VoiceState.REVOKED -> SoftRoseLight
                            else -> SoftAmberLight.copy(alpha = 0.6f)
                        }
                    )
                    .border(
                        1.dp,
                        when (voiceState) {
                            VoiceState.CONFIRMED -> SageGreenSafe.copy(alpha = 0.3f)
                            VoiceState.REVOKED -> SoftRose.copy(alpha = 0.3f)
                            else -> SoftAmber.copy(alpha = 0.3f)
                        },
                        RoundedCornerShape(14.dp)
                    )
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = when (voiceState) {
                            VoiceState.CONFIRMED -> Icons.Rounded.CheckCircle
                            VoiceState.REVOKED -> Icons.Rounded.Block
                            else -> Icons.Rounded.Mic
                        },
                        contentDescription = null,
                        tint = when (voiceState) {
                            VoiceState.CONFIRMED -> SageGreenSafe
                            VoiceState.REVOKED -> SoftRose
                            else -> SoftAmber
                        },
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = when (voiceState) {
                                VoiceState.CONFIRMED -> "Voice Command Authenticated"
                                VoiceState.REVOKED -> "Access Revoked & Tokens Purged"
                                else -> "Local TTS Challenge Prompt"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (voiceState) {
                                VoiceState.CONFIRMED -> SageGreenSafe
                                VoiceState.REVOKED -> SoftRose
                                else -> TextMuted
                            }
                        )
                        Text(
                            text = when (voiceState) {
                                VoiceState.CONFIRMED -> "Operator identity confirmed by voice challenge."
                                VoiceState.REVOKED -> "Emergency lockdown executed. All session tokens invalidated."
                                else -> "\"$promptText\""
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextCharcoal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Triggers Row: Confirm (Green) & Revoke (Rose)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f).height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SageGreenSafe,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Rounded.CheckCircle, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Confirm Identity", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = onRevoke,
                    modifier = Modifier.weight(1f).height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SoftRose,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Rounded.Block, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Revoke Token", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
