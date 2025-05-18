package com.nomanr.animate.compose.core

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp

fun Modifier.animateKeyframe(
    progress: State<Float>,
    keyframes: List<Keyframe>,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    clip: Boolean = false
): Modifier {
    val transform = keyframes.resolveTransform(progress.value)

    val skewModifier = if (transform.hasSkew()) {
        Modifier.skew(transform.skewX ?: 0f, transform.skewY ?: 0f)
    } else {
        Modifier
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
    }.then(skewModifier)
}

private fun List<Keyframe>.resolveTransform(progress: Float): TransformProperties {
    var result = TransformProperties()
    for (kf in this) {
        if (!kf.isActive(progress)) continue
        val partial = when (kf) {
            is Keyframe.Segment -> {
                val fraction = ((progress - kf.start) / (kf.end - kf.start)).coerceIn(0f, 1f)
                val eased = kf.easing?.transform(fraction) ?: fraction
                kf.from.interpolate(kf.to, eased)
            }

            is Keyframe.Static -> kf.transform
        }
        result = result.merge(partial)
    }
    return result
}

private fun Keyframe.isActive(progress: Float): Boolean = when (this) {
    is Keyframe.Segment -> progress in start..end
    is Keyframe.Static -> progress >= percent
}

private fun TransformProperties.merge(override: TransformProperties): TransformProperties =
    TransformProperties(
        translationX = override.translationX ?: this.translationX,
        translationY = override.translationY ?: this.translationY,
        scaleX = override.scaleX ?: this.scaleX,
        scaleY = override.scaleY ?: this.scaleY,
        rotationX = override.rotationX ?: this.rotationX,
        rotationY = override.rotationY ?: this.rotationY,
        rotationZ = override.rotationZ ?: this.rotationZ,
        skewX = override.skewX ?: this.skewX,
        skewY = override.skewY ?: this.skewY,
        alpha = override.alpha ?: this.alpha,
        cameraDistance = override.cameraDistance ?: this.cameraDistance
    )

fun Modifier.skew(
    skewX: Float = 0f, skewY: Float = 0f
): Modifier = this.drawWithCache {
    onDrawWithContent {
        val pivotFraction = 0.5f
        val px = size.width * pivotFraction
        val py = size.height * pivotFraction
        drawContext.canvas.translate(px, py)
        drawContext.canvas.skew(skewX, skewY)
        drawContext.canvas.translate(-px, -py)
        drawContent()
    }
}

internal fun TransformProperties.interpolate(
    to: TransformProperties, fraction: Float
): TransformProperties = TransformProperties(
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

private fun lerpIfNotNull(start: Float?, end: Float?, fraction: Float): Float? =
    if (start != null && end != null) lerp(start, end, fraction) else end ?: start

private fun TransformProperties.hasSkew(): Boolean = skewX != null || skewY != null