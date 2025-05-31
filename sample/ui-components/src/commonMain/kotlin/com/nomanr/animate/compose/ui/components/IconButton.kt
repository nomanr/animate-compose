package com.nomanr.animate.compose.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.LocalContentColor
import com.nomanr.animate.compose.ui.contentColorFor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: IconButtonVariant = IconButtonVariant.Primary,
    shape: Shape = IconButtonDefaults.ButtonSquareShape,
    onClick: () -> Unit = {},
    contentPadding: PaddingValues = IconButtonDefaults.ButtonPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val style = IconButtonDefaults.styleFor(variant, shape)

    IconButtonComponent(
        modifier = modifier,
        enabled = enabled,
        style = style,
        onClick = onClick,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
private fun IconButtonComponent(
    modifier: Modifier,
    enabled: Boolean,
    style: IconButtonStyle,
    onClick: () -> Unit,
    contentPadding: PaddingValues,
    interactionSource: MutableInteractionSource,
    content: @Composable () -> Unit,
) {
    val containerColor = style.colors.containerColor(enabled).value
    val contentColor = style.colors.contentColor(enabled).value

    Surface(
        onClick = onClick,
        modifier =
            modifier
                .defaultMinSize(
                    minWidth = IconButtonDefaults.ButtonSize,
                    minHeight = IconButtonDefaults.ButtonSize
                )
                .semantics { role = Role.Button },
        enabled = enabled,
        shape = style.shape,
        color = containerColor,
        contentColor = contentColor,
        interactionSource = interactionSource,
        hardShadow = true,
        border = true
    ) {
        Box(
            modifier = Modifier.padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            // Add a loading indicator if needed
            content()
        }
    }
}

enum class IconButtonVariant {
    Primary,
    Secondary,
    Destructive,
    Neutral,
    Ghost,
}

internal object IconButtonDefaults {
    val ButtonSize = 44.dp
    val ButtonPadding = PaddingValues(4.dp)
    val ButtonSquareShape = RoundedCornerShape(0.dp)

    @Composable
    fun styleFor(variant: IconButtonVariant, shape: Shape): IconButtonStyle {
        return when (variant) {
            IconButtonVariant.Primary -> primary(shape)
            IconButtonVariant.Secondary -> secondary(shape)
            IconButtonVariant.Destructive -> destructive(shape)
            IconButtonVariant.Neutral -> neutral(shape)
            IconButtonVariant.Ghost -> ghost(shape)
        }
    }

    @Composable
    fun primary(shape: Shape) =
        IconButtonStyle(
            colors =
                IconButtonColors(
                    containerColor = AppTheme.colors.primary,
                    contentColor = AppTheme.colors.onPrimary,
                    disabledContainerColor = AppTheme.colors.disabled,
                    disabledContentColor = AppTheme.colors.onDisabled,
                ),
            shape = shape,
        )


    @Composable
    fun secondary(shape: Shape) =
        IconButtonStyle(
            colors =
                IconButtonColors(
                    containerColor = AppTheme.colors.secondary,
                    contentColor = AppTheme.colors.onSecondary,
                    disabledContainerColor = AppTheme.colors.disabled,
                    disabledContentColor = AppTheme.colors.onDisabled,
                ),
            shape = shape,
        )


    @Composable
    fun destructive(shape: Shape) =
        IconButtonStyle(
            colors =
                IconButtonColors(
                    containerColor = AppTheme.colors.error,
                    contentColor = AppTheme.colors.onError,
                    disabledContainerColor = AppTheme.colors.disabled,
                    disabledContentColor = AppTheme.colors.onDisabled,
                ),
            shape = shape,
        )

    @Composable
    fun neutral(shape: Shape) =
        IconButtonStyle(
            colors =
                IconButtonColors(
                    containerColor = AppTheme.colors.surface,
                    contentColor = AppTheme.colors.onSurface,
                    disabledContainerColor = AppTheme.colors.disabled,
                    disabledContentColor = AppTheme.colors.onDisabled,
                ),
            shape = shape,
        )


    @Composable
    fun ghost(shape: Shape) =
        IconButtonStyle(
            colors =
                IconButtonColors(
                    containerColor = AppTheme.colors.transparent,
                    contentColor = LocalContentColor.current,
                    disabledContainerColor = AppTheme.colors.transparent,
                    disabledContentColor = AppTheme.colors.onDisabled,
                ),
            shape = shape,
        )
}

@Immutable
data class IconButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color? = null,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val disabledBorderColor: Color? = null,
) {
    @Composable
    fun containerColor(enabled: Boolean) =
        rememberUpdatedState(if (enabled) containerColor else disabledContainerColor)

    @Composable
    fun contentColor(enabled: Boolean) =
        rememberUpdatedState(if (enabled) contentColor else disabledContentColor)

}

