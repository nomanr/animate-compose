package com.nomanr.animate.compose.presets.rotatingexits

import androidx.compose.animation.core.EaseIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class RotateOutUpLeft(
    private val endRotation: Float = -45f
) : AnimationPreset {
    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f, end = 1f,
            from = TransformProperties(rotationZ = 0f, alpha = 1f),
            to = TransformProperties(rotationZ = endRotation, alpha = 0f),
            easing = EaseIn
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin(0f, 1f),
            clip = false
        )
}