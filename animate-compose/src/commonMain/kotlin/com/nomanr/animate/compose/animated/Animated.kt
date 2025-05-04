package com.nomanr.animate.compose.animated

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import com.nomanr.animate.compose.core.AnimationPreset

@Composable
fun Animated(
    modifier: Modifier = Modifier,
    preset: AnimationPreset,
    durationMillis: Int = 1000,
    enabled: Boolean = true,
    repeat: Boolean = false,
    animateOnEnter: Boolean = false,
    state: AnimatedState = rememberAnimatedState(),
    content: @Composable () -> Unit
) {
    LaunchedEffect(preset) {
        if (animateOnEnter || repeat) {
            state.animate()
        }
    }

    val progress = animationProgress(
        durationMillis = durationMillis,
        enabled = enabled,
        repeat = repeat,
        animatedState = state,
    )

    Layout(
        content = content,
        modifier = preset.animate(progress).then(modifier)
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val width = placeables.maxOfOrNull { it.width } ?: constraints.minWidth
        val height = placeables.maxOfOrNull { it.height } ?: constraints.minHeight
        layout(width, height) {
            placeables.forEach { it.placeRelative(0, 0) }
        }
    }
}


class AnimatedState {
    private val _animationCounter = mutableStateOf(0)
    private val _isAnimating = mutableStateOf(false)
    private val _isAnimationCompleted = mutableStateOf<Boolean?>(null)

    val animationCounter: State<Int> get() = _animationCounter
    val isAnimating: State<Boolean> get() = _isAnimating
    val isAnimationCompleted: State<Boolean?> get() = _isAnimationCompleted

    fun animate() {
        _animationCounter.value += 1
        _isAnimating.value = true
        _isAnimationCompleted.value = false
    }

    fun stopAnimation() {
        _isAnimating.value = false
        _isAnimationCompleted.value = true
    }
}

@Composable
fun rememberAnimatedState(): AnimatedState {
    return remember { AnimatedState() }
}

@Composable
private fun animationProgress(
    durationMillis: Int,
    enabled: Boolean,
    repeat: Boolean,
    animatedState: AnimatedState?,
): State<Float> {
    return if (!enabled) {
        remember { mutableStateOf(1f) }
    } else if (repeat) {
        val infiniteTransition = rememberInfiniteTransition()
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    } else {
        val animationCounter = animatedState?.animationCounter?.value ?: 0
        val animatable = remember { Animatable(0f) }

        LaunchedEffect(animationCounter) {
            if (animatedState?.isAnimating?.value == true) {
                animatable.snapTo(0f)
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = durationMillis, easing = LinearEasing),
                )
                animatedState.stopAnimation()
            } else {
                animatable.snapTo(1f)
            }
        }

        val progressState = remember { mutableStateOf(animatable.value) }
        LaunchedEffect(animatable) {
            snapshotFlow { animatable.value }.collect { progressState.value = it }
        }

        progressState
    }
}