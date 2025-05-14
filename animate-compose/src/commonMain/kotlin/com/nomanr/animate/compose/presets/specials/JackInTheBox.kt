package com.nomanr.animate.compose.presets.specials

import androidx.compose.animation.core.EaseOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class JackInTheBox(
) : AnimationPreset {
    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f, end = 0.5f,
            from = TransformProperties(
                scaleX = 0.1f,
                scaleY = 0.1f,
                rotationZ = 30f,
                alpha = 0f
            ),
            to = TransformProperties(
                scaleX = 1f,
                scaleY = 1f,
                rotationZ = -10f,
                alpha = 1f
            ),
            easing = EaseOut
        ),
        Keyframe.Segment(
            start = 0.5f, end = 0.7f,
            from = TransformProperties(
                rotationZ = -10f,
            ),
            to = TransformProperties(
                rotationZ = 3f,
            ),
            easing = EaseOut
        ),
        Keyframe.Segment(
            start = 0.7f, end = 1f,
            from = TransformProperties(
                rotationZ = 3f,
            ),
            to = TransformProperties(scaleX = 1f, scaleY = 1f, rotationZ = 0f, alpha = 1f),
            easing = EaseOut
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier =
        Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin(0.5f, 1f),
            clip = false
        )
}