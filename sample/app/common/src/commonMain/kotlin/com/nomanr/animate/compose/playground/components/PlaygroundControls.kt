package com.nomanr.animate.compose.playground.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun PlaygroundControls(
    onPlayAnimation: () -> Unit,
    onResetAnimation: () -> Unit,
    onExportAnimation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Controls",
            style = AppTheme.typography.h4
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                variant = ButtonVariant.Primary,
                onClick = onPlayAnimation,
                modifier = Modifier.weight(1f)
            ) {
                Text("Play")
            }

            Button(
                variant = ButtonVariant.Secondary,
                onClick = onResetAnimation,
                modifier = Modifier.weight(1f)
            ) {
                Text("Reset")
            }
        }

        Button(
            variant = ButtonVariant.Neutral,
            onClick = onExportAnimation,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Export Code")
        }
    }
}