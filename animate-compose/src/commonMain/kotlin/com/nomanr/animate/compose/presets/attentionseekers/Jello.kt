package com.nomanr.animate.compose.presets.attentionseekers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset
import kotlin.math.tan

object Jello : AnimationPreset {
    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            val progressValue = progress.value
            val skewDegrees = when {
                progressValue < 0.111f -> 0f
                progressValue < 0.222f -> lerp(0f, -12.5f, (progressValue - 0.111f) / (0.222f - 0.111f))
                progressValue < 0.333f -> lerp(-12.5f, 6.25f, (progressValue - 0.222f) / (0.333f - 0.222f))
                progressValue < 0.444f -> lerp(6.25f, -3.125f, (progressValue - 0.333f) / (0.444f - 0.333f))
                progressValue < 0.555f -> lerp(-3.125f, 1.5625f, (progressValue - 0.444f) / (0.555f - 0.444f))
                progressValue < 0.666f -> lerp(1.5625f, -0.78125f, (progressValue - 0.555f) / (0.666f - 0.555f))
                progressValue < 0.777f -> lerp(-0.78125f, 0.390625f, (progressValue - 0.666f) / (0.777f - 0.666f))
                progressValue < 0.888f -> lerp(0.390625f, -0.1953125f, (progressValue - 0.777f) / (0.888f - 0.777f))
                else -> lerp(-0.1953125f, 0f, (progressValue - 0.888f) / (1.0f - 0.888f))
            }
//            val angleRad = skewDegrees.toDouble().toRadians().toFloat()
//            val skewFactor = tan(angleRad)
            transformOrigin = TransformOrigin(0.5f, 0.5f)
//            transform = { matrix ->
//                matrix.reset()
//                matrix.skew(skewFactor, skewFactor)
//            }
            translationX = 0f
            translationY = 0f
            scaleX = 1f
            scaleY = 1f
            alpha = 1f
        }
    }
}