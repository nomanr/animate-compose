package com.nomanr.animate.compose.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF0D0D0D)
val White = Color(0xFFFFFFFF)

val NeoPrimary = Color(0xFF90EE90)
val NeoSecondary = Color(0xFFFFDB58)
val NeoTertiary = Color(0xFFB5D2AD)
val NeoSuccess = Color(0xFF90EE90)
val LoudRed = Color(0xFFFF0033)
val CoolGray = Color(0xFFF2F2F2)
val ColdSurface = Color(0xFFF9F9F9)
val LightGray = Color(0xFFE0E0E0)
val MidGray = Color(0xFF9E9E9E)
val SecondaryTextGray = Color(0xFF757575)
val DisabledTextGray = Color(0xFFBDBDBD)
val NeoLightBackground = Color(0xFFE3EEDF)
val NeoDarkBackground = Color(0xFF363739)

@Immutable
data class Colors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val error: Color,
    val onError: Color,
    val success: Color,
    val onSuccess: Color,
    val disabled: Color,
    val onDisabled: Color,
    val surface: Color,
    val onSurface: Color,
    val background: Color,
    val onBackground: Color,
    val outline: Color,
    val transparent: Color = Color.Transparent,
    val white: Color = White,
    val black: Color = Black,
    val text: Color,
    val textSecondary: Color,
    val textDisabled: Color,
    val scrim: Color,
)

internal val LightColors =
    Colors(
        primary = NeoPrimary,
        onPrimary = Black,
        secondary = NeoSecondary,
        onSecondary = Black,
        tertiary = NeoTertiary,
        onTertiary = Black,
        error = LoudRed,
        onError = Black,
        success = NeoSuccess,
        onSuccess = Black,
        disabled = LightGray,
        onDisabled = MidGray,
        surface = CoolGray,
        onSurface = Black,
        background = NeoLightBackground,
        onBackground = Black,
        outline = Black,
        transparent = Color.Transparent,
        white = White,
        black = Black,
        text = Black,
        textSecondary = SecondaryTextGray,
        textDisabled = DisabledTextGray,
        scrim = Black.copy(alpha = 0.5f),
    )

internal val DarkColors =
    Colors(
        primary = NeoPrimary,
        onPrimary = Black,
        secondary = NeoSecondary,
        onSecondary = Black,
        tertiary = NeoTertiary,
        onTertiary = Black,
        error = LoudRed,
        onError = White,
        success = NeoSuccess,
        onSuccess = Black,
        disabled = MidGray,
        onDisabled = LightGray,
        surface = Black,
        onSurface = White,
        background = NeoDarkBackground,
        onBackground = White,
        outline = Black,
        transparent = Color.Transparent,
        white = White,
        black = Black,
        text = White,
        textSecondary = DisabledTextGray,
        textDisabled = SecondaryTextGray,
        scrim = Black.copy(alpha = 0.7f),
    )

val LocalColors = staticCompositionLocalOf { LightColors }
val LocalContentColor = compositionLocalOf { Color.Black }
val LocalContentAlpha = compositionLocalOf { 1f }

fun Colors.contentColorFor(backgroundColor: Color): Color {
    return when (backgroundColor) {
        primary -> onPrimary
        secondary -> onSecondary
        tertiary -> onTertiary
        surface -> onSurface
        error -> onError
        success -> onSuccess
        disabled -> onDisabled
        background -> onBackground
        else -> Color.Unspecified
    }
}

fun Colors.hardShadowColorFor(color: Color): Color {
    // This is a placeholder function to determine the hard shadow color based on the provided color.
    return outline
}