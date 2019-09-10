package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.tetron.waybill.model.waybill.CurrentWaybillStatus

@Dao
interface CurrentWaybillStatusDao {

    @Insert(onConflict = REPLACE)
    fun addCurrentWaybillStatus(currentWaybillStatus: CurrentWaybillStatus)

    @Query("SELECT * FROM current_waybill_status")
    suspend fun getCurrentWaybillStatus(): CurrentWaybillStatus

    @Update
    suspend fun updateCurrentWaybillStatus(customerDoc: CurrentWaybillStatus)

    @Query("UPDATE current_waybill_status SET medicalApprove = :medicalApprove")
    suspend fun receivedMedicalApprove(medicalApprove: Boolean = true)

    @Query("UPDATE current_waybill_status SET technicalApprove = :technicalApprove")
    suspend fun receivedTechnicalApprove(technicalApprove: Boolean = true)

    //Added only for manual test's help
    @Query("UPDATE current_waybill_status SET medicalApprove = :medicalApprove, technicalApprove = :technicalApprove")
    suspend fun resetApprovals(medicalApprove: Boolean = false, technicalApprove: Boolean = false)
}