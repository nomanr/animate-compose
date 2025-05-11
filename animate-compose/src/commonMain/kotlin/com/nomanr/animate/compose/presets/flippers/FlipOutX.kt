package com.nomanr.animate.compose.presets.flippers

import androidx.compose.animation.core.CubicBezierEasing
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

class FlipOutX(
    private val cameraDistance: Dp = 8.dp
) : AnimationPreset {

    private val ease = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)

    private val keyframes = listOf(
        Keyframe.Segment(
            start = 0f,
            end = 0.3f,
            from = TransformProperties(rotationX = 0f, alpha = 1f),
            to = TransformProperties(rotationX = -20f, alpha = 1f),
            easing = ease
        ),
        Keyframe.Segment(
            start = 0.3f,
            end = 1f,
            from = TransformProperties(rotationX = -20f, alpha = 1f),
            to = TransformProperties(rotationX = 90f, alpha = 0f),
            easing = ease
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