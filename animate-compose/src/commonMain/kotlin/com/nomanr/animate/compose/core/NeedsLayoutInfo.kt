package com.nomanr.animate.compose.core

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

interface NeedsLayoutInfo {
    fun setLayoutInfo(layoutInfo: LayoutInfo)
}

data class LayoutInfo(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val containerWidth: Int,
    val containerHeight: Int,

    ) {
    companion object {
        fun create(size: IntSize, position: Offset, containerSize: IntSize): LayoutInfo {
            return LayoutInfo(
                x = position.x,
                y = position.y,
                width = size.width.toFloat(),
                height = size.height.toFloat(),
                containerWidth = containerSize.width,
                containerHeight = containerSize.height
            )
        }
    }
}

