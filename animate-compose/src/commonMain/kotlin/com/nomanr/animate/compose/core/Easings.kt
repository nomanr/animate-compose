package com.nomanr.animate.compose.core

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing

object Easings {
    /** a smooth ease-in then ease-out for natural motion */
    val SmoothComfort: Easing = CubicBezierEasing(0.30f, 0.05f, 0.70f, 0.90f)

    /** a snappy entrance with gentle deceleration */
    val QuickRise: Easing = CubicBezierEasing(0.60f, 0.05f, 0.90f, 0.60f)

    /** the classic ease-in-out “standard” (same as EaseInOut) */
    val BalancedEase: Easing = CubicBezierEasing(0.42f, 0.00f, 0.58f, 1.00f)

    /** a soft in-and-out for subtle transitions */
    val SoftLanding: Easing = CubicBezierEasing(0.25f, 0.10f, 0.25f, 1.00f)

    /** a fast ease-in then slow ease-out for quick motion */
    val EaseOut: Easing = CubicBezierEasing(0f, 0f, 0.58f, 1f)

    /** easing Curve that starts slowly, speeds up and then ends slowly.*/
    val EaseInOut: Easing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)

    /** a fast ease-in then slow ease-out for quick motion */
    val FastOutSlowInEasing: Easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)


}