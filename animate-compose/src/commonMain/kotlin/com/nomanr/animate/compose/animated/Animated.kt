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
    state: AnimatedState = rememberAnimatedState(),
    preset: AnimationPreset,
    durationMillis: Int = 1000,
    enabled: Boolean = true,
    repeat: Boolean = false,
    animateOnEnter: Boolean = false,
    content: @Composable () -> Unit
) {
    val progress = animationProgress(durationMillis, enabled, repeat, state, animateOnEnter)

// TODO: NOMAN - Add support for animating when preset changes
//    LaunchedEffect(preset) {
//        state.animate()
//    }

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
    private val _animationCounter = mutableStateOf(0)
    internal val animationCounter: State<Int> get() = _animationCounter
    fun animate() {
        _animationCounter.value += 1
    }
}

@Composable
fun rememberAnimatedState(): AnimatedState {
    return remember { AnimatedState() }
}

@Composable
private fun animationProgress(
    durationMillis: Int, enabled: Boolean, repeat: Boolean, animatedState: AnimatedState?, animateOnEnter: Boolean = false
): State<Float> {
    return if (!enabled) {
        remember { mutableStateOf(1f) }
    } else if (repeat) {
        val infiniteTransition = rememberInfiniteTransition()
        infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, easing = LinearEasing), repeatMode = RepeatMode.Restart
            )
        )
    } else {
        val animationCounter = animatedState?.animationCounter?.value ?: 0
        val animatable = remember { Animatable(0f) }
        LaunchedEffect(animationCounter, animateOnEnter) {
            // The animation is triggered if either:
            // 1. animateOnEnter is true, forcing the animation to run on composition, or
            // 2. the animation counter indicates that an explicit animation trigger (via animate()) has occurred.
            // If neither condition is met, we skip the animation by snapping immediately to the final state (1f).
            if (animateOnEnter || animationCounter > 0) {
                animatable.snapTo(0f)
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = durationMillis, easing = LinearEasing),
                )
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