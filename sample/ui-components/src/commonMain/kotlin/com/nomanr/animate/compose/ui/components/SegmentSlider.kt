package com.nomanr.animate.compose.ui.components

import androidx.annotation.IntRange
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    colors: SegmentSliderColors = SegmentSliderDefaults.colors(),
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
    state.onValueChange = { onValueChange(it.start..it.endInclusive) }
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
    colors: SegmentSliderColors = SegmentSliderDefaults.colors(),
    startInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    endInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    require(state.steps >= 0) { "steps should be >= 0" }

    BasicRangeSlider(
        modifier = modifier.height(32.dp),
        state = state,
        enabled = enabled,
        startInteractionSource = startInteractionSource,
        endInteractionSource = endInteractionSource,
        colors = colors.toSliderColors(),
        onlyThumbDraggable = true
    )
}

data class SegmentSliderColors(
    val segmentColor: Color,
    val handleColor: Color,
    val trackColor: Color,
    val disabledSegmentColor: Color,
    val disabledHandleColor: Color,
    val disabledTrackColor: Color,
) {
    fun toSliderColors(): SliderColors {
        return SliderColors(
            // Thumbs become handles
            thumbColor = handleColor,
            // Active track becomes segment
            activeTrackColor = segmentColor,
            activeTickColor = Color.Transparent,
            // Inactive track becomes timeline track
            inactiveTrackColor = trackColor,
            inactiveTickColor = Color.Transparent,
            // Disabled states
            disabledThumbColor = disabledHandleColor,
            disabledActiveTrackColor = disabledSegmentColor,
            disabledActiveTickColor = Color.Transparent,
            disabledInactiveTrackColor = disabledTrackColor,
            disabledInactiveTickColor = Color.Transparent,
        )
    }
}

object SegmentSliderDefaults {
    @Composable
    fun colors(
        segmentColor: Color = AppTheme.colors.primary,
        handleColor: Color = AppTheme.colors.onPrimary,
        trackColor: Color = AppTheme.colors.outline.copy(alpha = 0.3f),
        disabledSegmentColor: Color = AppTheme.colors.disabled,
        disabledHandleColor: Color = AppTheme.colors.onDisabled,
        disabledTrackColor: Color = AppTheme.colors.disabled.copy(alpha = 0.3f),
    ) = SegmentSliderColors(
        segmentColor = segmentColor,
        handleColor = handleColor,
        trackColor = trackColor,
        disabledSegmentColor = disabledSegmentColor,
        disabledHandleColor = disabledHandleColor,
        disabledTrackColor = disabledTrackColor,
    )
}