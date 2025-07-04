package com.nomanr.animate.compose.playground.components.configs.keyframeproperties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.nomanr.animate.compose.playground.PlaygroundState
import com.nomanr.animate.compose.playground.components.configs.ConfigurationSection
import com.nomanr.animate.compose.playground.updateKeyframe
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Accordion
import com.nomanr.animate.compose.ui.components.Icon
import com.nomanr.animate.compose.ui.components.IconButton
import com.nomanr.animate.compose.ui.components.IconButtonVariant
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.rememberAccordionState
import com.nomanr.animate.compose.ui.components.textfield.OutlinedTextField

@Composable
fun KeyframeProperties(state: PlaygroundState) {
    val selectedIndex = state.selectedKeyframeIndex ?: return
    val keyframe = state.keyframes.getOrNull(selectedIndex) ?: return

    when (keyframe) {
        is Keyframe.Segment -> SegmentProperties(keyframe, selectedIndex, state)
        is Keyframe.Static -> StaticProperties(keyframe, selectedIndex, state)
    }
}

@Composable
private fun SegmentProperties(
    keyframe: Keyframe.Segment, selectedIndex: Int, state: PlaygroundState
) {
    ConfigurationSection(title = "Transformation Properties") {

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            TranslationProperty(keyframe, selectedIndex, state)
            ScaleProperty(keyframe, selectedIndex, state)
            AlphaProperty(keyframe, selectedIndex, state)
            RotationProperty(keyframe, selectedIndex, state)
            SkewProperty(keyframe, selectedIndex, state)
            CameraDistanceProperty(keyframe, selectedIndex, state)
            EasingSection(keyframe, selectedIndex, state)
        }

    }
}

@Composable
private fun StaticProperties(
    keyframe: Keyframe.Static, selectedIndex: Int, state: PlaygroundState
) {
    ConfigurationSection(title = "Transformation Properties") {

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            StaticTranslationProperty(keyframe, selectedIndex, state)
            StaticScaleProperty(keyframe, selectedIndex, state)
            StaticAlphaProperty(keyframe, selectedIndex, state)
            StaticRotationProperty(keyframe, selectedIndex, state)
            StaticSkewProperty(keyframe, selectedIndex, state)
            StaticCameraDistanceProperty(keyframe, selectedIndex, state)
            StaticEasingSection(keyframe, selectedIndex, state)
        }

    }
}

@Composable
private fun TranslationProperty(
    keyframe: Keyframe.Segment, selectedIndex: Int, state: PlaygroundState
) {
    TransformPropertySection(
        title = "Translation",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "X",
                getValue = { it.translationX },
                updateValue = { transform, value -> transform.copy(translationX = value) }
            ),
            TransformPropertyConfig(
                name = "Y",
                getValue = { it.translationY },
                updateValue = { transform, value -> transform.copy(translationY = value) }
            )
        )
    )
}

@Composable
private fun ScaleProperty(
    keyframe: Keyframe.Segment, selectedIndex: Int, state: PlaygroundState
) {
    TransformPropertySection(
        title = "Scale",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "X",
                getValue = { it.scaleX },
                updateValue = { transform, value -> transform.copy(scaleX = value) }
            ),
            TransformPropertyConfig(
                name = "Y",
                getValue = { it.scaleY },
                updateValue = { transform, value -> transform.copy(scaleY = value) }
            )
        )
    )
}

@Composable
private fun AlphaProperty(
    keyframe: Keyframe.Segment, selectedIndex: Int, state: PlaygroundState
) {
    TransformPropertySection(
        title = "Alpha",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "Alpha",
                getValue = { it.alpha },
                updateValue = { transform, value -> transform.copy(alpha = value) }
            )
        )
    )
}

@Composable
private fun RotationProperty(
    keyframe: Keyframe.Segment, selectedIndex: Int, state: PlaygroundState
) {
    TransformPropertySection(
        title = "Rotation",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "X",
                getValue = { it.rotationX },
                updateValue = { transform, value -> transform.copy(rotationX = value) }
            ),
            TransformPropertyConfig(
                name = "Y",
                getValue = { it.rotationY },
                updateValue = { transform, value -> transform.copy(rotationY = value) }
            ),
            TransformPropertyConfig(
                name = "Z",
                getValue = { it.rotationZ },
                updateValue = { transform, value -> transform.copy(rotationZ = value) }
            )
        )
    )
}

@Composable
private fun SkewProperty(
    keyframe: Keyframe.Segment, selectedIndex: Int, state: PlaygroundState
) {
    TransformPropertySection(
        title = "Skew",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "X",
                getValue = { it.skewX },
                updateValue = { transform, value -> transform.copy(skewX = value) }
            ),
            TransformPropertyConfig(
                name = "Y",
                getValue = { it.skewY },
                updateValue = { transform, value -> transform.copy(skewY = value) }
            )
        )
    )
}

