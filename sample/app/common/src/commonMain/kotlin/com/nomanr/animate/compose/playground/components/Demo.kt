package com.nomanr.animate.compose.playground.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nomanr.animate.compose.animated.Animated
import com.nomanr.animate.compose.animated.rememberAnimatedState
import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.playground.PlaygroundState
import com.nomanr.animate.compose.playground.model.CustomAnimationPreset
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Icon
import com.nomanr.animate.compose.ui.components.IconButton
import com.nomanr.animate.compose.ui.components.IconButtonVariant
import com.nomanr.animate.compose.ui.components.Text

@Composable

fun Demo(
    state: PlaygroundState, modifier: Modifier = Modifier
) {
    val animationState = rememberAnimatedState()
    var trigger by remember { mutableStateOf(Pair(0, true)) }
    var customAnimation by remember { mutableStateOf(CustomAnimationPreset(emptyList())) }


    LaunchedEffect(state.keyframes, state.originX, state.originY) {
        customAnimation =
            CustomAnimationPreset(state.keyframes, TransformOrigin(state.originX, state.originY))
    }

    LaunchedEffect(state.isPlaying) {
        if (state.isPlaying) {

            trigger = Pair(trigger.first + 1, true)
            animationState.animate()
        }
    }

    LaunchedEffect(animationState.isAnimationFinished.value) {
        if (animationState.isAnimationFinished.value == true) {
            trigger = Pair(trigger.first + 1, false)
        }
    }


    val clipboardManager = LocalClipboardManager.current
    
    Column(modifier = modifier.clipToBounds()) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            // Copy button in top right corner
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = {
                        val code = generateKeyframeCode(state.keyframes)
                        clipboardManager.setText(AnnotatedString(code))
                    },
                    variant = IconButtonVariant.Secondary
                ) {
                    Icon(
                        Icons.Default.ContentCopy,
                        contentDescription = "Copy code"
                    )                }
            }
            
            if (trigger.second) {
                Animated(
                    preset = customAnimation,
                    state = animationState,
                    durationMillis = state.duration
                ) {
                    DemoContent()
                }
            } else {
                DemoContent()
            }
        }
    }
}

@Composable
private fun DemoContent() {
    Box(
        modifier = Modifier.size(120.dp).background(AppTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "DEMO", color = AppTheme.colors.onPrimary, style = AppTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold, fontSize = 18.sp
            )
        )
    }
}


private fun generateKeyframeCode(keyframes: List<Keyframe>): String {
    val sb = StringBuilder()
    sb.appendLine("val keyframes = listOf(")
    
    keyframes.forEach { keyframe ->
        when (keyframe) {
            is Keyframe.Static -> {
                sb.appendLine("    Keyframe.Static(")
                sb.appendLine("        percent = ${keyframe.percent}f,")
                sb.appendLine("        transform = ${formatTransformProperties(keyframe.transform)},")
                keyframe.easing?.let {
                    sb.appendLine("        easing = /* Add your easing here */")
                }
                sb.appendLine("    ),")
            }
            is Keyframe.Segment -> {
                sb.appendLine("    Keyframe.Segment(")
                sb.appendLine("        start = ${keyframe.start}f,")
                sb.appendLine("        end = ${keyframe.end}f,")
                sb.appendLine("        from = ${formatTransformProperties(keyframe.from)},")
                sb.appendLine("        to = ${formatTransformProperties(keyframe.to)},")
                keyframe.easing?.let {
                    sb.appendLine("        easing = /* Add your easing here */")
                }
                sb.appendLine("    ),")
            }
        }
    }
    
    sb.appendLine(")")
    return sb.toString()
}

private fun formatTransformProperties(props: com.nomanr.animate.compose.core.TransformProperties): String {
    val properties = mutableListOf<String>()
    
    props.translationX?.let { properties.add("translationX = ${it}f") }
    props.translationY?.let { properties.add("translationY = ${it}f") }
    props.scaleX?.let { properties.add("scaleX = ${it}f") }
    props.scaleY?.let { properties.add("scaleY = ${it}f") }
    props.rotationX?.let { properties.add("rotationX = ${it}f") }
    props.rotationY?.let { properties.add("rotationY = ${it}f") }
    props.rotationZ?.let { properties.add("rotationZ = ${it}f") }
    props.skewX?.let { properties.add("skewX = ${it}f") }
    props.skewY?.let { properties.add("skewY = ${it}f") }
    props.alpha?.let { properties.add("alpha = ${it}f") }
    props.cameraDistance?.let { properties.add("cameraDistance = ${it}f") }
    
    return if (properties.isEmpty()) {
        "TransformProperties()"
    } else {
        "TransformProperties(\n            ${properties.joinToString(",\n            ")}\n        )"
    }
}