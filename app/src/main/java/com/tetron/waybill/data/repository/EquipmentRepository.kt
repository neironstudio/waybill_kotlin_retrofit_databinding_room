package com.tetron.waybill.data.repository

import com.tetron.waybill.data.Result
import com.tetron.waybill.data.db.dao.EquipmentDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class EquipmentRepository : KoinComponent {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val equipmentDao: EquipmentDao by inject()

    suspend fun getEquipmentGroupName(waybillId: String): Result<String> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(equipmentDao.getEquipmentGroupName(waybillId))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

}