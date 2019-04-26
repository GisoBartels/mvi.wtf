package wtf.mvi.examples.android.number

import wtf.mvi.MviPresenter
import wtf.mvi.examples.android.number.NumberView.NumberIntent.MinusIntent
import wtf.mvi.examples.android.number.NumberView.NumberIntent.PlusIntent
import wtf.mvi.subscribeToIntents
import wtf.mvi.subscription.Subscription

class NumberPresenter(private val numberInteractor: NumberInteractor) : MviPresenter<NumberView> {

    private var numberSubscription: Subscription? = null
    private var intentSubscription: Subscription? = null

    override fun attachView(view: NumberView) {
        numberSubscription = numberInteractor.subscribe { view.render(NumberView.State(NumberInteractor.number)) }
        view.render(NumberView.State(NumberInteractor.number))
        intentSubscription = view.subscribeToIntents {
            when (it) {
                is PlusIntent -> numberInteractor.increase()
                is MinusIntent -> numberInteractor.decrease()
            }
        }
    }

    override fun detachView() {
        numberSubscription?.cancel()
        intentSubscription?.cancel()
    }

}