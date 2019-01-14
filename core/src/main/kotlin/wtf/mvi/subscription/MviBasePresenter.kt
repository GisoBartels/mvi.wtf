package wtf.mvi.subscription

import wtf.mvi.MviIntent
import wtf.mvi.MviPresenter
import wtf.mvi.MviView
import wtf.mvi.intents

abstract class MviBasePresenter<ViewType : MviView<ViewState>, ViewState : MviView.State>(initialViewState: ViewState) :
    MviPresenter<ViewType> {

    var viewState = initialViewState
        private set

    private var view: ViewType? = null

    private var intentSubscription: Subscription? = null

    fun updateViewState(newViewState: ViewState) {
        viewState = newViewState
        view?.render(newViewState)
    }

    fun updateViewStateWithoutRendering(newViewState: ViewState) {
        viewState = newViewState
    }

    override fun attachView(view: ViewType) {
        this.view = view
        view.render(viewState)
        intentSubscription = view.intents.subscribe { onIntent(it) }
        onAttachView(view)
    }

    override fun detachView() {
        onDetachView()
        intentSubscription?.cancel()
        intentSubscription = null
        view = null
    }

    open fun onIntent(intent: MviIntent) {}

    open fun onAttachView(view: ViewType) {}

    open fun onDetachView() {}
}