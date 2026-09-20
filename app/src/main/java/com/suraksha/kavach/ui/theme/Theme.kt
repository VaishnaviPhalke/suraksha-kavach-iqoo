package com.suraksha.kavach.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = SageGreenSafe,
    onPrimary = CardWhite,
    primaryContainer = SageGreenLight,
    onPrimaryContainer = SageGreenSafe,
    secondary = LavenderBlue,
    onSecondary = CardWhite,
    secondaryContainer = LavenderBlueLight,
    onSecondaryContainer = LavenderBlue,
    tertiary = SoftAmber,
    onTertiary = CardWhite,
    tertiaryContainer = SoftAmberLight,
    onTertiaryContainer = TextCharcoal,
    background = WarmOffWhite,
    onBackground = TextCharcoal,
    surface = CardWhite,
    onSurface = TextCharcoal,
    surfaceVariant = SurfaceTint,
    onSurfaceVariant = TextMuted,
    outline = BorderGray,
    error = SoftRose,
    onError = CardWhite,
    errorContainer = SoftRoseLight,
    onErrorContainer = SoftRose
)

@Composable
fun SurakshaKavachTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = WarmOffWhite.toArgb()
            window.navigationBarColor = WarmOffWhite.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
