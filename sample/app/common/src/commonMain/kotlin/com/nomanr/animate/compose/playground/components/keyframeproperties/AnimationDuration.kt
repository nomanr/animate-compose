package com.nomanr.animate.compose.playground.components.keyframeproperties

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.nomanr.animate.compose.playground.LocalPlaygroundState
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.textfield.OutlinedTextField

@Composable
fun AnimationDuration() {
    val presetState = LocalPlaygroundState.current
    var textValue by remember(presetState.duration) {
        mutableStateOf(presetState.duration.toString())
    }

    ConfigurationSection(title = "Animation Duration") {
        OutlinedTextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                newValue.toIntOrNull()?.let { milliseconds ->
                    if (milliseconds > 0) {
                        presetState.updateDuration(milliseconds)
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