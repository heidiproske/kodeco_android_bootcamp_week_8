package com.kodeco.android.countryinfo.flow

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// TODO: Remove this whole object as it's no longer needed.
//  _counterFlow, along with the while loop that updates this Flow
//  can be moved in to the viewmodel associated with CountryInfoScreen.
@OptIn(DelicateCoroutinesApi::class)
object Flows {
    private val _counterFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _tapFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _backFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    val counterFlow: StateFlow<Int> = _counterFlow
    val tapFlow: StateFlow<Int> = _tapFlow
    val backFlow: StateFlow<Int> = _backFlow

    init {
        GlobalScope.launch {
            while (true) {
                delay(1_000L)
                increment()
            }
        }
    }

    fun tap() {
        _tapFlow.value += 1
    }

    fun tapBack() {
        _backFlow.value += 1
    }

    private fun increment() {
        _counterFlow.value += 1
    }
}
