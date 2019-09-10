package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.data.ApproveMark
import com.tetron.waybill.data.Result
import com.tetron.waybill.data.WaybillStep
import com.tetron.waybill.data.repository.ApprovalRepository
import com.tetron.waybill.data.repository.CurrentCustomerDocStatusRepository
import com.tetron.waybill.data.repository.CurrentWaybillRepository
import com.tetron.waybill.model.customerdoc.CurrentCustomerDocStatus
import com.tetron.waybill.model.waybill.CurrentWaybillStatus
import com.tetron.waybill.util.Event
import com.tetron.waybill.util.SharedPreferenceHelper
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class CurrentWaybillViewModel : ViewModel(), KoinComponent {

    private val sharedPreferenceHelper: SharedPreferenceHelper by inject()

    private val currentWaybillRepository = CurrentWaybillRepository()
    private val approvalRepository = ApprovalRepository()
    private val currentCustomerDocStatusRepository = CurrentCustomerDocStatusRepository()

    private val _currentWaybillStep = MutableLiveData<WaybillStep>()
    val currentWaybillStep: LiveData<WaybillStep> = _currentWaybillStep

    private val _currentWaybillStatus = MutableLiveData<CurrentWaybillStatus>()
    val currentWaybillStatus: LiveData<CurrentWaybillStatus> = _currentWaybillStatus

    private val _currentCustomerDocStatus: LiveData<CurrentCustomerDocStatus> =
        currentCustomerDocStatusRepository.getCurrentCustomerDocStatus()
    val currentCustomerDocStatus: LiveData<CurrentCustomerDocStatus> = _currentCustomerDocStatus

    private val _switchWaybillStepEvent = MutableLiveData<Event<Unit>>()
    val switchWaybillStepEvent: LiveData<Event<Unit>> = _switchWaybillStepEvent

    private val _showApprovalSelectionFragmentEvent = MutableLiveData<Event<Unit>>()
    val showApprovalSelectionFragmentEvent: LiveData<Event<Unit>> =
        _showApprovalSelectionFragmentEvent

    private val _showApproveMarkFragmentEvent = MutableLiveData<Event<ApproveMark>>()
    val showApproveMarkFragmentEvent: LiveData<Event<ApproveMark>> = _showApproveMarkFragmentEvent

    private val _showWorkOnCustomerDocFragmentEvent = MutableLiveData<Event<Unit>>()
    val showWorkOnCustomerDocFragmentEvent: LiveData<Event<Unit>> =
        _showWorkOnCustomerDocFragmentEvent

    private val _showStopReasonSelectionFragmentEvent = MutableLiveData<Event<Unit>>()
    val showStopReasonSelectionFragmentEvent: LiveData<Event<Unit>> =
        _showStopReasonSelectionFragmentEvent

    private val _showWorkStoppedFragmentEvent = MutableLiveData<Event<Unit>>()
    val showWorkStoppedFragmentEvent: LiveData<Event<Unit>> = _showWorkStoppedFragmentEvent

    private var permissionLocation: Boolean = false

    var errorCallBack: MutableLiveData<String> = MutableLiveData()
    var stateRunServiceLocation: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _currentWaybillStep.value = currentWaybillRepository.getCurrentWaybillStep()
        stateRunServiceLocation.value = sharedPreferenceHelper.getLocationServiceStatus()
    }

    fun switchWaybillStep() {
        _currentWaybillStep.value =
            currentWaybillRepository.switchWaybillStep(_currentWaybillStep.value ?: return)
    }

    fun setPermissionLocation(value: Boolean) {
        permissionLocation = value
        if (!permissionLocation)
            errorCallBack.value = "error permission"
    }


    fun switchIfPermission() {
        if (permissionLocation) {
            switchWaybillStep()
            serviceLocationRun()
        } else setPermissionLocation(false)
    }

    private fun serviceLocationRun() {
        stateRunServiceLocation.value = true
        sharedPreferenceHelper.setLocationServiceStatus(true)
    }

    fun serviceLocationStop() {
        stateRunServiceLocation.value = false
        sharedPreferenceHelper.setLocationServiceStatus(false)
    }

    fun getCurrentWaybillStep(): WaybillStep {
        return currentWaybillRepository.getCurrentWaybillStep()
    }

    fun updateCurrentStatus() {
        viewModelScope.launch {
            val getCurrentWaybillStatusResult = approvalRepository.getCurrentWaybillStatus()

            if (getCurrentWaybillStatusResult is Result.Success) {
                _currentWaybillStatus.value = getCurrentWaybillStatusResult.data
            }
        }
    }

    fun checkApprovalsStatus() {
        val status = _currentWaybillStatus.value
        if (status != null) showRightApprovalFragment(status) else updateCurrentStatus()

    }

    private fun showRightApprovalFragment(status: CurrentWaybillStatus) {
        if (status.medicalApprove && status.technicalApprove) {
            _switchWaybillStepEvent.value = Event(Unit)
        } else {
            if (!status.medicalApprove && !status.technicalApprove) {
                _showApprovalSelectionFragmentEvent.value = Event(Unit)
            } else if (!status.medicalApprove) {
                showMedicalApproveFragment()
            } else showTechnicalApproveFragment()
        }
    }

    fun showMedicalApproveFragment() {
        _showApproveMarkFragmentEvent.value = Event(ApproveMark.MEDICAL_APPROVE)
    }

    fun showTechnicalApproveFragment() {
        _showApproveMarkFragmentEvent.value = Event(ApproveMark.TECHNICAL_APPROVE)
    }

    fun resetApprovals() {
        viewModelScope.launch {
            approvalRepository.resetApprovals()
        }
    }

    fun checkWorkStatus() {
        _currentCustomerDocStatus.value?.let {
            if (_currentWaybillStep.value == WaybillStep.WORK_ON_CUSTOMER_DOC) {
                if (it.isWork) {
                    showWorkOnCustomerDocFragment()
                } else showWorkStoppedFragment()
            }
        }
    }

    private fun showWorkOnCustomerDocFragment() {
        _showWorkOnCustomerDocFragmentEvent.value = Event(Unit)
    }

    fun showStopReasonSelectionFragment() {
        _showStopReasonSelectionFragmentEvent.value = Event(Unit)
    }

    fun showWorkStoppedFragment() {
        _showWorkStoppedFragmentEvent.value = Event(Unit)
    }

}