package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class ShakeX(
    offsetX: Float = 16f
) : AnimationPreset {

    private val ease = FastOutSlowInEasing

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.1f,
            from = TransformProperties(translationX = 0f),
            to = TransformProperties(translationX = -offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.1f,
            end = 0.2f,
            from = TransformProperties(translationX = -offsetX),
            to = TransformProperties(translationX = offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.2f,
            end = 0.3f,
            from = TransformProperties(translationX = offsetX),
            to = TransformProperties(translationX = -offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.3f,
            end = 0.4f,
            from = TransformProperties(translationX = -offsetX),
            to = TransformProperties(translationX = offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.4f,
            end = 0.5f,
            from = TransformProperties(translationX = offsetX),
            to = TransformProperties(translationX = -offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.5f,
            end = 0.6f,
            from = TransformProperties(translationX = -offsetX),
            to = TransformProperties(translationX = offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.6f,
            end = 0.7f,
            from = TransformProperties(translationX = offsetX),
            to = TransformProperties(translationX = -offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.7f,
            end = 0.8f,
            from = TransformProperties(translationX = -offsetX),
            to = TransformProperties(translationX = offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.8f,
            end = 0.9f,
            from = TransformProperties(translationX = offsetX),
            to = TransformProperties(translationX = -offsetX),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.9f,
            end = 1f,
            from = TransformProperties(translationX = -offsetX),
            to = TransformProperties(translationX = 0f),
            easing = ease
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(progress, keyframes)
    }
}