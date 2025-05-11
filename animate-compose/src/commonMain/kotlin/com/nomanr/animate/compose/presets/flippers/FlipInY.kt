package com.nomanr.animate.compose.presets.flippers

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class FlipInY(
    private val cameraDistance: Dp = 8.dp
) : AnimationPreset {

    private val easeInOut = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.4f,
            from = TransformProperties(alpha = 0f, rotationY = 90f),
            to = TransformProperties(alpha = 0.6f, rotationY = -20f),
            easing = EaseIn
        ),
        Keyframe.Segment(
            start = 0.4f,
            end = 0.6f,
            from = TransformProperties(alpha = 0.6f, rotationY = -20f),
            to = TransformProperties(alpha = 1f, rotationY = 10f),
            easing = easeInOut
        ),
        Keyframe.Segment(
            start = 0.6f,
            end = 0.8f,
            from = TransformProperties(alpha = 1f, rotationY = 10f),
            to = TransformProperties(alpha = 1f, rotationY = -5f),
            easing = easeInOut
        ),
        Keyframe.Segment(
            start = 0.8f,
            end = 1f,
            from = TransformProperties(alpha = 1f, rotationY = -5f),
            to = TransformProperties(alpha = 1f, rotationY = 0f),
            easing = easeInOut
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        val density = LocalDensity.current
        val cameraDistancePx = with(density) { cameraDistance.toPx() }

        val frames = keyframes.map { keyframe ->
            keyframe.copy(
                from = keyframe.from.copy(cameraDistance = cameraDistancePx),
                to = keyframe.to.copy(cameraDistance = cameraDistancePx)
            )
        }

        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = frames,
            transformOrigin = TransformOrigin.Center,
            clip = false
        )
    }
}