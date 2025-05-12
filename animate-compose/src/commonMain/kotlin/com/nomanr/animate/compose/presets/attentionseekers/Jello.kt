package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Jello : AnimationPreset {

    private val ease = FastOutSlowInEasing

    private val keyframes = listOf(
        Keyframe.Segment(
            0f, 0.111f,
            from = TransformProperties(skewX = 0f, skewY = 0f),
            to = TransformProperties(skewX = 0.15f, skewY = 0.15f),
            easing = ease
        ),
        Keyframe.Segment(
            0.111f, 0.222f,
            from = TransformProperties(skewX = 0.15f, skewY = 0.15f),
            to = TransformProperties(skewX = -0.15f, skewY = -0.15f),
            easing = ease
        ),
        Keyframe.Segment(
            0.222f, 0.333f,
            from = TransformProperties(skewX = -0.15f, skewY = -0.15f),
            to = TransformProperties(skewX = 0.075f, skewY = 0.075f),
            easing = ease
        ),
        Keyframe.Segment(
            0.333f, 0.444f,
            from = TransformProperties(skewX = 0.075f, skewY = 0.075f),
            to = TransformProperties(skewX = -0.0375f, skewY = -0.0375f),
            easing = ease
        ),
        Keyframe.Segment(
            0.444f, 0.555f,
            from = TransformProperties(skewX = -0.0375f, skewY = -0.0375f),
            to = TransformProperties(skewX = 0.01875f, skewY = 0.01875f),
            easing = ease
        ),
        Keyframe.Segment(
            0.555f, 0.666f,
            from = TransformProperties(skewX = 0.01875f, skewY = 0.01875f),
            to = TransformProperties(skewX = -0.009375f, skewY = -0.009375f),
            easing = ease
        ),
        Keyframe.Segment(
            0.666f, 0.777f,
            from = TransformProperties(skewX = -0.009375f, skewY = -0.009375f),
            to = TransformProperties(skewX = 0.0046875f, skewY = 0.0046875f),
            easing = ease
        ),
        Keyframe.Segment(
            0.777f, 0.888f,
            from = TransformProperties(skewX = 0.0046875f, skewY = 0.0046875f),
            to = TransformProperties(skewX = -0.00234375f, skewY = -0.00234375f),
            easing = ease
        ),
        Keyframe.Segment(
            0.888f, 1f,
            from = TransformProperties(skewX = -0.00234375f, skewY = -0.00234375f),
            to = TransformProperties(),
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