package com.nomanr.animate.compose.playground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nomanr.animate.compose.core.Keyframe

data class KeyframePresetState(
    val keyframes: List<Keyframe>,
    val duration: Float,
    val originX: Float,
    val originY: Float,
    val updateKeyframes: (List<Keyframe>) -> Unit,
    val updateDuration: (Float) -> Unit,
    val updateOrigin: (Float, Float) -> Unit
)

val LocalKeyframePreset = compositionLocalOf<KeyframePresetState> {
    error("No KeyframePresetState provided")
}

@Composable
fun KeyframeProvider(content: @Composable () -> Unit) {
    var keyframes by remember {
        mutableStateOf(listOf<Keyframe>())
    }

    var duration by remember { mutableStateOf(2f) }
    var originX by remember { mutableStateOf(0.5f) }
    var originY by remember { mutableStateOf(0.5f) }

    val presetState = KeyframePresetState(
        keyframes = keyframes,
        duration = duration,
        originX = originX,
        originY = originY,
        updateKeyframes = { keyframes = it },
        updateDuration = { duration = it },
        updateOrigin = { x, y ->
            originX = x
            originY = y
        }
    )

    CompositionLocalProvider(LocalKeyframePreset provides presetState) {
        content()
    }
}