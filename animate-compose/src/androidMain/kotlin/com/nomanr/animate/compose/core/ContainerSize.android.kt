package com.nomanr.animate.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
actual fun getContainerSize(): IntSize {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    return with(density) {
        IntSize(
            configuration.screenWidthDp.dp.roundToPx(), configuration.screenHeightDp.dp.roundToPx()
        )
    }
}