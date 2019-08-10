package wtf.mvi.example.number

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@UseExperimental(ExperimentalCoroutinesApi::class, FlowPreview::class)
class Source<T>(initialValue: T) {
    private val chan = ConflatedBroadcastChannel(initialValue)

    val flow: Flow<T> = chan.asFlow()

    var value: T
        get() = chan.value
        set(value) {
            chan.offer(value)
        }
}

