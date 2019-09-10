package com.tetron.waybill.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tetron.waybill.model.customerdoc.CurrentCustomerDocStatus

@Dao
interface CurrentCustomerDocStatusDao {

    @Query("SELECT * FROM current_customer_doc_status")
    fun getCurrentCustomerDocStatus(): LiveData<CurrentCustomerDocStatus>

    @Query("SELECT pauseReason FROM current_customer_doc_status")
    fun getWorkPauseReason(): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrentCustomerDocStatus(currentCustomerDocStatus: CurrentCustomerDocStatus)

    @Query("UPDATE current_customer_doc_status SET isWork = :isWork, pauseReason = :pauseReason, pauseComment = :pauseComment")
    suspend fun pauseWork(
        pauseReason: String,
        pauseComment: String = "",
        isWork: Boolean = false
    )

    @Query("UPDATE current_customer_doc_status SET isWork = :isWork")
    suspend fun resumeWork(isWork: Boolean = true)

}