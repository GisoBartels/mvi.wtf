package wtf.mvi

import wtf.mvi.subscription.Subscribable
import wtf.mvi.subscription.Subscription

abstract class MviBasePresenter<ViewType : MviView<*>>() : MviPresenter<ViewType> {

    private val subscriptions = mutableSetOf<Subscription>()

    override fun detachView() {
        super.detachView()
        subscriptions.forEach { it.cancel() }
        subscriptions.clear()
    }

    fun <T> Subscribable<T>.subscribeUntilViewDetach(action: (T) -> Unit) = subscribe(action).cancelOnDetach()

    fun Subscription.cancelOnDetach(): Subscription {
        subscriptions.add(this)
        return this
    }

}