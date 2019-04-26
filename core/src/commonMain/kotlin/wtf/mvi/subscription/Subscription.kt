package wtf.mvi.subscription

interface Subscription {
    fun cancel()

    fun onCancel(action: () -> Unit) = object : Subscription {
        override fun cancel() {
            action()
            this@Subscription.cancel()
        }
    }
}