package wtf.mvi.subscription

import kotlin.coroutines.CoroutineContext

class SubscribableValue<T>(
    initialValue: T,
    actionContext: CoroutineContext,
    retainLastItem: Boolean = false
) : CoroutineSubscribable<T>(actionContext, retainLastItem) {
    var value = initialValue
        set(value) {
            field = value
            post(value)
        }
}