package com.nomanr.animate.compose.playground.components.keyframeproperties

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.nomanr.animate.compose.playground.LocalKeyframePreset
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.textfield.OutlinedTextField

@Composable
fun AnimationDuration() {
    val presetState = LocalKeyframePreset.current
    var textValue by remember(presetState.duration) {
        mutableStateOf((presetState.duration * 1000).toString())
    }

    KeyframePropertiesSection(title = "Animation Duration") {
        OutlinedTextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                newValue.toFloatOrNull()?.let { floatValue ->
                    val seconds = floatValue / 1000f
                    if (seconds > 0) {
                        presetState.updateDuration(seconds)
                    }
                }
            },
            suffix = {
                Text(
                    "ms", style = AppTheme.typography.label2, color = AppTheme.colors.text
                )
            },
            textStyle = AppTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = {
                Text(
                    text = "Duration",
                    style = AppTheme.typography.label2,
                    color = AppTheme.colors.text
                )
            })
    }
}