package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Wobble(
    maxOffsetPx: Float = 80f,
    maxRotation: Float = 5f
) : AnimationPreset {

    private val ease = FastOutSlowInEasing

    private val keyframes = listOf(
        Keyframe.Segment(
            0f, 0.15f,
            from = TransformProperties(translationX = 0f, rotationZ = 0f),
            to = TransformProperties(translationX = -maxOffsetPx, rotationZ = -maxRotation),
            easing = ease
        ),
        Keyframe.Segment(
            0.15f, 0.30f,
            from = TransformProperties(translationX = -maxOffsetPx, rotationZ = -maxRotation),
            to = TransformProperties(
                translationX = maxOffsetPx * 0.8f,
                rotationZ = maxRotation * 0.6f
            ),
            easing = ease
        ),
        Keyframe.Segment(
            0.30f, 0.45f,
            from = TransformProperties(
                translationX = maxOffsetPx * 0.8f,
                rotationZ = maxRotation * 0.6f
            ),
            to = TransformProperties(
                translationX = -maxOffsetPx * 0.6f,
                rotationZ = -maxRotation * 0.6f
            ),
            easing = ease
        ),
        Keyframe.Segment(
            0.45f, 0.60f,
            from = TransformProperties(
                translationX = -maxOffsetPx * 0.6f,
                rotationZ = -maxRotation * 0.6f
            ),
            to = TransformProperties(
                translationX = maxOffsetPx * 0.4f,
                rotationZ = maxRotation * 0.4f
            ),
            easing = ease
        ),
        Keyframe.Segment(
            0.60f, 0.75f,
            from = TransformProperties(
                translationX = maxOffsetPx * 0.4f,
                rotationZ = maxRotation * 0.4f
            ),
            to = TransformProperties(
                translationX = -maxOffsetPx * 0.2f,
                rotationZ = -maxRotation * 0.2f
            ),
            easing = ease
        ),
        Keyframe.Segment(
            0.75f, 1f,
            from = TransformProperties(
                translationX = -maxOffsetPx * 0.2f,
                rotationZ = -maxRotation * 0.2f
            ),
            to = TransformProperties(translationX = 0f, rotationZ = 0f),
            easing = ease
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(progress, keyframes)
    }
}