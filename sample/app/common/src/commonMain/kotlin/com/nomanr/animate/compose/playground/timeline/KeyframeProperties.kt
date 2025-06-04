package com.nomanr.animate.compose.playground.timeline

import androidx.compose.animation.core.Easing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.textfield.OutlinedTextField
import com.nomanr.animate.compose.ui.components.textfield.UnderlinedTextField

@Composable
fun KeyframeProperties(
    state: TimelineState, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight().background(AppTheme.colors.surface).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Properties", style = AppTheme.typography.h3, color = AppTheme.colors.text
        )

        DurationField(state = state)


        val selectedKeyframe = state.selectedKeyframeIndex?.let { index ->
            state.keyframes.getOrNull(index)
        }

        if (selectedKeyframe == null) {
            Text(
                text = "No keyframe selected.\nClick on a keyframe in the timeline to edit its properties.",
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary.copy(alpha = 0.6f)
            )
        } else {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
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
    keyframe: Keyframe, keyframeIndex: Int, state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Keyframe Type
        Text(
            text = when (keyframe) {
                is Keyframe.Static -> "Static Keyframe"
                is Keyframe.Segment -> "Segment Keyframe"
            },
            style = AppTheme.typography.h4,
        )

        TimingPropertiesSection(
            keyframe = keyframe, keyframeIndex = keyframeIndex, state = state
        )

        // Transform Properties
        when (keyframe) {
            is Keyframe.Static -> {
                StaticTransformPropertiesSection(
                    keyframe = keyframe, keyframeIndex = keyframeIndex, state = state
                )
            }

            is Keyframe.Segment -> {
                SegmentTransformPropertiesSection(
                    keyframe = keyframe, keyframeIndex = keyframeIndex, state = state
                )
            }
        }
    }
}

@Composable
private fun TimingPropertiesSection(
    keyframe: Keyframe, keyframeIndex: Int, state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        when (keyframe) {
            is Keyframe.Static -> {
                var timeText by remember(keyframe.percent) {
                    mutableStateOf((keyframe.percent * 1000).toInt().toString())
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Time",
                        style = AppTheme.typography.body2,
                        color = AppTheme.colors.textSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    UnderlinedTextField(
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
                        suffix = {
                            Text(
                                "ms",
                                style = AppTheme.typography.body2,
                                color = AppTheme.colors.textSecondary
                            )
                        },
                        textStyle = AppTheme.typography.body2,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.width(80.dp)
                    )
                }
            }

            is Keyframe.Segment -> {
                var startTimeText by remember(keyframe.start) {
                    mutableStateOf((keyframe.start * 1000).toInt().toString())
                }
                var endTimeText by remember(keyframe.end) {
                    mutableStateOf((keyframe.end * 1000).toInt().toString())
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {


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
                        suffix = {
                            Text(
                                "ms",
                                style = AppTheme.typography.body2,
                                color = AppTheme.colors.textSecondary
                            )
                        },
                        textStyle = AppTheme.typography.body2,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        label = {
                            Text(
                                text = "Start",
                                style = AppTheme.typography.label1,
                                color = AppTheme.colors.text,
                                modifier = Modifier.weight(1f)
                            )
                        })
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {

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
                        suffix = {
                            Text(
                                "ms",
                                style = AppTheme.typography.body2,
                                color = AppTheme.colors.textSecondary
                            )
                        },
                        textStyle = AppTheme.typography.body2,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        label = {
                            Text(
                                text = "End",
                                color = AppTheme.colors.text,
                                style = AppTheme.typography.label1,
                                modifier = Modifier.weight(1f)
                            )
                        })
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Easing selector
        EasingSelector(
            easing = keyframe.easing, onEasingChange = { newEasing ->
                state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                    when (oldKeyframe) {
                        is Keyframe.Static -> oldKeyframe.copy(easing = newEasing)
                        is Keyframe.Segment -> oldKeyframe.copy(easing = newEasing)
                    }
                }
            })


    }
}

@Composable
private fun StaticTransformPropertiesSection(
    keyframe: Keyframe.Static, keyframeIndex: Int, state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Transform",
            style = AppTheme.typography.h5,
        )

        TransformPropertyFields(
            transform = keyframe.transform, onTransformChange = { newTransform ->
                state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                    (oldKeyframe as Keyframe.Static).copy(transform = newTransform)
                }
            })
    }
}

