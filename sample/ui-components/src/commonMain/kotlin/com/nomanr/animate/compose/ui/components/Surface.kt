package com.nomanr.animate.compose.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.LocalContentColor
import com.nomanr.animate.compose.ui.contentColorFor
import com.nomanr.animate.compose.ui.foundation.ripple
import com.nomanr.animate.compose.ui.hardShadowColorFor


@Composable
@NonRestartableComposable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colors.surface,
    hardShadow: Boolean = false, contentColor: Color = contentColorFor(color),
    border: Boolean = false,
    content: @Composable () -> Unit,
) {
    BaseSurface(
        modifier = modifier,
        shape = shape,
        color = color,
        hardShadow = hardShadow,
        border = border,
        contentColor = contentColor,
        content = content
    )
}

@Composable
@NonRestartableComposable
fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colors.background,
    hardShadow: Boolean = false,
    border: Boolean = false,
    contentColor: Color = contentColorFor(color),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    BaseSurface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        interactionSource = interactionSource,
        hardShadow = hardShadow,
        border = border,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@Composable
@NonRestartableComposable
fun Surface(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hardShadow: Boolean = false,
    border: Boolean = false,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colors.background,
    contentColor: Color = contentColorFor(color),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    BaseSurface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        interactionSource = interactionSource,
        hardShadow = hardShadow,
        border = border,
        selected = selected,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@Composable
@NonRestartableComposable
fun Surface(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hardShadow: Boolean = false,
    border: Boolean = false,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colors.background,
    contentColor: Color = contentColorFor(color),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    BaseSurface(
        modifier = modifier,
        shape = shape,
        color = color,
        enabled = enabled,
        contentColor = contentColor,
        interactionSource = interactionSource,
        hardShadow = hardShadow,
        border = border,
        checked = checked,
        onCheckedChange = onCheckedChange,
        content = content
    )
}

@Composable
@NonRestartableComposable
private fun BaseSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colors.surface,
    contentColor: Color = contentColorFor(color),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    hardShadow: Boolean = false,
    border: Boolean = false,
    enabled: Boolean = true,
    selected: Boolean? = null,
    checked: Boolean? = null,
    onClick: (() -> Unit)? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        HardShadowContainer(
            modifier = Modifier,
            shape = shape,
            interactionSource = interactionSource,
            color = hardShadowColorFor(color),
            hardShadow = hardShadow,
        ) {
            Box(
                modifier = modifier.surface(
                    shape = shape,
                    backgroundColor = color,
                    border = border,
                ).then(
                    when {
                        !enabled -> Modifier
                        onCheckedChange != null && checked != null -> Modifier.toggleable(
                            value = checked,
                            interactionSource = interactionSource,
                            indication = ripple(),
                            onValueChange = onCheckedChange,
                        )

                        onClick != null && selected != null -> Modifier.selectable(
                            selected = selected,
                            interactionSource = interactionSource,
                            indication = ripple(),
                            onClick = onClick,
                        )

                        onClick != null -> Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = ripple(),
                            onClick = onClick,
                        )

                        else -> Modifier.pointerInput(Unit) {}
                    }),
                propagateMinConstraints = true,
            ) {
                content()
            }
        }
    }
}

@Composable
private fun HardShadowContainer(
    modifier: Modifier,
    interactionSource: InteractionSource,
    shape: Shape,
    color: Color,
    hardShadow: Boolean,
    content: @Composable () -> Unit,
) {

    if (!hardShadow) {
        content()
        return
    }

    val isPressed by interactionSource.collectIsPressedAsState()

    val offset by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 4.dp, label = "SurfaceOffsetAnimation"
    )
    Box(
        modifier = modifier.background(
            color = color,
            shape = shape,
        ).offset { IntOffset(-offset.roundToPx(), -offset.roundToPx()) }) {
        content()
    }
}

@Composable
private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: Boolean,
) = this.background(shape = shape, color = backgroundColor)
    .then(
        if (border) Modifier.border(
            BorderStroke(
                BorderWidth,
                color = hardShadowColorFor(backgroundColor)
            ), shape = shape
        ) else Modifier
    )
    .clip(shape)

private val BorderWidth = 3.dp