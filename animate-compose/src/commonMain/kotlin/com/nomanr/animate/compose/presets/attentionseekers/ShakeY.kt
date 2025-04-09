package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class ShakeY(
    offsetY: Float = 16f
) : AnimationPreset {

    private val ease = FastOutSlowInEasing

    private val keyframes = listOf(
        shake(0f, 0.1f, 0f, -offsetY),
        shake(0.1f, 0.2f, -offsetY, offsetY),
        shake(0.2f, 0.3f, offsetY, -offsetY),
        shake(0.3f, 0.4f, -offsetY, offsetY),
        shake(0.4f, 0.5f, offsetY, -offsetY),
        shake(0.5f, 0.6f, -offsetY, offsetY),
        shake(0.6f, 0.7f, offsetY, -offsetY),
        shake(0.7f, 0.8f, -offsetY, offsetY),
        shake(0.8f, 0.9f, offsetY, -offsetY),
        shake(0.9f, 1f, -offsetY, 0f)
    )

    private fun shake(start: Float, end: Float, from: Float, to: Float) = Keyframe.Segment(
        start = start,
        end = end,
        from = TransformProperties(translationY = from),
        to = TransformProperties(translationY = to),
        easing = ease
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(progress, keyframes)
    }
}