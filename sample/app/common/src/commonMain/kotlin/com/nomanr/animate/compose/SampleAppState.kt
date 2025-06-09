package com.nomanr.animate.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nomanr.animate.compose.ui.components.snackbar.SnackbarHostState
import com.nomanr.animate.compose.ui.components.snackbar.rememberSnackbarHost

@Composable
fun rememberSampleAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = rememberSnackbarHost(),
): SampleAppState {
    return SampleAppState(
        navController = navController,
        snackbarHostState = snackbarHostState,
    )
}

@Stable
class SampleAppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
)
