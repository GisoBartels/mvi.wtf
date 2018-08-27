package wtf.mvi.examples.android.number

import wtf.mvi.subscription.CoroutineSubscribable
import kotlin.properties.Delegates.observable

object NumberInteractor : CoroutineSubscribable<Int>() {

    var number by observable(0) { _, _, newValue -> post(newValue) }
        private set

    fun increase() {
        number++
    }

    fun decrease() {
        number--
    }

}