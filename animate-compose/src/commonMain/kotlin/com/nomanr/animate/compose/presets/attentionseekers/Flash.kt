package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.atProgress
import com.nomanr.animate.compose.core.interpolate

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
        return Modifier.graphicsLayer {
            val p = progress.value

            val current = keyframes.atProgress(p)
            println(current.toString())
            val transform = when (current) {
                is Keyframe.Segment -> {
                    val fraction = (p - current.start) / (current.end - current.start)
                    val eased = current.easing?.transform(fraction) ?: 1f
                    current.from.interpolate(current.to, eased)
                }

                is Keyframe.Static -> current.transform
                else -> TransformProperties()
            }

            transform.alpha?.let { this.alpha = it }
        }
    }
}