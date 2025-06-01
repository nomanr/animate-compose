package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.SegmentSlider
import com.nomanr.animate.compose.ui.components.Slider
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun Timeline(
    state: TimelineState, modifier: Modifier = Modifier, onNodeSelected: ((String?) -> Unit)? = null
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    variant = ButtonVariant.Primary, onClick = {
                        val time = (state.currentTime + 0.5f).coerceAtMost(state.duration)
                        state.addStaticKeyframe(time)
                    }) {
                    Text("Add Static")
                }

                Button(
                    variant = ButtonVariant.Secondary, onClick = {
                        val startTime =
                            (state.currentTime + 0.5f).coerceAtMost(state.duration - 0.5f)
                        val endTime = (startTime + 0.5f).coerceAtMost(state.duration)
                        state.addSegmentKeyframe(startTime, endTime)
                    }) {
                    Text("Add Segment")
                }
            }

            Button(
                variant = ButtonVariant.Primary, onClick = {
                    state.play()
                }) {
                Text("Play")
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                .border(1.dp, AppTheme.colors.outline, RoundedCornerShape(8.dp))
                .background(AppTheme.colors.background).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (state.keyframes.isEmpty()) {
                Text(
                    text = "No keyframes. Use the buttons below to add keyframes.",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            } else {
                state.keyframes.forEachIndexed { index, keyframe ->
                    KeyframeSlider(
                        keyframe = keyframe,
                        keyframeIndex = index,
                        state = state,
                        isSelected = index == state.selectedKeyframeIndex,
                        onSelected = {
                            state.selectKeyframe(index)
                            onNodeSelected?.invoke(index.toString())
                        })
                }
            }
        }


    }
}

@Composable
private fun KeyframeSlider(
    keyframe: Keyframe,
    keyframeIndex: Int,
    state: TimelineState,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        when (keyframe) {
            is Keyframe.Static -> {
                Slider(
                    value = keyframe.percent / state.duration, onValueChange = { normalizedValue ->
                        val newTime = normalizedValue * state.duration
                        state.updateKeyframeTime(keyframeIndex, newTime)
                        onSelected()
                    }, valueRange = 0f..1f, modifier = Modifier.fillMaxWidth()
                )
            }

            is Keyframe.Segment -> {
                SegmentSlider(
                    value = (keyframe.start / state.duration)..(keyframe.end / state.duration),
                    onValueChange = { normalizedRange ->
                        val newStart = normalizedRange.start * state.duration
                        val newEnd = normalizedRange.endInclusive * state.duration
                        state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                            (oldKeyframe as Keyframe.Segment).copy(
                                start = newStart, end = newEnd
                            )
                        }
                        onSelected()
                    },
                    valueRange = 0f..1f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}