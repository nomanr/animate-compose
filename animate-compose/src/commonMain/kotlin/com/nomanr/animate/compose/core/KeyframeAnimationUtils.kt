package com.nomanr.animate.compose.core


/**
 * Find a matching keyframe for a given animation progress.
 * - Returns a Segment if progress is between start and end
 * - Returns a Static if progress is less than or equal to its percent
 */
fun List<Keyframe>.atProgress(progress: Float): Keyframe? {
    return this.findLast {
        when (it) {
            is Keyframe.Segment -> progress in it.start..it.end
            is Keyframe.Static -> progress >= it.percent
        }
    }
}