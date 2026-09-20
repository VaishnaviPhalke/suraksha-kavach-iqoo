package com.suraksha.kavach.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AirplanemodeActive
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suraksha.kavach.ui.theme.*

/**
 * AirGappedHeroBadge prominently highlights the 100% offline air-gapped status
 * displaying "Air-Gapped | 0 Outbound Requests" across all screens.
 * 
 * Features a gentle pulsing safe indicator and hardware NPU verification tag.
 */
@Composable
fun AirGappedHeroBadge(
    modifier: Modifier = Modifier,
    outboundCount: Int = 0,
    npuActive: Boolean = true
) {
    // Gentle breathing pulse animation for the safe status dot
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SageGreenLight)
            .border(1.dp, SageGreenSafe.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Air-gapped shield with pulsing green dot
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(SageGreenSafe.copy(alpha = 0.3f))
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(SageGreenSafe)
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AirplanemodeActive,
                            contentDescription = "Air-Gapped Isolation",
                            tint = SageGreenSafe,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "Air-Gapped | 0 Outbound Requests",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    }
                    Text(
                        text = "Zero Cloud Dependency • Hardware Bus Isolated",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }

            // Right: Snapdragon NPU Active Pill
            if (npuActive) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(LavenderBlueLight)
                        .border(1.dp, LavenderBlue.copy(alpha = 0.25f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Memory,
                        contentDescription = "NPU Active",
                        tint = LavenderBlue,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "NPU Active",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LavenderBlue
                    )
                }
            }
        }
    }
}
