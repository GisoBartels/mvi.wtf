package wtf.mvi

interface MviPresenter<ViewType : MviView<*>> {

    val intentActions: IntentActions<ViewType>

    fun attachView(view: ViewType) {
        intentActions.subscribe(view)
    }

    fun detachView() {
        intentActions.unsubscribe()
    }

}

fun <ViewType : MviView<*>> MviPresenter<ViewType>.bind(vararg actions: ViewType.() -> Pair<MviIntent, () -> Unit>) =
    IntentActions(*actions)