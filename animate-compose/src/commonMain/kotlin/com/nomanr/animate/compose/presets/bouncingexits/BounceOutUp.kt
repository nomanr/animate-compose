package com.nomanr.animate.compose.presets.bouncingexits

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe
import com.nomanr.animate.compose.presets.common.bounceOutEasing

class BounceOutUp(
    private val exitOffsetY: Float = -2000f
) : AnimationPreset {
    private val keyframes = listOf(
        Keyframe.Static(
            percent = 0f,
            transform = TransformProperties(alpha = 1f, translationY = 0f, scaleY = 1f),
            easing = bounceOutEasing
        ),
        Keyframe.Segment(
            start = 0f, end = 0.2f,
            from = TransformProperties(translationY = 0f, scaleY = 1f),
            to = TransformProperties(translationY = -10f, scaleY = 0.985f),
            easing = bounceOutEasing
        ),
        Keyframe.Segment(
            start = 0.2f, end = 0.45f,
            from = TransformProperties(translationY = -10f, scaleY = 0.985f),
            to = TransformProperties(alpha = 1f, translationY = 20f, scaleY = 0.9f),
            easing = bounceOutEasing
        ),
        Keyframe.Segment(
            start = 0.45f, end = 1f,
            from = TransformProperties(alpha = 1f, translationY = 20f, scaleY = 0.9f),
            to = TransformProperties(alpha = 0f, translationY = exitOffsetY, scaleY = 3f),
            easing = bounceOutEasing
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