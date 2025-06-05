package com.nomanr.animate.compose.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.components.AppTopbar
import com.nomanr.animate.compose.playground.components.Demo
import com.nomanr.animate.compose.playground.components.keyframeproperties.KeyframeProperties
import com.nomanr.animate.compose.playground.components.timeline.Timeline
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.VerticalDivider

@Composable
fun PlaygroundScreen(
    onNavigateToSample: (() -> Unit)? = null
) {

    PlaygroundStateProvider {
        val state = LocalPlaygroundState.current

        Column(modifier = Modifier.fillMaxSize()) {
            AppTopbar()

            Row(
                modifier = Modifier.fillMaxSize().background(AppTheme.colors.background),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Demo(
                        state = state, modifier = Modifier.weight(1.2f)
                    )

                    Timeline(
                        state = state,
                        modifier = Modifier.weight(1f)
                    )
                }

                VerticalDivider()

                KeyframeProperties(
                    state = state, modifier = Modifier.width(400.dp)
                )
            }
        }
    }
}
