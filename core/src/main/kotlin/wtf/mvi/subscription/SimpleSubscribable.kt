package wtf.mvi.subscription

open class SimpleSubscribable<T> : Subscribable<T> {

    private val subscriptions = mutableSetOf<(T) -> Unit>()

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