package wtf.mvi.example.number.android

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.number_activity.*
import wtf.mvi.example.number.NumberInteractor
import wtf.mvi.example.number.NumberPresenter
import wtf.mvi.example.number.NumberView
import wtf.mvi.example.number.NumberView.NumberIntent.MinusIntent
import wtf.mvi.example.number.NumberView.NumberIntent.PlusIntent
import wtf.mvi.example.number.R

class NumberActivity : Activity(), NumberView {

    private val numberPresenter = NumberPresenter(NumberInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.number_activity)

        plusButton.setOnClickListener { numberPresenter.onIntent(PlusIntent) }
        minusButton.setOnClickListener { numberPresenter.onIntent(MinusIntent) }

        numberPresenter.attachView(this)
    }

    override fun onDestroy() {
        numberPresenter.detachView()
        super.onDestroy()
    }

    override fun render(viewState: NumberView.State) {
        numberView.text = viewState.number.toString()
    }

}