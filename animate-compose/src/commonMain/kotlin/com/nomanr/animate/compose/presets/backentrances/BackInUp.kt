package com.nomanr.animate.compose.presets.backentrances

import androidx.compose.animation.core.EaseOut
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.*

class BackInUp(
    private val entranceOffsetY: Float = 1200f // positive because it enters from below
) : AnimationPreset, NeedsLayoutInfo {

    private var translationY by mutableStateOf(entranceOffsetY)

    private val keyframes: List<Keyframe>
        get() = listOf(
            Keyframe.Static(
                percent = 0f,
                transform = TransformProperties(
                    translationY = translationY,
                    scaleX = 0.7f,
                    scaleY = 0.7f,
                    alpha = 0.7f
                ),
                easing = EaseOut
            ),
            Keyframe.Segment(
                start = 0f,
                end = 0.8f,
                from = TransformProperties(
                    translationY = translationY,
                    scaleX = 0.7f,
                    scaleY = 0.7f,
                    alpha = 0.7f
                ),
                to = TransformProperties(
                    translationY = 0f,
                    scaleX = 0.7f,
                    scaleY = 0.7f,
                    alpha = 0.7f
                ),
                easing = EaseOut
            ),
            Keyframe.Segment(
                start = 0.8f,
                end = 1f,
                from = TransformProperties(
                    translationY = 0f,
                    scaleX = 0.7f,
                    scaleY = 0.7f,
                    alpha = 0.7f
                ),
                to = TransformProperties(
                    translationY = 0f,
                    scaleX = 1f,
                    scaleY = 1f,
                    alpha = 1f
                ),
                easing = EaseOut
            )
        )

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin.Center,
            clip = false
        )
    }

    override fun setLayoutInfo(layoutInfo: LayoutInfo) {
        translationY = (layoutInfo.containerHeight - layoutInfo.y) + 40f
    }
}