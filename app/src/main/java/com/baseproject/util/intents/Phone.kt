package com.baseproject.util.intents

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.callPhone(number: String) {
    val callUri = Uri.parse("tel:$number")
    startActivity(Intent(Intent.ACTION_DIAL, callUri))
}