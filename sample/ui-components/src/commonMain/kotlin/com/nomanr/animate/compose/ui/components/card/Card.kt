package com.nomanr.animate.compose.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Surface


@Composable
fun Card(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.Shape,
    colors: CardColors = CardDefaults.cardColors(),
    hardShadow: Boolean = true,
    border: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = colors.containerColor(enabled).value,
        contentColor = colors.contentColor(enabled).value,
        border = border,
        hardShadow = hardShadow,
    ) {
        Column(content = content)
    }
}

@Composable
fun OutlinedCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.OutlinedShape,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    hardShadow: Boolean = true,
    border: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) = Card(
    modifier = modifier,
    shape = shape,
    border = border,
    hardShadow = hardShadow,
    colors = colors,
    content = content,
)

object CardDefaults {
    val Shape: Shape @Composable get() = RectangleShape
    val OutlinedShape: Shape @Composable get() = Shape
    private val BorderWidth = 1.dp

    @Composable
    fun cardElevation(
        defaultElevation: Dp = 0.0.dp,
        pressedElevation: Dp = 0.0.dp,
        focusedElevation: Dp = 0.0.dp,
        hoveredElevation: Dp = 1.0.dp,
        draggedElevation: Dp = 3.0.dp,
        disabledElevation: Dp = 0.0.dp,
    ): CardElevation = CardElevation(
        defaultElevation = defaultElevation,
        pressedElevation = pressedElevation,
        focusedElevation = focusedElevation,
        hoveredElevation = hoveredElevation,
        draggedElevation = draggedElevation,
        disabledElevation = disabledElevation,
    )

    @Composable
    fun elevatedCardElevation(
        defaultElevation: Dp = 2.0.dp,
        pressedElevation: Dp = 4.0.dp,
        focusedElevation: Dp = 4.0.dp,
        hoveredElevation: Dp = 4.0.dp,
        draggedElevation: Dp = 4.0.dp,
        disabledElevation: Dp = 0.0.dp,
    ): CardElevation = CardElevation(
        defaultElevation = defaultElevation,
        pressedElevation = pressedElevation,
        focusedElevation = focusedElevation,
        hoveredElevation = hoveredElevation,
        draggedElevation = draggedElevation,
        disabledElevation = disabledElevation,
    )

    @Composable
    fun outlinedCardElevation(
        defaultElevation: Dp = 0.0.dp,
        pressedElevation: Dp = 0.0.dp,
        focusedElevation: Dp = 0.0.dp,
        hoveredElevation: Dp = 1.0.dp,
        draggedElevation: Dp = 3.0.dp,
        disabledElevation: Dp = 0.0.dp,
    ): CardElevation = CardElevation(
        defaultElevation = defaultElevation,
        pressedElevation = pressedElevation,
        focusedElevation = focusedElevation,
        hoveredElevation = hoveredElevation,
        draggedElevation = draggedElevation,
        disabledElevation = disabledElevation,
    )

    @Composable
    fun cardColors(
        containerColor: Color = AppTheme.colors.surface,
        contentColor: Color = AppTheme.colors.onSurface,
        disabledContainerColor: Color = AppTheme.colors.disabled,
        disabledContentColor: Color = AppTheme.colors.onDisabled,
    ): CardColors = CardColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun elevatedCardColors(
        containerColor: Color = AppTheme.colors.background,
        contentColor: Color = AppTheme.colors.onBackground,
        disabledContainerColor: Color = AppTheme.colors.disabled,
        disabledContentColor: Color = AppTheme.colors.onDisabled,
    ): CardColors = CardColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedCardColors(
        containerColor: Color = AppTheme.colors.background,
        contentColor: Color = AppTheme.colors.onBackground,
        disabledContainerColor: Color = AppTheme.colors.disabled,
        disabledContentColor: Color = AppTheme.colors.onDisabled,
    ): CardColors = CardColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedCardBorder(enabled: Boolean = true): BorderStroke {
        val color = if (enabled) {
            AppTheme.colors.outline
        } else {
            AppTheme.colors.disabled
        }
        return remember(color) { BorderStroke(BorderWidth, color) }
    }
}

@ConsistentCopyVisibility
@Immutable
data class CardColors internal constructor(
    private val containerColor: Color,
    private val contentColor: Color,
    private val disabledContainerColor: Color,
    private val disabledContentColor: Color,
) {
    @Composable
    internal fun containerColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) containerColor else disabledContainerColor)
    }

    @Composable
    internal fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }
}
