package com.nomanr.animate.compose.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.components.AppTopbar
import com.nomanr.animate.compose.playground.components.Demo
import com.nomanr.animate.compose.playground.components.configs.Configurations
import com.nomanr.animate.compose.playground.components.timeline.Timeline
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.VerticalDivider
import kotlinx.coroutines.launch

@Composable
fun PlaygroundScreen(
    onNavigateToSample: (() -> Unit)? = null,
    snackbarHostState: com.nomanr.animate.compose.ui.components.snackbar.SnackbarHostState
) {

    PlaygroundStateProvider {
        val state = LocalPlaygroundState.current
        val scope = rememberCoroutineScope()

        Column(modifier = Modifier.fillMaxSize()) {
            AppTopbar(
                isPlaygroundScreen = true,
                onNavigateToSample = onNavigateToSample
            )

            Row(
                modifier = Modifier.fillMaxSize().background(AppTheme.colors.background),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Demo(
                        state = state, 
                        modifier = Modifier.weight(1.2f),
                        onCodeCopied = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Code copied to clipboard")
                            }
                        }
                    )

                    Timeline(
                        state = state,
                        modifier = Modifier.weight(1f)
                    )
                }

                VerticalDivider()

                Configurations(
                    state = state, modifier = Modifier.width(400.dp)
                )
            }
        }
    }
}
