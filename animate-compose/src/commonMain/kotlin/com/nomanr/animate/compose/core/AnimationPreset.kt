package com.nomanr.animate.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

interface AnimationPreset {
    @Composable
    fun animate(progress: State<Float>): Modifier
}