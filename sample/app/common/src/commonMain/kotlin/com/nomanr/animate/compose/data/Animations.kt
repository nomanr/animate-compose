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
import com.nomanr.animate.compose.presets.backentrances.BackInDown
import com.nomanr.animate.compose.presets.backentrances.BackInLeft
import com.nomanr.animate.compose.presets.backentrances.BackInRight
import com.nomanr.animate.compose.presets.backentrances.BackInUp
import com.nomanr.animate.compose.presets.backexists.BackOutDown
import com.nomanr.animate.compose.presets.backexists.BackOutLeft
import com.nomanr.animate.compose.presets.backexists.BackOutRight
import com.nomanr.animate.compose.presets.backexists.BackOutUp

class Animation(
    val name: String,
    val preset: AnimationPreset,
    val resetOnFinish: Boolean = false,
    private val token: Int = 0,
) {
    fun copy(): Animation = Animation(
        name = name, preset = preset, token = token + 1 // to force recomposition
    )
}

data class AnimationSet(
    val section: AnimationSection, val animations: List<Animation>
)

enum class AnimationSection(val title: String) {
    AttentionSeekers("Attention Seekers"), BackEntrances("Back Entrances"), BackExits("Back Exist"),
}

val animationSets = listOf(
    AnimationSet(
        section = AnimationSection.AttentionSeekers, animations = listOf(
            Animation(
                "Test", BackOutDown()
            )
        )
    ), AnimationSet(
        section = AnimationSection.AttentionSeekers, animations = listOf(
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
    ), AnimationSet(
        section = AnimationSection.BackEntrances, animations = listOf(
            Animation("Back In Down", BackInDown()),
            Animation("Back In Up", BackInUp()),
            Animation("Back In Left", BackInLeft()),
            Animation("Back In Right", BackInRight())
        )
    ), AnimationSet(
        section = AnimationSection.BackExits, animations = listOf(
            Animation("Back Out Down", BackOutDown(), resetOnFinish = true),
            Animation("Back Out Up", BackOutUp(), resetOnFinish = true),
            Animation("Back Out Left", BackOutLeft(), resetOnFinish = true),
            Animation("Back Out Right", BackOutRight(), resetOnFinish = true)
        )
    )
)

