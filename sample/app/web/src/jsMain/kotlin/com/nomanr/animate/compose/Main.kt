package com.nomanr.animate.compose

import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("ComposeTarget") {
            SideEffect { document.title = "Animated.compose" }
            SampleApp()
        }
    }
}
