package wtf.mvi.subscription

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class CoroutineSubscribable<T>(
    override val coroutineContext: CoroutineContext = Dispatchers.Default,
    retainLastItem: Boolean = false
) : Subscribable<T>, CoroutineScope {

    private val channel: BroadcastChannel<T> = if (retainLastItem) ConflatedBroadcastChannel() else BroadcastChannel(1)

    override fun subscribe(action: (T) -> Unit): Subscription {
        val channelSubscription = channel.openSubscription()
        launch { channelSubscription.consumeEach { action.invoke(it) } }
        return Subscription(channelSubscription)
    }

    protected fun post(value: T) {
        launch { channel.send(value) }
    }

}