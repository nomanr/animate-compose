package com.nomanr.animate.compose.playground.timeline

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties

@Stable
class TimelineState(
    initialKeyframes: List<Keyframe> = emptyList(),
    initialDuration: Float = 1.0f
) {
    var keyframes by mutableStateOf(initialKeyframes)
        private set

    var duration by mutableStateOf(initialDuration)
        private set

    var currentTime by mutableStateOf(0f)
        private set

    var selectedKeyframeIndex by mutableStateOf<Int?>(null)
        private set

    var isPlaying by mutableStateOf(false)
        private set

    var dragState by mutableStateOf<DragState?>(null)
        private set

    fun addStaticKeyframe(time: Float): Keyframe.Static {
        val newKeyframe = Keyframe.Static(
            percent = time,
            transform = TransformProperties(),
            easing = null
        )
        keyframes = (keyframes + newKeyframe).sortedBy { getKeyframeTime(it) }
        return newKeyframe
    }

    fun addSegmentKeyframe(startTime: Float, endTime: Float): Keyframe.Segment {
        val newKeyframe = Keyframe.Segment(
            start = startTime,
            end = endTime,
            from = TransformProperties(),
            to = TransformProperties(),
            easing = null
        )
        keyframes = (keyframes + newKeyframe).sortedBy { getKeyframeTime(it) }
        return newKeyframe
    }

    private fun getKeyframeTime(keyframe: Keyframe): Float {
        return when (keyframe) {
            is Keyframe.Static -> keyframe.percent
            is Keyframe.Segment -> keyframe.start
        }
    }

    fun updateKeyframe(index: Int, update: (Keyframe) -> Keyframe) {
        keyframes = keyframes.mapIndexed { i, keyframe ->
            if (i == index) update(keyframe) else keyframe
        }
    }

    fun removeKeyframe(index: Int) {
        keyframes = keyframes.filterIndexed { i, _ -> i != index }
        if (selectedKeyframeIndex == index) {
            selectedKeyframeIndex = null
        }
    }

    fun selectKeyframe(index: Int?) {
        selectedKeyframeIndex = index
    }

    fun updateKeyframeTime(index: Int, newTime: Float) {
        updateKeyframe(index) { keyframe ->
            when (keyframe) {
                is Keyframe.Static -> keyframe.copy(percent = newTime)
                is Keyframe.Segment -> {
                    val segmentDuration = keyframe.end - keyframe.start
                    keyframe.copy(
                        start = newTime,
                        end = newTime + segmentDuration
                    )
                }
            }
        }
    }

    fun updateKeyframeTransform(index: Int, newTransform: TransformProperties) {
        updateKeyframe(index) { keyframe ->
            when (keyframe) {
                is Keyframe.Static -> keyframe.copy(transform = newTransform)
                is Keyframe.Segment -> keyframe.copy(from = newTransform)
            }
        }
    }

    fun updateCurrentTime(time: Float) {
        currentTime = time.coerceIn(0f, duration)
    }

    fun updateDuration(duration: Float) {
        this.duration = duration.coerceAtLeast(0.1f)
        currentTime = currentTime.coerceIn(0f, this.duration)
    }

    fun startDrag(keyframeIndex: Int, dragType: DragType, startTime: Float) {
        val keyframe = keyframes.getOrNull(keyframeIndex) ?: return
        dragState = DragState(
            keyframeIndex = keyframeIndex,
            type = dragType,
            startTime = startTime,
            originalKeyframeTime = getKeyframeTime(keyframe),
            originalEndTime = if (keyframe is Keyframe.Segment) keyframe.end else getKeyframeTime(keyframe)
        )
    }

    fun updateDrag(currentTime: Float) {
        val drag = dragState ?: return
        val keyframe = keyframes.getOrNull(drag.keyframeIndex) ?: return

        when (drag.type) {
            DragType.MOVE -> {
                val delta = currentTime - drag.startTime
                val newTime = (drag.originalKeyframeTime + delta).coerceIn(0f, duration)

                updateKeyframe(drag.keyframeIndex) { oldKeyframe ->
                    when (oldKeyframe) {
                        is Keyframe.Static -> oldKeyframe.copy(percent = newTime)
                        is Keyframe.Segment -> {
                            val segmentDuration = oldKeyframe.end - oldKeyframe.start
                            oldKeyframe.copy(
                                start = newTime,
                                end = newTime + segmentDuration
                            )
                        }
                    }
                }
            }

            DragType.RESIZE_END -> {
                if (keyframe is Keyframe.Segment) {
                    val newEndTime = currentTime.coerceIn(keyframe.start + 0.05f, duration)
                    updateKeyframe(drag.keyframeIndex) { oldKeyframe ->
                        (oldKeyframe as Keyframe.Segment).copy(end = newEndTime)
                    }
                }
            }

            DragType.RESIZE_START -> {
                val newStartTime = currentTime.coerceIn(0f, duration)
                when (keyframe) {
                    is Keyframe.Static -> {
                        updateKeyframe(drag.keyframeIndex) { oldKeyframe ->
                            (oldKeyframe as Keyframe.Static).copy(percent = newStartTime)
                        }
                    }

                    is Keyframe.Segment -> {
                        val newEndTime = maxOf(newStartTime + 0.05f, keyframe.end)
                        updateKeyframe(drag.keyframeIndex) { oldKeyframe ->
                            (oldKeyframe as Keyframe.Segment).copy(
                                start = newStartTime,
                                end = newEndTime
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
        return keyframes
    }

    fun getCurrentTransformProperties(progress: Float): TransformProperties {
        // Simple interpolation for demo purposes
        val currentKeyframes = keyframes.filter { 
            when (it) {
                is Keyframe.Static -> it.percent <= progress
                is Keyframe.Segment -> it.start <= progress && progress <= it.end
            }
        }
        
        return currentKeyframes.lastOrNull()?.let { keyframe ->
            when (keyframe) {
                is Keyframe.Static -> keyframe.transform
                is Keyframe.Segment -> {
                    val segmentProgress = (progress - keyframe.start) / (keyframe.end - keyframe.start)
                    // Simple interpolation between from and to
                    keyframe.from // Simplified - could add actual interpolation here
                }
            }
        } ?: TransformProperties()
    }
}

data class DragState(
    val keyframeIndex: Int,
    val type: DragType,
    val startTime: Float,
    val originalKeyframeTime: Float,
    val originalEndTime: Float
)

enum class DragType {
    MOVE,
    RESIZE_START,
    RESIZE_END
}