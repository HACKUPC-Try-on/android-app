package com.tryon.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
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
            ColorScheme(
                primary = TryOnTheme.Colors.primary,
                onPrimary = TryOnTheme.Colors.secondary,
                primaryContainer = TryOnTheme.Colors.tertiary,
                onPrimaryContainer = TryOnTheme.Colors.accent,
                inversePrimary = TryOnTheme.Colors.tertiary,
                secondary = TryOnTheme.Colors.secondary,
                onSecondary = TryOnTheme.Colors.accent,
                secondaryContainer = TryOnTheme.Colors.highlight,
                onSecondaryContainer = TryOnTheme.Colors.accent,
                tertiary = TryOnTheme.Colors.tertiary,
                onTertiary = TryOnTheme.Colors.accent,
                tertiaryContainer = TryOnTheme.Colors.primary,
                onTertiaryContainer = TryOnTheme.Colors.secondary,
                background = TryOnTheme.Colors.primary,
                onBackground = TryOnTheme.Colors.secondary,
                error = TryOnTheme.Colors.error,
                onError = TryOnTheme.Colors.accent,
                errorContainer = TryOnTheme.Colors.error,
                onErrorContainer = TryOnTheme.Colors.accent,
                surface = TryOnTheme.Colors.primary,
                onSurface = TryOnTheme.Colors.secondary,
                surfaceVariant = TryOnTheme.Colors.highlight,
                surfaceTint = TryOnTheme.Colors.highlight,
                onSurfaceVariant = TryOnTheme.Colors.accent,
                outline = TryOnTheme.Colors.tertiary,
                inverseOnSurface = TryOnTheme.Colors.primary,
                inverseSurface = TryOnTheme.Colors.secondary,
                outlineVariant = Color.Unspecified,
                scrim = Color.Unspecified,
                surfaceBright = Color.Unspecified,
                surfaceDim = Color.Unspecified,
                surfaceContainer = Color.Unspecified,
                surfaceContainerHigh = Color.Unspecified,
                surfaceContainerHighest = Color.Unspecified,
                surfaceContainerLow = Color.Unspecified,
                surfaceContainerLowest = Color.Unspecified,
            ), typography = TryOnTheme.Typography.material,
            content = content
        )
    }
}

fun setupCompositionLocals(darkTheme: Boolean): Array<ProvidedValue<out Any>> {
    return arrayOf(
        LocalColorsSystem provides colors(darkTheme),
        LocalDimenSystem provides dimen(),
        LocalTypographySystem provides textStyles()
    )
}

private val LocalColorsSystem =
    staticCompositionLocalOf<Colors> { error("Error colors") }

private val LocalTypographySystem =
    staticCompositionLocalOf<TextStyles> { error("Error textstyles") }

private val LocalDimenSystem =
    staticCompositionLocalOf<Dimen> { error("Error dimen") }

object TryOnTheme {
    val Colors @Composable get() = LocalColorsSystem.current
    val Dimen @Composable get() = LocalDimenSystem.current
    val Typography @Composable get() = LocalTypographySystem.current
}
