package com.nomanr.animate.compose.presets.bounceentrances

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe
import com.nomanr.animate.compose.presets.bounceentrances.bounceeasings.bounceEasing

class BounceInLeft(
    private val entranceOffsetX: Float = -3000f
) : AnimationPreset {


    private val keyframes = listOf(
        Keyframe.Static(
            percent = 0f,
            transform = TransformProperties(alpha = 0f, translationX = entranceOffsetX, scaleX = 3f),
            easing = bounceEasing
        ),
        Keyframe.Segment(
            start = 0f,
            end = 0.6f,
            from = TransformProperties(translationX = entranceOffsetX, scaleX = 3f),
            to = TransformProperties(alpha = 1f, translationX = 25f, scaleX = 1f),
            easing = bounceEasing
        ),
        Keyframe.Segment(
            start = 0.6f,
            end = 0.75f,
            from = TransformProperties(alpha = 1f, translationX = 25f, scaleX = 1f),
            to = TransformProperties(translationX = -10f, scaleX = 0.98f),
            easing = bounceEasing
        ),
        Keyframe.Segment(
            start = 0.75f,
            end = 0.9f,
            from = TransformProperties(translationX = -10f, scaleX = 0.98f),
            to = TransformProperties(translationX = 5f, scaleX = 0.995f),
            easing = bounceEasing
        ),
        Keyframe.Segment(
            start = 0.9f,
            end = 1f,
            from = TransformProperties(translationX = 5f, scaleX = 0.995f),
            to = TransformProperties(translationX = 0f, scaleX = 1f),
            easing = bounceEasing
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin.Center,
            clip = false
        )
    }
}