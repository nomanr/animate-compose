package com.nomanr.animate.compose.playground.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.playground.KeyframePresetState

@Stable
class PlaygroundState(
    private val presetState: KeyframePresetState? = null
) {
    val keyframes: List<Keyframe>
        get() = presetState?.keyframes ?: emptyList()

    val duration: Float
        get() = presetState?.duration ?: 1.0f

    var currentTime by mutableStateOf(0f)
        private set

    var selectedKeyframeIndex by mutableStateOf<Int?>(null)
        private set

    var isPlaying by mutableStateOf(false)
        private set


    fun addStaticKeyframe(time: Float? = null): Keyframe.Static {
        val actualTime = time ?: getNextKeyframeStartTime()
        val newKeyframe = Keyframe.Static(
            percent = actualTime,
            transform = TransformProperties(),
            easing = null
        )
        val updatedKeyframes = (keyframes + newKeyframe).sortedBy { getKeyframeTime(it) }
        presetState?.updateKeyframes?.invoke(updatedKeyframes)
        return newKeyframe
    }

    fun addSegmentKeyframe(startTime: Float? = null, endTime: Float? = null): Keyframe.Segment {
        val actualStartTime = startTime ?: getNextKeyframeStartTime()
        val actualEndTime = endTime ?: (actualStartTime + 0.2f).coerceAtMost(duration)
        val newKeyframe = Keyframe.Segment(
            start = actualStartTime,
            end = actualEndTime,
            from = TransformProperties(),
            to = TransformProperties(),
            easing = null
        )
        val updatedKeyframes = (keyframes + newKeyframe).sortedBy { getKeyframeTime(it) }
        presetState?.updateKeyframes?.invoke(updatedKeyframes)
        return newKeyframe
    }
    
    private fun getNextKeyframeStartTime(): Float {
        if (keyframes.isEmpty()) return 0f
        
        val lastKeyframeEnd = keyframes.maxOf { keyframe ->
            when (keyframe) {
                is Keyframe.Static -> keyframe.percent
                is Keyframe.Segment -> keyframe.end
            }
        }
        
        return lastKeyframeEnd
    }

    private fun getKeyframeTime(keyframe: Keyframe): Float {
        return when (keyframe) {
            is Keyframe.Static -> keyframe.percent
            is Keyframe.Segment -> keyframe.start
        }
    }

    fun updateKeyframe(index: Int, update: (Keyframe) -> Keyframe) {
        val updatedKeyframes = keyframes.mapIndexed { i, keyframe ->
            if (i == index) update(keyframe) else keyframe
        }
        presetState?.updateKeyframes?.invoke(updatedKeyframes)
    }


    fun removeKeyframe(index: Int) {
        val updatedKeyframes = keyframes.filterIndexed { i, _ -> i != index }
        presetState?.updateKeyframes?.invoke(updatedKeyframes)
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




    fun updateDuration(newDuration: Float) {
        val coercedDuration = newDuration.coerceAtLeast(0.1f)
        presetState?.updateDuration?.invoke(coercedDuration)
        currentTime = currentTime.coerceIn(0f, duration)
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
    
    fun updateCurrentTime(time: Float) {
        currentTime = time.coerceIn(0f, duration)
        if (currentTime >= duration) {
            isPlaying = false
            currentTime = duration
        }
    }


    fun toKeyframes(): List<Keyframe> {
        return keyframes
    }

}