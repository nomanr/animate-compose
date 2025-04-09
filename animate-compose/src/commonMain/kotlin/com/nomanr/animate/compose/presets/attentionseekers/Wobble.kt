package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object Wobble : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val p = progress.value
            val (translationXValue, rotationZValue) = when {
                p < 0.15f -> {
                    val fraction = p / 0.15f
                    lerp(0f, -25f, fraction) to lerp(0f, -5f, fraction)
                }
                p < 0.30f -> {
                    val fraction = (p - 0.15f) / 0.15f
                    lerp(-25f, 20f, fraction) to lerp(-5f, 3f, fraction)
                }
                p < 0.45f -> {
                    val fraction = (p - 0.30f) / 0.15f
                    lerp(20f, -15f, fraction) to lerp(3f, -3f, fraction)
                }
                p < 0.60f -> {
                    val fraction = (p - 0.45f) / 0.15f
                    lerp(-15f, 10f, fraction) to lerp(-3f, 2f, fraction)
                }
                p < 0.75f -> {
                    val fraction = (p - 0.60f) / 0.15f
                    lerp(10f, -5f, fraction) to lerp(2f, -1f, fraction)
                }
                else -> {
                    val fraction = (p - 0.75f) / 0.25f
                    lerp(-5f, 0f, fraction) to lerp(-1f, 0f, fraction)
                }
            }
            translationX = translationXValue
            rotationZ = rotationZValue
            translationY = 0f
            scaleX = 1f
            scaleY = 1f
            alpha = 1f
        }
    }
}