package com.nomanr.animate.compose

import androidx.compose.runtime.Composable
import com.nomanr.animate.compose.navigation.SampleAppNavHost
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Scaffold


@Composable
fun SampleApp() {
    val appState = rememberSampleAppState()
    AppTheme {
        Scaffold {
            SampleAppNavHost(
                appState = appState,
            )
        }
    }
}

