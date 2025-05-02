package com.nomanr.animate.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberSampleAppState(
    navController: NavHostController = rememberNavController(),
): SampleAppState {
    return SampleAppState(
        navController = navController,
    )
}

@Stable
class SampleAppState(
    val navController: NavHostController,
)
