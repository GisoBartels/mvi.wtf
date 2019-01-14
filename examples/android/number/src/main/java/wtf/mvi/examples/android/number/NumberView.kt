package wtf.mvi.examples.android.number

import wtf.mvi.MviIntent
import wtf.mvi.MviView

interface NumberView : MviView<NumberView.State> {

    sealed class NumberIntent : MviIntent {
        object PlusIntent : NumberIntent()
        object MinusIntent : NumberIntent()
    }

    data class State(val number: Int) : MviView.State

}