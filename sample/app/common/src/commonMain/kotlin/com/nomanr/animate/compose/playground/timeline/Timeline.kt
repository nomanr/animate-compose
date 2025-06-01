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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun Timeline(
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
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Timeline controls
        TimelineControls(
            state = state,
            onPlay = { state.play() },
            onPause = { state.pause() },
            onStop = { state.stop() }
        )

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
                            val time = offsetToTime(offset.x, timelineSize.width, state.duration)

                            // Check if clicking on existing node
                            val clickedNode = findNodeAtPosition(offset, timelineSize, state)
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
                                val currentTime = offsetToTime(offset.x, timelineSize.width, state.duration)
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