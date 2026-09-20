package com.suraksha.kavach.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suraksha.kavach.ui.theme.*

/**
 * RiskScoreMeter displays the visual risk score (e.g. 0.02 - Low)
 * derived from the Snapdragon NPU's on-device quantized INT8 model.
 */
@Composable
fun RiskScoreMeter(
    riskScore: Float,
    riskLevel: String,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = riskScore.coerceIn(0f, 1f),
        animationSpec = tween(1000),
        label = "riskProgress"
    )

    val meterColor = when {
        riskScore < 0.15f -> SageGreenSafe
        riskScore < 0.50f -> SoftAmber
        else -> SoftRose
    }

    val meterBgColor = when {
        riskScore < 0.15f -> SageGreenLight
        riskScore < 0.50f -> SoftAmberLight
        else -> SoftRoseLight
    }

    KavachCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Circular Progress Gauge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(92.dp)
            ) {
                Canvas(modifier = Modifier.size(80.dp)) {
                    // Track circle
                    drawCircle(
                        color = Color(0xFFF1F5F9),
                        radius = size.minDimension / 2,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                    // Active indicator arc
                    drawArc(
                        color = meterColor,
                        startAngle = -90f,
                        sweepAngle = (animatedProgress * 360f).coerceAtLeast(10f),
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format(java.util.Locale.US, "%.2f", riskScore),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = "SCORE",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMuted,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details and NPU Model Tag
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(meterBgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (riskScore < 0.5f) Icons.Rounded.CheckCircle else Icons.Rounded.Warning,
                            contentDescription = null,
                            tint = meterColor,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Text(
                        text = "Risk Level: $riskLevel",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Local LLM evaluation via Snapdragon NPU. 0 cloud telemetry.",
                    fontSize = 12.sp,
                    color = TextMuted,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LavenderBlueLight)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Psychology,
                        contentDescription = "On-Device LLM",
                        tint = LavenderBlue,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "DeepSeek-1.5B (Quantized Int8)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = LavenderBlue
                    )
                }
            }
        }
    }
}
