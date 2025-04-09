package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object Swing : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            transformOrigin = TransformOrigin(0.5f, 0f)
            val p = progress.value
            val rotationZValue = when {
                p < 0.20f -> lerp(0f, 15f, p / 0.20f)
                p < 0.40f -> lerp(15f, -10f, (p - 0.20f) / 0.20f)
                p < 0.60f -> lerp(-10f, 5f, (p - 0.40f) / 0.20f)
                p < 0.80f -> lerp(5f, -5f, (p - 0.60f) / 0.20f)
                else -> lerp(-5f, 0f, (p - 0.80f) / 0.20f)
            }
            rotationZ = rotationZValue
            translationX = 0f
            translationY = 0f
            scaleX = 1f
            scaleY = 1f
            alpha = 1f
        }
    }
}