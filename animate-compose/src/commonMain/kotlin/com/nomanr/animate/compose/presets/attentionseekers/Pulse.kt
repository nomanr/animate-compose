package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object Pulse : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val progressValue = progress.value
            val scale = if (progressValue < 0.5f) lerp(1f, 1.05f, progressValue / 0.5f) else lerp(1.05f, 1f, (progressValue - 0.5f) / 0.5f)
            scaleX = scale
            scaleY = scale
            translationX = 0f
            translationY = 0f
            alpha = 1f
        }
    }
}