@Composable
private fun SegmentTransformPropertiesSection(
    keyframe: Keyframe.Segment, keyframeIndex: Int, state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Transform",
            style = AppTheme.typography.h5,
        )

        // From and To side by side
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // From Transform
            Column(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "From",
                    style = AppTheme.typography.h5,
                )

                CompactTransformFields(
                    transform = keyframe.from, onTransformChange = { newTransform ->
                        state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                            (oldKeyframe as Keyframe.Segment).copy(from = newTransform)
                        }
                    })
            }

            // To Transform
            Column(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "To",
                    style = AppTheme.typography.h5,
                )

                CompactTransformFields(
                    transform = keyframe.to, onTransformChange = { newTransform ->
                        state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                            (oldKeyframe as Keyframe.Segment).copy(to = newTransform)
                        }
                    })
            }
        }
    }
}

@Composable
private fun TransformPropertyFields(
    transform: TransformProperties, onTransformChange: (TransformProperties) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Translation X & Y
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransformTextField(
                label = "Translation X",
                value = transform.translationX ?: 0f,
                onValueChange = { onTransformChange(transform.copy(translationX = it)) },
                suffix = "px",
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Translation Y",
                value = transform.translationY ?: 0f,
                onValueChange = { onTransformChange(transform.copy(translationY = it)) },
                suffix = "px",
                modifier = Modifier.weight(1f)
            )
        }

        // Scale X & Y
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransformTextField(
                label = "Scale X",
                value = transform.scaleX ?: 1f,
                onValueChange = { onTransformChange(transform.copy(scaleX = it)) },
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Scale Y",
                value = transform.scaleY ?: 1f,
                onValueChange = { onTransformChange(transform.copy(scaleY = it)) },
                modifier = Modifier.weight(1f)
            )
        }

        // Rotation X & Y
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransformTextField(
                label = "Rotation X",
                value = transform.rotationX ?: 0f,
                onValueChange = { onTransformChange(transform.copy(rotationX = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Rotation Y",
                value = transform.rotationY ?: 0f,
                onValueChange = { onTransformChange(transform.copy(rotationY = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
        }

        // Rotation Z & Skew X
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransformTextField(
                label = "Rotation Z",
                value = transform.rotationZ ?: 0f,
                onValueChange = { onTransformChange(transform.copy(rotationZ = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Skew X",
                value = transform.skewX ?: 0f,
                onValueChange = { onTransformChange(transform.copy(skewX = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
        }

        // Skew Y & Alpha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransformTextField(
                label = "Skew Y",
                value = transform.skewY ?: 0f,
                onValueChange = { onTransformChange(transform.copy(skewY = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Alpha",
                value = transform.alpha ?: 1f,
                onValueChange = { onTransformChange(transform.copy(alpha = it.coerceIn(0f, 1f))) },
                modifier = Modifier.weight(1f)
            )
        }

        // Camera Distance (single field)
        TransformTextField(
            label = "Camera Distance",
            value = transform.cameraDistance ?: 1250f,
            onValueChange = { onTransformChange(transform.copy(cameraDistance = it)) },
            modifier = Modifier.fillMaxWidth(0.5f)
        )
    }
}

@Composable
private fun TransformTextField(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    suffix: String? = null
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
        suffix = suffix?.let {
            {
                Text(
                    it,
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.text
                )
            }
        },
        textStyle = AppTheme.typography.body2,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier,
        label = {
            Text(
                text = label,
                style = AppTheme.typography.label2,
                color = AppTheme.colors.text
            )
        }
    )
}

@Composable
private fun CompactTransformFields(
    transform: TransformProperties, onTransformChange: (TransformProperties) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Translation X & Y
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransformTextField(
                label = "Translation X",
                value = transform.translationX ?: 0f,
                onValueChange = { onTransformChange(transform.copy(translationX = it)) },
                suffix = "px",
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Translation Y",
                value = transform.translationY ?: 0f,
                onValueChange = { onTransformChange(transform.copy(translationY = it)) },
                suffix = "px",
                modifier = Modifier.weight(1f)
            )
        }

        // Scale X & Y
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransformTextField(
                label = "Scale X",
                value = transform.scaleX ?: 1f,
                onValueChange = { onTransformChange(transform.copy(scaleX = it)) },
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Scale Y",
                value = transform.scaleY ?: 1f,
                onValueChange = { onTransformChange(transform.copy(scaleY = it)) },
                modifier = Modifier.weight(1f)
            )
        }

        // Rotation X & Y
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransformTextField(
                label = "Rotation X",
                value = transform.rotationX ?: 0f,
                onValueChange = { onTransformChange(transform.copy(rotationX = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Rotation Y",
                value = transform.rotationY ?: 0f,
                onValueChange = { onTransformChange(transform.copy(rotationY = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
        }

        // Rotation Z & Skew X
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransformTextField(
                label = "Rotation Z",
                value = transform.rotationZ ?: 0f,
                onValueChange = { onTransformChange(transform.copy(rotationZ = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Skew X",
                value = transform.skewX ?: 0f,
                onValueChange = { onTransformChange(transform.copy(skewX = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
        }

        // Skew Y & Alpha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransformTextField(
                label = "Skew Y",
                value = transform.skewY ?: 0f,
                onValueChange = { onTransformChange(transform.copy(skewY = it)) },
                suffix = "°",
                modifier = Modifier.weight(1f)
            )
            TransformTextField(
                label = "Alpha",
                value = transform.alpha ?: 1f,
                onValueChange = { onTransformChange(transform.copy(alpha = it.coerceIn(0f, 1f))) },
                modifier = Modifier.weight(1f)
            )
        }

        // Camera Distance (single field)
        TransformTextField(
            label = "Camera Distance",
            value = transform.cameraDistance ?: 1250f,
            onValueChange = { onTransformChange(transform.copy(cameraDistance = it)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
private fun EasingSelector(
    easing: Easing?, onEasingChange: (Easing?) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Easing",
            style = AppTheme.typography.h5,
        )

        var a by remember { mutableStateOf(0.25f) }
        var b by remember { mutableStateOf(0.1f) }
        var c by remember { mutableStateOf(0.25f) }
        var d by remember { mutableStateOf(1.0f) }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EasingParameterField(
                    label = "A",
                    value = a,
                    onValueChange = { a = it },
                    modifier = Modifier.weight(1f)
                )
                EasingParameterField(
                    label = "B",
                    value = b,
                    onValueChange = { b = it },
                    modifier = Modifier.weight(1f)
                )

                EasingParameterField(
                    label = "C",
                    value = c,
                    onValueChange = { c = it },
                    modifier = Modifier.weight(1f)
                )
                EasingParameterField(
                    label = "D",
                    value = d,
                    onValueChange = { d = it },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun EasingParameterField(
    label: String, value: Float, onValueChange: (Float) -> Unit, modifier: Modifier = Modifier
) {
    var textValue by remember(value) { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {


        OutlinedTextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                newValue.toFloatOrNull()?.let { floatValue ->
                    onValueChange(floatValue.coerceIn(0f, 1f))
                }
            },
            textStyle = AppTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.weight(1f),
            label = {
                Text(
                    text = label, style = AppTheme.typography.label2, color = AppTheme.colors.text
                )
            }

        )
    }
}

@Composable
private fun DurationField(
    state: TimelineState
) {
    var durationText by remember(state.duration) {
        mutableStateOf((state.duration * 1000).toInt().toString())
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = durationText,
            onValueChange = { newValue ->
                durationText = newValue
                newValue.toIntOrNull()?.let { durationMs ->
                    if (durationMs > 0) {
                        state.updateDuration(durationMs / 1000f)
                    }
                }
            },

            suffix = {
                Text(
                    "ms", style = AppTheme.typography.body2, color = AppTheme.colors.text
                )
            },
            textStyle = AppTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Duration",
                    style = AppTheme.typography.label1,
                    color = AppTheme.colors.text
                )
            })
    }
}