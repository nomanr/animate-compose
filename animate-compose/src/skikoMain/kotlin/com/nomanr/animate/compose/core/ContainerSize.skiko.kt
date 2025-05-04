package com.nomanr.animate.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getContainerSize(): IntSize {
    val windowInfo = LocalWindowInfo.current
    return windowInfo.containerSize
}