package com.nomanr.animate.compose.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.components.AppTopbar
import com.nomanr.animate.compose.playground.timeline.Demo
import com.nomanr.animate.compose.playground.timeline.Timeline
import com.nomanr.animate.compose.playground.timeline.TimelineState
import com.nomanr.animate.compose.playground.timeline.UpdateKeyframe as KeyframeProperties
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.VerticalDivider

@Composable
fun PlaygroundScreen(
    onNavigateToSample: (() -> Unit)? = null
) {
    val timelineState = remember { TimelineState() }
    var selectedNodeId by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopbar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.surface)
                .padding(16.dp),
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
                        timelineState = timelineState, 
                        modifier = Modifier.weight(1f)
                    )

                    Timeline(
                        state = timelineState, 
                        onNodeSelected = { nodeId -> selectedNodeId = nodeId }, 
                        modifier = Modifier.weight(1f)
                    )
                }

                VerticalDivider()

                KeyframeProperties(
                    state = timelineState, 
                    modifier = Modifier.width(300.dp)
                )
            }
        }
    }
}
