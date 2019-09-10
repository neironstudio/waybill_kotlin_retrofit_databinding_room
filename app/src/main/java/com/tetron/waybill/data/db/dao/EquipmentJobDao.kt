package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tetron.waybill.model.model1c.EquipmentJob

@Dao
interface EquipmentJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEquipmentJobs(equipmentJobs: List<EquipmentJob>)

    @Query("SELECT * FROM equipment_job WHERE waybillId = :waybillId")
    suspend fun getEquipmentJob(waybillId: String): EquipmentJob
}