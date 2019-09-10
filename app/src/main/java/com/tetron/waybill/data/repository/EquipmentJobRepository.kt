package com.tetron.waybill.data.repository

import com.tetron.waybill.data.Result
import com.tetron.waybill.data.db.dao.EquipmentJobDao
import com.tetron.waybill.model.model1c.EquipmentJob
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class EquipmentJobRepository : KoinComponent {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val equipmentJobDao: EquipmentJobDao by inject()

    suspend fun getEquipmentJob(waybillId: String): Result<EquipmentJob> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(equipmentJobDao.getEquipmentJob(waybillId))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

}