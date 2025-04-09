package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class HeartBeat(heartScale: Float = 1.25f) : AnimationPreset {
    private val easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.14f,
            from = TransformProperties(scaleX = 1f, scaleY = 1f),
            to = TransformProperties(scaleX = heartScale, scaleY = heartScale),
            easing = easing
        ),
        Keyframe.Segment(
            start = 0.14f,
            end = 0.28f,
            from = TransformProperties(scaleX = heartScale, scaleY = heartScale),
            to = TransformProperties(scaleX = 1f, scaleY = 1f),
            easing = easing
        ),
        Keyframe.Segment(
            start = 0.28f,
            end = 0.42f,
            from = TransformProperties(scaleX = 1f, scaleY = 1f),
            to = TransformProperties(scaleX = heartScale, scaleY = heartScale),
            easing = easing
        ),
        Keyframe.Segment(
            start = 0.42f,
            end = 0.70f,
            from = TransformProperties(scaleX = heartScale, scaleY = heartScale),
            to = TransformProperties(scaleX = 1f, scaleY = 1f),
            easing = easing
        ),
        Keyframe.Segment(
            start = 0.70f,
            end = 1f,
            from = TransformProperties(scaleX = 1f, scaleY = 1f),
            to = TransformProperties(scaleX = 1f, scaleY = 1f),
            easing = easing
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(progress, keyframes)
    }
}