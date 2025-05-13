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

class BounceIn : AnimationPreset {

    private val keyframes = listOf(
        Keyframe.Static(
            percent = 0f,
            transform = TransformProperties(alpha = 0f, scaleX = 0.3f, scaleY = 0.3f),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0f,
            end = 0.2f,
            from = TransformProperties(scaleX = 0.3f, scaleY = 0.3f),
            to = TransformProperties(scaleX = 1.1f, scaleY = 1.1f),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0.2f,
            end = 0.4f,
            from = TransformProperties(scaleX = 1.1f, scaleY = 1.1f),
            to = TransformProperties(scaleX = 0.9f, scaleY = 0.9f),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0.4f,
            end = 0.6f,
            from = TransformProperties(scaleX = 0.9f, scaleY = 0.9f),
            to = TransformProperties(alpha = 1f, scaleX = 1.03f, scaleY = 1.03f),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0.6f,
            end = 0.8f,
            from = TransformProperties(alpha = 1f, scaleX = 1.03f, scaleY = 1.03f),
            to = TransformProperties(scaleX = 0.97f, scaleY = 0.97f),
            easing = bounceInEasing
        ),
        Keyframe.Segment(
            start = 0.8f,
            end = 1f,
            from = TransformProperties(scaleX = 0.97f, scaleY = 0.97f),
            to = TransformProperties(alpha = 1f, scaleX = 1f, scaleY = 1f),
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