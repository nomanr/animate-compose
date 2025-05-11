package com.nomanr.animate.compose.presets.flippers

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Flip(
    private val cameraDistance: Dp = 5.dp
) : AnimationPreset {

    private val easeOut = CubicBezierEasing(0f, 0f, 0.58f, 1f)
    private val easeIn = CubicBezierEasing(0.42f, 0f, 1f, 1f)

    private val rawKeyframes = listOf(
        Keyframe.Segment(
            start = 0f, end = 0.4f, from = TransformProperties(
                rotationY = 0f,
            ), to = TransformProperties(
                rotationY = 180f,
            ), easing = easeOut
        ), Keyframe.Segment(
            start = 0.4f, end = 0.5f, from = TransformProperties(
                rotationY = 180f,
            ), to = TransformProperties(
                rotationY = 190f,
            ), easing = easeIn
        ), Keyframe.Segment(
            start = 0.5f, end = 0.8f, from = TransformProperties(
                rotationY = 190f,
            ), to = TransformProperties(
                rotationY = 360f,
            ), easing = easeIn
        ), Keyframe.Segment(
            start = 0.8f, end = 1f, from = TransformProperties(
                rotationY = 360f,
            ), to = TransformProperties(
                rotationY = 360f,
            ), easing = easeIn
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        val density = LocalDensity.current
        val cameraDistancePx = with(density) {
            cameraDistance.toPx()
        }

        val frames = rawKeyframes.map { keyframe ->
            keyframe.copy(
                from = keyframe.from.copy(
                    cameraDistance = cameraDistancePx
                ), to = keyframe.to.copy(
                    cameraDistance = cameraDistancePx
                )
            )
        }

        return Modifier.animateKeyframe(
            progress = progress, keyframes = frames
        )
    }
}