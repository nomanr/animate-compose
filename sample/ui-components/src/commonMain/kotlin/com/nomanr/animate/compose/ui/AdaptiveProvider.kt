package com.nomanr.animate.compose.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Adaptive screen size configuration
 */
data class AdaptiveInfo(
    val screenWidth: Dp,
    val screenHeight: Dp,
    val isCompact: Boolean,
    val isMedium: Boolean,
    val isExpanded: Boolean
) {
    companion object {
        val CompactMaxWidth = 600.dp
        val MediumMaxWidth = 840.dp

        fun fromSize(width: Dp, height: Dp): AdaptiveInfo {
            return AdaptiveInfo(
                screenWidth = width,
                screenHeight = height,
                isCompact = width < CompactMaxWidth,
                isMedium = width >= CompactMaxWidth && width < MediumMaxWidth,
                isExpanded = width >= MediumMaxWidth
            )
        }
    }
}

/**
 * CompositionLocal for providing adaptive screen information
 */
val LocalAdaptiveInfo = compositionLocalOf<AdaptiveInfo> {
    error("AdaptiveInfo not provided")
}

/**
 * Provider that automatically detects screen size and provides adaptive information
 */
@Composable
fun AdaptiveProvider(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val adaptiveInfo = AdaptiveInfo.fromSize(maxWidth, maxHeight)

        CompositionLocalProvider(
            LocalAdaptiveInfo provides adaptiveInfo,
            content = content
        )
    }
}

/**
 * Hook to access current adaptive information
 */
@Composable
fun currentAdaptiveInfo(): AdaptiveInfo = LocalAdaptiveInfo.current