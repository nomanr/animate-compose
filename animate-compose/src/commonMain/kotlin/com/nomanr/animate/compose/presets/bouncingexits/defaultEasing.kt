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

class BounceOut : AnimationPreset {
    private val keyframes = listOf(
        Keyframe.Static(
            percent = 0f,
            transform = TransformProperties(alpha = 1f, scaleX = 1f, scaleY = 1f),
            easing = bounceOutEasing
        ),
        Keyframe.Segment(
            start = 0f, end = 0.2f,
            from = TransformProperties(alpha = 1f, scaleX = 1f, scaleY = 1f),
            to   = TransformProperties(scaleX = 0.9f, scaleY = 0.9f),
            easing = bounceOutEasing
        ),
        Keyframe.Segment(
            start = 0.2f, end = 0.55f,
            from = TransformProperties(scaleX = 0.9f, scaleY = 0.9f),
            to   = TransformProperties(alpha = 1f, scaleX = 1.1f, scaleY = 1.1f),
            easing = bounceOutEasing
        ),
        Keyframe.Segment(
            start = 0.55f, end = 1f,
            from = TransformProperties(alpha = 1f, scaleX = 1.1f, scaleY = 1.1f),
            to   = TransformProperties(alpha = 0f, scaleX = 0.3f, scaleY = 0.3f),
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