@Immutable
data class IconButtonStyle(
    val colors: IconButtonColors,
    val shape: Shape,
)

@Composable
@Preview
fun PrimaryIconButtonPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BasicText(text = "Primary Icon Buttons", style = AppTheme.typography.h2)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(variant = IconButtonVariant.Primary) {
                    DummyIconForIconButtonPreview()
                }
            }
        }
    }
}


@Composable
@Preview
fun DestructiveIconButtonPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BasicText(text = "Destructive Icon Buttons", style = AppTheme.typography.h2)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(variant = IconButtonVariant.Neutral) {
                    DummyIconForIconButtonPreview()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun GhostIconButtonPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BasicText(text = "Ghost Icon Buttons", style = AppTheme.typography.h2)

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8))
                        .background(AppTheme.colors.background),
                    contentAlignment = Alignment.Center,
                ) {
                    CompositionLocalProvider(LocalContentColor provides contentColorFor(color = AppTheme.colors.background)) {
                        IconButton(variant = IconButtonVariant.Ghost) {
                            DummyIconForIconButtonPreview()
                        }
                    }
                }

                Box(
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8))
                        .background(AppTheme.colors.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    CompositionLocalProvider(LocalContentColor provides contentColorFor(color = AppTheme.colors.primary)) {
                        IconButton(variant = IconButtonVariant.Ghost) {
                            DummyIconForIconButtonPreview()
                        }
                    }
                }

                Box(
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8))
                        .background(AppTheme.colors.secondary),
                    contentAlignment = Alignment.Center,
                ) {
                    CompositionLocalProvider(LocalContentColor provides contentColorFor(color = AppTheme.colors.secondary)) {
                        IconButton(variant = IconButtonVariant.Ghost) {
                            DummyIconForIconButtonPreview()
                        }
                    }
                }

                Box(
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8))
                        .background(AppTheme.colors.tertiary),
                    contentAlignment = Alignment.Center,
                ) {
                    CompositionLocalProvider(LocalContentColor provides contentColorFor(color = AppTheme.colors.tertiary)) {
                        IconButton(variant = IconButtonVariant.Ghost) {
                            DummyIconForIconButtonPreview()
                        }
                    }
                }

                Box(
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8))
                        .background(AppTheme.colors.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    CompositionLocalProvider(LocalContentColor provides contentColorFor(color = AppTheme.colors.surface)) {
                        IconButton(variant = IconButtonVariant.Ghost) {
                            DummyIconForIconButtonPreview()
                        }
                    }
                }

                Box(
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8))
                        .background(AppTheme.colors.error),
                    contentAlignment = Alignment.Center,
                ) {
                    CompositionLocalProvider(LocalContentColor provides contentColorFor(color = AppTheme.colors.error)) {
                        IconButton(variant = IconButtonVariant.Ghost) {
                            DummyIconForIconButtonPreview()
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
private fun DummyIconForIconButtonPreview() {
    Canvas(modifier = Modifier.size(16.dp)) {
        val center = size / 2f
        val radius = size.minDimension * 0.4f
        val strokeWidth = 4f
        val cap = StrokeCap.Round

        drawLine(
            color = Color.Black,
            start = Offset(center.width - radius, center.height),
            end = Offset(center.width + radius, center.height),
            strokeWidth = strokeWidth,
            cap = cap,
        )

        drawLine(
            color = Color.Black,
            start = Offset(center.width, center.height - radius),
            end = Offset(center.width, center.height + radius),
            strokeWidth = strokeWidth,
            cap = cap,
        )

        val diagonalRadius = radius * 0.75f
        drawLine(
            color = Color.Black,
            start =
                Offset(
                    center.width - diagonalRadius,
                    center.height - diagonalRadius,
                ),
            end =
                Offset(
                    center.width + diagonalRadius,
                    center.height + diagonalRadius,
                ),
            strokeWidth = strokeWidth,
            cap = cap,
        )

        drawLine(
            color = Color.Black,
            start =
                Offset(
                    center.width - diagonalRadius,
                    center.height + diagonalRadius,
                ),
            end =
                Offset(
                    center.width + diagonalRadius,
                    center.height - diagonalRadius,
                ),
            strokeWidth = strokeWidth,
            cap = cap,
        )
    }
}
