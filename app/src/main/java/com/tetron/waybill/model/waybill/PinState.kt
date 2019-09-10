package com.tetron.waybill.model.waybill

/**
 * Data validation state of the PIN.
 */
data class PinState(
    val pinError: Int? = null,
    val isPinValid: Boolean = false,
    val isPinChecking: Boolean = false,
    val isPinChecked: Boolean = false
)