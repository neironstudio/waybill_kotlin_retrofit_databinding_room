package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.R
import com.tetron.waybill.data.Result
import com.tetron.waybill.data.repository.CustomerDocRepository
import com.tetron.waybill.data.repository.EquipmentRepository
import com.tetron.waybill.model.model1c.CustomerDoc
import kotlinx.coroutines.launch
import kotlin.reflect.KMutableProperty1

class CustomerMarkViewModel : ViewModel() {

    private val customerDocRepository = CustomerDocRepository()
    private val equipmentRepository = EquipmentRepository()

    private val currentFormState = FormState()

    private val _currentCustomerDoc = MutableLiveData<CustomerDoc>()
    val currentCustomerDoc: LiveData<CustomerDoc> = _currentCustomerDoc

    private val _equipmentGroupName = MutableLiveData<String>()
    val equipmentGroupName: LiveData<String> = _equipmentGroupName

    private val _formState = MutableLiveData<FormState>()
    val formState: LiveData<FormState> = _formState

    init {
        // Set initial state
        viewModelScope.launch {
            val getCustomerDocsResult = customerDocRepository.getCurrentCustomerDoc()

            if (getCustomerDocsResult is Result.Success) {

                val data = getCustomerDocsResult.data

                _currentCustomerDoc.value = data

                data.waybillId?.let {

                    val getEquipmentGroupNameResult = equipmentRepository.getEquipmentGroupName(it)

                    if (getEquipmentGroupNameResult is Result.Success) {
                        _equipmentGroupName.value = getEquipmentGroupNameResult.data
                    }

                }

                validateAllFormData(data)
            }
        }
    }

    private fun <T> validateIsNotNullOrBlank(value: Any?, instance: T, prop: KMutableProperty1<T, Int?>) {

        val res = if (value == null || value.toString().isBlank()) {
            R.string.err_field_is_empty
        } else {
            null
        }

        prop.set(instance, res)

    }

    fun validateContactPersonWithNotifyObservers(text: String?) {

        validateIsNotNullOrBlank(text, currentFormState, FormState::contactPersonError)

        checkAllDataStateWithNotifyObservers()

        if (isDataReadyToSave(text, currentFormState.contactPersonError)) {
            updateContactPerson(currentCustomerDoc.value?.id as String, text as String)
        }

    }

    fun validateContactPersonPositionWithNotifyObservers(text: String?) {

        validateIsNotNullOrBlank(text, currentFormState, FormState::contactPersonPositionError)

        checkAllDataStateWithNotifyObservers()

        if (isDataReadyToSave(text, currentFormState.contactPersonPositionError)) {
            updateContactPersonPosition(currentCustomerDoc.value?.id as String, text as String)
        }

    }

    private fun <T> validateSingleOdometer(value: Int?, instance: T, prop: KMutableProperty1<T, Int?>) {

        var result: Int? = null

        if (value == null) {
            result = R.string.err_field_is_empty
            return prop.set(instance, result)
        }

        if (value !in 0..999999) {
            result = R.string.err_odometer_interval
            return prop.set(instance, result)
        }

        prop.set(instance, result)

    }

    private fun validateOdometers(valueArrival: Int?, valueDeparture: Int?) {

        validateSingleOdometer(valueArrival, currentFormState, FormState::odometerArrivalError)

        validateSingleOdometer(valueDeparture, currentFormState, FormState::odometerDepartureError)

        if (currentFormState.odometerArrivalError == null && currentFormState.odometerDepartureError == null) {

            if ((valueArrival as Int) <= (valueDeparture as Int)) {
                currentFormState.odometerArrivalError = null
                currentFormState.odometerDepartureError = null
            } else {
                currentFormState.odometerArrivalError = R.string.err_odometer_arrival_more_departure
                currentFormState.odometerDepartureError = R.string.err_odometer_departure_less_arrival
            }
        }

    }

    fun validateOdometersWithNotifyObservers(valueArrival: Int?, valueDeparture: Int?) {

        validateOdometers(valueArrival, valueDeparture)

        checkAllDataStateWithNotifyObservers()

        if (isDataReadyToSave(valueArrival, currentFormState.odometerArrivalError)
            && isDataReadyToSave(valueDeparture, currentFormState.odometerDepartureError)
        ) {
            updateOdometers(currentCustomerDoc.value?.id as String, valueArrival as Int, valueDeparture as Int)
        }

    }

    fun validateMachineKilometersWithNotifyObservers(value: Int?) {

        validateIsNotNullOrBlank(value, currentFormState, FormState::machineKilometersError)

        checkAllDataStateWithNotifyObservers()

        if (isDataReadyToSave(value, currentFormState.machineKilometersError)) {
            updateMachineKilometers(currentCustomerDoc.value?.id as String, value as Int)
        }

    }

