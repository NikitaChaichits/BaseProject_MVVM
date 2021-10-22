package com.baseproject.util.update

import androidx.lifecycle.LifecycleOwner

interface PeriodUpdater {

    fun observe(lifecycleOwner: LifecycleOwner, action: () -> Unit)

}
