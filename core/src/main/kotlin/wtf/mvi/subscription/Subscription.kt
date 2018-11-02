package wtf.mvi.subscription

import kotlinx.coroutines.channels.ReceiveChannel

class Subscription internal constructor(private val channelSubscription: ReceiveChannel<*>) {
    fun cancel() {
        channelSubscription.cancel()
    }
}