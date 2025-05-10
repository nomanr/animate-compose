package com.nomanr.animate.compose.presets.backexists

import androidx.compose.animation.core.EaseOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.LayoutInfo
import com.nomanr.animate.compose.core.NeedsLayoutInfo
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.core.animateKeyframe

class BackOutLeft(
    private val exitOffsetX: Float = -2000f
) : AnimationPreset, NeedsLayoutInfo {

    private var translationX by mutableStateOf(exitOffsetX)

    private val keyframes: List<Keyframe>
        get() = listOf(
            Keyframe.Static(
                percent = 0f,
                transform = TransformProperties(scaleX = 1f, scaleY = 1f, alpha = 1f),
                easing = EaseOut
            ),
            Keyframe.Segment(
                start = 0f,
                end = 0.2f,
                from = TransformProperties(scaleX = 1f, scaleY = 1f, alpha = 1f),
                to = TransformProperties(scaleX = 0.7f, scaleY = 0.7f, alpha = 0.7f),
                easing = EaseOut
            ),
            Keyframe.Segment(
                start = 0.2f,
                end = 1f,
                from = TransformProperties(translationX = 0f, scaleX = 0.7f, scaleY = 0.7f, alpha = 0.7f),
                to = TransformProperties(translationX = translationX, scaleX = 0.7f, scaleY = 0.7f, alpha = 0.7f),
                easing = EaseOut
            )
        )

    override fun setLayoutInfo(layoutInfo: LayoutInfo) {
        translationX = -(layoutInfo.containerWidth - (layoutInfo.x  + layoutInfo.width))
    }

    @Composable
    override fun animate(progress: State<Float>): Modifier {
        return Modifier.animateKeyframe(
            progress = progress,
            keyframes = keyframes,
            transformOrigin = TransformOrigin.Center,
            clip = false
        )
    }
}