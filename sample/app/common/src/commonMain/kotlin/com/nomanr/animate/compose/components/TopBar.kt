package com.nomanr.animate.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import animate_compose.sample.app.common.generated.resources.Res
import animate_compose.sample.app.common.generated.resources.github
import animate_compose.sample.app.common.generated.resources.x
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.presets.attentionseekers.HeartBeat
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.HorizontalDivider
import com.nomanr.animate.compose.ui.components.Icon
import com.nomanr.animate.compose.ui.components.IconButton
import com.nomanr.animate.compose.ui.components.IconButtonVariant
import com.nomanr.animate.compose.ui.components.Text
import com.nomanr.animate.compose.ui.components.topbar.TopBar
import com.nomanr.animate.compose.ui.currentAdaptiveInfo
import org.jetbrains.compose.resources.painterResource

@Composable
fun AppTopbar(
    onNavigateToPlayground: (() -> Unit)? = null
) {
    val openUrl = rememberOpenUrl()
    val adaptiveInfo = currentAdaptiveInfo()

    Column {
        TopBar {
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (adaptiveInfo.isCompact) {
                        Arrangement.SpaceBetween
                    } else {
                        Arrangement.SpaceAround
                    },
                ) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        Logo()
                        Spacer(Modifier.width(8.dp))
//                        Docs()
                        if (!adaptiveInfo.isCompact) {
                            Playground(onNavigateToPlayground)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Github(openUrl)
                        X(openUrl)
                    }
                }
            }
        }
        HorizontalDivider()
    }
}

@Composable
fun Docs() {
    Button(variant = ButtonVariant.Ghost) {
        Text(text = "Docs", style = AppTheme.typography.h5)
    }
}

@Composable
fun Playground(onNavigateToPlayground: (() -> Unit)? = null) {
//    Animated(preset = HeartBeat(heartScale = 1.1f), repeat = true, durationMillis = 2000) {
        Button(
            variant = ButtonVariant.Ghost,
            onClick = { onNavigateToPlayground?.invoke() }
        ) {
            Text(text = "Playground", style = AppTheme.typography.h5)
        }
//    }
}

@Composable
fun LumoUI(openUrl: (String) -> Unit) {
    Button(
        variant = ButtonVariant.Ghost, onClick = { openUrl("https://lumoui.com") }) {
        Text(text = "Lumo UI", style = AppTheme.typography.h5)
    }
}

@Composable
fun Github(openUrl: (String) -> Unit) {
    TopBarIconButton(
        onClick = { openUrl("https://github.com/nomanr/animate-compose") }) {
        Icon(
            modifier = Modifier.size(16.dp), painter = painterResource(Res.drawable.github)
        )
    }
}

@Composable
fun X(openUrl: (String) -> Unit) {
    TopBarIconButton(
        onClick = { openUrl("https://x.com/nomanr_") }) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(Res.drawable.x) // TODO: Replace with X icon
        )
    }
}

@Composable
private fun TopBarIconButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(32.dp), onClick = onClick, variant = IconButtonVariant.Secondary
    ) {
        content()
    }
}

@Composable
fun rememberOpenUrl(): (String) -> Unit {
    val uriHandler = LocalUriHandler.current
    return remember(uriHandler) {
        { url -> uriHandler.openUri(url) }
    }
}