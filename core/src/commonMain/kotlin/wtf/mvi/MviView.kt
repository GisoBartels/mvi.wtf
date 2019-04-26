package wtf.mvi

import wtf.mvi.subscription.SimpleSubscribable
import wtf.mvi.subscription.Subscription

interface MviView<in ViewStateType : MviView.State> {

    interface State

    fun render(viewState: ViewStateType)

}

fun MviView<*>.subscribeToIntents(observer: (MviIntent) -> Unit): Subscription {
    val subscribable = intentSubscribables[this]
            ?: SimpleSubscribable<MviIntent>().also { intentSubscribables[this] = it }
    return subscribable.subscribe(observer).onCancel {
        if (subscribable.activeSubscriptions <= 1)
            intentSubscribables.remove(this)
    }
}

fun MviView<*>.publish(intent: MviIntent) {
    intentSubscribables[this]?.publish(intent)
}

private val intentSubscribables = mutableMapOf<MviView<*>, SimpleSubscribable<MviIntent>>()


