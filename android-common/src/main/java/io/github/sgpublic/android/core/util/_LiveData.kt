@file:Suppress("ObjectLiteralToLambda")

package io.github.sgpublic.android.core.util

import androidx.lifecycle.*
import io.github.sgpublic.kotlin.core.util.takeOr
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * fix 'Candidate resolution will be changed soon, please use fully qualified name to invoke the following closer candidate explicitly
 */
fun <T> MutableLiveData<T>.newObserve(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            observer(value)
        }
    })
}

fun <T: Any> ViewModel.launchWithIOContext(block: suspend CoroutineScope.() -> T) {
    viewModelScope.launch {
        withContext(Dispatchers.IO, block)
    }
}

class BooleanLiveData : MutableLiveData<Boolean?> {
    constructor() : super()
    constructor(init: Boolean) : super(init)

    fun toggle() {
        postValue(!value)
    }

    override fun getValue(): Boolean {
        return java.lang.Boolean.TRUE == super.getValue()
    }
}

class StringLiveData : MutableLiveData<String> {
    constructor() : super()
    constructor(init: String?) : super(init)

    fun append(c: Char) {
        postValue(value + c)
    }

    fun subend() {
        value.let {
            if (it.isEmpty()) return@let
            postValue(it.substring(0, it.length - 1))
        }
    }

    override fun getValue(): String {
        return super.getValue().takeOr("")
    }
}

fun <T: Any> MutableLiveData<T>.refresh() {
    postValue(value)
}