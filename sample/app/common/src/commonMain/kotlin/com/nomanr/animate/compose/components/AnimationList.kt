package com.nomanr.animate.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.data.Animation
import com.nomanr.animate.compose.data.animationSets
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun AnimationList(onSelectAnimation: (animation: Animation) -> Unit) {
    LazyColumn {
        animationSets.forEach { animationSet ->
            item { AnimationListHeader(title = animationSet.name) }
            items(animationSet.animations.size) { animationIndex ->
                val animation = animationSet.animations[animationIndex]
                AnimationListItem(animation = animation, onSelectAnimation = onSelectAnimation)
            }
        }
    }
}

@Composable
fun AnimationListHeader(
    title: String,
) {
    Text(title, style = AppTheme.typography.h2)
}

@Composable
fun AnimationListItem(
    animation: Animation, onSelectAnimation: (animation: Animation) -> Unit
) {
    Text(
        text = animation.name,
        style = AppTheme.typography.h4,
        modifier = Modifier.clickable(
            onClick = { onSelectAnimation(animation) },
            interactionSource = null,
            indication = null,
        )
    )
}