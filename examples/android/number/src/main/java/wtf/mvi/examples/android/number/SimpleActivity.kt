package wtf.mvi.examples.android.number

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.simple_activity.*
import wtf.mvi.android.clickIntent

class SimpleActivity : Activity(), NumberView {

    override val plusIntent by lazy { plusButton.clickIntent() }
    override val minusIntent by lazy { minusButton.clickIntent() }

    private val numberPresenter = NumberPresenter(NumberInteractor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_activity)

        numberPresenter.attachView(this)
    }

    override fun onDestroy() {
        numberPresenter.detachView()
        super.onDestroy()
    }

    override fun render(viewState: NumberViewState) {
        numberView.text = viewState.number.toString()
    }

}