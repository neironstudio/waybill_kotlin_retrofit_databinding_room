package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tetron.waybill.data.repository.RefuelingRepository
import com.tetron.waybill.model.waybill.Refueling

class FuelViewModel : ViewModel() {

    private val refuelingRepository = RefuelingRepository()

    private val _refuelings = MutableLiveData<List<Refueling>>().apply { value = refuelingRepository.getRefuelings() }
    val refuelings: LiveData<List<Refueling>> = _refuelings

}