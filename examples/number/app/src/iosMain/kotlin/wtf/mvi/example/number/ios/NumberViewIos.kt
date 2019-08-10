package wtf.mvi.example.number.ios

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import platform.UIKit.UILabel
import wtf.mvi.example.number.NumberInteractor
import wtf.mvi.example.number.NumberPresenter
import wtf.mvi.example.number.NumberView
import wtf.mvi.example.number.NumberView.NumberIntent.MinusIntent
import wtf.mvi.example.number.NumberView.NumberIntent.PlusIntent

class NumberViewIos : NumberView {

    private val numberPresenter = NumberPresenter(NumberInteractor())

    fun render(numberText: UILabel) {
        GlobalScope.launch(MainDispatcher) {
            numberPresenter.renderFlow.collect {
                numberText.text = it.number.toString()
            }
        }
    }

    fun minusPressed() {
        numberPresenter.onIntent(MinusIntent)
    }

    fun plusPressed() {
        numberPresenter.onIntent(PlusIntent)
    }

}