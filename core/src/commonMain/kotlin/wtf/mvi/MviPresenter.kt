package wtf.mvi

interface MviPresenter<ViewType : MviView<*>> {

    fun attachView(view: ViewType)

    fun detachView()

    fun onIntent(intent: MviIntent)

}




