package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.animated.rememberAnimatedState
import com.nomanr.animate.compose.playground.model.CustomAnimationPreset
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text

@Composable

fun Demo(
    timelineState: TimelineState,
    modifier: Modifier = Modifier
) {
    val animationState = rememberAnimatedState()
    var trigger by remember { mutableStateOf(Pair(0, true)) }
    
    val customAnimation by remember {
        derivedStateOf {
            val keyframes = timelineState.toKeyframes()
            if (keyframes.isNotEmpty()) {
                CustomAnimationPreset(keyframes)
            } else null
        }
    }

    LaunchedEffect(customAnimation) {
        if (customAnimation != null) {
            trigger = Pair(trigger.first + 1, true)
            animationState.animate()
        }
    }

    LaunchedEffect(animationState.isAnimationFinished.value) {
        if (animationState.isAnimationFinished.value == true) {
            trigger = Pair(trigger.first + 1, false)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(AppTheme.colors.surface.copy(alpha = 0.3f))
                .clipToBounds(),
            contentAlignment = Alignment.Center
        ) {
            if (trigger.second && customAnimation != null) {
                Animated(
                    preset = customAnimation!!,
                    state = animationState
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
        modifier = Modifier
            .size(120.dp)
            .background(AppTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "DEMO",
            color = AppTheme.colors.onPrimary,
            style = AppTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
    }
}