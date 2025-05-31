package com.nomanr.animate.compose.playground.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.animated.rememberAnimatedState
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.playground.timeline.TimelinePlaybackEffect
import com.nomanr.animate.compose.playground.timeline.TimelineState
import com.nomanr.animate.compose.playground.timeline.getCurrentTransformProperties
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun AnimationPreview(
    animation: AnimationPreset?,
    modifier: Modifier = Modifier
) {
    val animationState = rememberAnimatedState()
    var isAnimating by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, AppTheme.colors.outline, RoundedCornerShape(12.dp))
                .background(AppTheme.colors.surface.copy(alpha = 0.3f))
                .clipToBounds(),
            contentAlignment = Alignment.Center
        ) {
            if (animation != null && isAnimating) {
                Animated(
                    preset = animation,
                    state = animationState
                ) {
                    PreviewContent()
                }
            } else {
                PreviewContent()
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                variant = ButtonVariant.Primary,
                onClick = {
                    if (animation != null) {
                        isAnimating = true
                        animationState.animate()
                    }
                },
                enabled = animation != null
            ) {
                Text("Play Animation")
            }

            Button(
                variant = ButtonVariant.Secondary,
                onClick = {
                    isAnimating = false
                    animationState.reset()
                }
            ) {
                Text("Reset")
            }
        }
    }

    LaunchedEffect(animationState.isAnimationFinished.value) {
        if (animationState.isAnimationFinished.value == true) {
            isAnimating = false
        }
    }
}

@Composable
private fun PreviewContent() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(16.dp))
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

@Composable
fun TimelineAnimationPreview(
    timelineState: TimelineState,
    modifier: Modifier = Modifier
) {
    var currentProgress by remember { mutableStateOf(0f) }

    // Timeline playback effect
    TimelinePlaybackEffect(
        timelineState = timelineState,
        onAnimationUpdate = { progress ->
            currentProgress = progress
        }
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, AppTheme.colors.outline, RoundedCornerShape(12.dp))
                .background(AppTheme.colors.surface.copy(alpha = 0.3f))
                .clipToBounds(),
            contentAlignment = Alignment.Center
        ) {
            // Apply real-time transform based on timeline progress
            val transformProperties = timelineState.getCurrentTransformProperties(currentProgress)

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppTheme.colors.primary)
                    .then(applyTransformProperties(transformProperties)),
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                variant = ButtonVariant.Primary,
                onClick = {
                    if (timelineState.isPlaying) {
                        timelineState.pause()
                    } else {
                        timelineState.play()
                    }
                }
            ) {
                Text(if (timelineState.isPlaying) "Pause" else "Play")
            }

            Button(
                variant = ButtonVariant.Secondary,
                onClick = {
                    timelineState.stop()
                    currentProgress = 0f
                }
            ) {
                Text("Reset")
            }
        }
    }
}

@Composable
private fun applyTransformProperties(
    properties: com.nomanr.animate.compose.core.TransformProperties
): Modifier {
    return Modifier
        .let { modifier ->
            if (properties.translationX != null || properties.translationY != null) {
                modifier.then(
                    Modifier.offset(
                        x = (properties.translationX ?: 0f).dp,
                        y = (properties.translationY ?: 0f).dp
                    )
                )
            } else modifier
        }
        .let { modifier ->
            if (properties.scaleX != null || properties.scaleY != null) {
                modifier.then(
                    Modifier.scale(
                        scaleX = properties.scaleX ?: 1f,
                        scaleY = properties.scaleY ?: 1f
                    )
                )
            } else modifier
        }
        .let { modifier ->
            val rotation = properties.rotationZ
            if (rotation != null) {
                modifier.then(Modifier.rotate(rotation))
            } else modifier
        }
        .let { modifier ->
            val alphaValue = properties.alpha
            if (alphaValue != null) {
                modifier.then(Modifier.alpha(alphaValue))
            } else modifier
        }
}