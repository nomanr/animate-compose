package com.nomanr.animate.compose.presets.lightspeed

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class LightSpeedOutRight(
    private val exitOffset: Float = 1000f
) : AnimationPreset {

    private val easeIn = CubicBezierEasing(0.55f, 0.055f, 0.675f, 0.19f)

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.65f,
            from = TransformProperties(
                translationX = 0f,
                skewX = 0f,
                alpha = 1f
            ),
            to = TransformProperties(
                translationX = exitOffset / 2,
                skewX = 0.65f,
                alpha = 0f
            ),
            easing = easeIn
        ),
        Keyframe.Segment(
            start = 0.65f,
            end = 1f,
            from = TransformProperties(
                translationX = exitOffset / 2,
                skewX = 0.65f,
                alpha = 0f
            ),
            to = TransformProperties(
                translationX = exitOffset,
                skewX = 0.65f,
                alpha = 0f
            ),
            easing = easeIn
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin(1f, 0f),
            clip = false
        )
}