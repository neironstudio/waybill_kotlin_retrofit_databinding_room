package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tetron.waybill.util.Event

class SimpleSwitchFragmentEventViewModel : ViewModel() {

    private val _setFullScreenFragment = MutableLiveData<Event<Unit>>()
    val typesetFullScreenFragment: LiveData<Event<Unit>> = _setFullScreenFragment

    private val _unSetFullScreenFragment = MutableLiveData<Event<Unit>>()
    val typeUnSetFullScreenFragment: LiveData<Event<Unit>> = _unSetFullScreenFragment

    private val _customerMarkFragment = MutableLiveData<Event<Unit>>()
    val customerMarkFragment: LiveData<Event<Unit>> = _customerMarkFragment
    fun setCustomerMarkFragment() {
        _unSetFullScreenFragment.value = Event(Unit)
        _customerMarkFragment.value = Event(Unit)
    }

    private val _signDrawFragment = MutableLiveData<Event<Unit>>()
    val signDrawFragment: LiveData<Event<Unit>> = _signDrawFragment
    fun setSignDrawFragment() {
        _signDrawFragment.value = Event(Unit)
        _setFullScreenFragment.value = Event(Unit)
    }

}