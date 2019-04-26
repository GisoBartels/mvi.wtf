package wtf.mvi.subscription

interface Subscribable<T> {
    fun subscribe(action: (T) -> Unit): Subscription
    fun publish(item: T)
}