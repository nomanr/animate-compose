package com.nomanr.animate.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.ui.components.HorizontalDivider
import com.nomanr.animate.compose.ui.components.topbar.TopBar


@Composable
fun AppTopbar() {
    Column {
        TopBar {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.Start
            ) {
                Logo()
            }
        }
        HorizontalDivider()
    }
}