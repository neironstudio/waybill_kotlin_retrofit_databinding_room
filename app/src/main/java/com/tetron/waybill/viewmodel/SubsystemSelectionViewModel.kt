package com.tetron.waybill.viewmodel

import androidx.lifecycle.ViewModel
import com.tetron.waybill.data.repository.AccountRepository

class SubsystemSelectionViewModel : ViewModel() {

    private val accountRepository = AccountRepository()

    fun logout() = accountRepository.logout()

}