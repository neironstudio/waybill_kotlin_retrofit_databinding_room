package com.tetron.waybill.model.customerdoc


data class WorkPauseReasonState(
    val lunch: Boolean = false,
    val customer: Boolean = false,
    val driver: Boolean = false,
    val comment: String = "",
    val commentIsValid: Boolean = true
)