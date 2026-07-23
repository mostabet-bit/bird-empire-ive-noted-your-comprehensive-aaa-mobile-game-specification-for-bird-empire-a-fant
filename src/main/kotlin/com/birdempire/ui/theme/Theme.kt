package com.birdempire.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Primary Colors
val PrimaryDark = Color(0xFF2D1B69)
val PrimaryLight = Color(0xFF6C4EFF)
val SecondaryDark = Color(0xFFFF6B35)
val SecondaryLight = Color(0xFFFFB84D)
val TertiaryDark = Color(0xFF00D9FF)
val TertiaryLight = Color(0xFF4ECDC4)

// Neutral Colors
val BackgroundDark = Color(0xFF0F1419)
val BackgroundLight = Color(0xFFFAFAFA)
val SurfaceDark = Color(0xFF1A1F2E)
val SurfaceLight = Color(0xFFFFFFFF)
val OnSurfaceDark = Color(0xFFE1E6F0)
val OnSurfaceLight = Color(0xFF1A1F2E)

// Status Colors
val SuccessGreen = Color(0xFF2ECC71)
val ErrorRed = Color(0xFFE74C3C)
val WarningYellow = Color(0xFFF39C12)
val InfoBlue = Color(0xFF3498DB)

val darkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    error = ErrorRed
)

val lightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    tertiary = TertiaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    error = ErrorRed
)

@Composable
fun BirdEmpireTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BirdEmpireTypography,
        shapes = BirdEmpireShapes,
        content = content
    )
}
