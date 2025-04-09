package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.animateKeyframe
import com.nomanr.animate.compose.core.keyframeSegment

class HeartBeat(
    heartScale: Float = 1.25f,
) : AnimationPreset {

    private val easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)

    private val keyframes = listOf(
        keyframeSegment(0f, 0.14f, 1f, heartScale, easing),
        keyframeSegment(0.14f, 0.28f, heartScale, 1f, easing),
        keyframeSegment(0.28f, 0.42f, 1f, heartScale, easing),
        keyframeSegment(0.42f, 0.70f, heartScale, 1f, easing),
        keyframeSegment(0.70f, 1f, 1f, 1f, easing)
    )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(progress, keyframes)
    }
}