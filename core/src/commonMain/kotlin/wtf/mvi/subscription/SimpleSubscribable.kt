package wtf.mvi.subscription

open class SimpleSubscribable<T> : Subscribable<T> {

    private val subscriptions = mutableSetOf<(T) -> Unit>()

    val activeSubscriptions: Int
        get() = subscriptions.size

    override fun subscribe(action: (T) -> Unit): Subscription {
        subscriptions.add(action)
        return object : Subscription {
            override fun cancel() {
                subscriptions.remove(action)
            }
        }
    }

    override fun publish(item: T) {
        subscriptions.toList().forEach { it(item) }
    }

}