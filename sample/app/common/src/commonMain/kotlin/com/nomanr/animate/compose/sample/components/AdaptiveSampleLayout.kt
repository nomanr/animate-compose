package com.nomanr.animate.compose.sample.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import com.nomanr.animate.compose.data.Animation
import com.nomanr.animate.compose.data.AnimationSet
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.HorizontalDivider
import com.nomanr.animate.compose.ui.components.VerticalDivider
import com.nomanr.animate.compose.ui.currentAdaptiveInfo

@Composable
fun AdaptiveSampleLayout(
    animationSets: List<AnimationSet>,
    currentAnimation: Animation,
    onAnimationSelected: (Animation) -> Unit,
    onSizeChanged: (DpSize) -> Unit,
    modifier: Modifier = Modifier
) {
    val adaptiveInfo = currentAdaptiveInfo()

    if (adaptiveInfo.isCompact) {
        CompactLayout(
            animationSets = animationSets,
            currentAnimation = currentAnimation,
            onAnimationSelected = onAnimationSelected,
            onSizeChanged = onSizeChanged,
            modifier = modifier
        )
    } else {
        ExpandedLayout(
            animationSets = animationSets,
            currentAnimation = currentAnimation,
            onAnimationSelected = onAnimationSelected,
            onSizeChanged = onSizeChanged,
            modifier = modifier
        )
    }
}

@Composable
private fun CompactLayout(
    animationSets: List<AnimationSet>,
    currentAnimation: Animation,
    onAnimationSelected: (Animation) -> Unit,
    onSizeChanged: (DpSize) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(AppTheme.colors.background).fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AnimationDemoContainer(
            animation = currentAnimation,
            onSizeChanged = onSizeChanged,
            modifier = Modifier.weight(0.4f)
        )

        HorizontalDivider()

        AnimationList(
            animationSets, 
            onAnimationSelected,
            modifier = Modifier.fillMaxWidth().weight(0.6f)
        )
    }
}

@Composable
private fun ExpandedLayout(
    animationSets: List<AnimationSet>,
    currentAnimation: Animation,
    onAnimationSelected: (Animation) -> Unit,
    onSizeChanged: (DpSize) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.background(AppTheme.colors.background).fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
            horizontalArrangement = Arrangement.Start
        ) {
            AnimationList(animationSets, onAnimationSelected)
            VerticalDivider()
        }

        AnimationDemoContainer(
            animation = currentAnimation,
            onSizeChanged = onSizeChanged,
            modifier = Modifier.fillMaxHeight().weight(1f)
        )
    }
}