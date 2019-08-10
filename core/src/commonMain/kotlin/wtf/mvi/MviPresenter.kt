package wtf.mvi

import kotlinx.coroutines.flow.Flow

interface MviPresenter<ViewStateType : MviView.State> {

    val renderFlow: Flow<ViewStateType>

    fun onIntent(intent: MviIntent)

}




