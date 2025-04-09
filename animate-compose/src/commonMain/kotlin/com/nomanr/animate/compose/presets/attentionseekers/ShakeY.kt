package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object ShakeY : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val p = progress.value
            val translationYValue = when {
                p < 0.10f -> lerp(0f, -10f, p / 0.10f)
                p < 0.20f -> lerp(-10f, 10f, (p - 0.10f) / 0.10f)
                p < 0.30f -> lerp(10f, -10f, (p - 0.20f) / 0.10f)
                p < 0.40f -> lerp(-10f, 10f, (p - 0.30f) / 0.10f)
                p < 0.50f -> lerp(10f, -10f, (p - 0.40f) / 0.10f)
                p < 0.60f -> lerp(-10f, 10f, (p - 0.50f) / 0.10f)
                p < 0.70f -> lerp(10f, -10f, (p - 0.60f) / 0.10f)
                p < 0.80f -> lerp(-10f, 10f, (p - 0.70f) / 0.10f)
                p < 0.90f -> lerp(10f, -10f, (p - 0.80f) / 0.10f)
                else -> lerp(-10f, 0f, (p - 0.90f) / 0.10f)
            }
            translationY = translationYValue
            translationX = 0f
            scaleX = 1f
            scaleY = 1f
            alpha = 1f
        }
    }
}