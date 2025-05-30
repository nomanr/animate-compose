package com.nomanr.animate.compose.sample.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import com.nomanr.animate.compose.data.Animation

@Composable
fun AnimationDemoContainer(
    animation: Animation,
    onSizeChanged: (DpSize) -> Unit,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        LaunchedEffect(maxWidth, maxHeight) {
            onSizeChanged(DpSize(maxWidth, maxHeight))
        }

        AnimatedDemo(animation = animation)
    }
}