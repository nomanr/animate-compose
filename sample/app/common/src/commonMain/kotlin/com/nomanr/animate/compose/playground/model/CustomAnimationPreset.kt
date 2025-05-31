package com.nomanr.animate.compose.playground.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.animateKeyframe

class CustomAnimationPreset(
    private val keyframes: List<Keyframe>,
    private val transformOrigin: TransformOrigin = TransformOrigin.Center,
    private val clip: Boolean = false
) : AnimationPreset {

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = transformOrigin,
            clip = clip
        )
    }
}