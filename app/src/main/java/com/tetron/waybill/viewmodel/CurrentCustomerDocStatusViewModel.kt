package com.tetron.waybill.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.data.repository.CurrentCustomerDocStatusRepository
import com.tetron.waybill.model.customerdoc.CurrentCustomerDocStatus
import kotlinx.coroutines.launch

class CurrentCustomerDocStatusViewModel : ViewModel() {

    private val currentCustomerDocStatusRepository = CurrentCustomerDocStatusRepository()

    fun addCurrentCustomerDocStatus(id: String) {
        viewModelScope.launch {
            val status = CurrentCustomerDocStatus(id)
            currentCustomerDocStatusRepository.addCurrentCustomerDocStatus(status)
        }
    }

}