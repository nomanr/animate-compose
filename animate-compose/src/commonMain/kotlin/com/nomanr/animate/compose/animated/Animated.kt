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
    animateOnEnter: Boolean = true,
    state: AnimatedState = rememberAnimatedState(),
    content: @Composable () -> Unit
) {
    LaunchedEffect(preset) {
        if (animateOnEnter || repeat) {
            state.animate()
        }
    }

    val progress = animationProgress(
        preset = preset,
        durationMillis = durationMillis,
        enabled = enabled,
        repeat = repeat,
        animatedState = state,
    )

    Layout(
        content = content, modifier = preset.animate(progress).then(modifier)
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
    private val _animationToken = mutableStateOf(0)
    private val _isAnimating = mutableStateOf(false)
    private val _isAnimationFinished = mutableStateOf<Boolean?>(null)

    val animationToken: State<Int> get() = _animationToken
    val isAnimating: State<Boolean> get() = _isAnimating
    val isAnimationFinished: State<Boolean?> get() = _isAnimationFinished

    fun animate() {
        _animationToken.value += 1
        _isAnimating.value = true
        _isAnimationFinished.value = false
    }

    fun stopAnimation() {
        _isAnimating.value = false
        _isAnimationFinished.value = true
    }

    fun reset() {
        _isAnimating.value = false
        _isAnimationFinished.value = null
    }
}

@Composable
fun rememberAnimatedState(): AnimatedState {
    return remember { AnimatedState() }
}

@Composable
private fun animationProgress(
    preset: AnimationPreset,
    durationMillis: Int,
    enabled: Boolean,
    repeat: Boolean,
    animatedState: AnimatedState?,
): State<Float> {
    return if (!enabled) {
        mutableStateOf(0f)
    } else if (repeat) {
        val infiniteTransition = rememberInfiniteTransition()
        infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    } else {
        val animationCounter = animatedState?.animationToken?.value ?: 0
        val animatable = remember(animationCounter, preset) { Animatable(0f) }

        LaunchedEffect(animationCounter) {
            if (animatedState?.isAnimating?.value == true) {
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = durationMillis, easing = LinearEasing),
                )
                animatedState.stopAnimation()
            } else {
                animatable.snapTo(1f)
            }
        }

        return animatable.asState()
    }
}

