package com.nomanr.animate.compose.sample.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    LaunchedEffect(animation) {
        animationState.animate()
    }

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Animated(
            preset = animation.preset, state = animationState
        ) {
            AnimatedContent()
        }

        Spacer(Modifier.height(40.dp))

    }
}

@Composable
fun AnimatedContent() {
    Text(
        text = "Animated.compose",
        modifier = Modifier.padding(16.dp),
        color = Color.Black,
        style = AppTheme.typography.h1.copy(fontWeight = FontWeight.Black, fontSize = 35.sp)
    )
}