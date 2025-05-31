package com.nomanr.animate.compose.playground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.components.AppTopbar
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.playground.model.CustomAnimationPreset
import com.nomanr.animate.compose.playground.timeline.TimelineState
import com.nomanr.animate.compose.playground.timeline.TimelineView
import com.nomanr.animate.compose.ui.components.HorizontalDivider

@Composable
fun PlaygroundScreen(
    onNavigateToSample: (() -> Unit)? = null
) {
    val timelineState = remember { TimelineState() }
    var customAnimation by remember { mutableStateOf<AnimationPreset?>(null) }

    LaunchedEffect(timelineState.nodes.size, timelineState.nodes) {
        val keyframes = timelineState.toKeyframes()
        val animation = if (keyframes.isNotEmpty()) {
            CustomAnimationPreset(keyframes)
        } else null
        customAnimation = animation
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopbar()

        TimelineLayout(
            timelineState = timelineState,
            customAnimation = customAnimation,
            onAnimationChanged = { customAnimation = it }
        )
    }
}


@Composable
private fun TimelineLayout(
    timelineState: TimelineState,
    customAnimation: AnimationPreset?,
    onAnimationChanged: (AnimationPreset?) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        TimelineView(
            state = timelineState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

