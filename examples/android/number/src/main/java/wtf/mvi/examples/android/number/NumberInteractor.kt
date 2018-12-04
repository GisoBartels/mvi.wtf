package wtf.mvi.examples.android.number

import wtf.mvi.subscription.SimpleSubscribable
import kotlin.properties.Delegates.observable

object NumberInteractor : SimpleSubscribable<Int>() {

    var number by observable(0) { _, _, newValue -> publish(newValue) }
        private set

    fun increase() {
        number++
    }

    fun decrease() {
        number--
    }

}