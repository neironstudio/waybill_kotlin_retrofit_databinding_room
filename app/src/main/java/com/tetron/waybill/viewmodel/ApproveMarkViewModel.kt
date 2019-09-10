package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.R
import com.tetron.waybill.data.ApproveMark
import com.tetron.waybill.data.Result
import com.tetron.waybill.data.repository.ApprovalRepository
import com.tetron.waybill.model.waybill.PinState
import com.tetron.waybill.util.Event
import kotlinx.coroutines.launch
import org.koin.ext.isInt

class ApproveMarkViewModel : ViewModel() {

    private val approvalRepository = ApprovalRepository()

    private val _approveMarkBy = MutableLiveData<ApproveMark>()
    val approveMarkBy: LiveData<ApproveMark> = _approveMarkBy

    private val _nameOfApprovingEmployee = MutableLiveData<String>()
    val nameOfApprovingEmployee: LiveData<String> = _nameOfApprovingEmployee

    private val _pinState = MutableLiveData<PinState>()
    val pinState: LiveData<PinState> = _pinState

    private val _closedApproveMarkFragmentEvent = MutableLiveData<Event<Unit>>()
    val closedApproveMarkFragmentEvent: LiveData<Event<Unit>> = _closedApproveMarkFragmentEvent

    fun setup(mark: ApproveMark) {
        _approveMarkBy.value = mark
    }

    fun pinChanged(pin: String) {
        if (!isPinValid(pin)) {
            _pinState.value = PinState(pinError = R.string.err_invalid_pin_mask)
        } else {
            _pinState.value = PinState(isPinValid = true)
        }
    }

    private fun isPinValid(pin: String): Boolean {
        return pin.isNotEmpty() && (pin.length == 4) && pin.isInt()
    }

    fun updateApprovalStatus() {
        when (_approveMarkBy.value as ApproveMark) {
            ApproveMark.MEDICAL_APPROVE -> {
                viewModelScope.launch {
                    approvalRepository.receivedMedicalApprove()
                    _closedApproveMarkFragmentEvent.value = Event(Unit)
                }
            }
            ApproveMark.TECHNICAL_APPROVE -> {
                viewModelScope.launch {
                    approvalRepository.receivedTechnicalApprove()
                    _closedApproveMarkFragmentEvent.value = Event(Unit)
                }
            }
        }
    }

    fun checkPin(pin: String) {

        viewModelScope.launch {

            _pinState.value = PinState(isPinChecking = true)

            val checkResult = approvalRepository.checkPin(pin)

            if (checkResult is Result.Success) {

                _pinState.value = PinState(isPinChecking = false, isPinChecked = true)

                when (_approveMarkBy.value as ApproveMark) {
                    ApproveMark.MEDICAL_APPROVE -> {
                        getDoctorName()
                    }
                    ApproveMark.TECHNICAL_APPROVE -> {
                        getTechnicianName()
                    }
                }

            } else {
                _pinState.value = PinState(isPinChecking = false, pinError = R.string.err_invalid_pin)
            }

        }

    }

    private fun getDoctorName() {
        viewModelScope.launch {
            val getDoctorNameStatusResult = approvalRepository.getDoctorName()

            if (getDoctorNameStatusResult is Result.Success) {
                _nameOfApprovingEmployee.value = getDoctorNameStatusResult.data
            }
        }
    }

    private fun getTechnicianName() {
        viewModelScope.launch {
            val getTechnicianNameStatusResult = approvalRepository.getTechnicianName()

            if (getTechnicianNameStatusResult is Result.Success) {
                _nameOfApprovingEmployee.value = getTechnicianNameStatusResult.data
            }
        }
    }

}