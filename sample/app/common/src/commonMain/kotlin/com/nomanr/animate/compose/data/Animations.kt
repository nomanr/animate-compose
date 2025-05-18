package com.nomanr.animate.compose.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import com.nomanr.animate.compose.core.AnimationPreset
import com.nomanr.animate.compose.presets.attentionseekers.Bounce
import com.nomanr.animate.compose.presets.attentionseekers.Flash
import com.nomanr.animate.compose.presets.attentionseekers.HeartBeat
import com.nomanr.animate.compose.presets.attentionseekers.Jello
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
import com.nomanr.animate.compose.presets.bouncingentrances.BounceIn
import com.nomanr.animate.compose.presets.bouncingentrances.BounceInDown
import com.nomanr.animate.compose.presets.bouncingentrances.BounceInLeft
import com.nomanr.animate.compose.presets.bouncingentrances.BounceInRight
import com.nomanr.animate.compose.presets.bouncingentrances.BounceInUp
import com.nomanr.animate.compose.presets.bouncingexits.BounceOut
import com.nomanr.animate.compose.presets.bouncingexits.BounceOutDown
import com.nomanr.animate.compose.presets.bouncingexits.BounceOutLeft
import com.nomanr.animate.compose.presets.bouncingexits.BounceOutRight
import com.nomanr.animate.compose.presets.bouncingexits.BounceOutUp
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
import com.nomanr.animate.compose.presets.flippers.Flip
import com.nomanr.animate.compose.presets.flippers.FlipInX
import com.nomanr.animate.compose.presets.flippers.FlipInY
import com.nomanr.animate.compose.presets.flippers.FlipOutX
import com.nomanr.animate.compose.presets.flippers.FlipOutY
import com.nomanr.animate.compose.presets.lightspeed.LightSpeedInLeft
import com.nomanr.animate.compose.presets.lightspeed.LightSpeedInRight
import com.nomanr.animate.compose.presets.lightspeed.LightSpeedOutLeft
import com.nomanr.animate.compose.presets.lightspeed.LightSpeedOutRight
import com.nomanr.animate.compose.presets.rotatingentrances.RotateIn
import com.nomanr.animate.compose.presets.rotatingentrances.RotateInDownLeft
import com.nomanr.animate.compose.presets.rotatingentrances.RotateInDownRight
import com.nomanr.animate.compose.presets.rotatingentrances.RotateInUpLeft
import com.nomanr.animate.compose.presets.rotatingentrances.RotateInUpRight
import com.nomanr.animate.compose.presets.rotatingexits.RotateOut
import com.nomanr.animate.compose.presets.rotatingexits.RotateOutDownLeft
import com.nomanr.animate.compose.presets.rotatingexits.RotateOutDownRight
import com.nomanr.animate.compose.presets.rotatingexits.RotateOutUpLeft
import com.nomanr.animate.compose.presets.rotatingexits.RotateOutUpRight
import com.nomanr.animate.compose.presets.specials.Hinge
import com.nomanr.animate.compose.presets.specials.JackInTheBox
import com.nomanr.animate.compose.presets.specials.RollIn
import com.nomanr.animate.compose.presets.specials.RollOut
import com.nomanr.animate.compose.presets.zoomingextrances.ZoomIn
import com.nomanr.animate.compose.presets.zoomingextrances.ZoomInDown
import com.nomanr.animate.compose.presets.zoomingextrances.ZoomInLeft
import com.nomanr.animate.compose.presets.zoomingextrances.ZoomInRight
import com.nomanr.animate.compose.presets.zoomingextrances.ZoomInUp
import com.nomanr.animate.compose.presets.zooms.ZoomOut
import com.nomanr.animate.compose.presets.zooms.ZoomOutDown
import com.nomanr.animate.compose.presets.zooms.ZoomOutLeft
import com.nomanr.animate.compose.presets.zooms.ZoomOutRight
import com.nomanr.animate.compose.presets.zooms.ZoomOutUp

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
    AttentionSeekers("Attention Seekers"), BackEntrances("Back Entrances"), BackExits("Back Exists"), BouncingEntrances(
        "Bouncing Entrances"
    ),
    BouncingExits("Bouncing Exits"), RotatingEntrances("Rotating Entrances"), RotatingExits("Rotating Exits"), Specials(
        "Specials"
    ),
    FadeInEntrances(
        "Fade In Entrances"
    ),
    FadeOutExits("Fade Out Exits"), Flippers("Flippers"), LightSpeed("Light Speed"), ZoomingEntrances(
        "Zooming Entrances"
    ),
    ZoomingExits("Zooming Exits")
}

