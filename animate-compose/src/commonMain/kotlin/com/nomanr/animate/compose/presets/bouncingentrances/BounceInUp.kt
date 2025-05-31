package com.nomanr.animate.compose.presets.bouncingentrances

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe
import com.nomanr.animate.compose.presets.common.bounceInEasing

class BounceInUp(
    private val entranceOffsetY: Float = 3000f
) : AnimationPreset {

    private val keyframes = listOf(
        Keyframe.Static(
            percent = 0f,
            transform = TransformProperties(
                alpha = 0f,
                translationY = entranceOffsetY,
                scaleY = 5f
            ),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0f,
            end = 0.6f,
            from = TransformProperties(translationY = entranceOffsetY, scaleY = 5f),
            to = TransformProperties(alpha = 1f, translationY = -20f, scaleY = 0.9f),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0.6f,
            end = 0.75f,
            from = TransformProperties(alpha = 1f, translationY = -20f, scaleY = 0.9f),
            to = TransformProperties(translationY = 10f, scaleY = 0.95f),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0.75f,
            end = 0.9f,
            from = TransformProperties(translationY = 10f, scaleY = 0.95f),
            to = TransformProperties(translationY = -5f, scaleY = 0.985f),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0.9f,
            end = 1f,
            from = TransformProperties(translationY = -5f, scaleY = 0.985f),
            to = TransformProperties(translationY = 0f, scaleY = 1f),
            easing = bounceInEasing
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
