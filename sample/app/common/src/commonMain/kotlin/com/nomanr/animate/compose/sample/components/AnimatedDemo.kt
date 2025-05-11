package com.nomanr.animate.compose.sample.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (trigger.second) {
            Animated(
                preset = animation.preset,
                state = animationState,
//                durationMillis = 3000
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

@Composable
fun SlideVisibilityDemo() {
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isVisible,
            exit = slideOutHorizontally(targetOffsetX = { -it } // Slide left off-screen
            ),
            enter = slideInHorizontally(initialOffsetX = { -it } // Slide in from left
            )) {
            Box(
                modifier = Modifier.size(200.dp).background(Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Sliding Box", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { isVisible = !isVisible }) {
            Text(text = if (isVisible) "Hide" else "Show")
        }
    }
}