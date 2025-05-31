package com.nomanr.animate.compose.playground.timeline

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties

@Stable
class TimelineState(
    initialNodes: List<TimelineNode> = emptyList(),
    initialDuration: Float = 1.0f
) {
    var nodes by mutableStateOf(initialNodes)
        private set

    var duration by mutableStateOf(initialDuration)
        private set

    var currentTime by mutableStateOf(0f)
        private set

    var selectedNodeId by mutableStateOf<String?>(null)
        private set

    var isPlaying by mutableStateOf(false)
        private set

    var dragState by mutableStateOf<DragState?>(null)
        private set

    fun addNode(time: Float, type: TimelineNodeType): TimelineNode {
        val newNode = TimelineNode(
            id = generateNodeId(),
            time = time,
            type = type,
            transformProperties = TransformProperties()
        )

        nodes = (nodes + newNode).sortedBy { it.time }
        return newNode
    }

    fun updateNode(nodeId: String, update: (TimelineNode) -> TimelineNode) {
        nodes = nodes.map { node ->
            if (node.id == nodeId) update(node) else node
        }
    }

    fun removeNode(nodeId: String) {
        nodes = nodes.filter { it.id != nodeId }
        if (selectedNodeId == nodeId) {
            selectedNodeId = null
        }
    }

    fun selectNode(nodeId: String?) {
        selectedNodeId = nodeId
    }

    fun updateNodeTime(nodeId: String, newTime: Float) {
        updateNode(nodeId) { node ->
            when (val type = node.type) {
                is TimelineNodeType.Static -> node.copy(time = newTime)
                is TimelineNodeType.Segment -> {
                    val segmentDuration = type.endTime - node.time
                    node.copy(
                        time = newTime,
                        type = type.copy(endTime = newTime + segmentDuration)
                    )
                }
            }
        }
    }

    fun updateNodeTransform(nodeId: String, newTransform: TransformProperties) {
        updateNode(nodeId) { node ->
            node.copy(transformProperties = newTransform)
        }
    }

    fun updateCurrentTime(time: Float) {
        currentTime = time.coerceIn(0f, duration)
    }

    fun updateDuration(duration: Float) {
        this.duration = duration.coerceAtLeast(0.1f)
        currentTime = currentTime.coerceIn(0f, this.duration)
    }

    fun startDrag(nodeId: String, dragType: DragType, startTime: Float) {
        val node = nodes.find { it.id == nodeId } ?: return
        dragState = DragState(
            nodeId = nodeId,
            type = dragType,
            startTime = startTime,
            originalNodeTime = node.time,
            originalEndTime = if (node.type is TimelineNodeType.Segment) node.type.endTime else node.time
        )
    }

    fun updateDrag(currentTime: Float) {
        val drag = dragState ?: return
        val node = nodes.find { it.id == drag.nodeId } ?: return

        when (drag.type) {
            DragType.MOVE -> {
                val delta = currentTime - drag.startTime
                val newTime = (drag.originalNodeTime + delta).coerceIn(0f, duration)

                updateNode(drag.nodeId) { oldNode ->
                    when (val type = oldNode.type) {
                        is TimelineNodeType.Static -> oldNode.copy(time = newTime)
                        is TimelineNodeType.Segment -> {
                            val segmentDuration = type.endTime - oldNode.time
                            oldNode.copy(
                                time = newTime,
                                type = type.copy(endTime = newTime + segmentDuration)
                            )
                        }
                    }
                }
            }

            DragType.RESIZE_END -> {
                if (node.type is TimelineNodeType.Segment) {
                    val newEndTime = currentTime.coerceIn(node.time + 0.05f, duration)
                    updateNode(drag.nodeId) { oldNode ->
                        oldNode.copy(
                            type = (oldNode.type as TimelineNodeType.Segment).copy(endTime = newEndTime)
                        )
                    }
                }
            }

            DragType.RESIZE_START -> {
                val newStartTime = currentTime.coerceIn(0f, duration)
                when (val type = node.type) {
                    is TimelineNodeType.Static -> {
                        updateNode(drag.nodeId) { oldNode ->
                            oldNode.copy(time = newStartTime)
                        }
                    }

                    is TimelineNodeType.Segment -> {
                        val newEndTime = maxOf(newStartTime + 0.05f, type.endTime)
                        updateNode(drag.nodeId) { oldNode ->
                            oldNode.copy(
                                time = newStartTime,
                                type = type.copy(endTime = newEndTime)
                            )
                        }
                    }
                }
            }
        }
    }

    fun endDrag() {
        dragState = null
    }

    fun play() {
        isPlaying = true
        currentTime = 0f
    }

    fun pause() {
        isPlaying = false
    }

    fun stop() {
        isPlaying = false
        currentTime = 0f
    }

    fun toKeyframes(): List<Keyframe> {
        return nodes.map { node ->
            when (val type = node.type) {
                is TimelineNodeType.Static -> Keyframe.Static(
                    percent = node.time,
                    transform = node.transformProperties,
                    easing = null
                )

                is TimelineNodeType.Segment -> Keyframe.Segment(
                    start = node.time,
                    end = type.endTime,
                    from = node.transformProperties,
                    to = type.toTransformProperties,
                    easing = null
                )
            }
        }
    }

    private fun generateNodeId(): String = "node_${(0..999999).random()}"
}

data class TimelineNode(
    val id: String,
    val time: Float,
    val type: TimelineNodeType,
    val transformProperties: TransformProperties
)

sealed class TimelineNodeType {
    data object Static : TimelineNodeType()

    data class Segment(
        val endTime: Float,
        val toTransformProperties: TransformProperties
    ) : TimelineNodeType()
}

data class DragState(
    val nodeId: String,
    val type: DragType,
    val startTime: Float,
    val originalNodeTime: Float,
    val originalEndTime: Float
)

enum class DragType {
    MOVE,
    RESIZE_START,
    RESIZE_END
}