package wtf.mvi

import wtf.mvi.subscription.Subscription

interface MviPresenter<ViewType : MviView<*>> {

    val intentActions: IntentActions<ViewType>

    fun attachView(view: ViewType) {
        intentActions.subscribe(view)
    }

    fun detachView() {
        intentActions.unsubscribe()
    }

    fun intentActions(vararg actions: ViewType.() -> Subscription) = IntentActions(*actions)

}




