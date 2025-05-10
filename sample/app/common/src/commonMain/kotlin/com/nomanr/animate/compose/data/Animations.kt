package com.nomanr.animate.compose.data

import androidx.compose.runtime.Composable
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
import com.nomanr.animate.compose.utils.getSlideInOutHeight
import com.nomanr.animate.compose.utils.getSlideInOutWidth
import com.nomanr.animate.compose.utils.getSlideInOutWidthWithMenuOffset

class Animation(
    val name: String,
    val preset: AnimationPreset,
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

@Composable
fun animationSets() = listOf(
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
            Animation("Back In Down", BackInDown(-getSlideInOutHeight())),
            Animation("Back In Up", BackInUp(getSlideInOutHeight())),
            Animation("Back In Left", BackInLeft(-getSlideInOutWidthWithMenuOffset())),
            Animation("Back In Right", BackInRight(getSlideInOutWidth()))
        )
    ), AnimationSet(
        section = AnimationSection.BackExits, animations = listOf(
            Animation("Back Out Down", BackOutDown(getSlideInOutHeight())),
            Animation("Back Out Up", BackOutUp(-getSlideInOutHeight())),
            Animation("Back Out Left", BackOutLeft(-getSlideInOutWidthWithMenuOffset())),
            Animation("Back Out Right", BackOutRight(getSlideInOutWidth()))
        )
    )
)

