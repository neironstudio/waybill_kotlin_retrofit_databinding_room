package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.R
import com.tetron.waybill.data.WorkPauseReason
import com.tetron.waybill.data.repository.CurrentCustomerDocStatusRepository
import kotlinx.coroutines.launch

class WorkPausedViewModel : ViewModel() {

    private val currentCustomerDocStatusRepository = CurrentCustomerDocStatusRepository()

    private val _pauseReason: LiveData<String> =
        currentCustomerDocStatusRepository.getWorkPauseReason()
    val pauseReason: LiveData<String> = _pauseReason

    private val _subtitle = MutableLiveData<Int?>()
    val subtitle: LiveData<Int?> = _subtitle

    private val _showWarning = MutableLiveData<Boolean>()
    val showWarning: LiveData<Boolean> = _showWarning

    fun setReasonText(pauseReason: String) {
        when (pauseReason) {
            WorkPauseReason.LUNCH.name -> {
                _subtitle.value = R.string.tv_reason_lunch_title
                _showWarning.value = false
            }
            WorkPauseReason.CUSTOMER.name -> {
                _subtitle.value = R.string.tv_reason_customer_title
                _showWarning.value = true
            }
            WorkPauseReason.DRIVER.name -> {
                _subtitle.value = R.string.tv_reason_driver_title
                _showWarning.value = false
            }
        }
    }

    fun resumeWork() {
        viewModelScope.launch {
            currentCustomerDocStatusRepository.resumeWork()
        }
    }

}