    private fun <T> validateFormattedTime(text: String?, instance: T, prop: KMutableProperty1<T, Int?>) {

        var result: Int? = null

        if (text.isNullOrBlank()) {
            result = R.string.err_field_is_empty
            return prop.set(instance, result)
        }

        if (!text.contains(':')) {
            result = R.string.err_time_format
            return prop.set(instance, result)
        }

        val hours = getHoursFromFormattedTime(text)
        val minutes = getMinutesFromFormattedTime(text)

        if (hours == null || minutes == null) {
            result = R.string.err_time_format
            return prop.set(instance, result)
        }

        if (minutes !in 0..59) {
            result = R.string.err_invalid_minutes
            return prop.set(instance, result)
        }

        prop.set(instance, result)

    }

    fun validateTravelTimeWithNotifyObservers(text: String?) {

        validateFormattedTime(text, currentFormState, FormState::travelTimeError)

        checkAllDataStateWithNotifyObservers()

        if (isDataReadyToSave(text, currentFormState.travelTimeError)) {
            updateTimeTravel(currentCustomerDoc.value?.id as String, formattedTimeToInt(text as String))
        }

    }

    fun validateAdditionalEquipmentWorkingTimeWithNotifyObservers(text: String?) {

        validateFormattedTime(text, currentFormState, FormState::additionalEquipmentWorkingTimeError)

        checkAllDataStateWithNotifyObservers()

        if (isDataReadyToSave(text, currentFormState.additionalEquipmentWorkingTimeError)
        ) {
            updateAdditionalEquipmentWorkingTime(
                currentCustomerDoc.value?.id as String, formattedTimeToInt(text as String)
            )
        }

    }

    private fun getMinutesFromFormattedTime(formattedTime: String) = formattedTime.substringAfter(':').toIntOrNull()

    private fun getHoursFromFormattedTime(formattedTime: String) = formattedTime.substringBefore(':').toIntOrNull()

    private fun formattedTimeToInt(formattedTime: String) =
        (getHoursFromFormattedTime(formattedTime) as Int) * 60 + getMinutesFromFormattedTime(formattedTime) as Int

    private fun isDataReadyToSave(data: Any?, errorField: Int?): Boolean {
        return (currentCustomerDoc.value?.id != null && data != null && errorField == null)
    }

    private fun validateAllFormData(customerDoc: CustomerDoc) {
        customerDoc.let {

            validateIsNotNullOrBlank(it.contactPerson, currentFormState, FormState::contactPersonError)

            validateIsNotNullOrBlank(it.contactPersonPosition, currentFormState, FormState::contactPersonPositionError)

            validateOdometers(it.odometerStart, it.odometerFinish)

            validateIsNotNullOrBlank(it.executedDistance, currentFormState, FormState::machineKilometersError)

            validateIsNotNullOrBlank(it.executedTime, currentFormState, FormState::travelTimeError)

            validateIsNotNullOrBlank(
                it.executedEquipmentTime,
                currentFormState,
                FormState::additionalEquipmentWorkingTimeError
            )

            checkAllDataStateWithNotifyObservers()

        }
    }

    private fun isAllDataValid() {
        currentFormState.isDataValid = currentFormState.checkIsDataValid()
    }

    private fun notifyFormStateObservers() {
        _formState.value = currentFormState
    }

    private fun checkAllDataStateWithNotifyObservers() {
        isAllDataValid()
        notifyFormStateObservers()
    }

    private fun updateContactPerson(id: String, contactPerson: String) {
        viewModelScope.launch {
            customerDocRepository.updateContactPerson(id, contactPerson)
        }
    }

    private fun updateContactPersonPosition(id: String, contactPersonPosition: String) {
        viewModelScope.launch {
            customerDocRepository.updateContactPersonPosition(id, contactPersonPosition)
        }
    }

    private fun updateOdometers(id: String, odometerArrival: Int, odometerDeparture: Int) {
        viewModelScope.launch {
            customerDocRepository.updateOdometers(id, odometerArrival, odometerDeparture)
        }
    }

    private fun updateMachineKilometers(id: String, machineKilometers: Int) {
        viewModelScope.launch {
            customerDocRepository.updateMachineKilometers(id, machineKilometers)
        }
    }

    private fun updateTimeTravel(id: String, timeTravel: Int) {
        viewModelScope.launch {
            customerDocRepository.updateTimeTravel(id, timeTravel)
        }
    }

    private fun updateAdditionalEquipmentWorkingTime(id: String, additionalEquipmentWorkingTime: Int) {
        viewModelScope.launch {
            customerDocRepository.updateAdditionalEquipmentWorkingTime(id, additionalEquipmentWorkingTime)
        }
    }

    data class FormState(
        var contactPersonError: Int? = null,
        var contactPersonPositionError: Int? = null,
        var odometerArrivalError: Int? = null,
        var odometerDepartureError: Int? = null,
        var machineKilometersError: Int? = null,
        var travelTimeError: Int? = null,
        var additionalEquipmentWorkingTimeError: Int? = null,
        var isDataValid: Boolean = false
    ) {
        private fun <T> allElementsIsNull(vararg elements: T) = elements.all { it == null }

        fun checkIsDataValid(): Boolean {
            return allElementsIsNull(
                contactPersonError,
                contactPersonPositionError,
                odometerArrivalError,
                odometerDepartureError,
                machineKilometersError,
                travelTimeError,
                additionalEquipmentWorkingTimeError
            )
        }
    }

}