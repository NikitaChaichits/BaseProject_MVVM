@file:SuppressLint("WrongConstant")

package com.baseproject.util.view

import android.annotation.SuppressLint
import androidx.annotation.DimenRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.baseproject.R

fun SwipeRefreshLayout.setupAppearance(
    @DimenRes startOffsetRes: Int = R.dimen.srl_start_offset,
    @DimenRes endOffsetRes: Int = R.dimen.srl_end_offset
) {
    val startOffset = resources.getDimensionPixelOffset(startOffsetRes)
    val endOffset = resources.getDimensionPixelOffset(endOffsetRes)
    setProgressViewOffset(false, startOffset, endOffset)
    setColorSchemeResources(R.color.colorPrimaryDark)
}