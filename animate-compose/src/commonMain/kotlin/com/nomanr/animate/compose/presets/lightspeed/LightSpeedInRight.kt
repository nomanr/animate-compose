package com.nomanr.animate.compose.presets.lightspeed

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe


class LightSpeedInRight(
    private val entryOffset: Float = 1000f
) : AnimationPreset {

    private val easeOut = CubicBezierEasing(0.1f, 0f, 0.3f, 1f)

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.6f,
            from = TransformProperties(
                translationX = entryOffset,
                skewX = -0.65f,
                alpha = 0f
            ),
            to = TransformProperties(
                translationX = 0f,
                skewX = 0.45f,
                alpha = 1f
            ),
            easing = easeOut
        ),
        Keyframe.Segment(
            start = 0.6f,
            end = 0.8f,
            from = TransformProperties(skewX = 0.45f),
            to = TransformProperties(skewX = -0.15f),
            easing = easeOut
        ),
        Keyframe.Segment(
            start = 0.8f,
            end = 1f,
            from = TransformProperties(skewX = -0.15f),
            to = TransformProperties(skewX = 0f),
            easing = easeOut
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
        )
}