@Composable
private fun CameraDistanceProperty(
    keyframe: Keyframe.Segment, selectedIndex: Int, state: PlaygroundState
) {
    TransformPropertySection(
        title = "Camera Distance",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "Distance",
                getValue = { it.cameraDistance },
                updateValue = { transform, value -> transform.copy(cameraDistance = value) }
            )
        )
    )
}

// Static keyframe property composables

@Composable
private fun StaticTranslationProperty(
    keyframe: Keyframe.Static, selectedIndex: Int, state: PlaygroundState
) {
    StaticTransformPropertySection(
        title = "Translation",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "X",
                getValue = { it.translationX },
                updateValue = { transform, value -> transform.copy(translationX = value) }
            ),
            TransformPropertyConfig(
                name = "Y",
                getValue = { it.translationY },
                updateValue = { transform, value -> transform.copy(translationY = value) }
            )
        )
    )
}

@Composable
private fun StaticScaleProperty(
    keyframe: Keyframe.Static, selectedIndex: Int, state: PlaygroundState
) {
    StaticTransformPropertySection(
        title = "Scale",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "X",
                getValue = { it.scaleX },
                updateValue = { transform, value -> transform.copy(scaleX = value) }
            ),
            TransformPropertyConfig(
                name = "Y",
                getValue = { it.scaleY },
                updateValue = { transform, value -> transform.copy(scaleY = value) }
            )
        )
    )
}

@Composable
private fun StaticAlphaProperty(
    keyframe: Keyframe.Static, selectedIndex: Int, state: PlaygroundState
) {
    StaticTransformPropertySection(
        title = "Alpha",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "Alpha",
                getValue = { it.alpha },
                updateValue = { transform, value -> transform.copy(alpha = value) }
            )
        )
    )
}

@Composable
private fun StaticRotationProperty(
    keyframe: Keyframe.Static, selectedIndex: Int, state: PlaygroundState
) {
    StaticTransformPropertySection(
        title = "Rotation",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "X",
                getValue = { it.rotationX },
                updateValue = { transform, value -> transform.copy(rotationX = value) }
            ),
            TransformPropertyConfig(
                name = "Y",
                getValue = { it.rotationY },
                updateValue = { transform, value -> transform.copy(rotationY = value) }
            ),
            TransformPropertyConfig(
                name = "Z",
                getValue = { it.rotationZ },
                updateValue = { transform, value -> transform.copy(rotationZ = value) }
            )
        )
    )
}

@Composable
private fun StaticSkewProperty(
    keyframe: Keyframe.Static, selectedIndex: Int, state: PlaygroundState
) {
    StaticTransformPropertySection(
        title = "Skew",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "X",
                getValue = { it.skewX },
                updateValue = { transform, value -> transform.copy(skewX = value) }
            ),
            TransformPropertyConfig(
                name = "Y",
                getValue = { it.skewY },
                updateValue = { transform, value -> transform.copy(skewY = value) }
            )
        )
    )
}

@Composable
private fun StaticCameraDistanceProperty(
    keyframe: Keyframe.Static, selectedIndex: Int, state: PlaygroundState
) {
    StaticTransformPropertySection(
        title = "Camera Distance",
        keyframe = keyframe,
        selectedIndex = selectedIndex,
        state = state,
        properties = listOf(
            TransformPropertyConfig(
                name = "Distance",
                getValue = { it.cameraDistance },
                updateValue = { transform, value -> transform.copy(cameraDistance = value) }
            )
        )
    )
}

data class TransformPropertyConfig(
    val name: String,
    val getValue: (TransformProperties) -> Float?,
    val updateValue: (TransformProperties, Float?) -> TransformProperties
)

@Composable
private fun TransformPropertySection(
    title: String,
    keyframe: Keyframe.Segment,
    selectedIndex: Int,
    state: PlaygroundState,
    properties: List<TransformPropertyConfig>
) {
    PropertiesSection(title = title) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            properties.forEach { property ->
                TransformPropertyRow(
                    keyframe = keyframe,
                    selectedIndex = selectedIndex,
                    state = state,
                    propertyName = property.name,
                    getFromValue = property.getValue,
                    getToValue = property.getValue,
                    updateFromValue = property.updateValue,
                    updateToValue = property.updateValue
                )
            }
        }
    }
}

