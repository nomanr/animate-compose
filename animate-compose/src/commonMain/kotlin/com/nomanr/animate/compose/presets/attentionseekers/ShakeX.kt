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
        shake(0f, 0.1f, 0f, -offsetX),
        shake(0.1f, 0.2f, -offsetX, offsetX),
        shake(0.2f, 0.3f, offsetX, -offsetX),
        shake(0.3f, 0.4f, -offsetX, offsetX),
        shake(0.4f, 0.5f, offsetX, -offsetX),
        shake(0.5f, 0.6f, -offsetX, offsetX),
        shake(0.6f, 0.7f, offsetX, -offsetX),
        shake(0.7f, 0.8f, -offsetX, offsetX),
        shake(0.8f, 0.9f, offsetX, -offsetX),
        shake(0.9f, 1f, -offsetX, 0f)
    )

    private fun shake(start: Float, end: Float, from: Float, to: Float) = Keyframe.Segment(
        start = start,
        end = end,
        from = TransformProperties(translationX = from),
        to = TransformProperties(translationX = to),
        easing = ease
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(progress, keyframes)
    }
}