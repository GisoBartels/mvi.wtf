package wtf.mvi.examples.android.number

import wtf.mvi.MviView

interface NumberView : MviView<NumberView.State> {

    sealed class NumberIntent : MviView.Intent {
        object PlusIntent : NumberIntent()
        object MinusIntent : NumberIntent()
    }

    data class State(val number: Int) : MviView.State

}