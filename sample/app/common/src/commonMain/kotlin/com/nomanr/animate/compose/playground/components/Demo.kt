package com.nomanr.animate.compose.playground.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.animated.rememberAnimatedState
import com.nomanr.animate.compose.playground.PlaygroundState
import com.nomanr.animate.compose.playground.model.CustomAnimationPreset
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text

@Composable

fun Demo(
    state: PlaygroundState, modifier: Modifier = Modifier
) {
    val animationState = rememberAnimatedState()
    var trigger by remember { mutableStateOf(Pair(0, true)) }
    var customAnimation by remember { mutableStateOf(CustomAnimationPreset(emptyList())) }


    LaunchedEffect(state.keyframes, state.originX, state.originY) {
        customAnimation =
            CustomAnimationPreset(state.keyframes, TransformOrigin(state.originX, state.originY))
    }

    LaunchedEffect(state.isPlaying) {
        if (state.isPlaying) {

            trigger = Pair(trigger.first + 1, true)
            animationState.animate()
        }
    }

    LaunchedEffect(animationState.isAnimationFinished.value) {
        if (animationState.isAnimationFinished.value == true) {
            trigger = Pair(trigger.first + 1, false)
        }
    }


    Column(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            if (trigger.second) {
                Animated(
                    preset = customAnimation,
                    state = animationState,
                    durationMillis = state.duration
                ) {
                    DemoContent()
                }
            } else {
                DemoContent()
            }
        }
    }
}

@Composable
private fun DemoContent() {
    Box(
        modifier = Modifier.size(120.dp).background(AppTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "DEMO", color = AppTheme.colors.onPrimary, style = AppTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold, fontSize = 18.sp
            )
        )
    }
}