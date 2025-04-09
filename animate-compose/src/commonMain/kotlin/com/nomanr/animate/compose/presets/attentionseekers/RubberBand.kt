package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object RubberBand : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val progressValue = progress.value
            val (scaleXValue, scaleYValue) = when {
                progressValue < 0.3f -> {
                    val fraction = progressValue / 0.3f
                    lerp(1f, 1.25f, fraction) to lerp(1f, 0.75f, fraction)
                }
                progressValue < 0.4f -> {
                    val fraction = (progressValue - 0.3f) / 0.1f
                    lerp(1.25f, 0.75f, fraction) to lerp(0.75f, 1.25f, fraction)
                }
                progressValue < 0.5f -> {
                    val fraction = (progressValue - 0.4f) / 0.1f
                    lerp(0.75f, 1.15f, fraction) to lerp(1.25f, 0.85f, fraction)
                }
                progressValue < 0.65f -> {
                    val fraction = (progressValue - 0.5f) / 0.15f
                    lerp(1.15f, 0.95f, fraction) to lerp(0.85f, 1.05f, fraction)
                }
                progressValue < 0.75f -> {
                    val fraction = (progressValue - 0.65f) / 0.1f
                    lerp(0.95f, 1.05f, fraction) to lerp(1.05f, 0.95f, fraction)
                }
                else -> {
                    val fraction = (progressValue - 0.75f) / 0.25f
                    lerp(1.05f, 1f, fraction) to lerp(0.95f, 1f, fraction)
                }
            }
            scaleX = scaleXValue
            scaleY = scaleYValue
            translationX = 0f
            translationY = 0f
            alpha = 1f
        }
    }
}