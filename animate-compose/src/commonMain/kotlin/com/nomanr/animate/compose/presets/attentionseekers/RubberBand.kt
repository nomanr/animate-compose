package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class RubberBand(
    exaggeration: Float = 0.2f,
) : AnimationPreset {

    private val ease = FastOutSlowInEasing

    private val stretchX = 1f + exaggeration
    private val squashY = 1f - exaggeration
    private val squashX = 1f - exaggeration
    private val stretchY = 1f + exaggeration

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.3f,
            from = TransformProperties(scaleX = 1f, scaleY = 1f),
            to = TransformProperties(scaleX = stretchX, scaleY = squashY),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.3f,
            end = 0.4f,
            from = TransformProperties(scaleX = stretchX, scaleY = squashY),
            to = TransformProperties(scaleX = squashX, scaleY = stretchY),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.4f,
            end = 0.5f,
            from = TransformProperties(scaleX = squashX, scaleY = stretchY),
            to = TransformProperties(scaleX = 1.15f, scaleY = 0.85f),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.5f,
            end = 0.65f,
            from = TransformProperties(scaleX = 1.15f, scaleY = 0.85f),
            to = TransformProperties(scaleX = 0.95f, scaleY = 1.05f),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.65f,
            end = 0.75f,
            from = TransformProperties(scaleX = 0.95f, scaleY = 1.05f),
            to = TransformProperties(scaleX = 1.05f, scaleY = 0.95f),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.75f,
            end = 1f,
            from = TransformProperties(scaleX = 1.05f, scaleY = 0.95f),
            to = TransformProperties(scaleX = 1f, scaleY = 1f),
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