package com.nomanr.animate.compose.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties

data class PlaygroundState(
    val keyframes: List<Keyframe>,
    val duration: Int,
    val originX: Float,
    val originY: Float,
    val currentTime: Float,
    val selectedKeyframeIndex: Int?,
    val isPlaying: Boolean,
    val updateKeyframes: (List<Keyframe>) -> Unit,
    val updateDuration: (Int) -> Unit,
    val updateOrigin: (Float, Float) -> Unit,
    val updateCurrentTime: (Float) -> Unit,
    val selectKeyframe: (Int?) -> Unit,
    val setPlaying: (Boolean) -> Unit
)

val LocalPlaygroundState = compositionLocalOf<PlaygroundState> {
    error("No PlaygroundState provided")
}

@Composable
fun PlaygroundStateProvider(content: @Composable () -> Unit) {
    var keyframes by remember {
        mutableStateOf<List<Keyframe>>(
            listOf(
                Keyframe.Segment(
                    start = 0f,
                    end = 0.6f,
                    from = TransformProperties(translationX = 0f),
                    to = TransformProperties(translationX = 200f),
                    easing = null
                ),
                Keyframe.Segment(
                    start = 0.6f,
                    end = 1.0f,
                    from = TransformProperties(translationX = 200f),
                    to = TransformProperties(translationX = 200f),
                    easing = null
                ),
                Keyframe.Segment(
                    start = 0.4f,
                    end = 1.0f,
                    from = TransformProperties(translationY = 0f),
                    to = TransformProperties(translationY = 100f),
                    easing = null
                ),
            )
        )
    }

    var duration by remember { mutableStateOf(2000) }
    var originX by remember { mutableStateOf(0.5f) }
    var originY by remember { mutableStateOf(0.5f) }
    var currentTime by remember { mutableStateOf(0f) }
    var selectedKeyframeIndex by remember { mutableStateOf<Int?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    val state = PlaygroundState(
        keyframes = keyframes,
        duration = duration,
        originX = originX,
        originY = originY,
        currentTime = currentTime,
        selectedKeyframeIndex = selectedKeyframeIndex,
        isPlaying = isPlaying,
        updateKeyframes = { keyframes = it },
        updateDuration = { duration = it },
        updateOrigin = { x, y ->
            originX = x
            originY = y
        },
        updateCurrentTime = { currentTime = it },
        selectKeyframe = { selectedKeyframeIndex = it },
        setPlaying = { isPlaying = it })

    CompositionLocalProvider(LocalPlaygroundState provides state) {
        content()
    }
}

// Helper extension functions for PlaygroundState
fun PlaygroundState.addStaticKeyframe(time: Float? = null): Keyframe.Static {
    val actualTime = time ?: getNextKeyframeStartTime()
    val newKeyframe = Keyframe.Static(
        percent = actualTime, transform = TransformProperties(), easing = null
    )
    val updatedKeyframes = (keyframes + newKeyframe).sortedBy { getKeyframeTime(it) }
    updateKeyframes(updatedKeyframes)
    return newKeyframe
}

fun PlaygroundState.addSegmentKeyframe(
    startTime: Float? = null, endTime: Float? = null
): Keyframe.Segment {
    val actualStartTime = startTime ?: getNextKeyframeStartTime()
    val actualEndTime = endTime ?: (actualStartTime + 0.2f).coerceAtMost(1.0f)
    val newKeyframe = Keyframe.Segment(
        start = actualStartTime,
        end = actualEndTime,
        from = TransformProperties(),
        to = TransformProperties(),
        easing = null
    )
    val updatedKeyframes = (keyframes + newKeyframe).sortedBy { getKeyframeTime(it) }
    updateKeyframes(updatedKeyframes)
    return newKeyframe
}

private fun PlaygroundState.getNextKeyframeStartTime(): Float {
    if (keyframes.isEmpty()) return 0f

    val lastKeyframeEnd = keyframes.maxOf { keyframe ->
        when (keyframe) {
            is Keyframe.Static -> keyframe.percent
            is Keyframe.Segment -> keyframe.end
        }
    }

    return lastKeyframeEnd
}

private fun PlaygroundState.getKeyframeTime(keyframe: Keyframe): Float {
    return when (keyframe) {
        is Keyframe.Static -> keyframe.percent
        is Keyframe.Segment -> keyframe.start
    }
}

fun PlaygroundState.updateKeyframe(index: Int, update: (Keyframe) -> Keyframe) {
    val updatedKeyframes = keyframes.mapIndexed { i, keyframe ->
        if (i == index) update(keyframe) else keyframe
    }
    updateKeyframes(updatedKeyframes)
}

fun PlaygroundState.removeKeyframe(index: Int) {
    val updatedKeyframes = keyframes.filterIndexed { i, _ -> i != index }
    updateKeyframes(updatedKeyframes)
    if (selectedKeyframeIndex == index) {
        selectKeyframe(null)
    }
}

fun PlaygroundState.updateKeyframeTime(index: Int, newTime: Float) {
    updateKeyframe(index) { keyframe ->
        when (keyframe) {
            is Keyframe.Static -> keyframe.copy(percent = newTime)
            is Keyframe.Segment -> {
                val segmentDuration = keyframe.end - keyframe.start
                keyframe.copy(
                    start = newTime, end = newTime + segmentDuration
                )
            }
        }
    }
}

fun PlaygroundState.play() {
    setPlaying(true)
    updateCurrentTime(0f)
}

fun PlaygroundState.pause() {
    setPlaying(false)
}

fun PlaygroundState.stop() {
    setPlaying(false)
    updateCurrentTime(0f)
}

fun PlaygroundState.updateCurrentTimeWithBounds(time: Float) {
    val coercedTime = time.coerceIn(0f, 1.0f)
    updateCurrentTime(coercedTime)
    if (coercedTime >= 1.0f) {
        setPlaying(false)
        updateCurrentTime(1.0f)
    }
}

fun PlaygroundState.toKeyframes(): List<Keyframe> {
    return keyframes
}