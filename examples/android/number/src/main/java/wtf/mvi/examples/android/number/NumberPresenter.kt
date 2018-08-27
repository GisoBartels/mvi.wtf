package wtf.mvi.examples.android.number

import wtf.mvi.MviPresenter
import wtf.mvi.bind
import wtf.mvi.subscription.Subscription

class NumberPresenter(private val numberInteractor: NumberInteractor) : MviPresenter<NumberView> {

    override val intentActions = bind(
        { plusIntent to { NumberInteractor.increase() } },
        { minusIntent to { NumberInteractor.decrease() } })

    private var numberSubscription: Subscription? = null

    override fun attachView(view: NumberView) {
        super.attachView(view)
        numberSubscription = numberInteractor.subscribe { view.render(
            NumberViewState(
                NumberInteractor.number
            )
        ) }
        view.render(NumberViewState(NumberInteractor.number))
    }

    override fun detachView() {
        numberSubscription?.cancel()
        super.detachView()
    }

}