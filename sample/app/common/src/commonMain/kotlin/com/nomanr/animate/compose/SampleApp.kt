package com.nomanr.animate.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nomanr.animate.compose.navigation.SampleAppNavHost
import com.nomanr.animate.compose.ui.AdaptiveProvider
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Scaffold


@Composable
fun SampleApp() {
    val appState = rememberSampleAppState()
    AppTheme {
        AdaptiveProvider(modifier = Modifier.fillMaxSize()) {
            Scaffold {
                SampleAppNavHost(
                    appState = appState,
                )
            }
        }
    }
}

