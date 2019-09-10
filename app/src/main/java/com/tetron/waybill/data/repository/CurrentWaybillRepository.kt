package com.tetron.waybill.data.repository

import com.tetron.waybill.data.WaybillStep
import com.tetron.waybill.util.SharedPreferenceHelper
import org.koin.core.KoinComponent
import org.koin.core.inject

class CurrentWaybillRepository : KoinComponent {

    private val sharedPref: SharedPreferenceHelper by inject()

    fun getCurrentWaybillStep(): WaybillStep = when (sharedPref.getWaybillStep()) {
        "PRINT" -> WaybillStep.PRINT
        "APPROVAL_SELECTION" -> WaybillStep.APPROVAL_SELECTION
        "PRINT_COMPLETED_WAYBILL" -> WaybillStep.PRINT_COMPLETED_WAYBILL
        "DEPARTURE_TO_CUSTOMER" -> WaybillStep.DEPARTURE_TO_CUSTOMER
        "BEGINNING_OF_WORK" -> WaybillStep.BEGINNING_OF_WORK
        "WORK_ON_CUSTOMER_DOC" -> WaybillStep.WORK_ON_CUSTOMER_DOC
        "TIME_SPENT" -> WaybillStep.TIME_SPENT
        "CUSTOMER_MARK" -> WaybillStep.CUSTOMER_MARK
        "RETURN_TO_BASE" -> WaybillStep.RETURN_TO_BASE
        "FUEL" -> WaybillStep.FUEL
        "PERFORMANCE_INDICATORS" -> WaybillStep.PERFORMANCE_INDICATORS
        "WAYBILL_CLOSURE" -> WaybillStep.WAYBILL_CLOSURE
        else -> WaybillStep.PRINT
    }

    fun switchWaybillStep(step: WaybillStep): WaybillStep {
        val nextStep = when (step) {
            WaybillStep.PRINT -> WaybillStep.APPROVAL_SELECTION
            WaybillStep.APPROVAL_SELECTION -> WaybillStep.PRINT_COMPLETED_WAYBILL
            WaybillStep.PRINT_COMPLETED_WAYBILL -> WaybillStep.DEPARTURE_TO_CUSTOMER
            WaybillStep.DEPARTURE_TO_CUSTOMER -> WaybillStep.BEGINNING_OF_WORK
            WaybillStep.BEGINNING_OF_WORK -> WaybillStep.WORK_ON_CUSTOMER_DOC
            WaybillStep.WORK_ON_CUSTOMER_DOC -> WaybillStep.TIME_SPENT
            WaybillStep.TIME_SPENT -> WaybillStep.CUSTOMER_MARK
            WaybillStep.CUSTOMER_MARK -> WaybillStep.RETURN_TO_BASE
            WaybillStep.RETURN_TO_BASE -> WaybillStep.FUEL
            WaybillStep.FUEL -> WaybillStep.PERFORMANCE_INDICATORS
            WaybillStep.PERFORMANCE_INDICATORS -> WaybillStep.WAYBILL_CLOSURE
            WaybillStep.WAYBILL_CLOSURE -> WaybillStep.PRINT
            else -> WaybillStep.UNKNOWN
        }

        saveCurrentWaybillStep(nextStep)
        return nextStep
    }

    private fun saveCurrentWaybillStep(step: WaybillStep) {
        sharedPref.saveWaybillStep(step)
    }
}