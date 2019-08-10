package wtf.mvi.example.number

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import wtf.mvi.MviIntent
import wtf.mvi.MviPresenter
import wtf.mvi.example.number.NumberView.NumberIntent.MinusIntent
import wtf.mvi.example.number.NumberView.NumberIntent.PlusIntent

class NumberPresenter(private val numberInteractor: NumberInteractor) : MviPresenter<NumberView.State> {

    override val renderFlow: Flow<NumberView.State> = numberInteractor.number.flow.map { NumberView.State(it) }

    override fun onIntent(intent: MviIntent) {
        when (intent) {
            is PlusIntent -> numberInteractor.increase()
            is MinusIntent -> numberInteractor.decrease()
        }
    }

}