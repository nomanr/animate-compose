package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.textfield.OutlinedTextField

@Composable
fun KeyframeProperties(
    state: TimelineState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(AppTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Properties",
            style = AppTheme.typography.h3
        )
        
        // Duration input field
        DurationSection(state = state)

        val selectedKeyframe = state.selectedKeyframeIndex?.let { index ->
            state.keyframes.getOrNull(index)
        }

        if (selectedKeyframe == null) {
            Text(
                text = "No keyframe selected.\nClick on a keyframe in the timeline to edit its properties.",
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary
            )
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                KeyframePropertiesPanel(
                    keyframe = selectedKeyframe,
                    keyframeIndex = state.selectedKeyframeIndex!!,
                    state = state
                )
            }
        }
    }
}

@Composable
private fun KeyframePropertiesPanel(
    keyframe: Keyframe,
    keyframeIndex: Int,
    state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Keyframe Type
        Text(
            text = when (keyframe) {
                is Keyframe.Static -> "Static Keyframe"
                is Keyframe.Segment -> "Segment Keyframe"
            },
            style = AppTheme.typography.h4
        )

        // Timing Properties
        TimingPropertiesSection(
            keyframe = keyframe,
            keyframeIndex = keyframeIndex,
            state = state
        )

        // Transform Properties
        when (keyframe) {
            is Keyframe.Static -> {
                StaticTransformPropertiesSection(
                    keyframe = keyframe,
                    keyframeIndex = keyframeIndex,
                    state = state
                )
            }
            is Keyframe.Segment -> {
                SegmentTransformPropertiesSection(
                    keyframe = keyframe,
                    keyframeIndex = keyframeIndex,
                    state = state
                )
            }
        }
    }
}

@Composable
private fun TimingPropertiesSection(
    keyframe: Keyframe,
    keyframeIndex: Int,
    state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Timing",
            style = AppTheme.typography.h5
        )

        when (keyframe) {
            is Keyframe.Static -> {
                var timeText by remember(keyframe.percent) { 
                    mutableStateOf((keyframe.percent * 1000).toInt().toString()) 
                }

                OutlinedTextField(
                    value = timeText,
                    onValueChange = { newValue ->
                        timeText = newValue
                        newValue.toFloatOrNull()?.let { timeMs ->
                            val timeSeconds = timeMs / 1000f
                            if (timeSeconds >= 0f && timeSeconds <= state.duration) {
                                state.updateKeyframeTime(keyframeIndex, timeSeconds)
                            }
                        }
                    },
                    label = { Text("Time (ms)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is Keyframe.Segment -> {
                var startTimeText by remember(keyframe.start) { 
                    mutableStateOf((keyframe.start * 1000).toInt().toString()) 
                }
                var endTimeText by remember(keyframe.end) { 
                    mutableStateOf((keyframe.end * 1000).toInt().toString()) 
                }

                OutlinedTextField(
                    value = startTimeText,
                    onValueChange = { newValue ->
                        startTimeText = newValue
                        newValue.toFloatOrNull()?.let { timeMs ->
                            val timeSeconds = timeMs / 1000f
                            if (timeSeconds >= 0f && timeSeconds <= state.duration) {
                                state.updateKeyframeTime(keyframeIndex, timeSeconds)
                            }
                        }
                    },
                    label = { Text("Start Time (ms)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = endTimeText,
                    onValueChange = { newValue ->
                        endTimeText = newValue
                        newValue.toFloatOrNull()?.let { timeMs ->
                            val timeSeconds = timeMs / 1000f
                            if (timeSeconds >= keyframe.start && timeSeconds <= state.duration) {
                                state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                                    (oldKeyframe as Keyframe.Segment).copy(end = timeSeconds)
                                }
                            }
                        }
                    },
                    label = { Text("End Time (ms)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun StaticTransformPropertiesSection(
    keyframe: Keyframe.Static,
    keyframeIndex: Int,
    state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Transform",
            style = AppTheme.typography.h5
        )

        TransformPropertyFields(
            transform = keyframe.transform,
            onTransformChange = { newTransform ->
                state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                    (oldKeyframe as Keyframe.Static).copy(transform = newTransform)
                }
            }
        )
    }
}

@Composable
private fun SegmentTransformPropertiesSection(
    keyframe: Keyframe.Segment,
    keyframeIndex: Int,
    state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Transform",
            style = AppTheme.typography.h5
        )

        // From Transform
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "From",
                style = AppTheme.typography.h5
            )

            TransformPropertyFields(
                transform = keyframe.from,
                onTransformChange = { newTransform ->
                    state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                        (oldKeyframe as Keyframe.Segment).copy(from = newTransform)
                    }
                }
            )
        }

        // To Transform
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "To",
                style = AppTheme.typography.h5
            )

            TransformPropertyFields(
                transform = keyframe.to,
                onTransformChange = { newTransform ->
                    state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                        (oldKeyframe as Keyframe.Segment).copy(to = newTransform)
                    }
                }
            )
        }
    }
}

@Composable
private fun TransformPropertyFields(
    transform: TransformProperties,
    onTransformChange: (TransformProperties) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Translation X
        PropertyTextField(
            label = "Translation X",
            value = transform.translationX ?: 0f,
            onValueChange = { newValue ->
                onTransformChange(transform.copy(translationX = newValue))
            }
        )

        // Translation Y
        PropertyTextField(
            label = "Translation Y",
            value = transform.translationY ?: 0f,
            onValueChange = { newValue ->
                onTransformChange(transform.copy(translationY = newValue))
            }
        )

        // Scale X
        PropertyTextField(
            label = "Scale X",
            value = transform.scaleX ?: 1f,
            onValueChange = { newValue ->
                onTransformChange(transform.copy(scaleX = newValue))
            }
        )

        // Scale Y
        PropertyTextField(
            label = "Scale Y",
            value = transform.scaleY ?: 1f,
            onValueChange = { newValue ->
                onTransformChange(transform.copy(scaleY = newValue))
            }
        )

        // Rotation Z
        PropertyTextField(
            label = "Rotation (degrees)",
            value = transform.rotationZ ?: 0f,
            onValueChange = { newValue ->
                onTransformChange(transform.copy(rotationZ = newValue))
            }
        )

        // Alpha
        PropertyTextField(
            label = "Alpha",
            value = transform.alpha ?: 1f,
            onValueChange = { newValue ->
                onTransformChange(transform.copy(alpha = newValue.coerceIn(0f, 1f)))
            }
        )
    }
}

@Composable
private fun PropertyTextField(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var textValue by remember(value) { mutableStateOf(value.toString()) }

    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            textValue = newValue
            newValue.toFloatOrNull()?.let { floatValue ->
                onValueChange(floatValue)
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun DurationSection(
    state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Animation Duration",
            style = AppTheme.typography.h5
        )
        
        var durationText by remember(state.duration) { 
            mutableStateOf((state.duration * 1000).toInt().toString()) 
        }
        
        OutlinedTextField(
            value = durationText,
            onValueChange = { newValue ->
                durationText = newValue
                newValue.toIntOrNull()?.let { durationMs ->
                    if (durationMs > 0) {
                        state.updateDuration(durationMs / 1000f)
                    }
                }
            },
            label = { Text("Duration (ms)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}