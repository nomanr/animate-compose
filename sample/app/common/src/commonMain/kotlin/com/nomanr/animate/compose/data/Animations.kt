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
import com.nomanr.animate.compose.presets.fadeinentrances.FadeIn
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInBottomLeft
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInBottomRight
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInDown
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInDownBig
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInLeft
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInLeftBig
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInRight
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInRightBig
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInTopLeft
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInTopRight
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInUp
import com.nomanr.animate.compose.presets.fadeinentrances.FadeInUpBig
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOut
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutBottomLeft
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutBottomRight
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutDown
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutDownBig
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutLeft
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutLeftBig
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutRight
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutRightBig
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutTopLeft
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutTopRight
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutUp
import com.nomanr.animate.compose.presets.fadeoutexits.FadeOutUpBig
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
    val section: AnimationSection,
    val animations: List<Animation>
)

enum class AnimationSection(val title: String) {
    AttentionSeekers("Attention Seekers"),
    BackEntrances("Back Entrances"),
    BackExits("Back Exists"),
    FadeInEntrances("Fade In Entrances"),
    FadeOutExits("Fade Out Exits")
}

@Composable
fun animationSets() = listOf(
    AnimationSet(
        section = AnimationSection.AttentionSeekers,
        animations = listOf(
            Animation("Test", BackOutDown())
        )
    ),
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
    AnimationSet(
        section = AnimationSection.BackEntrances,
        animations = listOf(
            Animation("Back In Down", BackInDown(-getSlideInOutHeight())),
            Animation("Back In Up", BackInUp(getSlideInOutHeight())),
            Animation("Back In Left", BackInLeft(-getSlideInOutWidthWithMenuOffset())),
            Animation("Back In Right", BackInRight(getSlideInOutWidth()))
        )
    ),
    AnimationSet(
        section = AnimationSection.BackExits,
        animations = listOf(
            Animation("Back Out Down", BackOutDown(getSlideInOutHeight())),
            Animation("Back Out Up", BackOutUp(-getSlideInOutHeight())),
            Animation("Back Out Left", BackOutLeft(-getSlideInOutWidthWithMenuOffset())),
            Animation("Back Out Right", BackOutRight(getSlideInOutWidth()))
        )
    ),
    AnimationSet(
        section = AnimationSection.FadeInEntrances,
        animations = listOf(
            Animation("Fade In", FadeIn()),
            Animation("Fade In Down", FadeInDown()),
            Animation("Fade In Down Big", FadeInDownBig(-getSlideInOutHeight())),
            Animation("Fade In Up", FadeInUp()),
            Animation("Fade In Up Big", FadeInUpBig(getSlideInOutHeight())),
            Animation("Fade In Left", FadeInLeft()),
            Animation("Fade In Left Big", FadeInLeftBig(-getSlideInOutWidthWithMenuOffset())),
            Animation("Fade In Right", FadeInRight()),
            Animation("Fade In Right Big", FadeInRightBig(getSlideInOutWidth())),
            Animation("Fade In Top Left", FadeInTopLeft()),
            Animation("Fade In Top Right", FadeInTopRight()),
            Animation("Fade In Bottom Left", FadeInBottomLeft()),
            Animation("Fade In Bottom Right", FadeInBottomRight())
        )
    ),
    AnimationSet(
        section = AnimationSection.FadeOutExits,
        animations = listOf(
            Animation("Fade Out", FadeOut()),
            Animation("Fade Out Down", FadeOutDown()),
            Animation("Fade Out Down Big", FadeOutDownBig(-getSlideInOutHeight())),
            Animation("Fade Out Up", FadeOutUp()),
            Animation("Fade Out Up Big", FadeOutUpBig(getSlideInOutHeight())),
            Animation("Fade Out Left", FadeOutLeft()),
            Animation("Fade Out Left Big", FadeOutLeftBig(-getSlideInOutWidthWithMenuOffset())),
            Animation("Fade Out Right", FadeOutRight()),
            Animation("Fade Out Right Big", FadeOutRightBig(getSlideInOutWidth())),
            Animation("Fade Out Top Left", FadeOutTopLeft()),
            Animation("Fade Out Top Right", FadeOutTopRight()),
            Animation("Fade Out Bottom Left", FadeOutBottomLeft()),
            Animation("Fade Out Bottom Right", FadeOutBottomRight())
        )
    )
)
