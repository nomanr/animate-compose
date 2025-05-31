package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.playground.components.TimelineAnimationPreview
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.HorizontalDivider
import com.nomanr.animate.compose.ui.components.IconButton
import com.nomanr.animate.compose.ui.components.Slider
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.VerticalDivider

@Composable
fun TimelineView(
    state: TimelineState,
    modifier: Modifier = Modifier,
    onNodeSelected: ((String?) -> Unit)? = null
) {
    val density = LocalDensity.current
    var timelineSize by remember { mutableStateOf(IntSize.Zero) }
    var showNodeTypeMenu by remember { mutableStateOf(false) }
    var menuPosition by remember { mutableStateOf(DpOffset.Zero) }
    var pendingAddTime by remember { mutableStateOf(0f) }

    Column(
        modifier = modifier
            .background(AppTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top section - Animation preview and controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Timeline controls and track (left side)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Timeline controls
                TimelineControls(
                    state = state,
                    onPlay = { state.play() },
                    onPause = { state.pause() },
                    onStop = { state.stop() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Timeline track
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, AppTheme.colors.outline, RoundedCornerShape(8.dp))
                        .background(AppTheme.colors.background)
                        .onSizeChanged { timelineSize = it }
                        .pointerInput(state) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val time =
                                        offsetToTime(offset.x, timelineSize.width, state.duration)

                                    // Check if clicking on existing node
                                    val clickedNode =
                                        findNodeAtPosition(offset, timelineSize, state)
                                    if (clickedNode != null) {
                                        state.selectNode(clickedNode.node.id)
                                        onNodeSelected?.invoke(clickedNode.node.id)

                                        val dragType = determineDragType(clickedNode)
                                        state.startDrag(clickedNode.node.id, dragType, time)
                                    } else {
                                        // Clicking on empty space - add new node
                                        pendingAddTime = time
                                        menuPosition = with(density) {
                                            DpOffset(offset.x.toDp(), offset.y.toDp())
                                        }
                                        showNodeTypeMenu = true
                                    }
                                },
                                onDrag = { _, offset ->
                                    if (state.dragState != null) {
                                        val currentTime = offsetToTime(
                                            offset.x,
                                            timelineSize.width,
                                            state.duration
                                        )
                                        state.updateDrag(currentTime)
                                    }
                                },
                                onDragEnd = {
                                    state.endDrag()
                                }
                            )
                        }
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawTimelineBackground()
                        drawTimeMarkers(state)
                        drawNodes(state)
                        drawPlayhead(state)
                    }
                }

                // Node type selection menu
                if (showNodeTypeMenu) {
                    Box(
                        modifier = Modifier
                            .offset(menuPosition.x, menuPosition.y)
                            .background(AppTheme.colors.surface, RoundedCornerShape(8.dp))
                            .border(1.dp, AppTheme.colors.outline, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Button(
                                variant = ButtonVariant.Ghost,
                                onClick = {
                                    state.addNode(pendingAddTime, TimelineNodeType.Static)
                                    showNodeTypeMenu = false
                                }
                            ) {
                                Text("Static Keyframe")
                            }
                            Button(
                                variant = ButtonVariant.Ghost,
                                onClick = {
                                    state.addNode(
                                        pendingAddTime,
                                        TimelineNodeType.Segment(
                                            endTime = (pendingAddTime + 0.5f).coerceAtMost(state.duration),
                                            toTransformProperties = com.nomanr.animate.compose.core.TransformProperties()
                                        )
                                    )
                                    showNodeTypeMenu = false
                                }
                            ) {
                                Text("Segment")
                            }
                        }
                    }
                }
            }

            VerticalDivider()

            // Animation preview (center)
            TimelineAnimationPreview(
                timelineState = state,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            VerticalDivider()

            // Keyframe controls (right side)
            KeyframeControlsPanel(
                state = state,
                modifier = Modifier.width(300.dp)
            )
        }

        HorizontalDivider()
    }
}

@Composable
private fun TimelineControls(
    state: TimelineState,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            variant = ButtonVariant.Primary,
            onClick = if (state.isPlaying) onPause else onPlay
        ) {
            Text(if (state.isPlaying) "Pause" else "Play")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            variant = ButtonVariant.Secondary,
            onClick = onStop
        ) {
            Text("Stop")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text("${(state.currentTime * 1000).toInt()}ms / ${(state.duration * 1000).toInt()}ms")
    }
}

private fun DrawScope.drawTimelineBackground() {
    drawRect(
        color = Color.Gray.copy(alpha = 0.1f),
        size = size
    )
}

