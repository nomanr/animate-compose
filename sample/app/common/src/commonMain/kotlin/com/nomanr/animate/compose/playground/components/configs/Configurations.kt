package com.nomanr.animate.compose.playground.components.configs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.playground.PlaygroundState
import com.nomanr.animate.compose.playground.components.configs.keyframeproperties.KeyframeTiming
import com.nomanr.animate.compose.playground.components.configs.keyframeproperties.KeyframeProperties
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun Configurations(
    state: PlaygroundState, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        AnimationDuration()


        val selectedKeyframe = state.selectedKeyframeIndex?.let { index ->
            state.keyframes.getOrNull(index)
        }

        if (selectedKeyframe == null) {
            Text(
                text = "No keyframe selected.\nClick on a keyframe in the timeline to edit its properties.",
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary.copy(alpha = 0.6f)
            )
        } else {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                KeyframePropertiesPanel(
                    keyframe = selectedKeyframe,
                    keyframeIndex = state.selectedKeyframeIndex,
                    state = state
                )
            }
        }
    }
}

@Composable
private fun KeyframePropertiesPanel(
    keyframe: Keyframe, keyframeIndex: Int, state: PlaygroundState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = when (keyframe) {
                is Keyframe.Static -> "Static Keyframe"
                is Keyframe.Segment -> "Segment Keyframe"
            },
            style = AppTheme.typography.h4,
        )

        KeyframeTiming(state = state)

        KeyframeProperties(state = state)
    }
}


