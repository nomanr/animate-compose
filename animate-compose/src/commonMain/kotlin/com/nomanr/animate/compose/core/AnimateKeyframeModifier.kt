package com.nomanr.animate.compose.core

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer

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
        transform.rotationY?.let { this.rotationY = it }
        transform.rotationZ?.let { this.rotationZ = it }
        transform.alpha?.let { this.alpha = it }
    }
}