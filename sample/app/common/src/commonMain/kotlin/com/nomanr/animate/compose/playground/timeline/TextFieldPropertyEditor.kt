package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.Surface
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.textfield.TextField

@Composable
fun TextFieldPropertyEditor(
    state: TimelineState,
    modifier: Modifier = Modifier
) {
    val selectedNode = state.selectedNodeId?.let { nodeId ->
        state.nodes.find { it.id == nodeId }
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = AppTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (selectedNode != null) {
                Text(
                    "Property Editor",
                    style = AppTheme.typography.h4
                )

                // Node info
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Node: ${selectedNode.id}")
                    Text(
                        "Type: ${
                            when (selectedNode.type) {
                                is TimelineNodeType.Static -> "Static Keyframe"
                                is TimelineNodeType.Segment -> "Animation Segment"
                            }
                        }"
                    )
                    Text("Time: ${(selectedNode.time * 100).toInt() / 100.0}")

                    if (selectedNode.type is TimelineNodeType.Segment) {
                        Text("Duration: ${((selectedNode.type.endTime - selectedNode.time) * 100).toInt() / 100.0}")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Transform properties for "from" state
                Text("From Properties:", style = AppTheme.typography.h5)
                TransformPropertyFields(
                    properties = selectedNode.transformProperties,
                    onPropertiesChanged = { newProperties ->
                        state.updateNode(selectedNode.id) { node ->
                            node.copy(transformProperties = newProperties)
                        }
                    }
                )

                // Transform properties for "to" state (segments only)
                if (selectedNode.type is TimelineNodeType.Segment) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("To Properties:", style = AppTheme.typography.h5)
                    TransformPropertyFields(
                        properties = selectedNode.type.toTransformProperties,
                        onPropertiesChanged = { newProperties ->
                            state.updateNode(selectedNode.id) { node ->
                                val segmentType = node.type as TimelineNodeType.Segment
                                node.copy(
                                    type = segmentType.copy(toTransformProperties = newProperties)
                                )
                            }
                        }
                    )
                }

                // Quick preset buttons
                Spacer(modifier = Modifier.height(16.dp))
                Text("Quick Presets:", style = AppTheme.typography.h4)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        variant = ButtonVariant.Secondary,
                        onClick = {
                            val fadeProps = TransformProperties(alpha = 0f)
                            state.updateNode(selectedNode.id) { node ->
                                node.copy(transformProperties = fadeProps)
                            }
                        }
                    ) {
                        Text("Fade Out")
                    }

                    Button(
                        variant = ButtonVariant.Secondary,
                        onClick = {
                            val scaleProps = TransformProperties(scaleX = 1.5f, scaleY = 1.5f)
                            state.updateNode(selectedNode.id) { node ->
                                node.copy(transformProperties = scaleProps)
                            }
                        }
                    ) {
                        Text("Scale Up")
                    }
                }

            } else {
                Text(
                    text = "Select a keyframe to edit its properties",
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.textSecondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Click 'Add Keyframe' to create new keyframes\n• Click on keyframe headers to select them\n• Drag sliders to adjust timing\n• Use text fields for precise property values",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary
                )
            }
        }
    }
}

@Composable
private fun TransformPropertyFields(
    properties: TransformProperties,
    onPropertiesChanged: (TransformProperties) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Translation
        Text("Translation", style = AppTheme.typography.h4)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PropertyTextField(
                label = "X",
                value = properties.translationX ?: 0f,
                onValueChange = { value ->
                    onPropertiesChanged(properties.copy(translationX = value))
                },
                suffix = "px",
                modifier = Modifier.weight(1f)
            )
            PropertyTextField(
                label = "Y",
                value = properties.translationY ?: 0f,
                onValueChange = { value ->
                    onPropertiesChanged(properties.copy(translationY = value))
                },
                suffix = "px",
                modifier = Modifier.weight(1f)
            )
        }

        // Scale
        Text("Scale", style = AppTheme.typography.h4)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PropertyTextField(
                label = "X",
                value = properties.scaleX ?: 1f,
                onValueChange = { value ->
                    onPropertiesChanged(properties.copy(scaleX = value))
                },
                modifier = Modifier.weight(1f)
            )
            PropertyTextField(
                label = "Y",
                value = properties.scaleY ?: 1f,
                onValueChange = { value ->
                    onPropertiesChanged(properties.copy(scaleY = value))
                },
                modifier = Modifier.weight(1f)
            )
        }

        // Rotation
        Text("Rotation", style = AppTheme.typography.h4)
        PropertyTextField(
            label = "Z-axis",
            value = properties.rotationZ ?: 0f,
            onValueChange = { value ->
                onPropertiesChanged(properties.copy(rotationZ = value))
            },
            suffix = "°",
            modifier = Modifier.fillMaxWidth()
        )

        // Alpha
        Text("Opacity", style = AppTheme.typography.h4)
        PropertyTextField(
            label = "Alpha",
            value = properties.alpha ?: 1f,
            onValueChange = { value ->
                onPropertiesChanged(properties.copy(alpha = value.coerceIn(0f, 1f)))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PropertyTextField(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    suffix: String = ""
) {
    var textValue by remember(value) { mutableStateOf(value.toString()) }
    var isError by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        TextField(
            value = textValue,
            onValueChange = { newText ->
                textValue = newText
                newText.toFloatOrNull()?.let { floatValue ->
                    onValueChange(floatValue)
                    isError = false
                } ?: run {
                    isError = newText.isNotEmpty()
                }
            },
            label = { Text("$label${if (suffix.isNotEmpty()) " ($suffix)" else ""}") },
            singleLine = true,
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            supportingText = if (isError) {
                { Text("Invalid number", color = AppTheme.colors.error) }
            } else null
        )
    }
}