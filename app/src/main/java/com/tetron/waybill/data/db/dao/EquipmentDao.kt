package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tetron.waybill.model.model1c.Equipment


@Dao
interface EquipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEquipments(equipments: List<Equipment>)

    @Query("SELECT equipmentGroupName FROM equipment WHERE waybillId = :waybillId")
    suspend fun getEquipmentGroupName(waybillId: String): String

}