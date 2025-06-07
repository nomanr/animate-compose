package com.nomanr.animate.compose.playground.components.configs.keyframeproperties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
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
        is Keyframe.Static -> {
            // Not implemented yet
        }
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
            RotationProperty(keyframe, selectedIndex, state)
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
                    text = propertyName, style = AppTheme.typography.label2, color = AppTheme.colors.text
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
                    text = propertyName, style = AppTheme.typography.label2, color = AppTheme.colors.text
                )
            },
            modifier = Modifier.weight(1f)
        )
    }
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
                    Icon(Icons.Default.Add)
                }
            }
        }, bodyContent = content)

    }

}