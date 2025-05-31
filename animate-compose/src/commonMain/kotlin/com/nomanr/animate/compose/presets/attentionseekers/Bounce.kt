package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Bounce(bounceHeightInPx: Float = 60f) : AnimationPreset {

    private val bounceEasing = CubicBezierEasing(0.3f, 0.05f, 0.7f, 0.9f)
    private val fallEasing = CubicBezierEasing(0.6f, 0.05f, 0.9f, 0.6f)

    private val keyframes = listOf(
        Keyframe.Static(
            percent = 0f,
            transform = TransformProperties(translationY = 0f, scaleY = 1f),
            easing = bounceEasing
        ),

        Keyframe.Segment(
            start = 0.15f,
            end = 0.35f,
            from = TransformProperties(translationY = 0f, scaleY = 1f),
            to = TransformProperties(translationY = -bounceHeightInPx, scaleY = 1.09f),
            easing = bounceEasing
        ),

        Keyframe.Segment(
            start = 0.35f,
            end = 0.43f,
            from = TransformProperties(translationY = -bounceHeightInPx, scaleY = 1.09f),
            to = TransformProperties(translationY = -bounceHeightInPx, scaleY = 1.1f),
            easing = null
        ),

        Keyframe.Segment(
            start = 0.43f,
            end = 0.53f,
            from = TransformProperties(translationY = -bounceHeightInPx, scaleY = 1.1f),
            to = TransformProperties(translationY = 0f, scaleY = 1.1f),
            easing = fallEasing
        ),

        Keyframe.Segment(
            start = 0.53f,
            end = 0.70f,
            from = TransformProperties(translationY = 0f, scaleY = 1.1f),
            to = TransformProperties(translationY = -bounceHeightInPx / 2f, scaleY = 1f),
            easing = bounceEasing
        ),

        Keyframe.Segment(
            start = 0.70f,
            end = 0.80f,
            from = TransformProperties(translationY = -bounceHeightInPx / 2.1f, scaleY = 1f),
            to = TransformProperties(translationY = 0f, scaleY = 0.95f),
            easing = fallEasing
        ),

        Keyframe.Segment(
            start = 0.80f,
            end = 0.90f,
            from = TransformProperties(translationY = 0f, scaleY = 0.95f),
            to = TransformProperties(translationY = -bounceHeightInPx / 7.5f, scaleY = 1.02f),
            easing = bounceEasing
        ),

        Keyframe.Segment(
            start = 0.90f,
            end = 1f,
            from = TransformProperties(translationY = -bounceHeightInPx / 7.5f, scaleY = 1.02f),
            to = TransformProperties(translationY = 0f, scaleY = 1f),
            easing = bounceEasing
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin(0.5f, 0.5f),
            clip = false
        )
    }
}

