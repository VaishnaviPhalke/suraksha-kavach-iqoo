package com.suraksha.kavach.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suraksha.kavach.ui.theme.BorderGray
import com.suraksha.kavach.ui.theme.CardWhite

/**
 * KavachCard is the foundational card container for Suraksha Kavach.
 * Features 20dp smooth corners, subtle #E2E8F0 border, clean #FFFFFF background,
 * and soft elevation for a friendly, warm aesthetic.
 */
@Composable
fun KavachCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = CardWhite,
    borderColor: Color = BorderGray,
    cornerRadius: Dp = 20.dp,
    elevation: Dp = 1.dp,
    contentPadding: Dp = 16.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                spotColor = Color(0x0F000000),
                ambientColor = Color(0x08000000)
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(cornerRadius)),
        color = backgroundColor,
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}
