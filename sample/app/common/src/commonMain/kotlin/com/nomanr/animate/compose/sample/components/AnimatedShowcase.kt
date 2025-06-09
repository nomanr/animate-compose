package com.nomanr.animate.compose.sample.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.presets.attentionseekers.Bounce
import com.nomanr.animate.compose.presets.attentionseekers.RubberBand
import com.nomanr.animate.compose.presets.attentionseekers.ShakeY
import com.nomanr.animate.compose.presets.attentionseekers.Swing
import com.nomanr.animate.compose.presets.attentionseekers.Wobble
import com.nomanr.animate.compose.presets.flippers.Flip
import com.nomanr.animate.compose.presets.rotatingentrances.RotateIn
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Surface
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun AnimatedShowcase() {
    Column(
        modifier = Modifier.padding(60.dp)
            .background(Color.White).padding(48.dp)
    ) {

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimatedLogo()
            Text("Animated.compose", style = AppTheme.typography.h1.copy(fontSize = 30.sp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Design, preview, and export Jetpack Compose animations",
            style = AppTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "An interactive playground for building reusable Jetpack Compose animations.",
            style = AppTheme.typography.h2.copy(fontWeight = FontWeight.W400)
        )
        Spacer(modifier = Modifier.height(40.dp))

        // First row of shapes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bouncing Circle
            AnimatedShape(
                preset = Bounce(bounceHeightInPx = 30f)
            ) {
                CircleShape(AppTheme.colors.neoColor1)
            }

            // Wobbling Triangle
            AnimatedShape(
                preset = Wobble(maxOffsetPx = 30f)
            ) {
                TriangleShape(AppTheme.colors.neoColor3)
            }

            AnimatedShape(
                preset = Flip()
            ) {
                SquareShape(AppTheme.colors.primary)
            }

            AnimatedShape(
                preset = ShakeY()
            ) {
                HexagonShape(AppTheme.colors.neoColor2)
            }

            AnimatedShape(
                preset = Swing()
            ) {
                DiamondShape(AppTheme.colors.neoColor4)
            }

            AnimatedShape(
                preset = RubberBand()
            ) {
                StarShape(AppTheme.colors.secondary)
            }

            AnimatedShape(
                preset = RotateIn()
            ) {
                PentagonShape(AppTheme.colors.tertiary)
            }

        }
    }
}

@Composable
private fun AnimatedLogo() {
    Surface(
        modifier = Modifier.size(48.dp),
        color = AppTheme.colors.primary,
        hardShadow = true,
        border = true
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(14.dp)
                    .background(color = AppTheme.colors.onPrimary, shape = RectangleShape),
            )
        }
    }
}

@Composable
private fun AnimatedShape(
    preset: AnimationPreset,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.size(60.dp)) {
        Animated(
            preset = preset,
            durationMillis = 2000,
            repeat = true,
        ) {
            content()
        }
    }
}

@Composable
private fun CircleShape(color: Color) {
    Box(
        modifier = Modifier.fillMaxSize().clip(CircleShape).background(color)
    )
}

@Composable
private fun TriangleShape(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(size.width / 2, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(path, color)
    }
}

@Composable
private fun SquareShape(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    )
}

@Composable
private fun HexagonShape(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = minOf(centerX, centerY)

            // Create hexagon path
            for (i in 0..5) {
                val angle = 60f * i - 30f
                val x = centerX + radius * kotlin.math.cos(kotlin.math.PI * angle / 180.0).toFloat()
                val y = centerY + radius * kotlin.math.sin(kotlin.math.PI * angle / 180.0).toFloat()

                if (i == 0) {
                    moveTo(x, y)
                } else {
                    lineTo(x, y)
                }
            }
            close()
        }
        drawPath(path, color)
    }
}

@Composable
private fun DiamondShape(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(size.width / 2, 0f)
            lineTo(size.width, size.height / 2)
            lineTo(size.width / 2, size.height)
            lineTo(0f, size.height / 2)
            close()
        }
        drawPath(path, color)
    }
}

@Composable
private fun StarShape(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val outerRadius = minOf(centerX, centerY)
            val innerRadius = outerRadius * 0.4f

            for (i in 0..9) {
                val angle = kotlin.math.PI * i / 5.0
                val radius = if (i % 2 == 0) outerRadius else innerRadius
                val x = centerX + radius * kotlin.math.cos(angle - kotlin.math.PI / 2).toFloat()
                val y = centerY + radius * kotlin.math.sin(angle - kotlin.math.PI / 2).toFloat()

                if (i == 0) {
                    moveTo(x, y)
                } else {
                    lineTo(x, y)
                }
            }
            close()
        }
        drawPath(path, color)
    }
}

@Composable
private fun PentagonShape(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = minOf(centerX, centerY)

            for (i in 0..4) {
                val angle = 72f * i - 90f
                val x = centerX + radius * kotlin.math.cos(kotlin.math.PI * angle / 180.0).toFloat()
                val y = centerY + radius * kotlin.math.sin(kotlin.math.PI * angle / 180.0).toFloat()

                if (i == 0) {
                    moveTo(x, y)
                } else {
                    lineTo(x, y)
                }
            }
            close()
        }
        drawPath(path, color)
    }
}