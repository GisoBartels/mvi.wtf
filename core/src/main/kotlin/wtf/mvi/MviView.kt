package wtf.mvi

import wtf.mvi.subscription.SimpleSubscribable
import wtf.mvi.subscription.Subscribable
import java.util.*

interface MviView<in ViewStateType : MviView.State> {

    interface State

    fun render(viewState: ViewStateType)

}

val MviView<*>.intents: Subscribable<MviIntent>
    get() = intentSubscribables[this] ?: SimpleSubscribable<MviIntent>().also { intentSubscribables[this] = it }

private val intentSubscribables = WeakHashMap<MviView<*>, Subscribable<MviIntent>>()


