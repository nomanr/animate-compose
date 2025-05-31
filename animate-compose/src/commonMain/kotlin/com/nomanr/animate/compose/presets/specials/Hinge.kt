package com.nomanr.animate.compose.presets.specials

import androidx.compose.animation.core.EaseInOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Hinge(
    private val fallDistance: Float = 700f
) : AnimationPreset {
    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f, end = 0.2f,
            from = TransformProperties(rotationZ = 0f),
            to = TransformProperties(rotationZ = 80f),
            easing = EaseInOut
        ),
        Keyframe.Segment(
            start = 0.2f, end = 0.4f,
            from = TransformProperties(rotationZ = 80f),
            to = TransformProperties(rotationZ = 60f),
            easing = EaseInOut
        ),
        Keyframe.Segment(
            start = 0.4f, end = 0.6f,
            from = TransformProperties(rotationZ = 60f),
            to = TransformProperties(rotationZ = 80f),
            easing = EaseInOut
        ),
        Keyframe.Segment(
            start = 0.6f, end = 0.8f,
            from = TransformProperties(rotationZ = 80f),
            to = TransformProperties(rotationZ = 60f),
            easing = EaseInOut
        ),
        Keyframe.Segment(
            start = 0.8f, end = 1f,
            from = TransformProperties(rotationZ = 60f, translationY = 0f, alpha = 1f),
            to = TransformProperties(rotationZ = 10f, translationY = fallDistance, alpha = 0f),
            easing = EaseInOut
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin(0f, 0f),
            clip = false
        )
}