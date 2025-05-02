package com.nomanr.animate.compose.navigation

import SampleScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nomanr.animate.compose.SampleAppState

@Composable
fun SampleAppNavHost(
    startDestination: NavRoute = NavRoute.Sample,
    appState: SampleAppState,
) {
    val navController = appState.navController


    NavHost(navController = navController, startDestination = startDestination) {
        composable<NavRoute.Sample> {
            SampleScreen()
        }

//        composable<NavRoute.Demo>(
//            typeMap = componentIdNavTypeMap,
//        ) {
//            val args = it.toRoute<NavRoute.Demo>()
//            SampleScreen(componentId = args.componentId, navigateUp = { navController.navigateUp() })
//        }


    }
}

