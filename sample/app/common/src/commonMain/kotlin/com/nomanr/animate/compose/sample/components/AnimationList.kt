package com.nomanr.animate.compose.sample.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.data.Animation
import com.nomanr.animate.compose.data.AnimationSet
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.HorizontalDivider
import com.nomanr.animate.compose.ui.components.Text


@Composable
fun AnimationList(animationSetsMap:  List<AnimationSet>, onSelectAnimation: (animation: Animation) -> Unit) {
    val backgroundColors = listOf(
        AppTheme.colors.neoColor1,
        AppTheme.colors.neoColor2,
        AppTheme.colors.neoColor3,
        AppTheme.colors.neoColor4
    )

    Column(
        modifier = Modifier.widthIn(max = AnimationListMaxWidth)
            .verticalScroll(rememberScrollState())
    ) {
        animationSetsMap.forEachIndexed() { index, animationSet ->
            Column(
                modifier = Modifier.background(color = backgroundColors[index % backgroundColors.size])
            ) {
                AnimationListHeader(title = animationSet.section.title)
                animationSet.animations.forEach { animation ->
                    AnimationListItem(animation = animation, onSelectAnimation = onSelectAnimation)
                }
            }
        }
    }
}

@Composable
fun AnimationListHeader(
    title: String,
) {
    ListItem {
        Text(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            text = title,
            style = AppTheme.typography.h2
        )
    }
}

@Composable
fun AnimationListItem(
    animation: Animation, onSelectAnimation: (animation: Animation) -> Unit
) {
    ListItem {
        Text(
            text = animation.name,
            style = AppTheme.typography.h4,
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = { onSelectAnimation(animation) },
            ).padding(horizontal = 32.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun ListItem(
    content: @Composable () -> Unit,
) {
    Column {
        content()
        HorizontalDivider()
    }
}

val AnimationListMaxWidth = 240.dp