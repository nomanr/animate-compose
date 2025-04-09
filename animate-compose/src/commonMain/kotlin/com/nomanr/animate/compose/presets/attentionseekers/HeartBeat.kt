package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object HeartBeat : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val progressValue = progress.value
            val scale = when {
                progressValue < 0.14f -> lerp(1f, 1.3f, progressValue / 0.14f)
                progressValue < 0.28f -> lerp(1.3f, 1f, (progressValue - 0.14f) / 0.14f)
                progressValue < 0.42f -> lerp(1f, 1.3f, (progressValue - 0.28f) / 0.14f)
                progressValue < 0.70f -> lerp(1.3f, 1f, (progressValue - 0.42f) / 0.28f)
                else -> 1f
            }
            scaleX = scale
            scaleY = scale
            translationX = 0f
            translationY = 0f
            alpha = 1f
        }
    }
}