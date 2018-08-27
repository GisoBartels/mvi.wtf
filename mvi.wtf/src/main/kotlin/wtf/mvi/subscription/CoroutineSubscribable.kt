package wtf.mvi.subscription

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

abstract class CoroutineSubscribable<T>(
    private val actionContext: CoroutineContext = DefaultDispatcher,
    retainLastItem: Boolean = false
) : Subscribable<T> {

    private val channel: BroadcastChannel<T> = if (retainLastItem) ConflatedBroadcastChannel() else BroadcastChannel(1)

    override fun subscribe(action: (T) -> Unit): Subscription {
        val channelSubscription = channel.openSubscription()
        launch(actionContext) { channelSubscription.consumeEach { action.invoke(it) } }
        return Subscription(channelSubscription)
    }

    protected fun post(value: T) {
        launch(actionContext) {
            channel.send(value)
        }
    }

}