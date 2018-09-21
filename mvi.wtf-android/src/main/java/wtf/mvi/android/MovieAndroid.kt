package wtf.mvi.android

import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.Main
import wtf.mvi.MviIntent
import wtf.mvi.post
import java.util.*

fun View.clickIntent(): MviIntent<Unit> {
    val intent = viewClickIntents[this] ?: MviIntent<Unit>(Dispatchers.Main).also { viewClickIntents[this] = it }
    setOnClickListener { intent.post() }
    return intent
}

private val viewClickIntents = WeakHashMap<View, MviIntent<Unit>>()