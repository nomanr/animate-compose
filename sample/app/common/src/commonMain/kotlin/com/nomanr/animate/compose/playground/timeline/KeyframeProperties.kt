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
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import com.nomanr.animate.compose.core.Easings
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import com.nomanr.animate.compose.ui.components.textfield.UnderlinedTextField

@Composable
fun KeyframeProperties(
    state: TimelineState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(AppTheme.colors.surface)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header with inline duration
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Properties",
                style = AppTheme.typography.h4,
                color = AppTheme.colors.text,
                modifier = Modifier.weight(1f)
            )
            DurationField(state = state)
        }

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
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
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
    keyframe: Keyframe,
    keyframeIndex: Int,
    state: TimelineState
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
            style = AppTheme.typography.body1,
            color = AppTheme.colors.textSecondary
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Timing",
            style = AppTheme.typography.body1,
            color = AppTheme.colors.text
        )
        
        // Easing selector
        EasingSelector(
            easing = keyframe.easing,
            onEasingChange = { newEasing ->
                state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                    when (oldKeyframe) {
                        is Keyframe.Static -> oldKeyframe.copy(easing = newEasing)
                        is Keyframe.Segment -> oldKeyframe.copy(easing = newEasing)
                    }
                }
            }
        )

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
                        suffix = { Text("ms", style = AppTheme.typography.body2, color = AppTheme.colors.textSecondary) },
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Start",
                        style = AppTheme.typography.body2,
                        color = AppTheme.colors.textSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    UnderlinedTextField(
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
                        suffix = { Text("ms", style = AppTheme.typography.body2, color = AppTheme.colors.textSecondary) },
                        textStyle = AppTheme.typography.body2,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.width(80.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "End",
                        style = AppTheme.typography.body2,
                        color = AppTheme.colors.textSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    UnderlinedTextField(
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
                        suffix = { Text("ms", style = AppTheme.typography.body2, color = AppTheme.colors.textSecondary) },
                        textStyle = AppTheme.typography.body2,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.width(80.dp)
                    )
                }
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Transform",
            style = AppTheme.typography.body1,
            color = AppTheme.colors.text
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
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Transform",
            style = AppTheme.typography.body1,
            color = AppTheme.colors.text
        )

        // From and To side by side
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // From Transform
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "From",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary
                )

                CompactTransformFields(
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
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "To",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary
                )

                CompactTransformFields(
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
}

@Composable
private fun TransformPropertyFields(
    transform: TransformProperties,
    onTransformChange: (TransformProperties) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Translation section
        PropertySection(title = "Translation") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PropertyTextField(
                    label = "X",
                    value = transform.translationX ?: 0f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(translationX = newValue))
                    },
                    suffix = "px",
                    modifier = Modifier.weight(1f)
                )
                PropertyTextField(
                    label = "Y",
                    value = transform.translationY ?: 0f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(translationY = newValue))
                    },
                    suffix = "px",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Scale section
        PropertySection(title = "Scale") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PropertyTextField(
                    label = "X",
                    value = transform.scaleX ?: 1f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(scaleX = newValue))
                    },
                    modifier = Modifier.weight(1f)
                )
                PropertyTextField(
                    label = "Y",
                    value = transform.scaleY ?: 1f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(scaleY = newValue))
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Rotation section
        PropertySection(title = "Rotation") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PropertyTextField(
                        label = "X",
                        value = transform.rotationX ?: 0f,
                        onValueChange = { newValue ->
                            onTransformChange(transform.copy(rotationX = newValue))
                        },
                        suffix = "°",
                        modifier = Modifier.weight(1f)
                    )
                    PropertyTextField(
                        label = "Y",
                        value = transform.rotationY ?: 0f,
                        onValueChange = { newValue ->
                            onTransformChange(transform.copy(rotationY = newValue))
                        },
                        suffix = "°",
                        modifier = Modifier.weight(1f)
                    )
                }
                PropertyTextField(
                    label = "Z",
                    value = transform.rotationZ ?: 0f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(rotationZ = newValue))
                    },
                    suffix = "°"
                )
            }
        }

        // Skew section
        PropertySection(title = "Skew") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PropertyTextField(
                    label = "X",
                    value = transform.skewX ?: 0f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(skewX = newValue))
                    },
                    suffix = "°",
                    modifier = Modifier.weight(1f)
                )
                PropertyTextField(
                    label = "Y",
                    value = transform.skewY ?: 0f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(skewY = newValue))
                    },
                    suffix = "°",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Appearance section
        PropertySection(title = "Appearance") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PropertyTextField(
                    label = "Opacity",
                    value = transform.alpha ?: 1f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(alpha = newValue.coerceIn(0f, 1f)))
                    }
                )
                PropertyTextField(
                    label = "Camera Distance",
                    value = transform.cameraDistance ?: 1250f,
                    onValueChange = { newValue ->
                        onTransformChange(transform.copy(cameraDistance = newValue))
                    }
                )
            }
        }
    }
}

