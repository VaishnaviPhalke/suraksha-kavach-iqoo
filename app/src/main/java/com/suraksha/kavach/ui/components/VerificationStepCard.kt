package com.suraksha.kavach.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suraksha.kavach.data.model.StepStatus
import com.suraksha.kavach.ui.theme.*

/**
 * VerificationStepCard renders an interactive verification step in the
 * 3-Factor Multi-Modal Access Gate.
 */
@Composable
fun VerificationStepCard(
    stepNumber: Int,
    title: String,
    subtitle: String,
    icon: ImageVector,
    status: StepStatus,
    detailText: String? = null,
    actionButtonText: String = "Verify Factor",
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    extraContent: (@Composable () -> Unit)? = null
) {
    val statusColor = when (status) {
        StepStatus.VERIFIED -> SageGreenSafe
        StepStatus.VERIFYING -> LavenderBlue
        StepStatus.FAILED -> SoftRose
        StepStatus.PENDING -> TextLight
    }

    val statusBg = when (status) {
        StepStatus.VERIFIED -> SageGreenLight
        StepStatus.VERIFYING -> LavenderBlueLight
        StepStatus.FAILED -> SoftRoseLight
        StepStatus.PENDING -> SurfaceTint
    }

    KavachCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = if (status == StepStatus.VERIFIED) SageGreenSafe.copy(alpha = 0.4f) else BorderGray
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Step number pill & Icon
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(statusBg)
                            .border(1.dp, statusColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (status == StepStatus.PENDING) TextMuted else statusColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "FACTOR 0$stepNumber • $title",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (status == StepStatus.VERIFIED) SageGreenSafe else LavenderBlue,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = subtitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextCharcoal
                        )
                    }
                }

                // Status Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(statusBg)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        when (status) {
                            StepStatus.VERIFIED -> {
                                Icon(Icons.Rounded.Check, null, tint = SageGreenSafe, modifier = Modifier.size(13.dp))
                                Text("Verified", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SageGreenSafe)
                            }
                            StepStatus.VERIFYING -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(11.dp),
                                    strokeWidth = 2.dp,
                                    color = LavenderBlue
                                )
                                Text("Verifying...", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = LavenderBlue)
                            }
                            StepStatus.FAILED -> {
                                Icon(Icons.Rounded.Close, null, tint = SoftRose, modifier = Modifier.size(13.dp))
                                Text("Failed", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SoftRose)
                            }
                            StepStatus.PENDING -> {
                                Text("Pending", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextMuted)
                            }
                        }
                    }
                }
            }

            // Optional Detail Text or Result
            if (detailText != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceTint)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = detailText,
                        fontSize = 12.sp,
                        color = TextMuted,
                        lineHeight = 16.sp
                    )
                }
            }

            // Extra Content (e.g. Camera Preview target or GPS breakdown)
            if (extraContent != null) {
                Spacer(modifier = Modifier.height(10.dp))
                extraContent()
            }

            // Action Button
            if (status != StepStatus.VERIFIED) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onActionClick,
                    enabled = status != StepStatus.VERIFYING,
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LavenderBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = actionButtonText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
