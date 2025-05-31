package com.nomanr.animate.compose.playground.model

import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties

data class KeyframeData(
    val type: Type = Type.Static,
    val percent: Float = 0f,
    val startPercent: Float = 0f,
    val endPercent: Float = 1f,
    val transformProperties: TransformProperties = TransformProperties(),
    val toTransformProperties: TransformProperties = TransformProperties(),
    val easing: EasingOption = EasingOption.None
) {
    enum class Type {
        Static,
        Segment
    }

    fun toKeyframe(): Keyframe {
        return when (type) {
            Type.Static -> Keyframe.Static(
                percent = percent,
                transform = transformProperties,
                easing = easing.toEasing()
            )

            Type.Segment -> Keyframe.Segment(
                start = startPercent,
                end = endPercent,
                from = transformProperties,
                to = toTransformProperties,
                easing = easing.toEasing()
            )
        }
    }
}