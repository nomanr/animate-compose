package com.nomanr.animate.compose.playground.components

import com.nomanr.animate.compose.core.Keyframe
import com.nomanr.animate.compose.core.TransformProperties

fun generateKeyframeCode(keyframes: List<Keyframe>, originX: Float = 0.5f, originY: Float = 0.5f): String {
    val sb = StringBuilder()
    
    // Package and imports
    sb.appendLine("import androidx.compose.runtime.Composable")
    sb.appendLine("import androidx.compose.runtime.State")
    sb.appendLine("import androidx.compose.ui.Modifier")
    sb.appendLine("import androidx.compose.ui.graphics.TransformOrigin")
    sb.appendLine("import com.nomanr.animate.compose.core.AnimationPreset")
    sb.appendLine("import com.nomanr.animate.compose.core.Keyframe")
    sb.appendLine("import com.nomanr.animate.compose.core.TransformProperties")
    sb.appendLine("import com.nomanr.animate.compose.core.animateKeyframe")
    
    // Check if any keyframe has easing
    val hasEasing = keyframes.any { keyframe ->
        when (keyframe) {
            is Keyframe.Static -> keyframe.easing != null
            is Keyframe.Segment -> keyframe.easing != null
        }
    }
    
    if (hasEasing) {
        sb.appendLine("// TODO: Add easing imports as needed")
        sb.appendLine("// import com.nomanr.animate.compose.core.Easings")
        sb.appendLine("// import androidx.compose.animation.core.CubicBezierEasing")
    }
    
    sb.appendLine()
    
    // Class definition
    sb.appendLine("class UntitledPreset : AnimationPreset {")
    sb.appendLine()
    
    // Keyframes
    sb.appendLine("    private val keyframes = listOf(")
    
    keyframes.forEachIndexed { index, keyframe ->
        when (keyframe) {
            is Keyframe.Static -> {
                sb.appendLine("        Keyframe.Static(")
                sb.appendLine("            percent = ${keyframe.percent}f,")
                sb.append("            transform = ${formatTransformProperties(keyframe.transform, indent = "            ")}")
                keyframe.easing?.let {
                    sb.appendLine(",")
                    sb.append("            easing = null // TODO: Add your easing")
                }
                sb.append(")")
                
                if (index < keyframes.size - 1) {
                    sb.appendLine(",")
                } else {
                    sb.appendLine()
                }
            }
            is Keyframe.Segment -> {
                sb.appendLine("        Keyframe.Segment(")
                sb.appendLine("            start = ${keyframe.start}f,")
                sb.appendLine("            end = ${keyframe.end}f,")
                sb.appendLine("            from = ${formatTransformProperties(keyframe.from, indent = "            ")},")
                sb.append("            to = ${formatTransformProperties(keyframe.to, indent = "            ")}")
                keyframe.easing?.let {
                    sb.appendLine(",")
                    sb.append("            easing = null // TODO: Add your easing")
                }
                sb.append(")")
                
                if (index < keyframes.size - 1) {
                    sb.appendLine(",")
                } else {
                    sb.appendLine()
                }
            }
        }
    }
    
    sb.appendLine("    )")
    sb.appendLine()
    
    // Animate function
    sb.appendLine("    @Composable")
    sb.appendLine("    override fun animate(progress: State<Float>): Modifier =")
    sb.appendLine("        Modifier.animateKeyframe(")
    sb.appendLine("            progress = progress,")
    sb.appendLine("            keyframes = keyframes,")
    
    // Generate transform origin
    if (originX == 0.5f && originY == 0.5f) {
        sb.appendLine("            transformOrigin = TransformOrigin.Center")
    } else {
        sb.appendLine("            transformOrigin = TransformOrigin(${originX}f, ${originY}f)")
    }
    
    sb.appendLine("        )")
    sb.appendLine("}")
    
    return sb.toString()
}

private fun formatTransformProperties(props: TransformProperties, indent: String = "            "): String {
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
        "TransformProperties(\n$indent    ${properties.joinToString(",\n$indent    ")}\n$indent)"
    }
}