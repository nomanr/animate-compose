package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Tada(
    scaleAmount: Float = 0.1f,
    rotationAngle: Float = 3f
) : AnimationPreset {

    private val ease = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)
    private val minScale = 1f - scaleAmount
    private val maxScale = 1f + scaleAmount

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.1f,
            from = TransformProperties(scaleX = 1f, scaleY = 1f, rotationZ = 0f),
            to = TransformProperties(
                scaleX = minScale,
                scaleY = minScale,
                rotationZ = -rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.1f,
            end = 0.2f,
            from = TransformProperties(
                scaleX = minScale,
                scaleY = minScale,
                rotationZ = -rotationAngle
            ),
            to = TransformProperties(
                scaleX = minScale,
                scaleY = minScale,
                rotationZ = -rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.2f,
            end = 0.3f,
            from = TransformProperties(
                scaleX = minScale,
                scaleY = minScale,
                rotationZ = -rotationAngle
            ),
            to = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.3f,
            end = 0.4f,
            from = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = rotationAngle
            ),
            to = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = -rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.4f,
            end = 0.5f,
            from = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = -rotationAngle
            ),
            to = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.5f,
            end = 0.6f,
            from = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = rotationAngle
            ),
            to = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = -rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.6f,
            end = 0.7f,
            from = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = -rotationAngle
            ),
            to = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.7f,
            end = 0.8f,
            from = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = rotationAngle
            ),
            to = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = -rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.8f,
            end = 0.9f,
            from = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = -rotationAngle
            ),
            to = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = rotationAngle
            ),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.9f,
            end = 1f,
            from = TransformProperties(
                scaleX = maxScale,
                scaleY = maxScale,
                rotationZ = rotationAngle
            ),
            to = TransformProperties(scaleX = 1f, scaleY = 1f, rotationZ = 0f),
            easing = ease
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(progress, keyframes)
    }
}