package com.nomanr.animate.compose.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.animated.rememberAnimatedState
import com.nomanr.animate.compose.data.Animation
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun AnimatedDemo(animation: Animation) {
    val animationState = rememberAnimatedState()
    var trigger by remember { mutableStateOf(Pair(0, true)) }

    LaunchedEffect(animation) {
        trigger = Pair(trigger.first + 1, true)
        animationState.animate()
    }

    LaunchedEffect(animationState.isAnimationFinished.value) {
        if (animationState.isAnimationFinished.value == true) {
            trigger = Pair(trigger.first + 1, false)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().clipToBounds(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (trigger.second) {
            Animated(
                preset = animation.preset,
                state = animationState,
            ) {
                AnimatedContent()
            }
        } else {
            AnimatedContent()
        }
    }
}

@Composable
fun AnimatedContent() {
    Text(
        text = "Animated",
        modifier = Modifier.padding(16.dp),
        color = Color.Black,
        style = AppTheme.typography.h1.copy(fontWeight = FontWeight.Black, fontSize = 50.sp)
    )
}