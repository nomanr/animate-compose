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

class ZoomInUp(
    private val peakOffsetY: Float = 400f,
    private val prePeakDelta: Float = 20f,
    private val overshootOffsetY: Float = -60f
) : AnimationPreset {

    private val enterEasing = CubicBezierEasing(0.55f, 0.055f, 0.675f, 0.19f)
    private val settleEasing = CubicBezierEasing(0.175f, 0.885f, 0.32f, 1f)

    private val startOffsetY = peakOffsetY - prePeakDelta

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.2f,
            from = TransformProperties(
                translationX = 0f,
                translationY = startOffsetY,
                scaleX = 0.1f,
                scaleY = 0.1f,
                alpha = 0f
            ),
            to = TransformProperties(
                translationX = 0f,
                translationY = peakOffsetY,
                scaleX = 0.15f,
                scaleY = 0.15f,
                alpha = 0.3f
            ),
            easing = enterEasing
        ),
        Keyframe.Segment(
            start = 0.2f,
            end = 0.6f,
            from = TransformProperties(
                translationX = 0f,
                translationY = peakOffsetY,
                scaleX = 0.15f,
                scaleY = 0.15f,
                alpha = 0.3f
            ),
            to = TransformProperties(
                translationX = 0f,
                translationY = overshootOffsetY,
                scaleX = 0.475f,
                scaleY = 0.475f,
                alpha = 1f
            ),
            easing = enterEasing
        ),
        Keyframe.Segment(
            start = 0.6f,
            end = 1f,
            from = TransformProperties(
                translationX = 0f,
                translationY = overshootOffsetY,
                scaleX = 0.475f,
                scaleY = 0.475f,
                alpha = 1f
            ),
            to = TransformProperties(
                translationX = 0f,
                translationY = 0f,
                scaleX = 1f,
                scaleY = 1f,
                alpha = 1f
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