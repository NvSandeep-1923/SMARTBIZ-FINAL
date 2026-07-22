package com.example.smartbiz.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val IndigoPrimary = Color(0xFF1A237E)
val IndigoSecondary = Color(0xFF3949AB)
val IndigoTertiary = Color(0xFF5C6BC0)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFC5CAE9),
    secondary = Color(0xFF9FA8DA),
    tertiary = Color(0xFF7986CB)
)

private val LightColorScheme = lightColorScheme(
    primary = IndigoPrimary,
    secondary = IndigoSecondary,
    tertiary = IndigoTertiary,
    background = Color(0xFFF0F2F8),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun SmartBizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
