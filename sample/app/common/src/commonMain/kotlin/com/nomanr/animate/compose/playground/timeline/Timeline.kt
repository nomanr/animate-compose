package com.nomanr.animate.compose.playground.timeline

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.SegmentSlider
import com.nomanr.animate.compose.ui.components.StaticSlider
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun Timeline(
    state: TimelineState, modifier: Modifier = Modifier, onNodeSelected: ((String?) -> Unit)? = null
) {
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

        Car(
            modifier = Modifier.fillMaxWidth().clip(
                RoundedCornerShape(8.dp))
                    .border(1.dp, AppTheme.colors.outline, RoundedCornerShape(8.dp))
                    .background(AppTheme.colors.background).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TimelineRuler(
                    modifier = Modifier.fillMaxWidth()
                )

                if (state.keyframes.isEmpty()) {
                    Text(
                        text = "No keyframes. Use the buttons below to add keyframes.",
                        style = AppTheme.typography.body2,
                        color = AppTheme.colors.textSecondary,
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
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


    }
}

@Composable
private fun KeyframeSlider(
    keyframe: Keyframe,
    keyframeIndex: Int,
    state: TimelineState,
    onSelected: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        when (keyframe) {
            is Keyframe.Static -> {
                StaticSlider(
                    value = keyframe.percent / state.duration, onValueChange = { normalizedValue ->
                        val newTime = normalizedValue * state.duration
                        state.updateKeyframeTime(keyframeIndex, newTime)
                        onSelected()
                    }, valueRange = 0f..1f, modifier = Modifier.fillMaxWidth()
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
                    valueRange = 0f..1f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TimelineRuler(
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val textColor = AppTheme.colors.outline
    val lineColor = AppTheme.colors.outline

    Canvas(
        modifier = modifier.height(36.dp).padding(horizontal = 20.dp)
    ) {
        drawRuler(textMeasurer, textColor, lineColor)
    }
}

private fun DrawScope.drawRuler(
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
            val text = when (i) {
                0 -> "0"
                10 -> "0.2"
                20 -> "0.4"
                30 -> "0.6"
                40 -> "0.8"
                50 -> "1"
                else -> ""
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