@Composable
private fun TransformPropertyRow(
    keyframe: Keyframe.Segment,
    selectedIndex: Int,
    state: PlaygroundState,
    propertyName: String,
    getFromValue: (TransformProperties) -> Float?,
    getToValue: (TransformProperties) -> Float?,
    updateFromValue: (TransformProperties, Float?) -> TransformProperties,
    updateToValue: (TransformProperties, Float?) -> TransformProperties
) {
    var fromTextValue by remember(getFromValue(keyframe.from)) {
        mutableStateOf(getFromValue(keyframe.from)?.let {
            ((it * 1000).toInt() / 1000.0).toString()
        } ?: "")
    }

    var toTextValue by remember(getToValue(keyframe.to)) {
        mutableStateOf(getToValue(keyframe.to)?.let {
            ((it * 1000).toInt() / 1000.0).toString()
        } ?: "")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = fromTextValue,
            onValueChange = { newValue ->
                fromTextValue = newValue
                val floatValue = if (newValue.isEmpty()) null else newValue.toFloatOrNull()
                state.updateKeyframe(selectedIndex) { old ->
                    (old as Keyframe.Segment).copy(
                        from = updateFromValue(old.from, floatValue)
                    )
                }
            },
            textStyle = AppTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = {
                Text(
                    text = propertyName,
                    style = AppTheme.typography.label2,
                    color = AppTheme.colors.text
                )
            },
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            modifier = Modifier.padding(horizontal = 12.dp).padding(top = 20.dp),
        )

        OutlinedTextField(
            value = toTextValue,
            onValueChange = { newValue ->
                toTextValue = newValue
                val floatValue = if (newValue.isEmpty()) null else newValue.toFloatOrNull()
                state.updateKeyframe(selectedIndex) { old ->
                    (old as Keyframe.Segment).copy(
                        to = updateToValue(old.to, floatValue)
                    )
                }
            },
            textStyle = AppTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = {
                Text(
                    text = propertyName,
                    style = AppTheme.typography.label2,
                    color = AppTheme.colors.text
                )
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun EasingSection(
    keyframe: Keyframe.Segment, selectedIndex: Int, state: PlaygroundState
) {
    PropertiesSection(title = "Easing") {
        var a by remember { mutableStateOf(0.25f) }
        var b by remember { mutableStateOf(0.1f) }
        var c by remember { mutableStateOf(0.25f) }
        var d by remember { mutableStateOf(1.0f) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PropertyField(
                label = "A",
                value = a,
                onValueChange = { a = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "B",
                value = b,
                onValueChange = { b = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "C",
                value = c,
                onValueChange = { c = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "D",
                value = d,
                onValueChange = { d = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StaticEasingSection(
    keyframe: Keyframe.Static, selectedIndex: Int, state: PlaygroundState
) {
    PropertiesSection(title = "Easing") {
        var a by remember { mutableStateOf(0.25f) }
        var b by remember { mutableStateOf(0.1f) }
        var c by remember { mutableStateOf(0.25f) }
        var d by remember { mutableStateOf(1.0f) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PropertyField(
                label = "A",
                value = a,
                onValueChange = { a = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "B",
                value = b,
                onValueChange = { b = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "C",
                value = c,
                onValueChange = { c = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "D",
                value = d,
                onValueChange = { d = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PropertyField(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    suffix: String? = null,
    range: ClosedFloatingPointRange<Float>? = null
) {
    var textValue by remember(value) { mutableStateOf(value.toString()) }

    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            textValue = newValue
            newValue.toFloatOrNull()?.let { floatValue ->
                val finalValue = range?.let { floatValue.coerceIn(it) } ?: floatValue
                onValueChange(finalValue)
            }
        },
        suffix = suffix?.let {
            {
                Text(
                    it, style = AppTheme.typography.body2, color = AppTheme.colors.text
                )
            }
        },
        textStyle = AppTheme.typography.body2,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier,
        label = {
            Text(
                text = label, style = AppTheme.typography.label2, color = AppTheme.colors.text
            )
        })
}

@Composable
private fun StaticTransformPropertySection(
    title: String,
    keyframe: Keyframe.Static,
    selectedIndex: Int,
    state: PlaygroundState,
    properties: List<TransformPropertyConfig>
) {
    PropertiesSection(title = title) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            properties.forEach { property ->
                StaticTransformPropertyField(
                    keyframe = keyframe,
                    selectedIndex = selectedIndex,
                    state = state,
                    propertyName = property.name,
                    getValue = property.getValue,
                    updateValue = property.updateValue
                )
            }
        }
    }
}

@Composable
private fun StaticTransformPropertyField(
    keyframe: Keyframe.Static,
    selectedIndex: Int,
    state: PlaygroundState,
    propertyName: String,
    getValue: (TransformProperties) -> Float?,
    updateValue: (TransformProperties, Float?) -> TransformProperties
) {
    var textValue by remember(getValue(keyframe.transform)) {
        mutableStateOf(getValue(keyframe.transform)?.let {
            ((it * 1000).toInt() / 1000.0).toString()
        } ?: "")
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            textValue = newValue
            val floatValue = if (newValue.isEmpty()) null else newValue.toFloatOrNull()
            state.updateKeyframe(selectedIndex) { old ->
                (old as Keyframe.Static).copy(
                    transform = updateValue(old.transform, floatValue)
                )
            }
        },
        textStyle = AppTheme.typography.body2,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        label = {
            Text(
                text = propertyName,
                style = AppTheme.typography.label2,
                color = AppTheme.colors.text
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PropertiesSection(title: String, content: @Composable () -> Unit) {
    val state = rememberAccordionState()
    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Accordion(state = state, headerContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, style = AppTheme.typography.h4)
                IconButton(
                    variant = IconButtonVariant.Ghost, onClick = {
                        state.toggle()
                    }) {
                    Icon(if (state.expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown)
                }
            }
        }, bodyContent = content)

    }

}