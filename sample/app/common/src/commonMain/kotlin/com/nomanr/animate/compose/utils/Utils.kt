package com.nomanr.animate.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import com.nomanr.animate.compose.core.getContainerSize
import com.nomanr.animate.compose.sample.components.AnimationListMaxWidth

@Composable
fun getSlideInOutWidth(): Float {
    return getContainerSize().width * Offset
}

@Composable
fun getSlideInOutWidthWithMenuOffset(): Float {
    val density = LocalDensity.current
    return getContainerSize().width * Offset +
        with(density) { AnimationListMaxWidth.toPx() }
}

@Composable
fun getSlideInOutHeight(): Float {
    return getContainerSize().height * Offset
}

private const val Offset = 0.8f