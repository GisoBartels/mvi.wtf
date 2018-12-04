package wtf.mvi.android

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import wtf.mvi.MviView
import wtf.mvi.subscription.Subscribable

fun Subscribable<MviView.Intent>.publishOnTextChange(
    textView: TextView,
    intentConverter: (String) -> MviView.Intent
) {
    textView.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@publishOnTextChange.publish(intentConverter(s?.toString() ?: ""))
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}
