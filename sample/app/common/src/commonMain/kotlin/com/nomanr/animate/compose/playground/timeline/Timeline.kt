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

                            // Check if clicking on existing keyframe
                            val clickedKeyframe = findKeyframeAtPosition(offset, timelineSize, state)
                            if (clickedKeyframe != null) {
                                state.selectKeyframe(clickedKeyframe.index)
                                onNodeSelected?.invoke(clickedKeyframe.index.toString())

                                val dragType = determineDragType(clickedKeyframe)
                                state.startDrag(clickedKeyframe.index, dragType, time)
                            } else {
                                // Clicking on empty space - add new keyframe
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
                drawKeyframes(state)
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
                            state.addStaticKeyframe(pendingAddTime)
                            showNodeTypeMenu = false
                        }
                    ) {
                        Text("Static Keyframe")
                    }
                    Button(
                        variant = ButtonVariant.Ghost,
                        onClick = {
                            val endTime = (pendingAddTime + 0.5f).coerceAtMost(state.duration)
                            state.addSegmentKeyframe(pendingAddTime, endTime)
                            showNodeTypeMenu = false
                        }
                    ) {
                        Text("Segment")
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    variant = ButtonVariant.Primary,
                    onClick = {
                        val time = state.currentTime + 0.5f
                        state.addStaticKeyframe(time)
                    }
                ) {
                    Text("Add Static")
                }

                Button(
                    variant = ButtonVariant.Secondary,
                    onClick = {
                        val time = state.currentTime + 0.5f
                        val endTime = (time + 0.5f).coerceAtMost(state.duration)
                        state.addSegmentKeyframe(time, endTime)
                    }
                ) {
                    Text("Add Segment")
                }
            }

            Button(
                variant = ButtonVariant.Primary,
                onClick = {
                    state.play()
                }
            ) {
                Text("Play")
            }
        }
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

private fun DrawScope.drawKeyframes(state: TimelineState) {
    state.keyframes.forEachIndexed { index, keyframe ->
        val isSelected = index == state.selectedKeyframeIndex
        val keyframeColor = if (isSelected) Color.Blue else Color.Red

        when (keyframe) {
            is com.nomanr.animate.compose.core.Keyframe.Static -> {
                val x = (keyframe.percent / state.duration) * size.width
                val keyframeWidth = 8.dp.toPx()

                drawRect(
                    color = keyframeColor,
                    topLeft = Offset(x - keyframeWidth / 2, size.height * 0.2f),
                    size = androidx.compose.ui.geometry.Size(keyframeWidth, size.height * 0.6f)
                )
            }

            is com.nomanr.animate.compose.core.Keyframe.Segment -> {
                val startX = (keyframe.start / state.duration) * size.width
                val endX = (keyframe.end / state.duration) * size.width
                val segmentWidth = endX - startX

                drawRect(
                    color = keyframeColor.copy(alpha = 0.6f),
                    topLeft = Offset(startX, size.height * 0.3f),
                    size = androidx.compose.ui.geometry.Size(segmentWidth, size.height * 0.4f)
                )

                // Draw resize handles
                val handleWidth = 4.dp.toPx()
                drawRect(
                    color = keyframeColor,
                    topLeft = Offset(startX - handleWidth / 2, size.height * 0.2f),
                    size = androidx.compose.ui.geometry.Size(handleWidth, size.height * 0.6f)
                )
                drawRect(
                    color = keyframeColor,
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

private data class KeyframeHitResult(
    val index: Int,
    val keyframe: com.nomanr.animate.compose.core.Keyframe,
    val hitType: KeyframeHitType
)

private enum class KeyframeHitType {
    START_HANDLE,
    END_HANDLE,
    BODY
}

private fun findKeyframeAtPosition(
    offset: Offset,
    timelineSize: IntSize,
    state: TimelineState
): KeyframeHitResult? {
    val handleThreshold = 20f // pixels

    state.keyframes.forEachIndexed { index, keyframe ->
        when (keyframe) {
            is com.nomanr.animate.compose.core.Keyframe.Static -> {
                val keyframeX = (keyframe.percent / state.duration) * timelineSize.width
                if (kotlin.math.abs(offset.x - keyframeX) < handleThreshold) {
                    return KeyframeHitResult(index, keyframe, KeyframeHitType.BODY)
                }
            }

            is com.nomanr.animate.compose.core.Keyframe.Segment -> {
                val startX = (keyframe.start / state.duration) * timelineSize.width
                val endX = (keyframe.end / state.duration) * timelineSize.width

                when {
                    kotlin.math.abs(offset.x - startX) < handleThreshold ->
                        return KeyframeHitResult(index, keyframe, KeyframeHitType.START_HANDLE)

                    kotlin.math.abs(offset.x - endX) < handleThreshold ->
                        return KeyframeHitResult(index, keyframe, KeyframeHitType.END_HANDLE)

                    offset.x in startX..endX ->
                        return KeyframeHitResult(index, keyframe, KeyframeHitType.BODY)
                }
            }
        }
    }

    return null
}

private fun determineDragType(
    hitResult: KeyframeHitResult
): DragType {
    return when (hitResult.hitType) {
        KeyframeHitType.START_HANDLE -> DragType.RESIZE_START
        KeyframeHitType.END_HANDLE -> DragType.RESIZE_END
        KeyframeHitType.BODY -> DragType.MOVE
    }
}