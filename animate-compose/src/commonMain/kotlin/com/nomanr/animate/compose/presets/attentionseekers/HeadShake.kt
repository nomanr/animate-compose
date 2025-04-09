package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object HeadShake : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val progressValue = progress.value
            val (translationXValue, rotationYValue) = when {
                progressValue < 0.065f -> {
                    val fraction = progressValue / 0.065f
                    lerp(0f, -6f, fraction) to lerp(0f, -9f, fraction)
                }
                progressValue < 0.185f -> {
                    val fraction = (progressValue - 0.065f) / (0.185f - 0.065f)
                    lerp(-6f, 5f, fraction) to lerp(-9f, 7f, fraction)
                }
                progressValue < 0.315f -> {
                    val fraction = (progressValue - 0.185f) / (0.315f - 0.185f)
                    lerp(5f, -3f, fraction) to lerp(7f, -5f, fraction)
                }
                progressValue < 0.435f -> {
                    val fraction = (progressValue - 0.315f) / (0.435f - 0.315f)
                    lerp(-3f, 2f, fraction) to lerp(-5f, 3f, fraction)
                }
                progressValue < 0.5f -> {
                    val fraction = (progressValue - 0.435f) / (0.5f - 0.435f)
                    lerp(2f, 0f, fraction) to lerp(3f, 0f, fraction)
                }
                else -> {
                    0f to 0f
                }
            }

            translationX = translationXValue
            translationY = 0f
            scaleX = 1f
            scaleY = 1f
            alpha = 1f
        }
    }
}