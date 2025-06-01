package com.nomanr.animate.compose.ui.components

import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.nomanr.composables.slider.BasicRangeSlider
import com.nomanr.composables.slider.RangeSliderState
import com.nomanr.composables.slider.SliderColors

@Composable
fun SegmentSlider(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    @IntRange(from = 0) steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: SliderColors = SegmentSliderDefaults.colors(),
    startInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    endInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val state = remember(steps, valueRange) {
        RangeSliderState(
            value.start,
            value.endInclusive,
            steps,
            onValueChangeFinished,
            valueRange,
        )
    }

    state.onValueChangeFinished = onValueChangeFinished
    state.onValueChange = { range -> onValueChange(range.start..range.endInclusive) }
    state.activeRangeStart = value.start
    state.activeRangeEnd = value.endInclusive

    SegmentSlider(
        state = state,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        startInteractionSource = startInteractionSource,
        endInteractionSource = endInteractionSource,
    )
}

@Composable
fun SegmentSlider(
    state: RangeSliderState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SliderColors = SegmentSliderDefaults.colors(),
    startInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    endInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    require(state.steps >= 0) { "steps should be >= 0" }

    BasicRangeSlider(
        state = state,
        modifier = modifier.height(32.dp),
        enabled = enabled,
        colors = colors,
        trackHeight = 32.dp,
        onlyThumbDraggable = true,
        startInteractionSource = startInteractionSource,
        endInteractionSource = endInteractionSource,
        track = { rangeSliderState ->
            SegmentTrack(
                rangeSliderState = rangeSliderState,
                colors = colors,
                enabled = enabled
            )
        }
    )
}

@Composable
private fun SegmentTrack(
    rangeSliderState: RangeSliderState,
    colors: SliderColors,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val inactiveTrackColor = if (enabled) colors.inactiveTrackColor else colors.disabledInactiveTrackColor
    val activeTrackColor = if (enabled) colors.activeTrackColor else colors.disabledActiveTrackColor
    
    // Calculate fractions manually since internal properties aren't accessible
    val valueRange = rangeSliderState.valueRange
    val activeRangeStartFraction = (rangeSliderState.activeRangeStart - valueRange.start) / (valueRange.endInclusive - valueRange.start)
    val activeRangeEndFraction = (rangeSliderState.activeRangeEnd - valueRange.start) / (valueRange.endInclusive - valueRange.start)
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
    ) {
        drawSegmentTrack(
            activeRangeStartFraction,
            activeRangeEndFraction,
            inactiveTrackColor,
            activeTrackColor
        )
    }
}

private fun DrawScope.drawSegmentTrack(
    activeRangeStart: Float,
    activeRangeEnd: Float,
    inactiveTrackColor: Color,
    activeTrackColor: Color
) {
    val trackHeight = size.height
    val trackWidth = size.width
    val cornerRadius = trackHeight / 2f

    // Draw full background track
    drawTrackPath(
        offset = Offset.Zero,
        size = Size(trackWidth, trackHeight),
        color = inactiveTrackColor,
        cornerRadius = cornerRadius
    )

    // Draw active segment
    val activeStart = activeRangeStart * trackWidth
    val activeWidth = (activeRangeEnd - activeRangeStart) * trackWidth
    
    if (activeWidth > 0f) {
        drawTrackPath(
            offset = Offset(activeStart, 0f),
            size = Size(activeWidth, trackHeight),
            color = activeTrackColor,
            cornerRadius = cornerRadius
        )
    }
}

private fun DrawScope.drawTrackPath(
    offset: Offset,
    size: Size,
    color: Color,
    cornerRadius: Float
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

object SegmentSliderDefaults {
    @Composable
    fun colors() = SliderColors(
        thumbColor = AppTheme.colors.primary,
        activeTrackColor = AppTheme.colors.primary,
        activeTickColor = AppTheme.colors.transparent,
        inactiveTrackColor = AppTheme.colors.tertiary,
        inactiveTickColor = AppTheme.colors.transparent,
        disabledThumbColor = AppTheme.colors.transparent,
        disabledActiveTrackColor = AppTheme.colors.transparent,
        disabledActiveTickColor = AppTheme.colors.transparent,
        disabledInactiveTrackColor = AppTheme.colors.transparent,
        disabledInactiveTickColor = AppTheme.colors.transparent,
    )
}