package wtf.mvi.example.number

import wtf.mvi.subscription.SimpleSubscribable
import kotlin.properties.Delegates.observable

class NumberInteractor : SimpleSubscribable<Int>() {

    var number by observable(0) { _, _, newValue -> publish(newValue) }
        private set

    fun increase() {
        number++
    }

    fun decrease() {
        number--
    }

}