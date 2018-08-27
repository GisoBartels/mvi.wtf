package wtf.mvi.android

import android.view.View
import wtf.mvi.MviIntent
import wtf.mvi.post
import java.util.*

fun View.clickIntent(): MviIntent {
    val intent = viewClickIntents[this] ?: object : MviIntent {}.also { viewClickIntents[this] = it }
    setOnClickListener { intent.post() }
    return intent
}

private val viewClickIntents = WeakHashMap<View, MviIntent>()