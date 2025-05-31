package com.nomanr.animate.compose.sample.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
//                durationMillis = 5000
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
fun SkewGrid(
    skewX: Float,
    lines: Int = 10,
    sizeDp: Dp = 150.dp
) {

    Box(Modifier.size(sizeDp).border(1.dp, Color.Black).drawWithCache {
        // precompute a 4×4 column-major skew matrix
        val mat4 = Matrix(
            floatArrayOf(
                1f, 0f, 0f, 0f,
                skewX, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        )
        onDrawWithContent {

            drawContext.canvas.skew(skewX, 0f)

            drawContent()

            drawContext.canvas.restore()
        }
    }) {
        Box(Modifier.size(sizeDp).background(Color.Green))
    }

//    Canvas(Modifier.size(sizeDp).border(1.dp, Color.Black)) {
//        val w = size.width
//        val h = size.height
//        val cx = w / 2f
//        val cy = h / 2f
//
//
//        // 2) save, pivot to center, concat, pivot back
//        drawContext.canvas.skew(skewX, 0f)
//
//        // 3) draw your grid
//        val stepX = w / lines
//        val stepY = h / lines
//        for (i in 0..lines) {
//            drawLine(Color.Green, Offset(0f, i*stepY), Offset(w, i*stepY),   2f)
//            drawLine(Color.Green, Offset(i*stepX, 0f),    Offset(i*stepX, h), 2f)
//        }
//        drawLine(Color.Green, Offset.Zero, Offset(w, h), 4f)
//
//        // 4) restore so nothing else is skewed
//        drawContext.canvas.restore()
//    }
}