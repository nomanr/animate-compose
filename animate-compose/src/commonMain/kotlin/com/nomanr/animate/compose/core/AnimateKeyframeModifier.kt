package com.nomanr.animate.compose.core

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp

fun Modifier.animateKeyframe(
    progress: State<Float>,
    keyframes: List<Keyframe>,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    clip: Boolean = false
): Modifier {
    val p = progress.value
    val transform = when (val current = keyframes.atProgress(p)) {
        is Keyframe.Segment -> {
            val fraction = (p - current.start) / (current.end - current.start)
            val eased = current.easing?.transform(fraction) ?: 1f
            current.from.interpolate(current.to, eased)
        }

        is Keyframe.Static -> current.transform
        else -> TransformProperties()
    }

    return this.graphicsLayer {
        this.transformOrigin = transformOrigin
        this.clip = clip
        transform.translationX?.let { this.translationX = it }
        transform.translationY?.let { this.translationY = it }
        transform.scaleX?.let { this.scaleX = it }
        transform.scaleY?.let { this.scaleY = it }
        transform.rotationX?.let { this.rotationX = it }
        transform.rotationY?.let { this.rotationY = it }
        transform.rotationZ?.let { this.rotationZ = it }
        transform.alpha?.let { this.alpha = it }
        transform.cameraDistance?.let { this.cameraDistance = it }
    }
}

internal fun TransformProperties.interpolate(to: TransformProperties, fraction: Float): TransformProperties {
    return TransformProperties(
        translationX = lerpIfNotNull(this.translationX, to.translationX, fraction),
        translationY = lerpIfNotNull(this.translationY, to.translationY, fraction),
        scaleX = lerpIfNotNull(this.scaleX, to.scaleX, fraction),
        scaleY = lerpIfNotNull(this.scaleY, to.scaleY, fraction),
        rotationY = lerpIfNotNull(this.rotationY, to.rotationY, fraction),
        rotationX = lerpIfNotNull(this.rotationX, to.rotationX, fraction),
        rotationZ = lerpIfNotNull(this.rotationZ, to.rotationZ, fraction),
        skewX = lerpIfNotNull(this.skewX, to.skewX, fraction),
        skewY = lerpIfNotNull(this.skewY, to.skewY, fraction),
        alpha = lerpIfNotNull(this.alpha, to.alpha, fraction),
        cameraDistance = lerpIfNotNull(this.cameraDistance, to.cameraDistance, fraction),
    )
}

private fun lerpIfNotNull(start: Float?, end: Float?, fraction: Float): Float? {
    return if (start != null && end != null) {
        lerp(start, end, fraction)
    } else {
        end ?: start
    }
}
