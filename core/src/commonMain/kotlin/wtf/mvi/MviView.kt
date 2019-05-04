package wtf.mvi

interface MviView<in ViewStateType : MviView.State> {

    interface State

    fun render(viewState: ViewStateType)

}
