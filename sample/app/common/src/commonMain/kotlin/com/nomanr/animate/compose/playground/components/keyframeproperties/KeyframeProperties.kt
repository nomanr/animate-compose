package com.nomanr.animate.compose.playground.components.keyframeproperties

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.playground.state.PlaygroundState
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.textfield.OutlinedTextField

@Composable
fun KeyframeProperties(
    state: PlaygroundState, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        AnimationDuration()


        val selectedKeyframe = state.selectedKeyframeIndex?.let { index ->
            state.keyframes.getOrNull(index)
        }

        if (selectedKeyframe == null) {
            Text(
                text = "No keyframe selected.\nClick on a keyframe in the timeline to edit its properties.",
                style = AppTheme.typography.body2,
                color = AppTheme.colors.textSecondary.copy(alpha = 0.6f)
            )
        } else {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                KeyframePropertiesPanel(
                    keyframe = selectedKeyframe,
                    keyframeIndex = state.selectedKeyframeIndex!!,
                    state = state
                )
            }
        }
    }
}

@Composable
private fun KeyframePropertiesPanel(
    keyframe: Keyframe, keyframeIndex: Int, state: PlaygroundState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = when (keyframe) {
                is Keyframe.Static -> "Static Keyframe"
                is Keyframe.Segment -> "Segment Keyframe"
            },
            style = AppTheme.typography.h4,
        )

        KeyframeTiming(state = state)

        Spacer(modifier = Modifier.height(8.dp))
        
        EasingSection(keyframe = keyframe, keyframeIndex = keyframeIndex, state = state)
    }
}


@Composable
private fun EasingSection(
    keyframe: Keyframe, keyframeIndex: Int, state: PlaygroundState
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Easing", style = AppTheme.typography.h5)

        var a by remember { mutableStateOf(0.25f) }
        var b by remember { mutableStateOf(0.1f) }
        var c by remember { mutableStateOf(0.25f) }
        var d by remember { mutableStateOf(1.0f) }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PropertyField(
                label = "A",
                value = a,
                onValueChange = { a = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "B",
                value = b,
                onValueChange = { b = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "C",
                value = c,
                onValueChange = { c = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
            PropertyField(
                label = "D",
                value = d,
                onValueChange = { d = it },
                range = 0f..1f,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
private fun PropertyField(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    suffix: String? = null,
    range: ClosedFloatingPointRange<Float>? = null
) {
    var textValue by remember(value) { mutableStateOf(value.toString()) }

    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            textValue = newValue
            newValue.toFloatOrNull()?.let { floatValue ->
                val finalValue = range?.let { floatValue.coerceIn(it) } ?: floatValue
                onValueChange(finalValue)
            }
        },
        suffix = suffix?.let {
            {
                Text(
                    it, style = AppTheme.typography.body2, color = AppTheme.colors.text
                )
            }
        },
        textStyle = AppTheme.typography.body2,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier,
        label = {
            Text(
                text = label, style = AppTheme.typography.label2, color = AppTheme.colors.text
            )
        })
}