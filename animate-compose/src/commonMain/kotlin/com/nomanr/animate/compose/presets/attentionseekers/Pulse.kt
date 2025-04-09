package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.EaseInOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class Pulse(maxPulseScale: Float = 1.06f) : AnimationPreset {
    private val keyframes = listOf<Keyframe>(
        Keyframe.Segment(
            start = 0f,
            end = 0.5f,
            from = TransformProperties(scaleX = 1f, scaleY = 1f),
            to = TransformProperties(scaleX = maxPulseScale, scaleY = maxPulseScale),
            easing = EaseInOut
        ),
        Keyframe.Segment(
            start = 0.5f,
            end = 1f,
            from = TransformProperties(scaleX = maxPulseScale, scaleY = maxPulseScale),
            to = TransformProperties(scaleX = 1f, scaleY = 1f),
            easing = EaseInOut
        )
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes
        )
    }
}