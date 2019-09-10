package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tetron.waybill.util.Event

class DialogEventViewModel : ViewModel() {

    private val _confirmationEvent = MutableLiveData<Event<Unit>>()
    val confirmationEvent: LiveData<Event<Unit>> = _confirmationEvent

    fun confirmationClickEvent() {
        _confirmationEvent.value = Event(Unit)
    }

    private val _stringEvent = MutableLiveData<Event<String>>()
    val stringEvent: LiveData<Event<String>> = _stringEvent

    fun setStringEvent(string: String) {
        _stringEvent.value = Event(string)
    }

    private val _stringEventFun = MutableLiveData<HashMap<String, Any>>()
    val stringEventFun: LiveData<HashMap<String, Any>> = _stringEventFun

    fun setStringEventFun(any: HashMap<String, Any>) {
        _stringEventFun.value = any
    }
}