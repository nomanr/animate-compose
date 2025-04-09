package com.nomanr.animate.compose.core

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Easing

data class TransformProperties(
    val translationX: Float? = null,
    val translationY: Float? = null,
    val scaleX: Float? = null,
    val scaleY: Float? = null,
    val rotationZ: Float? = null,
    val rotationY: Float? = null,
    val skewX: Float? = null,
    val skewY: Float? = null,
    val alpha: Float? = null
)

sealed class Keyframe {
    abstract val easing: Easing?

    data class Static(
        val percent: Float,
        val transform: TransformProperties,
        override val easing: Easing? = null
    ) : Keyframe()

    data class Segment(
        val start: Float,
        val end: Float,
        val from: TransformProperties,
        val to: TransformProperties,
        override val easing: Easing? = null
    ) : Keyframe()
}

internal fun keyframeSegment(start: Float, end: Float, from: Float, to: Float, easing: Easing? = null) = Keyframe.Segment(
    start = start,
    end = end,
    from = TransformProperties(scaleX = from, scaleY = from),
    to = TransformProperties(scaleX = to, scaleY = to),
    easing = easing
)