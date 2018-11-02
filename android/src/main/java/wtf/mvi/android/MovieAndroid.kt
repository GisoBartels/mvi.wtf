package wtf.mvi.android

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import wtf.mvi.MviIntent
import wtf.mvi.post
import java.util.*

private val viewClickIntents = WeakHashMap<View, MviIntent<Unit>>()

fun View.clickIntent(): MviIntent<Unit> {
    val intent = viewClickIntents[this] ?: MviIntent<Unit>(Dispatchers.Main).also { viewClickIntents[this] = it }
    setOnClickListener { intent.post() }
    return intent
}

private val textChangeIntents = WeakHashMap<TextView, MviIntent<String>>()

fun TextView.textChangeIntent(): MviIntent<String> {
    val intent = textChangeIntents[this] ?: MviIntent<String>(Dispatchers.Main)
        .also { textChangeIntents[this] = it }

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            intent.post(s?.toString() ?: "")
        }
    })
    return intent
}

