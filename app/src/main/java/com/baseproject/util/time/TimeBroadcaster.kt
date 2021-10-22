package com.baseproject.util.time

import androidx.lifecycle.LifecycleOwner
import com.baseproject.common.constant.TIME_BROADCASTER_REFRESH_PERIOD_IN_SECONDS
import com.baseproject.util.flow.collectWhileStarted
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TimeBroadcaster(private val scope: CoroutineScope) {

    private val time = MutableSharedFlow<Long>(replay = 1)
    private val refreshPeriod = TIME_BROADCASTER_REFRESH_PERIOD_IN_SECONDS.toLong().fromSecondsToMillis()

    init {
        scope.launch {
            while (true) {
                time.emit(timeNow)
                delay(refreshPeriod)
            }
        }
    }

    fun observeTime(action: (value: Long) -> Unit) =
        scope.launch {
            time.collect {
                action(it)
            }
        }

    fun observeTime(lifecycleOwner: LifecycleOwner, action: (value: Long) -> Unit) =
        time.collectWhileStarted(lifecycleOwner) {
            action(it)
        }
}
