package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Flash(minAlpha: Float = 0f, maxAlpha: Float = 1f) : AnimationPreset {

    private val ease = FastOutSlowInEasing

    private val keyframes = listOf<Keyframe>(
        Keyframe.Segment(
            start = 0f,
            end = 0.25f,
            from = TransformProperties(alpha = maxAlpha),
            to = TransformProperties(alpha = minAlpha),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.25f,
            end = 0.5f,
            from = TransformProperties(alpha = minAlpha),
            to = TransformProperties(alpha = maxAlpha),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.5f,
            end = 0.75f,
            from = TransformProperties(alpha = maxAlpha),
            to = TransformProperties(alpha = minAlpha),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.75f,
            end = 1f,
            from = TransformProperties(alpha = minAlpha),
            to = TransformProperties(alpha = maxAlpha),
            easing = ease
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
        )
    }
}