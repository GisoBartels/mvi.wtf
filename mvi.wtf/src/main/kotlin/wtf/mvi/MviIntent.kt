package wtf.mvi

import wtf.mvi.subscription.Subscription
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import java.util.*

interface MviIntent

fun MviIntent.post() {
    val intent = this
    launch { subscriptions[intent]?.send(intent) }
}

fun MviIntent.subscribe(action: () -> Unit): Subscription {
    val channel = subscriptions[this] ?: BroadcastChannel<MviIntent>(1).also { subscriptions[this] = it }
    val channelSubscription = channel.openSubscription()
    launch { channelSubscription.consumeEach { action() } }
    return Subscription(channelSubscription)
}

private val subscriptions = WeakHashMap<MviIntent, BroadcastChannel<MviIntent>>()

class IntentActions<ViewType : MviView<*>>(private vararg val actions: ViewType.() -> Pair<MviIntent, () -> Unit>) {

    private val subscriptions = mutableSetOf<Subscription>()

    fun subscribe(view: ViewType) {
        actions.forEach {
            val (intent, action) = it(view)
            subscriptions.add(intent.subscribe(action))
        }
    }

    fun unsubscribe() {
        subscriptions.forEach { it.cancel() }
        subscriptions.clear()
    }

}
