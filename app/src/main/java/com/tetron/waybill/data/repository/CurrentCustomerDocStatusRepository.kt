package com.tetron.waybill.data.repository

import androidx.lifecycle.LiveData
import com.tetron.waybill.data.db.dao.CurrentCustomerDocStatusDao
import com.tetron.waybill.model.customerdoc.CurrentCustomerDocStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject


class CurrentCustomerDocStatusRepository : KoinComponent {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val currentCustomerDocStatusDao: CurrentCustomerDocStatusDao by inject()

    fun getCurrentCustomerDocStatus(): LiveData<CurrentCustomerDocStatus> =
        currentCustomerDocStatusDao.getCurrentCustomerDocStatus()

    fun getWorkPauseReason(): LiveData<String> = currentCustomerDocStatusDao.getWorkPauseReason()

    suspend fun addCurrentCustomerDocStatus(currentStatus: CurrentCustomerDocStatus) =
        withContext(ioDispatcher) {
            currentCustomerDocStatusDao.addCurrentCustomerDocStatus(currentStatus)
        }

    suspend fun pauseWork(stoppingReason: String) = withContext(ioDispatcher) {
        currentCustomerDocStatusDao.pauseWork(stoppingReason)
    }

    suspend fun pauseWorkWithComment(stoppingReason: String, stoppingComment: String) =
        withContext(ioDispatcher) {
            currentCustomerDocStatusDao.pauseWork(stoppingReason, stoppingComment)
        }

    suspend fun resumeWork() = withContext(ioDispatcher) {
        currentCustomerDocStatusDao.resumeWork()
    }
}