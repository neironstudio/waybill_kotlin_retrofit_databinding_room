package com.tetron.waybill.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetron.waybill.R
import com.tetron.waybill.data.WorkPauseReason
import com.tetron.waybill.data.repository.CurrentCustomerDocStatusRepository
import com.tetron.waybill.model.customerdoc.WorkPauseReasonState
import kotlinx.coroutines.launch

class WorkPauseReasonSelectionViewModel : ViewModel() {

    private val currentCustomerDocStatusRepository = CurrentCustomerDocStatusRepository()

    private val _workPauseReasonState = MutableLiveData<WorkPauseReasonState>()
    val workPauseReasonState: LiveData<WorkPauseReasonState> = _workPauseReasonState

    private val _pauseReason = MutableLiveData<WorkPauseReason>()
    val pauseReason: LiveData<WorkPauseReason> = _pauseReason

    private val _reasonCommentError = MutableLiveData<Int?>()
    val reasonCommentError: LiveData<Int?> = _reasonCommentError

    init {
        checkedReasonLunch()
    }

    fun checkedReasonLunch() {
        _workPauseReasonState.value = WorkPauseReasonState(lunch = true)
        _pauseReason.value = WorkPauseReason.LUNCH
    }

    fun checkedReasonCustomer() {
        _workPauseReasonState.value = WorkPauseReasonState(customer = true)
        _pauseReason.value = WorkPauseReason.CUSTOMER
    }

    fun checkedReasonDriver(text: String) {
        validateReasonComment(text)
        _pauseReason.value = WorkPauseReason.DRIVER
    }

    fun validateReasonComment(text: String) {
        if (text.isBlank()) {
            _reasonCommentError.value = R.string.err_required_reason_comment
            _workPauseReasonState.value =
                WorkPauseReasonState(driver = true, commentIsValid = false)
        } else {
            _reasonCommentError.value = null
            _workPauseReasonState.value = WorkPauseReasonState(driver = true, commentIsValid = true)
        }
    }

    fun pauseWork(pauseReasonComment: String) {

        _pauseReason.value?.let {

            val currentWorkPauseReason = it.name

            viewModelScope.launch {

                if (currentWorkPauseReason == WorkPauseReason.DRIVER.name) {
                    currentCustomerDocStatusRepository.pauseWorkWithComment(
                        currentWorkPauseReason,
                        pauseReasonComment
                    )
                } else currentCustomerDocStatusRepository.pauseWork(currentWorkPauseReason)

            }

        }

    }


}