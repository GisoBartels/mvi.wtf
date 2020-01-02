package wtf.mvi.example.number.ios

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import wtf.mvi.example.number.NumberPresenter
import wtf.mvi.example.number.NumberView

class NumberFlowCollector(private val numberPresenter: NumberPresenter) {
    private var job: Job? = null

    fun start(action: (NumberView.State) -> Unit) {
        job?.cancel()
        job = GlobalScope.launch(MainDispatcher) {
            numberPresenter.renderFlow.collect { action(it) }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}