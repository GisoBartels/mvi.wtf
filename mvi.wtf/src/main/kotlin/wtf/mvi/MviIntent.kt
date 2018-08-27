package wtf.mvi

import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import wtf.mvi.subscription.Subscription

class MviIntent<Data>(private val coroutineDispatcher: CoroutineDispatcher = DefaultDispatcher) {

    private val channel = BroadcastChannel<Data>(1)

    fun post(data: Data) {
        launch(coroutineDispatcher) { channel.send(data) }
    }

    fun subscribe(action: (Data) -> Unit): Subscription {
        val channelSubscription = channel.openSubscription()
        launch(coroutineDispatcher) { channelSubscription.consumeEach { action(it) } }
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