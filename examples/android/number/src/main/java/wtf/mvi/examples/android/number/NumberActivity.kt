package wtf.mvi.examples.android.number

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.simple_activity.*
import wtf.mvi.examples.android.number.NumberView.NumberIntent.MinusIntent
import wtf.mvi.examples.android.number.NumberView.NumberIntent.PlusIntent
import wtf.mvi.publish

class NumberActivity : Activity(), NumberView {

    private val numberPresenter = NumberPresenter(NumberInteractor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_activity)

        plusButton.setOnClickListener { publish(PlusIntent) }
        minusButton.setOnClickListener { publish(MinusIntent) }

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