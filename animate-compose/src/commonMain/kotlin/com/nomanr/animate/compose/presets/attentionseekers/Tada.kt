package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object Tada : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val p = progress.value
            val (scale, rotation) = when {
                p < 0.1f -> {
                    val fraction = p / 0.1f
                    lerp(1f, 0.9f, fraction) to lerp(0f, -3f, fraction)
                }
                p < 0.2f -> 0.9f to -3f
                p < 0.3f -> {
                    val fraction = (p - 0.2f) / 0.1f
                    lerp(0.9f, 1.1f, fraction) to lerp(-3f, 3f, fraction)
                }
                p < 0.4f -> {
                    val fraction = (p - 0.3f) / 0.1f
                    1.1f to lerp(3f, -3f, fraction)
                }
                p < 0.5f -> {
                    val fraction = (p - 0.4f) / 0.1f
                    1.1f to lerp(-3f, 3f, fraction)
                }
                p < 0.6f -> {
                    val fraction = (p - 0.5f) / 0.1f
                    1.1f to lerp(3f, -3f, fraction)
                }
                p < 0.7f -> {
                    val fraction = (p - 0.6f) / 0.1f
                    1.1f to lerp(-3f, 3f, fraction)
                }
                p < 0.8f -> {
                    val fraction = (p - 0.7f) / 0.1f
                    1.1f to lerp(3f, -3f, fraction)
                }
                p < 0.9f -> {
                    val fraction = (p - 0.8f) / 0.1f
                    1.1f to lerp(-3f, 3f, fraction)
                }
                else -> {
                    val fraction = (p - 0.9f) / 0.1f
                    lerp(1.1f, 1f, fraction) to lerp(3f, 0f, fraction)
                }
            }
            scaleX = scale
            scaleY = scale
            rotationZ = rotation
            translationX = 0f
            translationY = 0f
            alpha = 1f
        }
    }
}