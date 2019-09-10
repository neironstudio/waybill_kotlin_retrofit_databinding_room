package com.tetron.waybill.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.R
import com.tetron.waybill.data.Result.Success
import com.tetron.waybill.data.repository.CustomerDocRepository
import com.tetron.waybill.model.model1c.CustomerDoc
import com.tetron.waybill.model.waybill.PaidSubmissionLimit
import kotlinx.coroutines.launch

class CurrentCustomerDocViewModel(app: Application) : AndroidViewModel(app) {

    private val customerDocRepository = CustomerDocRepository()
    private val paidTimeLimit = PaidSubmissionLimit.TIME.limit
    private val paidDistanceLimit = PaidSubmissionLimit.DISTANCE.limit

    private val _currentCustomerDoc = MutableLiveData<CustomerDoc>()
    val currentCustomerDoc: LiveData<CustomerDoc> = _currentCustomerDoc

    private val _timeToCustomerError = MutableLiveData<Int?>()
    val timeToCustomerError: LiveData<Int?> = _timeToCustomerError

    private val _distanceToCustomerError = MutableLiveData<Int?>()
    val distanceToCustomerError: LiveData<Int?> = _distanceToCustomerError

    private val _timeToBaseError = MutableLiveData<Int?>()
    val timeToBaseError: LiveData<Int?> = _timeToBaseError

    private val _distanceToBaseError = MutableLiveData<Int?>()
    val distanceToBaseError: LiveData<Int?> = _distanceToBaseError

    private val _isDataValid = MutableLiveData<Boolean>()
    val isDataValid: LiveData<Boolean> = _isDataValid

    init {
        // Set initial state
        viewModelScope.launch {
            val getCustomerDocsResult = customerDocRepository.getCurrentCustomerDoc()

            if (getCustomerDocsResult is Success) {
                _currentCustomerDoc.value = getCustomerDocsResult.data
            }
        }
    }

    fun updateCustomerDoc() {
        _currentCustomerDoc.value?.let {
            viewModelScope.launch {
                customerDocRepository.updateCustomerDoc(it)
            }
        }
    }

    fun updateTimeSpent() {
        _currentCustomerDoc.value?.let {
            viewModelScope.launch {
                customerDocRepository.updateTimeSpent(
                    it.id as String,
                    it.timeToCustomer as Int,
                    it.distanceToCustomer as Int,
                    it.timeToBase as Int,
                    it.distanceToBase as Int
                )
            }
        }
    }

    fun timeToCustomerChanged(text: String) {
        if (!text.isBlank()) {
            val time = text.toInt()
            if (time in 0..paidTimeLimit) {
                _timeToCustomerError.value = null
                _isDataValid.value = true
                setNewTimeValue(time)
            } else {
                _timeToCustomerError.value = R.string.err_invalid_time_to_customer
                _isDataValid.value = false
            }
        } else {
            _timeToCustomerError.value = R.string.err_field_is_empty
            _isDataValid.value = false
        }
    }

    private fun setNewTimeValue(time: Int) {
        _currentCustomerDoc.value?.let {
            it.timeToCustomer = time
            if (time >= paidTimeLimit / 2) {
                it.timeToBase = paidTimeLimit - time
            } else it.timeToBase = time
            _currentCustomerDoc.value = it
        }
    }

    fun distanceToCustomerChanged(text: String) {
        if (!text.isBlank()) {
            val distance = text.toInt()
            if (distance in 0..paidDistanceLimit) {
                _distanceToCustomerError.value = null
                _isDataValid.value = true
                setNewDistanceValue(distance)
            } else {
                _distanceToCustomerError.value = R.string.err_invalid_distance_to_customer
                _isDataValid.value = false
            }
        } else {
            _distanceToCustomerError.value = R.string.err_field_is_empty
            _isDataValid.value = false
        }
    }

    private fun setNewDistanceValue(distance: Int) {
        _currentCustomerDoc.value?.let {
            it.distanceToCustomer = distance
            if (distance >= paidDistanceLimit / 2) {
                it.distanceToBase = paidDistanceLimit - distance
            } else it.distanceToBase = distance
            _currentCustomerDoc.value = it
        }
    }

    fun timeToBaseChanged(text: String) {
        if (!text.isBlank()) {
            val time = text.toInt()
            val rangeTo = paidTimeLimit - currentCustomerDoc.value?.timeToCustomer as Int
            if (time in 0..rangeTo) {
                _timeToBaseError.value = null
                _isDataValid.value = true
                setNewTimeToBaseValue(time)
            } else {
                _timeToBaseError.value = R.string.err_invalid_time_to_base
                _isDataValid.value = false
            }
        } else {
            _timeToBaseError.value = R.string.err_field_is_empty
            _isDataValid.value = false
        }
    }

    private fun setNewTimeToBaseValue(time: Int) {
        _currentCustomerDoc.value?.let {
            it.timeToBase = time
            _currentCustomerDoc.value = it
        }
    }

    fun distanceToBaseChanged(text: String) {
        if (!text.isBlank()) {
            val distance = text.toInt()
            val rangeTo = paidDistanceLimit - currentCustomerDoc.value?.distanceToCustomer as Int
            if (distance in 0..rangeTo) {
                _distanceToBaseError.value = null
                _isDataValid.value = true
                setNewDistanceToBaseValue(distance)
            } else {
                _distanceToBaseError.value = R.string.err_invalid_distance_to_base
                _isDataValid.value = false
            }
        } else {
            _distanceToBaseError.value = R.string.err_field_is_empty
            _isDataValid.value = false
        }
    }

    private fun setNewDistanceToBaseValue(distance: Int) {
        _currentCustomerDoc.value?.let {
            it.distanceToBase = distance
            _currentCustomerDoc.value = it
        }
    }

}