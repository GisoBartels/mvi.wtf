package wtf.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import wtf.mvi.subscription.Subscription
import kotlin.coroutines.CoroutineContext

class MviIntent<Data>(override val coroutineContext: CoroutineContext = Dispatchers.Main) : CoroutineScope {

    private val channel = BroadcastChannel<Data>(1)

    fun post(data: Data) {
        launch { channel.send(data) }
    }

    fun subscribe(action: (Data) -> Unit): Subscription {
        val channelSubscription = channel.openSubscription()
        launch { channelSubscription.consumeEach { action(it) } }
        return Subscription(channelSubscription)
    }
}

fun MviIntent<Unit>.post() {
    post(Unit)
}

fun MviIntent<Unit>.subscribe(action: () -> Unit) = subscribe { action() }

class IntentActions<ViewType : MviView<*>>(private vararg val intentActions: ViewType.() -> Subscription) {

    private val subscriptions = mutableSetOf<Subscription>()

    fun subscribe(view: ViewType) {
        intentActions.forEach { intentAction ->
            subscriptions.add(intentAction(view))
        }
    }

    fun unsubscribe() {
        subscriptions.forEach { it.cancel() }
        subscriptions.clear()
    }

}