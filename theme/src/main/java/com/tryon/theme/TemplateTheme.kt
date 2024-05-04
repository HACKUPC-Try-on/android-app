package com.tryon.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.tryon.theme.colors.Colors
import com.tryon.theme.colors.colors

@Composable
fun TryOnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        *setupCompositionLocals(darkTheme)
    ) {
        MaterialTheme(
            colorScheme = ColorScheme(
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
                TryOnTheme.Colors.Background.default,
            ),
            typography = Typography,
            content = content
        )
    }
}

fun setupCompositionLocals(darkTheme: Boolean): Array<ProvidedValue<out Any>> {
    return arrayOf(
        LocalColorsSystem provides colors(darkTheme),
        LocalDimenSystem provides dimen(),
        LocalTypographySystem provides Typography
    )
}

private val LocalColorsSystem =
    staticCompositionLocalOf<Colors> { error("Error colors") }

private val LocalTypographySystem =
    staticCompositionLocalOf<Typography> { error("Error typography") }

private val LocalDimenSystem =
    staticCompositionLocalOf<Dimen> { error("Error dimen") }

object TryOnTheme {
    val Colors @Composable get() = LocalColorsSystem.current
    val Dimen @Composable get() = LocalDimenSystem.current
    val Typography @Composable get() = LocalTypographySystem.current
}
