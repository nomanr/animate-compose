package com.nomanr.animate.compose.animated

/**
 * Callback interface for your composable animation lifecycle.
 * Override any combination of these methods
 * to get notified when an animation starts
 * when it is running
 * and when it finishes
 */
interface AnimationCallbacks {
    /** called once, immediately before the animation to 1f begins */
    fun onStart() = Unit

    /** called each time the animated progress value updates */
    fun onAnimating(progress: Float) = Unit

    /** called once, as soon as the animation reaches its end */
    fun onFinish() = Unit
}