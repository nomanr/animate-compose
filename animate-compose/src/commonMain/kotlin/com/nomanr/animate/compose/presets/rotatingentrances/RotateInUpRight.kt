package com.nomanr.animate.compose.presets.rotatingentrances

import androidx.compose.animation.core.EaseOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class RotateInUpRight(
    private val rotationAngle: Float = 45f,
) : AnimationPreset {
    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f, end = 1f,
            from = TransformProperties(rotationZ = -rotationAngle, alpha = 0f),
            to = TransformProperties(rotationZ = 0f, alpha = 1f),
            easing = EaseOut
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin(1f, 1f),
            clip = false
        )
}