@Composable
private fun PropertyTextField(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    suffix: String? = null,
    compact: Boolean = false
) {
    var textValue by remember(value) { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(if (compact) 8.dp else 16.dp))
        UnderlinedTextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                newValue.toFloatOrNull()?.let { floatValue ->
                    onValueChange(floatValue)
                }
            },
            suffix = suffix?.let { { Text(it, style = AppTheme.typography.body2, color = AppTheme.colors.textSecondary) } },
            textStyle = AppTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.width(if (compact) 60.dp else 80.dp)
        )
    }
}

@Composable
private fun CompactTransformFields(
    transform: TransformProperties,
    onTransformChange: (TransformProperties) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Translation
        CompactPropertyRow(
            label1 = "X",
            value1 = transform.translationX ?: 0f,
            onValue1Change = { onTransformChange(transform.copy(translationX = it)) },
            suffix1 = "px",
            label2 = "Y",
            value2 = transform.translationY ?: 0f,
            onValue2Change = { onTransformChange(transform.copy(translationY = it)) },
            suffix2 = "px"
        )

        // Scale
        CompactPropertyRow(
            label1 = "Scale X",
            value1 = transform.scaleX ?: 1f,
            onValue1Change = { onTransformChange(transform.copy(scaleX = it)) },
            label2 = "Y",
            value2 = transform.scaleY ?: 1f,
            onValue2Change = { onTransformChange(transform.copy(scaleY = it)) }
        )

        // Rotation
        PropertyTextField(
            label = "Rotation",
            value = transform.rotationZ ?: 0f,
            onValueChange = { onTransformChange(transform.copy(rotationZ = it)) },
            suffix = "°",
            compact = true
        )

        // Opacity
        PropertyTextField(
            label = "Opacity",
            value = transform.alpha ?: 1f,
            onValueChange = { onTransformChange(transform.copy(alpha = it.coerceIn(0f, 1f))) },
            compact = true
        )
    }
}

@Composable
private fun PropertySection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary.copy(alpha = 0.8f)
        )
        content()
    }
}

@Composable
private fun CompactPropertyRow(
    label1: String,
    value1: Float,
    onValue1Change: (Float) -> Unit,
    suffix1: String? = null,
    label2: String,
    value2: Float,
    onValue2Change: (Float) -> Unit,
    suffix2: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PropertyTextField(
            label = label1,
            value = value1,
            onValueChange = onValue1Change,
            suffix = suffix1,
            compact = true,
            modifier = Modifier.weight(1f)
        )
        PropertyTextField(
            label = label2,
            value = value2,
            onValueChange = onValue2Change,
            suffix = suffix2,
            compact = true,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun EasingSelector(
    easing: Easing?,
    onEasingChange: (Easing?) -> Unit
) {
    val easingOptions = listOf(
        "Linear" to LinearEasing,
        "Ease In Out" to Easings.EaseInOut,
        "Ease Out" to Easings.EaseOut,
        "Fast Out Slow In" to Easings.FastOutSlowInEasing,
        "Smooth Comfort" to Easings.SmoothComfort,
        "Quick Rise" to Easings.QuickRise,
        "Balanced Ease" to Easings.BalancedEase,
        "Soft Landing" to Easings.SoftLanding
    )
    
    val currentEasing = easingOptions.find { it.second == easing } ?: easingOptions[0]
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Easing",
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary,
            modifier = Modifier.weight(1f)
        )
        // For now, display as text. Can be enhanced with a dropdown later
        Text(
            text = currentEasing.first,
            style = AppTheme.typography.body2,
            color = AppTheme.colors.text
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Duration",
            style = AppTheme.typography.body2,
            color = AppTheme.colors.textSecondary
        )
        Spacer(modifier = Modifier.width(8.dp))
        UnderlinedTextField(
            value = durationText,
            onValueChange = { newValue ->
                durationText = newValue
                newValue.toIntOrNull()?.let { durationMs ->
                    if (durationMs > 0) {
                        state.updateDuration(durationMs / 1000f)
                    }
                }
            },
            suffix = { Text("ms", style = AppTheme.typography.body2, color = AppTheme.colors.textSecondary) },
            textStyle = AppTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.width(80.dp)
        )
    }
}