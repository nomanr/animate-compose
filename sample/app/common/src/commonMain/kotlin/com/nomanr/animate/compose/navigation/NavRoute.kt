package com.nomanr.animate.compose.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable
    data object Sample : NavRoute()

    @Serializable
    data object Playground : NavRoute()
}
