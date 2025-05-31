package com.nomanr.animate.compose.navigation

import SampleScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nomanr.animate.compose.SampleAppState
import com.nomanr.animate.compose.playground.PlaygroundScreen

@Composable
fun SampleAppNavHost(
    startDestination: NavRoute = NavRoute.Sample,
    appState: SampleAppState,
) {
    val navController = appState.navController


    NavHost(navController = navController, startDestination = startDestination) {
        composable<NavRoute.Sample> {
            SampleScreen(
                onNavigateToPlayground = {
                    navController.navigate(NavRoute.Playground)
                }
            )
        }

        composable<NavRoute.Playground> {
            PlaygroundScreen(
                onNavigateToSample = {
                    navController.navigate(NavRoute.Sample) {
                        popUpTo(NavRoute.Sample) { inclusive = true }
                    }
                }
            )
        }

//        composable<NavRoute.Demo>(
//            typeMap = componentIdNavTypeMap,
//        ) {
//            val args = it.toRoute<NavRoute.Demo>()
//            SampleScreen(componentId = args.componentId, navigateUp = { navController.navigateUp() })
//        }


    }
}

