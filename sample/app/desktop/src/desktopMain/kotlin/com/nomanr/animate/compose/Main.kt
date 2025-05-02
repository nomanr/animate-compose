package com.nomanr.animate.compose

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.nomanr.animate.compose.SampleApp

fun main() {
    val windowState =
        WindowState(
            size = DpSize(402.dp, 974.dp),
        )

    singleWindowApplication(windowState) {
        SampleApp()
    }
}
