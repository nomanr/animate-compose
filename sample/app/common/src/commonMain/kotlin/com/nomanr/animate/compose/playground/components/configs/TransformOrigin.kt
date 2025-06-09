package com.nomanr.animate.compose.playground.components.configs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.playground.PlaygroundState
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.textfield.OutlinedTextField

@Composable
fun TransformOrigin(state: PlaygroundState) {
    ConfigurationSection(title = "Transform Origin") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OriginField(
                label = "X",
                value = state.originX,
                onValueChange = { newValue ->
                    state.updateOrigin(newValue, state.originY)
                },
                modifier = Modifier.weight(1f)
            )
            
            OriginField(
                label = "Y",
                value = state.originY,
                onValueChange = { newValue ->
                    state.updateOrigin(state.originX, newValue)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun OriginField(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var textValue by remember(value) { 
        mutableStateOf(((value * 100).toInt() / 100.0).toString()) 
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            textValue = newValue
            newValue.toFloatOrNull()?.let { floatValue ->
                val clampedValue = floatValue.coerceIn(0f, 1f)
                onValueChange(clampedValue)
            }
        },
        label = {
            Text(
                text = label,
                style = AppTheme.typography.label2,
                color = AppTheme.colors.text
            )
        },
        suffix = {
            Text(
                text = "(0-1)",
                style = AppTheme.typography.label2,
                color = AppTheme.colors.textSecondary
            )
        },
        textStyle = AppTheme.typography.body2,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier
    )
}