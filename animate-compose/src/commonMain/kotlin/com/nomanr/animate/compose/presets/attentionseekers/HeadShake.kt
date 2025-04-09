package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class HeadShake(shakeOffset: Float = 1f) : AnimationPreset {

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.065f,
            from = TransformProperties(translationX = 0f, scaleX = 1f),
            to = TransformProperties(translationX = -12f * shakeOffset, scaleX = 0.98f),
            easing = EaseInOut
        ),
        Keyframe.Segment(
            start = 0.065f,
            end = 0.185f,
            from = TransformProperties(translationX = -12f * shakeOffset, scaleX = 0.98f),
            to = TransformProperties(translationX = 10f * shakeOffset, scaleX = 1f),
            easing = EaseInOut
        ),
        Keyframe.Segment(
            start = 0.185f,
            end = 0.315f,
            from = TransformProperties(translationX = 10f * shakeOffset, scaleX = 1f),
            to = TransformProperties(translationX = -6f * shakeOffset, scaleX = 1f),
            easing = EaseInOut
        ),
        Keyframe.Segment(
            start = 0.315f,
            end = 0.435f,
            from = TransformProperties(translationX = -6f * shakeOffset, scaleX = 1f),
            to = TransformProperties(translationX = 4f * shakeOffset, scaleX = 1f),
            easing = EaseOut
        ),
        Keyframe.Segment(
            start = 0.435f,
            end = 0.5f,
            from = TransformProperties(translationX = 4f * shakeOffset, scaleX = 1f),
            to = TransformProperties(translationX = 0f, scaleX = 1f),
            easing = EaseOut
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes
        )
    }
}