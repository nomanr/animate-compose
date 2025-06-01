package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.HorizontalDivider
import com.nomanr.animate.compose.ui.components.IconButton
import com.nomanr.animate.compose.ui.components.Slider
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun UpdateKeyframe(
    state: TimelineState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(AppTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (state.nodes.isEmpty()) {
            Text(
                text = "No keyframes yet.\nClick on the timeline or 'Add' to create one.",
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
                state.nodes.sortedBy { it.time }.forEach { node ->
                    KeyframeItem(
                        node = node,
                        isSelected = node.id == state.selectedNodeId,
                        onSelect = {
                            state.selectNode(node.id)
                        },
                        onDelete = {
                            state.removeNode(node.id)
                        },
                        onTimeChange = { newTime ->
                            state.updateNodeTime(node.id, newTime)
                        },
                        onTransformChange = { newTransform ->
                            state.updateNodeTransform(node.id, newTransform)
                        },
                        duration = state.duration
                    )
                }
            }
        }
    }
}

@Composable
private fun KeyframeItem(
    node: TimelineNode,
    isSelected: Boolean,
    onSelect: (() -> Unit)? = null,
    onDelete: () -> Unit,
    onTimeChange: (Float) -> Unit,
    onTransformChange: (com.nomanr.animate.compose.core.TransformProperties) -> Unit,
    duration: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) AppTheme.colors.primary else AppTheme.colors.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .background(if (isSelected) AppTheme.colors.primary.copy(alpha = 0.05f) else AppTheme.colors.background)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (node.type) {
                    is TimelineNodeType.Static -> "Keyframe #${node.id.take(4)}"
                    is TimelineNodeType.Segment -> "Segment #${node.id.take(4)}"
                },
                style = AppTheme.typography.h4
            )

            IconButton(
                onClick = onDelete
            ) {
                Text("✕")
            }
        }

        // Time control
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Time:",
                style = AppTheme.typography.body2,
                modifier = Modifier.width(40.dp)
            )

            Slider(
                value = node.time,
                onValueChange = { onTimeChange(it) },
                valueRange = 0f..duration,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${(node.time * 1000).toInt()}ms",
                style = AppTheme.typography.body2,
                modifier = Modifier.width(50.dp)
            )
        }

        // Segment end time (if applicable)
        if (node.type is TimelineNodeType.Segment) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "End:",
                    style = AppTheme.typography.body2,
                    modifier = Modifier.width(40.dp)
                )

                Slider(
                    value = node.type.endTime,
                    onValueChange = { _ ->
                        // TODO: Need to implement segment end time updates in TimelineState
                    },
                    valueRange = node.time..duration,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "${(node.type.endTime * 1000).toInt()}ms",
                    style = AppTheme.typography.body2,
                    modifier = Modifier.width(50.dp)
                )
            }
        }

        HorizontalDivider()

        // Transform properties
        TransformControls(
            transform = node.transformProperties,
            onTransformChange = onTransformChange
        )
    }
}

@Composable
private fun TransformControls(
    transform: com.nomanr.animate.compose.core.TransformProperties,
    onTransformChange: (com.nomanr.animate.compose.core.TransformProperties) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Transform",
            style = AppTheme.typography.body1
        )

        // Translation X
        PropertySlider(
            label = "X",
            value = transform.translationX ?: 0f,
            onValueChange = {
                onTransformChange(transform.copy(translationX = it))
            },
            valueRange = -500f..500f
        )

        // Translation Y
        PropertySlider(
            label = "Y",
            value = transform.translationY ?: 0f,
            onValueChange = {
                onTransformChange(transform.copy(translationY = it))
            },
            valueRange = -500f..500f
        )

        // Scale X
        PropertySlider(
            label = "Scale X",
            value = transform.scaleX ?: 1f,
            onValueChange = {
                onTransformChange(transform.copy(scaleX = it))
            },
            valueRange = 0f..3f
        )

        // Scale Y
        PropertySlider(
            label = "Scale Y",
            value = transform.scaleY ?: 1f,
            onValueChange = {
                onTransformChange(transform.copy(scaleY = it))
            },
            valueRange = 0f..3f
        )

        // Rotation
        PropertySlider(
            label = "Rotation",
            value = transform.rotationZ ?: 0f,
            onValueChange = {
                onTransformChange(transform.copy(rotationZ = it))
            },
            valueRange = -360f..360f
        )

        // Alpha
        PropertySlider(
            label = "Alpha",
            value = transform.alpha ?: 1f,
            onValueChange = {
                onTransformChange(transform.copy(alpha = it))
            },
            valueRange = 0f..1f
        )
    }
}

@Composable
private fun PropertySlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = AppTheme.typography.body2,
            modifier = Modifier.width(60.dp)
        )

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = when (label) {
                "Alpha" -> "${(value * 100).toInt() / 100f}"
                "Rotation" -> "${value.toInt()}°"
                "Scale X", "Scale Y" -> "${(value * 100).toInt() / 100f}"
                else -> value.toInt().toString()
            },
            style = AppTheme.typography.body2,
            modifier = Modifier.width(50.dp)
        )
    }
}