package com.nomanr.animate.compose.core

import androidx.compose.ui.util.lerp

internal fun TransformProperties.interpolate(to: TransformProperties, fraction: Float): TransformProperties {
    return TransformProperties(
        translationX = lerpIfNotNull(this.translationX, to.translationX, fraction),
        translationY = lerpIfNotNull(this.translationY, to.translationY, fraction),
        scaleX = lerpIfNotNull(this.scaleX, to.scaleX, fraction),
        scaleY = lerpIfNotNull(this.scaleY, to.scaleY, fraction),
        rotationZ = lerpIfNotNull(this.rotationZ, to.rotationZ, fraction),
        skewX = lerpIfNotNull(this.skewX, to.skewX, fraction),
        skewY = lerpIfNotNull(this.skewY, to.skewY, fraction),
        alpha = lerpIfNotNull(this.alpha, to.alpha, fraction)
    )
}

private fun lerpIfNotNull(start: Float?, end: Float?, fraction: Float): Float? {
    return if (start != null && end != null) {
        lerp(start, end, fraction)
    } else {
        end ?: start
    }
}

/**
 * Find a matching keyframe for a given animation progress.
 * - Returns a Segment if progress is between start and end
 * - Returns a Static if progress is less than or equal to its percent
 */
fun List<Keyframe>.atProgress(progress: Float): Keyframe? {
    return this.findLast {
        when (it) {
            is Keyframe.Segment -> progress in it.start..it.end
            is Keyframe.Static -> progress >= it.percent
        }
    }
}