@Composable
fun animationSets(containerSize: DpSize): List<AnimationSet> {

    val containerWidth = getContainerWidth(containerSize)
    val containerHeight = getContainerHeight(containerSize)

    return listOf(
        AnimationSet(
            section = AnimationSection.AttentionSeekers, animations = listOf(
                Animation("Test", Jello())
            )
        ),
        AnimationSet(
            section = AnimationSection.AttentionSeekers, animations = listOf(
                Animation("Bounce", Bounce()),
                Animation("Flash", Flash()),
                Animation("Pulse", Pulse()),
                Animation("Rubber Band", RubberBand()),
                Animation("ShakeX", ShakeX()),
                Animation("ShakeY", ShakeY()),
                Animation("Jello", Jello()),
                Animation("Swing", Swing()),
                Animation("Tada", Tada()),
                Animation("Wobble", Wobble()),
                Animation("Heart Beat", HeartBeat())
            )
        ),
        AnimationSet(
            section = AnimationSection.BackEntrances, animations = listOf(
                Animation("Back In Down", BackInDown(-containerHeight)),
                Animation("Back In Up", BackInUp(containerHeight)),
                Animation("Back In Left", BackInLeft(-containerWidth)),
                Animation("Back In Right", BackInRight(containerWidth))
            )
        ),
        AnimationSet(
            section = AnimationSection.BackExits, animations = listOf(
                Animation("Back Out Down", BackOutDown(containerHeight)),
                Animation("Back Out Up", BackOutUp(-containerHeight)),
                Animation("Back Out Left", BackOutLeft(-containerWidth)),
                Animation("Back Out Right", BackOutRight(containerWidth))
            )
        ),
        AnimationSet(
            section = AnimationSection.BackExits, animations = listOf(
                Animation("Back Out Down", BackOutDown(containerHeight)),
                Animation("Back Out Up", BackOutUp(-containerHeight)),
                Animation("Back Out Left", BackOutLeft(-containerWidth)),
                Animation("Back Out Right", BackOutRight(containerWidth))
            )
        ),
        AnimationSet(
            section = AnimationSection.BouncingEntrances, animations = listOf(
                Animation("Bounce In", BounceIn()),
                Animation("Bounce In Down", BounceInDown(-containerHeight)),
                Animation("Bounce In Up", BounceInUp(containerHeight)),
                Animation("Bounce In Left", BounceInLeft(-containerWidth)),
                Animation("Bounce In Right", BounceInRight(containerWidth))
            )
        ),
        AnimationSet(
            section = AnimationSection.BouncingExits, animations = listOf(
                Animation("Bounce Out", BounceOut()),
                Animation("Bounce Out Down", BounceOutDown(containerHeight)),
                Animation("Bounce Out Up", BounceOutUp(-containerHeight)),
                Animation("Bounce Out Left", BounceOutLeft(-containerWidth)),
                Animation("Bounce Out Right", BounceOutRight(containerWidth))
            )
        ),
        AnimationSet(
            section = AnimationSection.FadeInEntrances, animations = listOf(
                Animation("Fade In", FadeIn()),
                Animation("Fade In Down", FadeInDown()),
                Animation("Fade In Down Big", FadeInDownBig(-containerHeight)),
                Animation("Fade In Up", FadeInUp()),
                Animation("Fade In Up Big", FadeInUpBig(containerHeight)),
                Animation("Fade In Left", FadeInLeft()),
                Animation("Fade In Left Big", FadeInLeftBig(-containerWidth)),
                Animation("Fade In Right", FadeInRight()),
                Animation("Fade In Right Big", FadeInRightBig(containerWidth)),
                Animation("Fade In Top Left", FadeInTopLeft()),
                Animation("Fade In Top Right", FadeInTopRight()),
                Animation("Fade In Bottom Left", FadeInBottomLeft()),
                Animation("Fade In Bottom Right", FadeInBottomRight())
            )
        ),
        AnimationSet(
            section = AnimationSection.FadeOutExits, animations = listOf(
                Animation("Fade Out", FadeOut()),
                Animation("Fade Out Down", FadeOutDown()),
                Animation("Fade Out Down Big", FadeOutDownBig(-containerHeight)),
                Animation("Fade Out Up", FadeOutUp()),
                Animation("Fade Out Up Big", FadeOutUpBig(containerHeight)),
                Animation("Fade Out Left", FadeOutLeft()),
                Animation("Fade Out Left Big", FadeOutLeftBig(-containerWidth)),
                Animation("Fade Out Right", FadeOutRight()),
                Animation("Fade Out Right Big", FadeOutRightBig(containerWidth)),
                Animation("Fade Out Top Left", FadeOutTopLeft()),
                Animation("Fade Out Top Right", FadeOutTopRight()),
                Animation("Fade Out Bottom Left", FadeOutBottomLeft()),
                Animation("Fade Out Bottom Right", FadeOutBottomRight())
            )
        ),
        AnimationSet(
            section = AnimationSection.Flippers, animations = listOf(
                Animation("Flip", Flip()),
                Animation("Flip In X", FlipInX()),
                Animation("Flip In Y", FlipInY()),
                Animation("Flip Out X", FlipOutX()),
                Animation("Flip Out Y", FlipOutY()),
            )
        ),
        AnimationSet(
            section = AnimationSection.LightSpeed, animations = listOf(
                Animation("Light Speed In Left", LightSpeedInLeft(-containerWidth)), Animation(
                    "Light Speed In Right", LightSpeedInRight(containerWidth)
                ), Animation(
                    "Light Speed Out Left", LightSpeedOutLeft(-containerWidth)
                ), Animation(
                    "Light Speed Out Right", LightSpeedOutRight(
                        containerWidth
                    )
                )
            )
        ),
        AnimationSet(
            section = AnimationSection.RotatingEntrances, animations = listOf(
                Animation("Rotate In", RotateIn()),
                Animation("Rotate In Down Left", RotateInDownLeft()),
                Animation("Rotate In Up Left", RotateInUpLeft()),
                Animation("Rotate In Up Right", RotateInUpRight()),
                Animation("Rotate In Down Right", RotateInDownRight())
            )
        ),
        AnimationSet(
            section = AnimationSection.RotatingExits, animations = listOf(
                Animation("Rotate Out", RotateOut()),
                Animation("Rotate Out Down Left", RotateOutDownLeft()),
                Animation("Rotate Out Up Left", RotateOutUpLeft()),
                Animation("Rotate Out Up Right", RotateOutUpRight()),
                Animation("Rotate Out Down Right", RotateOutDownRight())
            )
        ),
        AnimationSet(
            section = AnimationSection.Specials, animations = listOf(
                Animation("Hinge", Hinge()),
                Animation("Jack In The Box", JackInTheBox()),
                Animation("Roll In", RollIn(-containerWidth)),
                Animation("Roll Out", RollOut()),
            )
        ),
        AnimationSet(
            section = AnimationSection.ZoomingEntrances, animations = listOf(
                Animation("Zoom In", ZoomIn()),
                Animation(
                    "Zoom In Down", ZoomInDown(
                        peakOffsetY = -(containerHeight / 3),
                        prePeakDelta = (containerHeight / 3) * 0.1f
                    )
                ),
                Animation("Zoom In Left", ZoomInLeft(peakOffsetX = -(containerWidth / 3))),
                Animation("Zoom In Right", ZoomInRight(peakOffsetX = containerWidth / 3)),
                Animation(
                    "Zoom In Up", ZoomInUp(
                        peakOffsetY = (containerHeight / 3),
                        prePeakDelta = (containerHeight / 3) * 0.1f
                    )
                ),
            )
        ),
        AnimationSet(
            section = AnimationSection.ZoomingExits, animations = listOf(
                Animation("Zoom Out", ZoomOut()),
                Animation(
                    "Zoom Out Down", ZoomOutDown(
                        vanishOffsetY = -(containerHeight / 3),
                    )
                ),
                Animation("Zoom Out Left", ZoomOutLeft(vanishOffsetX = -(containerWidth / 3))),
                Animation("Zoom Out Right", ZoomOutRight(vanishOffsetX = containerWidth / 3)),
                Animation(
                    "Zoom Out Up", ZoomOutUp(
                        vanishOffsetY = (containerHeight / 3),
                    )
                ),
            )
        ),
    )
}


@Composable
private fun getContainerWidth(size: DpSize): Float {
    val density = LocalDensity.current
    return with(density) {
        size.width.toPx()
    }
}

@Composable
private fun getContainerHeight(size: DpSize): Float {
    val density = LocalDensity.current
    return with(density) {
        size.width.toPx()
    }
}

