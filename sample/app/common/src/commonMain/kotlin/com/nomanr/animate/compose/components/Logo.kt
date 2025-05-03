package com.nomanr.animate.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.presets.attentionseekers.Bounce
import com.nomanr.animate.compose.presets.attentionseekers.Flash
import com.nomanr.animate.compose.presets.attentionseekers.HeartBeat
import com.nomanr.animate.compose.presets.attentionseekers.Pulse
import com.nomanr.animate.compose.presets.attentionseekers.RubberBand
import com.nomanr.animate.compose.presets.attentionseekers.ShakeX
import com.nomanr.animate.compose.presets.attentionseekers.ShakeY
import com.nomanr.animate.compose.presets.attentionseekers.Swing
import com.nomanr.animate.compose.presets.attentionseekers.Tada
import com.nomanr.animate.compose.presets.attentionseekers.Wobble
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Surface
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun Logo() {
    val defaultDuration = 1500
    val presets = listOf(
        Bounce(bounceHeightInPx = 10f),
        Flash(),
        Pulse(maxPulseScale = 1.5f),
        RubberBand(),
        Swing(),
        Tada(scaleAmount = 0.2f),
        Wobble(maxOffsetPx = 10f),
        HeartBeat(heartScale = 1.5f),
        ShakeX(offsetX = 4f),
        ShakeY(offsetY = 4f)
    )
    var currentPreset by remember { mutableStateOf(presets.first()) }


    LaunchedEffect(Unit) {
        coroutineScope {
            while (true) {
                delay(defaultDuration.toLong() * 3)
                currentPreset = presets[(presets.indexOf(currentPreset) + 1) % presets.size]
            }
        }
    }

    Surface(
        modifier = Modifier.size(32.dp), color = AppTheme.colors.primary, hardShadow = true, border = true
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {

            Animated(preset = currentPreset, durationMillis = defaultDuration, repeat = true) {
                Box(
                    modifier = Modifier.size(8.dp).background(color = AppTheme.colors.onPrimary, shape = RectangleShape),
                )
            }
        }
    }
}