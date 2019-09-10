package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tetron.waybill.data.repository.WebServer1CRepository
import com.tetron.waybill.model.model1c.WaybillDoc

class OrderDataViewModel : ViewModel() {

    private val webServerRepository: WebServer1CRepository = WebServer1CRepository()

    fun getOrderResponse() {
        webServerRepository.getOrderResponse()
    }

    fun getWayBillDocLiveData(): LiveData<List<WaybillDoc>> {
        return webServerRepository.getWayBillDocsList()
    }
}