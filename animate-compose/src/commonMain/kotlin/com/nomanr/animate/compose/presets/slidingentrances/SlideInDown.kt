package com.nomanr.animate.compose.presets.slidingentrances

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.*

class SlideInDown(
    private val startOffsetY: Float = -200f
) : AnimationPreset {

    private val exitEasing = CubicBezierEasing(0.325f, 0.81f, 0.45f, 0.945f)
    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.6f,
            from = TransformProperties(translationY = startOffsetY),
            to = TransformProperties(translationY = 0f),
            easing = exitEasing
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin.Center,
            clip = false
        )
}