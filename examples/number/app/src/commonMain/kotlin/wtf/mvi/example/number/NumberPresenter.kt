package wtf.mvi.example.number

import wtf.mvi.MviIntent
import wtf.mvi.MviPresenter
import wtf.mvi.example.number.NumberView.NumberIntent.MinusIntent
import wtf.mvi.example.number.NumberView.NumberIntent.PlusIntent
import wtf.mvi.subscription.Subscription

class NumberPresenter(private val numberInteractor: NumberInteractor) : MviPresenter<NumberView> {

    private var numberSubscription: Subscription? = null
    private var intentSubscription: Subscription? = null

    override fun attachView(view: NumberView) {
        numberSubscription = numberInteractor.subscribe { view.render(NumberView.State(numberInteractor.number)) }
        view.render(NumberView.State(numberInteractor.number))
    }

    override fun detachView() {
        numberSubscription?.cancel()
        intentSubscription?.cancel()
    }

    override fun onIntent(intent: MviIntent) {
        when (intent) {
            is PlusIntent -> numberInteractor.increase()
            is MinusIntent -> numberInteractor.decrease()
        }
    }

}