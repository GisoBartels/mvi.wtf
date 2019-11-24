package wtf.mvi.android

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

@UseExperimental(ExperimentalCoroutinesApi::class)
fun View.onClickFlow(): Flow<ClickEvent> = callbackFlow {
    setOnClickListener {
        offer(ClickEvent(this@onClickFlow))
    }
    awaitClose { setOnClickListener(null) }
}

data class ClickEvent(val view: View)

@UseExperimental(ExperimentalCoroutinesApi::class)
val TextView.textChangeFlow: Flow<String>
    get() = callbackFlow<String> {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                offer(s?.toString() ?: "")
            }
        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }.buffer(Channel.CONFLATED)
