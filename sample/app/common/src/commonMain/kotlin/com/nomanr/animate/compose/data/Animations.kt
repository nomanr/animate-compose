package com.nomanr.animate.compose.data

import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.presets.attentionseekers.Bounce
import com.nomanr.animate.compose.presets.attentionseekers.Flash
import com.nomanr.animate.compose.presets.attentionseekers.HeartBeat
import com.nomanr.animate.compose.presets.attentionseekers.Pulse
import com.nomanr.animate.compose.presets.attentionseekers.RubberBand
import com.nomanr.animate.compose.presets.attentionseekers.ShakeX
import com.nomanr.animate.compose.presets.attentionseekers.ShakeY
import com.nomanr.animate.compose.presets.attentionseekers.Swing
import com.nomanr.animate.compose.presets.attentionseekers.Tada
import com.nomanr.animate.compose.presets.attentionseekers.Wobble

class Animation(
    val name: String,
    val preset: AnimationPreset,
    private val token: Int = 0,
){
    fun copy(): Animation = Animation(
        name = name,
        preset = preset,
        token = token + 1 // to force recomposition
    )
}

data class AnimationSet(
    val section: AnimationSection,
    val animations: List<Animation>
)

enum class AnimationSection(val title: String) {
    AttentionSeekers("Attention Seekers"),
}

val animationSets = listOf(
    AnimationSet(
        section = AnimationSection.AttentionSeekers,
        animations = listOf(
            Animation("Bounce", Bounce()),
            Animation("Flash", Flash()),
            Animation("Pulse", Pulse()),
            Animation("Rubber Band", RubberBand()),
            Animation("ShakeX", ShakeX()),
            Animation("ShakeY", ShakeY()),
            Animation("Swing", Swing()),
            Animation("Tada", Tada()),
            Animation("Wobble", Wobble()),
            Animation("Heart Beat", HeartBeat())
        )
    ),
)

