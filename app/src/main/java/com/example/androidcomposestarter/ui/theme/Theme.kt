package com.example.androidcomposestarter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LeafGreen = Color(0xFF7CDB4E)
val DesktopBg = Color(0xFF0B0F0C)
val CapsuleBg = Color(0xE6121814)
val CapsuleBorder = Color(0xFF2A332C)
val OnDesktop = Color(0xFFF2F7F0)
val Muted = Color(0xFFA8B3A6)
val RainBlue = Color(0xFF64B5F6)
val ColdCyan = Color(0xFF80DEEA)
val RestrictAmber = Color(0xFFFFB74D)

private val scheme = darkColorScheme(
    primary = LeafGreen,
    onPrimary = Color(0xFF0B0F0C),
    background = DesktopBg,
    onBackground = OnDesktop,
    surface = CapsuleBg,
    onSurface = OnDesktop,
    outline = CapsuleBorder,
)

@Composable
fun YecaiIslandTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = scheme,
        content = content,
    )
}
