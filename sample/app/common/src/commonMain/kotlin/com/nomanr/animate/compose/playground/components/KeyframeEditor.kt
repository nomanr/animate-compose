package com.nomanr.animate.compose.playground.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.core.TransformProperties
import com.nomanr.animate.compose.playground.model.CustomAnimationPreset
import com.nomanr.animate.compose.playground.model.KeyframeData
import com.nomanr.animate.compose.ui.AppTheme
import com.nomanr.animate.compose.ui.components.Button
import com.nomanr.animate.compose.ui.components.ButtonVariant
import com.nomanr.animate.compose.ui.components.IconButton
import com.nomanr.animate.compose.ui.components.IconButtonVariant
import com.nomanr.animate.compose.ui.components.Surface
import com.nomanr.animate.compose.ui.components.Text

@Composable
fun KeyframeEditor(
    onAnimationChanged: (AnimationPreset?) -> Unit
) {
    var keyframes by remember {
        mutableStateOf(
            listOf(
                KeyframeData(
                    type = KeyframeData.Type.Static,
                    percent = 0f,
                    transformProperties = TransformProperties(
                        translationY = 0f,
                        scaleX = 1f,
                        scaleY = 1f,
                        alpha = 1f
                    )
                )
            )
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Keyframes",
                style = AppTheme.typography.h3
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    variant = ButtonVariant.Secondary,
                    onClick = {
                        keyframes = keyframes + KeyframeData(
                            type = KeyframeData.Type.Static,
                            percent = 1f,
                            transformProperties = TransformProperties()
                        )
                    }
                ) {
                    Text("Add Keyframe")
                }

                Button(
                    variant = ButtonVariant.Primary,
                    onClick = {
                        val animation = CustomAnimationPreset(keyframes.map { it.toKeyframe() })
                        onAnimationChanged(animation)
                    },
                    enabled = keyframes.isNotEmpty()
                ) {
                    Text("Generate")
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            keyframes.forEachIndexed { index, keyframe ->
                KeyframeCard(
                    keyframe = keyframe,
                    onKeyframeChanged = { updatedKeyframe ->
                        keyframes = keyframes.toMutableList().apply {
                            set(index, updatedKeyframe)
                        }
                    },
                    onDeleteKeyframe = if (keyframes.size > 1) {
                        {
                            keyframes = keyframes.toMutableList().apply {
                                removeAt(index)
                            }
                        }
                    } else null
                )
            }
        }
    }
}

@Composable
private fun KeyframeCard(
    keyframe: KeyframeData,
    onKeyframeChanged: (KeyframeData) -> Unit,
    onDeleteKeyframe: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = AppTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Keyframe at ${(keyframe.percent * 100).toInt()}%",
                    style = AppTheme.typography.h5
                )

                if (onDeleteKeyframe != null) {
                    IconButton(
                        variant = IconButtonVariant.Secondary,
                        onClick = onDeleteKeyframe
                    ) {
                        Text("×")
                    }
                }
            }

            // Progress/Percent slider
            Column {
                Text("Progress: ${(keyframe.percent * 100).toInt()}%")
                Slider(
                    value = keyframe.percent,
                    onValueChange = {
                        onKeyframeChanged(keyframe.copy(percent = it))
                    },
                    valueRange = 0f..1f
                )
            }

            // Transform properties
            TransformPropertyEditor(
                label = "Translation Y",
                value = keyframe.transformProperties.translationY ?: 0f,
                onValueChange = { value ->
                    onKeyframeChanged(
                        keyframe.copy(
                            transformProperties = keyframe.transformProperties.copy(
                                translationY = value
                            )
                        )
                    )
                },
                range = -300f..300f
            )

            TransformPropertyEditor(
                label = "Scale X",
                value = keyframe.transformProperties.scaleX ?: 1f,
                onValueChange = { value ->
                    onKeyframeChanged(
                        keyframe.copy(
                            transformProperties = keyframe.transformProperties.copy(
                                scaleX = value
                            )
                        )
                    )
                },
                range = 0f..3f
            )

            TransformPropertyEditor(
                label = "Scale Y",
                value = keyframe.transformProperties.scaleY ?: 1f,
                onValueChange = { value ->
                    onKeyframeChanged(
                        keyframe.copy(
                            transformProperties = keyframe.transformProperties.copy(
                                scaleY = value
                            )
                        )
                    )
                },
                range = 0f..3f
            )

            TransformPropertyEditor(
                label = "Alpha",
                value = keyframe.transformProperties.alpha ?: 1f,
                onValueChange = { value ->
                    onKeyframeChanged(
                        keyframe.copy(
                            transformProperties = keyframe.transformProperties.copy(
                                alpha = value
                            )
                        )
                    )
                },
                range = 0f..1f
            )

            TransformPropertyEditor(
                label = "Rotation Z",
                value = keyframe.transformProperties.rotationZ ?: 0f,
                onValueChange = { value ->
                    onKeyframeChanged(
                        keyframe.copy(
                            transformProperties = keyframe.transformProperties.copy(
                                rotationZ = value
                            )
                        )
                    )
                },
                range = -360f..360f
            )
        }
    }
}

@Composable
private fun TransformPropertyEditor(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>
) {
    Column {
        Text("$label: ${(value * 100).toInt() / 100f}")
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range
        )
    }
}