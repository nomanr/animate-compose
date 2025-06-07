package com.nomanr.animate.compose.playground.components.configs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.card.Card


@Composable
fun ConfigurationSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column{
        Text(title, style = AppTheme.typography.h4)

        Spacer(modifier = Modifier.height(12.dp))

        Card {
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    }
}