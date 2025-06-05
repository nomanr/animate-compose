package com.nomanr.animate.compose.playground.components.timeline

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.playground.state.PlaygroundState
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.SegmentSlider
import com.nomanr.animate.compose.ui.components.StaticSlider
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.card.Card
import kotlinx.coroutines.delay
import kotlin.time.TimeSource

@Composable
fun Timeline(
    state: PlaygroundState, modifier: Modifier = Modifier, onNodeSelected: ((String?) -> Unit)? = null
) {
    // Handle animation playback
    LaunchedEffect(state.isPlaying) {
        if (state.isPlaying) {
            val startTime = state.currentTime
            val animationDuration = state.duration - startTime
            val timeSource = TimeSource.Monotonic
            val startMark = timeSource.markNow()

            while (state.isPlaying) {
                val elapsed = startMark.elapsedNow().inWholeMilliseconds
                val progress = (elapsed.toFloat() / (animationDuration * 1000)).coerceIn(0f, 1f)
                val newTime = startTime + (animationDuration * progress)

                state.updateCurrentTime(newTime)

                if (progress >= 1f) {
                    state.updateCurrentTime(0f)
                    break
                }

                delay(16) // ~60 FPS
            }
        }
    }

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    variant = ButtonVariant.Primary, onClick = {
                        state.addStaticKeyframe()
                    }) {
                    Text("Add Static")
                }

                Button(
                    variant = ButtonVariant.Secondary, onClick = {
                        state.addSegmentKeyframe()
                    }) {
                    Text("Add Segment")
                }
            }

            Button(
                variant = ButtonVariant.Primary, onClick = {
                    state.play()
                }) {
                Text("Play")
            }
        }


        val scrollState = rememberScrollState()

        Card(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TimelineRuler(
                        duration = state.duration,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (state.keyframes.isEmpty()) {
                        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "No keyframes. Add keyframes to get started!",
                                style = AppTheme.typography.h5,
                                color = AppTheme.colors.text,
                            )
                        }
                    } else {
                        Column(Modifier.verticalScroll(scrollState).padding(bottom = 16.dp)) {
                            state.keyframes.forEachIndexed { index, keyframe ->
                                KeyframeSlider(
                                    keyframe = keyframe,
                                    keyframeIndex = index,
                                    state = state,
                                    onSelected = {
                                        state.selectKeyframe(index)
                                        onNodeSelected?.invoke(index.toString())
                                    })
                            }
                        }
                    }
                }

                if (state.isPlaying || state.currentTime > 0) {
                    val progress = state.currentTime / state.duration
                    val cursorColor = AppTheme.colors.primary.copy(alpha = 0.7f)
                    Canvas(
                        modifier = Modifier.fillMaxHeight().fillMaxWidth()
                    ) {
                        val cursorX = progress * size.width
                        drawLine(
                            color = cursorColor,
                            start = Offset(cursorX, 0f),
                            end = Offset(cursorX, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun KeyframeSlider(
    keyframe: Keyframe, keyframeIndex: Int, state: PlaygroundState, onSelected: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        when (keyframe) {
            is Keyframe.Static -> {
                StaticSlider(
                    value = keyframe.percent / state.duration, 
                    onValueChange = { normalizedValue ->
                        val newTime = normalizedValue * state.duration
                        state.updateKeyframeTime(keyframeIndex, newTime)
                        onSelected()
                    }, 
                    onClick = onSelected,
                    valueRange = 0f..1f, 
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is Keyframe.Segment -> {
                SegmentSlider(
                    value = (keyframe.start / state.duration)..(keyframe.end / state.duration),
                    onValueChange = { normalizedRange ->
                        val newStart = normalizedRange.start * state.duration
                        val newEnd = normalizedRange.endInclusive * state.duration
                        state.updateKeyframe(keyframeIndex) { oldKeyframe ->
                            (oldKeyframe as Keyframe.Segment).copy(
                                start = newStart, end = newEnd
                            )
                        }
                        onSelected()
                    },
                    onClick = onSelected,
                    valueRange = 0f..1f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TimelineRuler(
    duration: Float,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val textColor = AppTheme.colors.outline
    val lineColor = AppTheme.colors.outline

    Canvas(
        modifier = modifier.height(36.dp).padding(horizontal = 20.dp)
    ) {
        drawRuler(duration, textMeasurer, textColor, lineColor)
    }
}

private fun DrawScope.drawRuler(
    duration: Float,
    textMeasurer: TextMeasurer, textColor: Color, lineColor: Color
) {
    val width = size.width
    val textStyle = TextStyle(
        fontSize = 11.sp, color = textColor
    )

    val dotY = 24.dp.toPx()

    for (i in 0..50) {
        val fraction = i / 50f
        val x = fraction * width

        val shouldShowText = i % 10 == 0
        val isSecondaryDot = i % 5 == 0

        if (shouldShowText) {
            val timeInSeconds = fraction * duration
            val text = if (timeInSeconds < 10) {
                "${(timeInSeconds * 10).toInt() / 10.0}s"
            } else {
                "${timeInSeconds.toInt()}s"
            }
            val textLayoutResult = textMeasurer.measure(text, textStyle)
            drawText(
                textLayoutResult = textLayoutResult, topLeft = Offset(
                    x - textLayoutResult.size.width / 2, dotY - textLayoutResult.size.height / 2
                )
            )
        } else {
            val dotRadius = if (isSecondaryDot) 2.dp.toPx() else 1.dp.toPx()

            drawCircle(
                color = lineColor, radius = dotRadius, center = Offset(x, dotY)
            )
        }
    }
}