package wtf.mvi.example.number.android

import android.app.Activity
import android.os.Bundle
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.remember
import androidx.compose.state
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import wtf.mvi.example.number.NumberInteractor
import wtf.mvi.example.number.NumberPresenter
import wtf.mvi.example.number.NumberView
import wtf.mvi.example.number.NumberView.NumberIntent.MinusIntent
import wtf.mvi.example.number.NumberView.NumberIntent.PlusIntent

class NumberActivity : Activity(), NumberView, CoroutineScope by MainScope() {

    private val numberPresenter = NumberPresenter(NumberInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewState = collect(numberPresenter.renderFlow)
            MaterialTheme {
                Center {
                    Row(arrangement = Arrangement.Center) {
                        Button(onClick = { numberPresenter.onIntent(MinusIntent) }) {
                            Text("-")
                        }
                        Spacer(modifier = LayoutWidth(16.dp))
                        Text(
                            text = viewState?.number?.toString() ?: "",
                            style = TextStyle(fontSize = 24.sp)
                        )
                        Spacer(modifier = LayoutWidth(16.dp))
                        Button(onClick = { numberPresenter.onIntent(PlusIntent) }) {
                            Text("+")
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun <T> CoroutineScope.collect(flow: Flow<T>): T? {
        val state = state<T?> { null }
        val observer = remember { { newValue: T -> state.value = newValue } }

        onCommit(flow) {
            val job = launch { flow.collect { observer(it) } }
            onDispose { job.cancel() }
        }

        return state.value
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}
