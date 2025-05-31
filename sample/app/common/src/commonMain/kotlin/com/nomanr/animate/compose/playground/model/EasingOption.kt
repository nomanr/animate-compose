package com.nomanr.animate.compose.playground.model

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import com.nomanr.animate.compose.core.Easings

enum class EasingOption(val displayName: String) {
    None("None"),
    Linear("Linear"),
    SmoothComfort("Smooth Comfort"),
    QuickRise("Quick Rise"),
    BalancedEase("Balanced Ease"),
    SoftLanding("Soft Landing"),
    EaseOut("Ease Out"),
    EaseInOut("Ease In Out"),
    FastOutSlowIn("Fast Out Slow In");

    fun toEasing(): Easing? = when (this) {
        None -> null
        Linear -> LinearEasing
        SmoothComfort -> Easings.SmoothComfort
        QuickRise -> Easings.QuickRise
        BalancedEase -> Easings.BalancedEase
        SoftLanding -> Easings.SoftLanding
        EaseOut -> Easings.EaseOut
        EaseInOut -> Easings.EaseInOut
        FastOutSlowIn -> Easings.FastOutSlowInEasing
    }
}