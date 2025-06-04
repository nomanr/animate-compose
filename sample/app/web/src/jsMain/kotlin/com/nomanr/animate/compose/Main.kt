package com.nomanr.animate.compose

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        document.title = "Animated.compose"
        CanvasBasedWindow("ComposeTarget") {
            SampleApp()
        }
    }
}
