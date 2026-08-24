package com.example.gridprocessor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val GridBlack = Color(0xFF050914)
val GridSurface = Color(0xFF09111F)
val GridSurface2 = Color(0xFF0D1728)
val GridWhite = Color(0xFFF5F7FF)
val GridMuted = Color(0xFF8D9AB0)
val GridLine = Color(0xFF1B2A42)
val GridPurple = Color(0xFF8B5CF6)
val GridCyan = Color(0xFF19D9FF)
val GridGreen = Color(0xFF27E58A)

private val DarkColors = darkColorScheme(
    primary = GridPurple,
    secondary = GridCyan,
    tertiary = GridGreen,
    background = GridBlack,
    surface = GridSurface,
    onBackground = GridWhite,
    onSurface = GridWhite
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF2764E8),
    secondary = Color(0xFF00A6C7),
    tertiary = Color(0xFF18A957),
    background = Color(0xFFF5F7FB),
    surface = Color.White,
    onBackground = Color(0xFF172236),
    onSurface = Color(0xFF172236)
)

@Composable
fun GridProcessorTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography(),
        content = content
    )
}
