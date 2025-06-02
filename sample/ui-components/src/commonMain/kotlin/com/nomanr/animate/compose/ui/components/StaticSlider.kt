package com.nomanr.animate.compose.ui.components

import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.composables.slider.BasicSlider
import com.nomanr.composables.slider.SliderColors
import com.nomanr.composables.slider.SliderState

@Composable
fun StaticSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    @IntRange(from = 0) steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: SliderColors = StaticSliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onlyThumbDraggable: Boolean = true,
) {
    val state = remember(steps, valueRange) {
        SliderState(
            value,
            steps,
            onValueChangeFinished,
            valueRange,
        )
    }

    state.onValueChangeFinished = onValueChangeFinished
    state.onValueChange = onValueChange
    state.value = value

    StaticSlider(
        state = state,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
        onlyThumbDraggable = onlyThumbDraggable,
    )
}

@Composable
fun StaticSlider(
    state: SliderState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SliderColors = StaticSliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onlyThumbDraggable: Boolean = true,
) {
    require(state.steps >= 0) { "steps should be >= 0" }

    BasicSlider(
        state = state,
        modifier = modifier.height(32.dp),
        enabled = enabled,
        colors = colors,
        trackHeight = 32.dp,
        thumbWidth = 12.dp,
        thumbHeight = 28.dp,
        onlyThumbDraggable = onlyThumbDraggable,
        interactionSource = interactionSource,
        thumb = { _ ->
            StaticThumb(
                colors = colors, enabled = enabled
            )
        },
        track = { sliderState ->
            StaticTrack(
                sliderState = sliderState, colors = colors, enabled = enabled
            )
        }
    )
}

@Composable
private fun StaticThumb(
    colors: SliderColors, enabled: Boolean, modifier: Modifier = Modifier
) {
    val thumbColor = if (enabled) colors.thumbColor else colors.disabledThumbColor

    Box(
        modifier = modifier
            .padding(top = 2.dp)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier.width(8.dp).height(28.dp).clip(RoundedCornerShape(6.dp)).background(thumbColor)
        )
    }
}

@Composable
private fun StaticTrack(
    sliderState: SliderState,
    colors: SliderColors,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val inactiveTrackColor =
        if (enabled) colors.inactiveTrackColor else colors.disabledInactiveTrackColor
    val activeTrackColor = if (enabled) colors.activeTrackColor else colors.disabledActiveTrackColor

    val valueRange = sliderState.valueRange
    val activeFraction =
        (sliderState.value - valueRange.start) / (valueRange.endInclusive - valueRange.start)

    Canvas(
        modifier = modifier.fillMaxWidth().height(28.dp)
    ) {
        drawStaticTrack(
            activeFraction, inactiveTrackColor, activeTrackColor
        )
    }
}

private fun DrawScope.drawStaticTrack(
    activeFraction: Float,
    inactiveTrackColor: Color,
    activeTrackColor: Color
) {
    val trackHeight = size.height
    val trackWidth = size.width
    val cornerRadius = 4.dp.toPx()

    drawStaticTrackPath(
        offset = Offset.Zero,
        size = Size(trackWidth, trackHeight),
        color = inactiveTrackColor,
        cornerRadius = cornerRadius
    )

    val activeWidth = activeFraction * trackWidth

    if (activeWidth > 0f) {
        drawStaticTrackPath(
            offset = Offset.Zero,
            size = Size(activeWidth, trackHeight),
            color = activeTrackColor,
            cornerRadius = cornerRadius
        )
    }
}

private fun DrawScope.drawStaticTrackPath(
    offset: Offset, size: Size, color: Color, cornerRadius: Float
) {
    val trackPath = Path()
    val cornerRadiusObj = CornerRadius(cornerRadius, cornerRadius)
    val roundRect = RoundRect(
        rect = Rect(offset, size),
        topLeft = cornerRadiusObj,
        topRight = cornerRadiusObj,
        bottomRight = cornerRadiusObj,
        bottomLeft = cornerRadiusObj
    )
    trackPath.addRoundRect(roundRect)
    drawPath(trackPath, color)
}

object StaticSliderDefaults {
    @Composable
    fun colors() = SliderColors(
        thumbColor = AppTheme.colors.secondary,
        activeTrackColor = AppTheme.colors.transparent,
        activeTickColor = AppTheme.colors.transparent,
        inactiveTrackColor = AppTheme.colors.transparent,
        inactiveTickColor = AppTheme.colors.transparent,
        disabledThumbColor = AppTheme.colors.transparent,
        disabledActiveTrackColor = AppTheme.colors.transparent,
        disabledActiveTickColor = AppTheme.colors.transparent,
        disabledInactiveTrackColor = AppTheme.colors.transparent,
        disabledInactiveTickColor = AppTheme.colors.transparent,
    )
}