package com.nomanr.animate.compose.playground.components.configs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.playground.PlaygroundState
import com.nomanr.animate.compose.playground.components.configs.keyframeproperties.KeyframeTiming
import com.nomanr.animate.compose.playground.components.configs.keyframeproperties.KeyframeProperties
import com.nomanr.animate.compose.playground.removeKeyframe
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Icon
import com.nomanr.animate.compose.ui.components.IconButton
import com.nomanr.animate.compose.ui.components.IconButtonVariant
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun Configurations(
    state: PlaygroundState, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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
            KeyframePropertiesPanel(
                keyframe = selectedKeyframe,
                keyframeIndex = state.selectedKeyframeIndex,
                state = state
            )
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (keyframe) {
                    is Keyframe.Static -> "Static Keyframe"
                    is Keyframe.Segment -> "Segment Keyframe"
                },
                style = AppTheme.typography.h3,
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    variant = IconButtonVariant.Ghost,
                    onClick = {
                        state.selectKeyframe(null)
                    }
                ) {
                    Icon(Icons.Default.Close)
                }
                
                IconButton(
                    variant = IconButtonVariant.Ghost,
                    onClick = {
                        state.removeKeyframe(keyframeIndex)
                    }
                ) {
                    Icon(Icons.Outlined.Delete, tint = AppTheme.colors.error)
                }
            }
        }

        KeyframeTiming(state = state)

        KeyframeProperties(state = state)
    }
}


