package com.nomanr.animate.compose.playground.components.keyframeproperties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.playground.PlaygroundState
import com.nomanr.animate.compose.playground.updateKeyframeTime
import com.nomanr.animate.compose.playground.updateKeyframe
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.textfield.OutlinedTextField

@Composable
fun KeyframeTiming(state: PlaygroundState) {
    val selectedIndex = state.selectedKeyframeIndex ?: return
    val keyframe = state.keyframes.getOrNull(selectedIndex) ?: return

    when (keyframe) {
        is Keyframe.Static -> StaticTiming(keyframe, selectedIndex, state)
        is Keyframe.Segment -> SegmentTiming(keyframe, selectedIndex, state)
    }
}

@Composable
private fun StaticTiming(
    keyframe: Keyframe.Static,
    selectedIndex: Int,
    state: PlaygroundState
) {
    var textValue by remember(keyframe.percent) {
        mutableStateOf(keyframe.percent.toString())
    }

    KeyframePropertiesSection(title = "Keyframe Position") {
        OutlinedTextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                newValue.toFloatOrNull()?.let { value ->
                    if (value in 0f..1f) {
                        state.updateKeyframeTime(selectedIndex, value)
                    }
                }
            },
            textStyle = AppTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = {
                Text(
                    text = "Position",
                    style = AppTheme.typography.label2,
                    color = AppTheme.colors.text
                )
            })
    }
}

@Composable
private fun SegmentTiming(
    keyframe: Keyframe.Segment,
    selectedIndex: Int,
    state: PlaygroundState
) {
    var startTextValue by remember(keyframe.start) {
        mutableStateOf(keyframe.start.toString())
    }
    
    var endTextValue by remember(keyframe.end) {
        mutableStateOf(keyframe.end.toString())
    }

    KeyframePropertiesSection(title = "Keyframe Position") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = startTextValue,
                onValueChange = { newValue ->
                    startTextValue = newValue
                    newValue.toFloatOrNull()?.let { value ->
                        if (value in 0f..keyframe.end && value <= 1f) {
                            state.updateKeyframe(selectedIndex) { old ->
                                (old as Keyframe.Segment).copy(start = value)
                            }
                        }
                    }
                },
                textStyle = AppTheme.typography.body2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                label = {
                    Text(
                        text = "Start",
                        style = AppTheme.typography.label2,
                        color = AppTheme.colors.text
                    )
                },
                modifier = Modifier.weight(1f)
            )
            
            OutlinedTextField(
                value = endTextValue,
                onValueChange = { newValue ->
                    endTextValue = newValue
                    newValue.toFloatOrNull()?.let { value ->
                        if (value >= keyframe.start && value <= 1f) {
                            state.updateKeyframe(selectedIndex) { old ->
                                (old as Keyframe.Segment).copy(end = value)
                            }
                        }
                    }
                },
                textStyle = AppTheme.typography.body2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                label = {
                    Text(
                        text = "End",
                        style = AppTheme.typography.label2,
                        color = AppTheme.colors.text
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}