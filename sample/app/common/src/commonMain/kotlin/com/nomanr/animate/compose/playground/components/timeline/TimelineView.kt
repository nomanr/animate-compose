package com.nomanr.animate.compose.playground.components.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.playground.components.Demo
import com.nomanr.animate.compose.playground.state.PlaygroundState
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.VerticalDivider

@Composable
fun TimelineView(
    state: PlaygroundState, modifier: Modifier = Modifier, onNodeSelected: ((String?) -> Unit)? = null
) {
    Column(
        modifier = modifier.background(AppTheme.colors.surface),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f), 
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Demo(
                    timelineState = state, 
                    modifier = Modifier.weight(1f)
                )

                Timeline(
                    state = state, 
                    onNodeSelected = onNodeSelected, 
                    modifier = Modifier.weight(1f)
                )
            }

            VerticalDivider()


        }

    }
}