private fun DrawScope.drawTimeMarkers(state: TimelineState) {
    val step = 0.5f // Draw marker every 0.5 (500ms equivalent)
    val steps = (state.duration / step).toInt()

    for (i in 0..steps) {
        val time = i * step
        val x = (time / state.duration) * size.width

        drawLine(
            color = Color.Gray.copy(alpha = 0.3f),
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = 1.dp.toPx()
        )
    }
}

private fun DrawScope.drawNodes(state: TimelineState) {
    state.nodes.forEach { node ->
        val isSelected = node.id == state.selectedNodeId
        val nodeColor = if (isSelected) Color.Blue else Color.Red

        when (val type = node.type) {
            is TimelineNodeType.Static -> {
                val x = (node.time / state.duration) * size.width
                val nodeWidth = 8.dp.toPx()

                drawRect(
                    color = nodeColor,
                    topLeft = Offset(x - nodeWidth / 2, size.height * 0.2f),
                    size = androidx.compose.ui.geometry.Size(nodeWidth, size.height * 0.6f)
                )
            }

            is TimelineNodeType.Segment -> {
                val startX = (node.time / state.duration) * size.width
                val endX = (type.endTime / state.duration) * size.width
                val segmentWidth = endX - startX

                drawRect(
                    color = nodeColor.copy(alpha = 0.6f),
                    topLeft = Offset(startX, size.height * 0.3f),
                    size = androidx.compose.ui.geometry.Size(segmentWidth, size.height * 0.4f)
                )

                // Draw resize handles
                val handleWidth = 4.dp.toPx()
                drawRect(
                    color = nodeColor,
                    topLeft = Offset(startX - handleWidth / 2, size.height * 0.2f),
                    size = androidx.compose.ui.geometry.Size(handleWidth, size.height * 0.6f)
                )
                drawRect(
                    color = nodeColor,
                    topLeft = Offset(endX - handleWidth / 2, size.height * 0.2f),
                    size = androidx.compose.ui.geometry.Size(handleWidth, size.height * 0.6f)
                )
            }
        }
    }
}

private fun DrawScope.drawPlayhead(state: TimelineState) {
    val x = (state.currentTime / state.duration) * size.width

    drawLine(
        color = Color.Green,
        start = Offset(x, 0f),
        end = Offset(x, size.height),
        strokeWidth = 2.dp.toPx()
    )
}

private fun offsetToTime(offsetX: Float, timelineWidth: Int, duration: Float): Float {
    val ratio = (offsetX / timelineWidth).coerceIn(0f, 1f)
    return ratio * duration
}

private data class NodeHitResult(
    val node: TimelineNode,
    val hitType: NodeHitType
)

private enum class NodeHitType {
    START_HANDLE,
    END_HANDLE,
    BODY
}

private fun findNodeAtPosition(
    offset: Offset,
    timelineSize: IntSize,
    state: TimelineState
): NodeHitResult? {
    val handleThreshold = 20f // pixels

    for (node in state.nodes) {
        when (val type = node.type) {
            is TimelineNodeType.Static -> {
                val nodeX = (node.time / state.duration) * timelineSize.width
                if (kotlin.math.abs(offset.x - nodeX) < handleThreshold) {
                    return NodeHitResult(node, NodeHitType.BODY)
                }
            }

            is TimelineNodeType.Segment -> {
                val startX = (node.time / state.duration) * timelineSize.width
                val endX = (type.endTime / state.duration) * timelineSize.width

                when {
                    kotlin.math.abs(offset.x - startX) < handleThreshold ->
                        return NodeHitResult(node, NodeHitType.START_HANDLE)

                    kotlin.math.abs(offset.x - endX) < handleThreshold ->
                        return NodeHitResult(node, NodeHitType.END_HANDLE)

                    offset.x in startX..endX ->
                        return NodeHitResult(node, NodeHitType.BODY)
                }
            }
        }
    }

    return null
}

private fun determineDragType(
    hitResult: NodeHitResult
): DragType {
    return when (hitResult.hitType) {
        NodeHitType.START_HANDLE -> DragType.RESIZE_START
        NodeHitType.END_HANDLE -> DragType.RESIZE_END
        NodeHitType.BODY -> DragType.MOVE
    }
}

@Composable
private fun KeyframeControlsPanel(
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
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Keyframes",
                style = AppTheme.typography.h3
            )

            Button(
                variant = ButtonVariant.Primary,
                onClick = {
                    val time = (state.currentTime + 0.5f).coerceAtMost(state.duration)
                    state.addNode(time, TimelineNodeType.Static)
                }
            ) {
                Text("+ Add")
            }
        }

        HorizontalDivider()

        // Keyframes list
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
                    onValueChange = { newEndTime ->
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