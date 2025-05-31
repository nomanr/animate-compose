package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun SliderTimelineView(
    state: TimelineState,
    modifier: Modifier = Modifier,
    onNodeSelected: ((String?) -> Unit)? = null
) {
    var timelineSize by remember { mutableStateOf(IntSize.Zero) }
    var showNodeTypeMenu by remember { mutableStateOf(false) }
    var pendingAddTime by remember { mutableStateOf(0f) }

    Column(
        modifier = modifier
            .background(AppTheme.colors.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Timeline controls
        TimelineControls(
            state = state,
            onPlay = { state.play() },
            onPause = { state.pause() },
            onStop = { state.stop() }
        )

        // Visual timeline track (read-only)
        Text("Timeline", style = AppTheme.typography.h5)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, AppTheme.colors.outline, RoundedCornerShape(8.dp))
                .background(AppTheme.colors.background)
                .onSizeChanged { timelineSize = it }
                .clickable {
                    // Click to add new node
                    showNodeTypeMenu = true
                }
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawTimelineBackground()
                drawTimeMarkers(state)
                drawNodes(state, timelineSize)
                drawPlayhead(state)
            }
        }

        if (showNodeTypeMenu) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    variant = ButtonVariant.Primary,
                    onClick = {
                        state.addNode(pendingAddTime, TimelineNodeType.Static)
                        showNodeTypeMenu = false
                    }
                ) {
                    Text("Add Static Keyframe")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    variant = ButtonVariant.Secondary,
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
                    Text("Add Segment")
                }
            }
        }

        // Timeline scrubber slider
        TimelineScrubber(state)

        // Node selection and editing
        NodeSelectionControls(
            state = state,
            onNodeSelected = onNodeSelected
        )

        // Selected node controls
        val selectedNode = state.selectedNodeId?.let { nodeId ->
            state.nodes.find { it.id == nodeId }
        }

        if (selectedNode != null) {
            SelectedNodeControls(
                node = selectedNode,
                state = state
            )
        }
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

        Text("${(state.currentTime * 100).toInt() / 100.0} / ${(state.duration * 100).toInt() / 100.0}")
    }
}

@Composable
private fun TimelineScrubber(state: TimelineState) {
    Column {
        Text("Timeline Position: ${(state.currentTime * 100).toInt() / 100.0}")
        Slider(
            value = state.currentTime,
            onValueChange = { value ->
                state.updateCurrentTime(value)
            },
            valueRange = 0f..state.duration
        )
    }
}

@Composable
private fun NodeSelectionControls(
    state: TimelineState,
    onNodeSelected: ((String?) -> Unit)?
) {
    Column {
        Text("Nodes (${state.nodes.size})", style = AppTheme.typography.h5)

        if (state.nodes.isEmpty()) {
            Text(
                "No nodes yet. Click the timeline above to add keyframes.",
                color = AppTheme.colors.textSecondary
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.nodes.forEach { node ->
                    val isSelected = node.id == state.selectedNodeId
                    Button(
                        variant = if (isSelected) ButtonVariant.Primary else ButtonVariant.Secondary,
                        onClick = {
                            state.selectNode(if (isSelected) null else node.id)
                            onNodeSelected?.invoke(if (isSelected) null else node.id)
                        }
                    ) {
                        val typeText = when (node.type) {
                            is TimelineNodeType.Static -> "S"
                            is TimelineNodeType.Segment -> "Seg"
                        }
                        Text("$typeText@${(node.time * 100).toInt() / 100.0}")
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectedNodeControls(
    node: TimelineNode,
    state: TimelineState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Editing Node", style = AppTheme.typography.h5)
            Button(
                variant = ButtonVariant.Destructive,
                onClick = { state.removeNode(node.id) }
            ) {
                Text("Delete")
            }
        }

        // Node time position slider
        Column {
            Text("Time Position: ${(node.time * 100).toInt() / 100.0}")
            Slider(
                value = node.time,
                onValueChange = { value ->
                    state.updateNode(node.id) { oldNode ->
                        val newTime = value.coerceIn(0f, state.duration)
                        when (val type = oldNode.type) {
                            is TimelineNodeType.Static -> oldNode.copy(time = newTime)
                            is TimelineNodeType.Segment -> {
                                val duration = type.endTime - oldNode.time
                                oldNode.copy(
                                    time = newTime,
                                    type = type.copy(
                                        endTime = (newTime + duration).coerceAtMost(
                                            state.duration
                                        )
                                    )
                                )
                            }
                        }
                    }
                },
                valueRange = 0f..state.duration
            )
        }

        // Segment duration slider (if segment)
        if (node.type is TimelineNodeType.Segment) {
            val duration = node.type.endTime - node.time
            Column {
                Text("Duration: ${(duration * 100).toInt() / 100.0}")
                Slider(
                    value = duration,
                    onValueChange = { value ->
                        state.updateNode(node.id) { oldNode ->
                            val newDuration = value.coerceAtLeast(0.05f)
                            val newEndTime =
                                (oldNode.time + newDuration).coerceAtMost(state.duration)
                            oldNode.copy(
                                type = (oldNode.type as TimelineNodeType.Segment).copy(endTime = newEndTime)
                            )
                        }
                    },
                    valueRange = 0.05f..(state.duration - node.time)
                )
            }
        }
    }
}

// Drawing functions (simplified, read-only)
private fun DrawScope.drawTimelineBackground() {
    drawRect(
        color = Color.Gray.copy(alpha = 0.1f),
        size = size
    )
}

private fun DrawScope.drawTimeMarkers(state: TimelineState) {
    val step = 0.25f
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

private fun DrawScope.drawNodes(state: TimelineState, timelineSize: IntSize) {
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