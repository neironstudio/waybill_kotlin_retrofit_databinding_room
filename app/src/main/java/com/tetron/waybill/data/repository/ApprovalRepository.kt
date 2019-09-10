package com.tetron.waybill.data.repository

import com.tetron.waybill.data.Result
import com.tetron.waybill.data.db.dao.CurrentWaybillStatusDao
import com.tetron.waybill.data.db.dao.PermissionDao
import com.tetron.waybill.data.network.testRequestApi
import com.tetron.waybill.model.testRequest.Register
import com.tetron.waybill.model.waybill.CurrentWaybillStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call

class ApprovalRepository : KoinComponent {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val networkDispatcher: CoroutineDispatcher = Dispatchers.Default

    private val currentWaybillStatusDao: CurrentWaybillStatusDao by inject()
    private val permissionDao: PermissionDao by inject()

    suspend fun getCurrentWaybillStatus(): Result<CurrentWaybillStatus> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(currentWaybillStatusDao.getCurrentWaybillStatus())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    suspend fun getDoctorName(): Result<String> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(permissionDao.getDoctorName())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getTechnicianName(): Result<String> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(permissionDao.getTechnicianName())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun receivedMedicalApprove() = withContext(ioDispatcher) {
        currentWaybillStatusDao.receivedMedicalApprove()
    }

    suspend fun receivedTechnicalApprove() = withContext(ioDispatcher) {
        currentWaybillStatusDao.receivedTechnicalApprove()
    }

    suspend fun resetApprovals() = withContext(ioDispatcher) {
        currentWaybillStatusDao.resetApprovals()
    }

    suspend fun checkPin(pin: String): Result<Any> = withContext(networkDispatcher) {

        val body = Register("eve.holt@reqres.in", pin)
        val request: Call<Any> = testRequestApi.checkPin(body)

        return@withContext try {
            Result.Success(request.execute())
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

}