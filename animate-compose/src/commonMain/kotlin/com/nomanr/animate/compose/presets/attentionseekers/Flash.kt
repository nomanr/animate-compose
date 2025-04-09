package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object Flash : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val progressValue = progress.value
            alpha = when {
                progressValue < 0.25f -> lerp(1f, 0f, progressValue / 0.25f)
                progressValue < 0.5f -> lerp(0f, 1f, (progressValue - 0.25f) / 0.25f)
                progressValue < 0.75f -> lerp(1f, 0f, (progressValue - 0.5f) / 0.25f)
                else -> lerp(0f, 1f, (progressValue - 0.75f) / 0.25f)
            }
            translationX = 0f
            translationY = 0f
            scaleX = 1f
            scaleY = 1f
        }
    }
}