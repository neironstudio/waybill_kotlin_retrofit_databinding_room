package com.tetron.waybill.service

import androidx.lifecycle.MutableLiveData

class DataControllerWayPathSelected private constructor() {

    var selectedWayPathId = MutableLiveData<Int>()
        internal set

    companion object {
        val controllerInstance = DataControllerWayPathSelected()
    }

}
