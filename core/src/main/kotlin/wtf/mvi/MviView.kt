package wtf.mvi

import wtf.mvi.subscription.SimpleSubscribable
import wtf.mvi.subscription.Subscribable

interface MviView<in ViewStateType : MviView.State> {

    interface Intent

    val intents: Subscribable<Intent>

    interface State

    fun render(viewState: ViewStateType)

}

fun intentSubscribable() : Subscribable<MviView.Intent> = SimpleSubscribable()
