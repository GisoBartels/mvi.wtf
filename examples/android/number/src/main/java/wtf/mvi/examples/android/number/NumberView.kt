package wtf.mvi.examples.android.number

import wtf.mvi.MviIntent
import wtf.mvi.MviView

interface NumberView : MviView<NumberViewState> {

    val plusIntent: MviIntent<Unit>
    val minusIntent: MviIntent<Unit>

}