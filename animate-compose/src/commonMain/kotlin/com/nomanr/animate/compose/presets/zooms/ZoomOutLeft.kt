package com.nomanr.animate.compose.presets.zooms

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.*

class ZoomOutLeft(
    private val peakOffsetX: Float = 42f,
    private val vanishOffsetX: Float = -400f
) : AnimationPreset {

    private val enterEasing = CubicBezierEasing(0.55f, 0.055f, 0.675f, 0.19f)
    private val settleEasing = CubicBezierEasing(0.175f, 0.885f, 0.32f, 1f)

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.4f,
            from = TransformProperties(
                translationX = 0f,
                scaleX = 1f,
                scaleY = 1f,
                alpha = 1f
            ),
            to = TransformProperties(
                translationX = peakOffsetX,
                scaleX = 0.475f,
                scaleY = 0.475f,
                alpha = 1f
            ),
            easing = enterEasing
        ),
        Keyframe.Segment(
            start = 0.4f,
            end = 1f,
            from = TransformProperties(
                translationX = peakOffsetX,
                scaleX = 0.475f,
                scaleY = 0.475f,
                alpha = 1f
            ),
            to = TransformProperties(
                translationX = vanishOffsetX,
                scaleX = 0.1f,
                scaleY = 0.1f,
                alpha = 0f
            ),
            easing = settleEasing
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