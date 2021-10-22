package com.baseproject.util.camera

import androidx.camera.core.AspectRatio
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.Executor
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val RATIO_4_3_VALUE = 4.0 / 3.0
private const val RATIO_16_9_VALUE = 16.0 / 9.0

/**
 *  [androidx.camera.core.AspectRatio]. Currently it has values of 4:3 & 16:9.
 *
 *  Detecting the most suitable ratio for dimensions provided in @params by counting absolute
 *  of preview ratio to one of the provided values.
 *
 *  @param width - preview width
 *  @param height - preview height
 *  @return suitable aspect ratio
 */
fun aspectRatio(width: Int, height: Int): Int {
    val previewRatio = max(width, height).toDouble() / min(width, height)
    if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE))
        return AspectRatio.RATIO_4_3
    return AspectRatio.RATIO_16_9
}

/**
 * The order of the parameters is just changed for convenience
 */
fun <V> ListenableFuture<V>.addListener(executor: Executor, listener: Runnable) {
    this.addListener(listener, executor)
}

