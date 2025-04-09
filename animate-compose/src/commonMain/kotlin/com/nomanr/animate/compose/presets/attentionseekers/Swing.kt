package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Swing(
    swingAngle: Float = 15f
) : AnimationPreset {

    private val ease = FastOutSlowInEasing

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.20f,
            from = TransformProperties(rotationZ = 0f),
            to = TransformProperties(rotationZ = swingAngle),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.20f,
            end = 0.40f,
            from = TransformProperties(rotationZ = swingAngle),
            to = TransformProperties(rotationZ = -swingAngle * 0.66f),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.40f,
            end = 0.60f,
            from = TransformProperties(rotationZ = -swingAngle * 0.66f),
            to = TransformProperties(rotationZ = swingAngle * 0.33f),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.60f,
            end = 0.80f,
            from = TransformProperties(rotationZ = swingAngle * 0.33f),
            to = TransformProperties(rotationZ = -swingAngle * 0.33f),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.80f,
            end = 1f,
            from = TransformProperties(rotationZ = -swingAngle * 0.33f),
            to = TransformProperties(rotationZ = 0f),
            easing = ease
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin(0.5f, 0f)
        )
    }
}