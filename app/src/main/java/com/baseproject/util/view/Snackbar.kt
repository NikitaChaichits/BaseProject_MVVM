@file:SuppressLint("WrongConstant")

package com.baseproject.util.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.baseproject.R
import com.baseproject.common.constant.SNACKBAR_DURATION
import com.google.android.material.snackbar.Snackbar

typealias ActionType = () -> Unit

fun Context.snackbar(
    view: View,
    @StringRes messageStringRes: Int,
    anchorView: View? = null,
    @StringRes actionStringRes: Int? = null,
    action: ActionType = {}
) {
    snackbar(
        view,
        getString(messageStringRes),
        anchorView,
        getString(actionStringRes ?: R.string.close_caps, action),
        action
    )
}

fun Context.snackbar(
    view: View,
    messageString: String,
    anchorView: View? = null,
    actionString: String? = null,
    action: ActionType = {}
) {

    hideKeyboard(view)

    if (view.isAttachedToWindow) {
        Snackbar.make(view, messageString, Snackbar.LENGTH_INDEFINITE)
            .setDuration(SNACKBAR_DURATION)
            .setAnchorView(anchorView)
            .setAction(actionString) { action() }
            .addCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {
                    super.onShown(sb)
                    // this is a crutchy way to fix a snackbar leaking,
                    // happened when the anchor is specified
                    sb?.anchorView = null
                }
            })
            .show()
    }
}