package wtf.mvi.subscription

import java.util.concurrent.CopyOnWriteArraySet

open class SimpleSubscribable<T> : Subscribable<T> {

    private val subscriptions: MutableSet<(T) -> Unit> = CopyOnWriteArraySet()

    override fun subscribe(action: (T) -> Unit): Subscription {
        subscriptions.add(action)
        return object : Subscription {
            override fun cancel() {
                subscriptions.remove(action)
            }
        }
    }

    override fun publish(item: T) {
        subscriptions.forEach { it(item) }
    }
}