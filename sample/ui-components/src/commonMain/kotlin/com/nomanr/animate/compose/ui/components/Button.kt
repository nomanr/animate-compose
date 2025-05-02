package com.nomanr.animate.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.LocalContentColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String? = null,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.Primary,
    onClick: () -> Unit = {},
    contentPadding: PaddingValues = ButtonDefaults.contentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: (@Composable () -> Unit)? = null,
) {
    ButtonComponent(
        text = text,
        modifier = modifier,
        enabled = enabled,
        style = buttonStyleFor(variant),
        onClick = onClick,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
internal fun ButtonComponent(
    text: String? = null,
    modifier: Modifier,
    enabled: Boolean = true,
    style: ButtonStyle,
    onClick: () -> Unit,
    contentPadding: PaddingValues = ButtonDefaults.contentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: (@Composable () -> Unit)? = null,
) {
    val containerColor = style.colors.containerColor(enabled).value
    val contentColor = style.colors.contentColor(enabled).value

    Surface(
        onClick = onClick,
        modifier =
            modifier
                .defaultMinSize(minHeight = ButtonDefaults.MinHeight)
                .semantics { role = Role.Button },
        enabled = enabled,
        shape = style.shape,
        color = containerColor,
        contentColor = contentColor,
        interactionSource = interactionSource,
    ) {
        DefaultButtonContent(
            text = text,
            contentColor = contentColor,
            content = content,
            modifier = Modifier.padding(contentPadding),
        )
    }
}

@Composable
private fun DefaultButtonContent(
    modifier: Modifier = Modifier,
    text: String? = null,
    contentColor: Color,
    content: (@Composable () -> Unit)? = null,
) {
    if (text?.isEmpty() == false) {
        Row(
            modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = AnnotatedString(text = text),
                textAlign = TextAlign.Center,
                style = AppTheme.typography.button,
                overflow = TextOverflow.Clip,
                color = contentColor,
            )
        }
    } else if (content != null) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

enum class ButtonVariant {
    Primary,
    Secondary,
    Destructive,
    Ghost,
}

@Composable
internal fun buttonStyleFor(variant: ButtonVariant): ButtonStyle {
    return when (variant) {
        ButtonVariant.Primary -> ButtonDefaults.primaryFilled()
        ButtonVariant.Secondary -> ButtonDefaults.secondaryFilled()
        ButtonVariant.Destructive -> ButtonDefaults.destructiveFilled()
        ButtonVariant.Ghost -> ButtonDefaults.ghost()
    }
}

internal object ButtonDefaults {
    internal val MinHeight = 44.dp
    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 8.dp
    private val ButtonShape = RoundedCornerShape(12)

    val contentPadding =
        PaddingValues(
            start = ButtonHorizontalPadding,
            top = ButtonVerticalPadding,
            end = ButtonHorizontalPadding,
            bottom = ButtonVerticalPadding,
        )

    private val filledShape = ButtonShape

    @Composable
    fun primaryFilled() =
        ButtonStyle(
            colors =
                ButtonColors(
                    containerColor = AppTheme.colors.primary,
                    contentColor = AppTheme.colors.onPrimary,
                    disabledContainerColor = AppTheme.colors.disabled,
                    disabledContentColor = AppTheme.colors.onDisabled,
                ),
            shape = filledShape,
            contentPadding = contentPadding,
        )


    @Composable
    fun secondaryFilled() =
        ButtonStyle(
            colors =
                ButtonColors(
                    containerColor = AppTheme.colors.secondary,
                    contentColor = AppTheme.colors.onSecondary,
                    disabledContainerColor = AppTheme.colors.disabled,
                    disabledContentColor = AppTheme.colors.onDisabled,
                ),
            shape = filledShape,
            contentPadding = contentPadding,
        )

    @Composable
    fun destructiveFilled() =
        ButtonStyle(
            colors =
                ButtonColors(
                    containerColor = AppTheme.colors.error,
                    contentColor = AppTheme.colors.onError,
                    disabledContainerColor = AppTheme.colors.disabled,
                    disabledContentColor = AppTheme.colors.onDisabled,
                ),
            shape = filledShape,
            contentPadding = contentPadding,
        )

    @Composable
    fun ghost() =
        ButtonStyle(
            colors =
                ButtonColors(
                    containerColor = AppTheme.colors.transparent,
                    contentColor = LocalContentColor.current,
                    disabledContainerColor = AppTheme.colors.transparent,
                    disabledContentColor = AppTheme.colors.onDisabled,
                    disabledBorderColor = AppTheme.colors.transparent,
                ),
            shape = filledShape,
            contentPadding = contentPadding,
        )
}

@Immutable
internal data class ButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val disabledBorderColor: Color? = null,
) {
    @Composable
    internal fun containerColor(enabled: Boolean) =
        rememberUpdatedState(newValue = if (enabled) containerColor else disabledContainerColor)

    @Composable
    internal fun contentColor(enabled: Boolean) =
        rememberUpdatedState(newValue = if (enabled) contentColor else disabledContentColor)
}

@Immutable
internal data class ButtonStyle(
    val colors: ButtonColors,
    val shape: Shape,
    val contentPadding: PaddingValues,
)

@Composable
@Preview
fun ButtonPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        PrimaryButtonPreview()
        SecondaryButtonPreview()
        DestructiveButtonPreview()

    }
}

@Composable
@Preview
fun PrimaryButtonPreview() {
    AppTheme {
        Column(
            modifier =
                Modifier
                    .background(AppTheme.colors.background)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "Primary Buttons", style = AppTheme.typography.h2)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(text = "PrimaryFilled", variant = ButtonVariant.Primary, onClick = {})

                Button(
                    text = "Disabled",
                    variant = ButtonVariant.Primary,
                    enabled = false,
                )
            }

        }
    }
}

@Composable
@Preview
fun SecondaryButtonPreview() {
    AppTheme {
        Column(
            modifier =
                Modifier
                    .background(AppTheme.colors.background)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "Secondary Buttons", style = AppTheme.typography.h2)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(text = "SecondaryFilled", variant = ButtonVariant.Secondary, onClick = {})

                Button(
                    text = "Disabled",
                    variant = ButtonVariant.Secondary,
                    enabled = false,
                )
            }

        }
    }
}

@Composable
@Preview
fun DestructiveButtonPreview() {
    AppTheme {
        Column(
            modifier =
                Modifier
                    .background(AppTheme.colors.background)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "Destructive Buttons", style = AppTheme.typography.h2)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(text = "DestructiveFilled", variant = ButtonVariant.Destructive, onClick = {})

                Button(
                    text = "Disabled",
                    variant = ButtonVariant.Destructive,
                    enabled = false,
                )
            }

        }
    }
}
