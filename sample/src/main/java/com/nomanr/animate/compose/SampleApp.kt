package com.nomanr.animate.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.animated.rememberAnimatedState
import com.nomanr.animate.compose.presets.BounceAnimationPreset


@Composable
fun SampleApp() {
    var animationEnabled by remember { mutableStateOf(true) }
    val animationState = rememberAnimatedState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Animated(
            preset = BounceAnimationPreset, durationMillis = 1000, repeat = true, enabled = animationEnabled
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Red)
            )
        }


        Animated(
            preset = BounceAnimationPreset, state = animationState
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Blue)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(Modifier.clickable {
            animationEnabled = !animationEnabled
        }) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                BasicText(text = "Toggle Enabled: $animationEnabled")
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(Modifier.clickable {
            animationState.animate()
        }) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                BasicText(text = "Animate")
            }
        }

    }
}

