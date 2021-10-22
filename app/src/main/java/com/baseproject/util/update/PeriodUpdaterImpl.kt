package com.baseproject.util.update

import androidx.lifecycle.LifecycleOwner
import com.baseproject.util.flow.collectWhileStarted
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PeriodUpdaterImpl(scope: CoroutineScope, private val refreshPeriod: Long) : PeriodUpdater {

    private val update = MutableSharedFlow<Unit>(replay = 1)

    init {
        scope.launch {
            while (true) {
                update.emit(Unit)
                delay(refreshPeriod)
            }
        }
    }

    override fun observe(lifecycleOwner: LifecycleOwner, action: () -> Unit) {
        update.collectWhileStarted(lifecycleOwner) {
            action()
        }
    }
}
