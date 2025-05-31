package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.Surface
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun TimelinePropertyEditor(
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (selectedNode != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Node Properties",
                        style = AppTheme.typography.h4
                    )

                    Button(
                        variant = ButtonVariant.Destructive,
                        onClick = {
                            state.removeNode(selectedNode.id)
                        }
                    ) {
                        Text("Delete")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Time position
                Text("Time: ${(selectedNode.time * 100).toInt() / 100.0}")

                // Node type info
                when (val type = selectedNode.type) {
                    is TimelineNodeType.Static -> {
                        Text("Type: Static Keyframe")
                    }

                    is TimelineNodeType.Segment -> {
                        Text("Type: Segment (${(selectedNode.time * 100).toInt() / 100.0} → ${(type.endTime * 100).toInt() / 100.0})")
                        Text("Duration: ${((type.endTime - selectedNode.time) * 100).toInt() / 100.0}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Transform properties for "from" state
                Text("From Properties:", style = AppTheme.typography.h5)
                TransformPropertySliders(
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
                    TransformPropertySliders(
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

            } else {
                Text(
                    text = "Select a node to edit its properties",
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.textSecondary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Click on timeline to add new nodes\n• Drag nodes to move them\n• Drag segment edges to resize",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.textSecondary
                )
            }
        }
    }
}

@Composable
private fun TransformPropertySliders(
    properties: TransformProperties,
    onPropertiesChanged: (TransformProperties) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PropertySlider(
            label = "Translation Y",
            value = properties.translationY ?: 0f,
            range = -300f..300f,
            onValueChange = { value ->
                onPropertiesChanged(properties.copy(translationY = value))
            }
        )

        PropertySlider(
            label = "Translation X",
            value = properties.translationX ?: 0f,
            range = -300f..300f,
            onValueChange = { value ->
                onPropertiesChanged(properties.copy(translationX = value))
            }
        )

        PropertySlider(
            label = "Scale X",
            value = properties.scaleX ?: 1f,
            range = 0f..3f,
            onValueChange = { value ->
                onPropertiesChanged(properties.copy(scaleX = value))
            }
        )

        PropertySlider(
            label = "Scale Y",
            value = properties.scaleY ?: 1f,
            range = 0f..3f,
            onValueChange = { value ->
                onPropertiesChanged(properties.copy(scaleY = value))
            }
        )

        PropertySlider(
            label = "Rotation Z",
            value = properties.rotationZ ?: 0f,
            range = -360f..360f,
            onValueChange = { value ->
                onPropertiesChanged(properties.copy(rotationZ = value))
            }
        )

        PropertySlider(
            label = "Alpha",
            value = properties.alpha ?: 1f,
            range = 0f..1f,
            onValueChange = { value ->
                onPropertiesChanged(properties.copy(alpha = value))
            }
        )
    }
}

@Composable
private fun PropertySlider(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Column {
        Text("$label: ${(value * 100).toInt() / 100f}")
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range
        )
    }
}