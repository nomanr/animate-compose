package com.nomanr.animate.compose.presets.zoomingextrances

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class ZoomIn(
) : AnimationPreset {

    private val ease = CubicBezierEasing(0.25f, 0.10f, 0.25f, 1f)

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.5f,
            from = TransformProperties(
                alpha = 0.3f,
            ),
            to = TransformProperties(
                alpha = 1f,
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0f,
            end = 1f,
            from = TransformProperties(
                scaleX = 0.33f,
                scaleY = 0.33f
            ),
            to = TransformProperties(
                scaleX = 1f,
                scaleY = 1f
            ),
            easing = ease
        ),

    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin(0.5f, 0.5f),
            clip = false
        )
}