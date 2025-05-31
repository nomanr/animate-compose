package com.nomanr.animate.compose.playground.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun TimelinePlaybackEffect(
    timelineState: TimelineState,
    onAnimationUpdate: ((Float) -> Unit)? = null
) {
    LaunchedEffect(timelineState.isPlaying) {
        if (timelineState.isPlaying) {
            var elapsed = 0f

            while (isActive && timelineState.isPlaying) {
                val currentTime = elapsed % timelineState.duration

                timelineState.updateCurrentTime(currentTime)

                // Calculate animation progress (0f to 1f)
                val progress = currentTime / timelineState.duration
                onAnimationUpdate?.invoke(progress)

                // Check if animation completed one cycle
                if (elapsed >= timelineState.duration) {
                    timelineState.pause()
                    timelineState.updateCurrentTime(0f)
                    break
                }

                delay(16) // ~60 FPS
                elapsed += 0.016f // 16ms as float fraction
            }
        }
    }

    DisposableEffect(timelineState) {
        onDispose {
            timelineState.pause()
        }
    }
}

/**
 * Calculate the current transform properties based on timeline progress
 */
fun TimelineState.getCurrentTransformProperties(progress: Float): com.nomanr.animate.compose.core.TransformProperties {
    val progressTime = progress * duration

    // Find the active keyframes at this time
    val sortedNodes = nodes.sortedBy { it.time }

    var result = com.nomanr.animate.compose.core.TransformProperties()

    for (node in sortedNodes) {
        when (val type = node.type) {
            is TimelineNodeType.Static -> {
                if (progressTime >= node.time) {
                    result = result.merge(node.transformProperties)
                }
            }

            is TimelineNodeType.Segment -> {
                when {
                    progressTime < node.time -> {
                        // Before segment starts
                        continue
                    }

                    progressTime > type.endTime -> {
                        // After segment ends - use end properties
                        result = result.merge(type.toTransformProperties)
                    }

                    else -> {
                        // Inside segment - interpolate
                        val segmentProgress =
                            (progressTime - node.time) / (type.endTime - node.time)
                        val interpolated = node.transformProperties.interpolate(
                            type.toTransformProperties,
                            segmentProgress
                        )
                        result = result.merge(interpolated)
                    }
                }
            }
        }
    }

    return result
}

private fun com.nomanr.animate.compose.core.TransformProperties.merge(
    other: com.nomanr.animate.compose.core.TransformProperties
): com.nomanr.animate.compose.core.TransformProperties =
    com.nomanr.animate.compose.core.TransformProperties(
        translationX = other.translationX ?: this.translationX,
        translationY = other.translationY ?: this.translationY,
        scaleX = other.scaleX ?: this.scaleX,
        scaleY = other.scaleY ?: this.scaleY,
        rotationX = other.rotationX ?: this.rotationX,
        rotationY = other.rotationY ?: this.rotationY,
        rotationZ = other.rotationZ ?: this.rotationZ,
        skewX = other.skewX ?: this.skewX,
        skewY = other.skewY ?: this.skewY,
        alpha = other.alpha ?: this.alpha,
        cameraDistance = other.cameraDistance ?: this.cameraDistance
    )

private fun com.nomanr.animate.compose.core.TransformProperties.interpolate(
    to: com.nomanr.animate.compose.core.TransformProperties,
    fraction: Float
): com.nomanr.animate.compose.core.TransformProperties =
    com.nomanr.animate.compose.core.TransformProperties(
        translationX = lerpIfNotNull(this.translationX, to.translationX, fraction),
        translationY = lerpIfNotNull(this.translationY, to.translationY, fraction),
        scaleX = lerpIfNotNull(this.scaleX, to.scaleX, fraction),
        scaleY = lerpIfNotNull(this.scaleY, to.scaleY, fraction),
        rotationX = lerpIfNotNull(this.rotationX, to.rotationX, fraction),
        rotationY = lerpIfNotNull(this.rotationY, to.rotationY, fraction),
        rotationZ = lerpIfNotNull(this.rotationZ, to.rotationZ, fraction),
        skewX = lerpIfNotNull(this.skewX, to.skewX, fraction),
        skewY = lerpIfNotNull(this.skewY, to.skewY, fraction),
        alpha = lerpIfNotNull(this.alpha, to.alpha, fraction),
        cameraDistance = lerpIfNotNull(this.cameraDistance, to.cameraDistance, fraction)
    )

private fun lerpIfNotNull(start: Float?, end: Float?, fraction: Float): Float? =
    if (start != null && end != null) {
        start + (end - start) * fraction
    } else {
        end ?: start
    }