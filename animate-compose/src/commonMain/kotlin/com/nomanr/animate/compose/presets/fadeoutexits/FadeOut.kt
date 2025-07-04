package com.nomanr.animate.compose.presets.fadeoutexits

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class FadeOut : AnimationPreset {

    private val ease = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 1f,
            from = TransformProperties(alpha = 1f),
            to = TransformProperties(alpha = 0f),
            easing = ease
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes
        )
}