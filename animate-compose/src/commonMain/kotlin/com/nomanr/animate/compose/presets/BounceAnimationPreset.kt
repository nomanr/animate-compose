package com.nomanr.animate.compose.presets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.nomanr.animate.compose.core.AnimationPreset

object BounceAnimationPreset : AnimationPreset {
    @Composable
    override fun animate(progressState: State<Float>): Modifier {
        return Modifier.graphicsLayer {
            clip = false
            transformOrigin = TransformOrigin(0.5f, 1f)
            val progress = progressState.value
            val (translationY, scaleY) = when {
                progress < 0.2f -> 0f to 1f
                progress < 0.4f -> {
                    val fraction = (progress - 0.2f) / 0.2f
                    lerp(0f, -30f, fraction) to lerp(1f, 1.1f, fraction)
                }
                progress < 0.43f -> -30f to 1.1f
                progress < 0.53f -> {
                    val fraction = (progress - 0.43f) / 0.1f
                    lerp(-30f, 0f, fraction) to lerp(1.1f, 1f, fraction)
                }
                progress < 0.7f -> {
                    val fraction = (progress - 0.53f) / 0.17f
                    lerp(0f, -15f, fraction) to lerp(1f, 1.05f, fraction)
                }
                progress < 0.8f -> {
                    val fraction = (progress - 0.7f) / 0.1f
                    lerp(-15f, 0f, fraction) to lerp(1.05f, 0.95f, fraction)
                }
                progress < 0.9f -> {
                    val fraction = (progress - 0.8f) / 0.1f
                    lerp(0f, -4f, fraction) to lerp(0.95f, 1.02f, fraction)
                }
                else -> {
                    val fraction = (progress - 0.9f) / 0.1f
                    lerp(-4f, 0f, fraction) to lerp(1.02f, 1f, fraction)
                }
            }
            this.translationY = translationY
            this.scaleY = scaleY
            scaleX = 1f
            alpha = 1f
        }
    }

}