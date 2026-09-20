package com.suraksha.kavach.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Suraksha Kavach Rounded Friendly Shapes
 * Smooth 20dp corners on cards create an inviting, human, tactile feel.
 */
val Shapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp), // Core card corner radius (smooth 20dp)
    extraLarge = RoundedCornerShape(28.dp)
)

val CardShape20 = RoundedCornerShape(20.dp)
val PillShape = RoundedCornerShape(100.dp)
val ButtonShape = RoundedCornerShape(14.dp)
